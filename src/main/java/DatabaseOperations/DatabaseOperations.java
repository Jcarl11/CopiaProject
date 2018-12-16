
package DatabaseOperations;

import Entities.NotesEntity;
import MiscellaneousClasses.MyUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseOperations 
{
    AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
    List<Future<Response>> responses;
    public ArrayList<String> uploadFile(ArrayList<File> files)
    {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        ArrayList<String> jsonResponses = new ArrayList<>();
        try {
            
            List<Callable<Response>> callables = new ArrayList<>();
            for (final File file : files) {
                try {
                    final byte[] data = Files.readAllBytes(file.toPath());
                    Callable<Response> callable = new Callable<Response>() {
                        @Override
                        public Response call() throws Exception {
                            ListenableFuture<Response> lf = asyncHttpClient.preparePost(MyUtils.URL_FILE + file.getName())
                                    .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                                    .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                                    .setHeader("Content-type", "application/octet-stream")
                                    .setBody(data)
                                    .execute(new AsyncCompletionHandler<Response>() {
                                        @Override
                                        public Response onCompleted(Response rspns) throws Exception {
                                            return rspns;
                                        }
                                    });
                            return lf.get();
                        }
                    };
                    callables.add(callable);
                } catch (IOException ex) {
                    Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            responses = executor.invokeAll(callables);
            
            for (Future<Response> futures : responses) {
                jsonResponses.add(futures.get().getResponseBody());
            }
        } 
        catch (InterruptedException interruptedException) {interruptedException.printStackTrace();} 
        catch (ExecutionException executionException) {executionException.printStackTrace();}
        finally
        {
            try 
            {
                executor.shutdown();
                if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {executor.shutdownNow();}
            } catch (InterruptedException ex) {Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);}
        }
        return jsonResponses;
    }
    public HashMap<String, Object> uploadRecord(ArrayList<String> urls, JSONObject data, String searchClass)
    {
        HashMap<String, Object> results = new HashMap<>();
        try {
            ListenableFuture<Response> lf = asyncHttpClient.preparePost(MyUtils.URL + searchClass)
                    .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                    .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                    .setHeader("Content-type", "application/json")
                    .setBody(data.toString())
                    .execute(new AsyncCompletionHandler<Response>() {
                        @Override
                        public Response onCompleted(Response rspns) throws Exception {
                            System.out.println(rspns.getResponseBody());
                            return rspns;
                        }
                    });
            JSONObject response = new JSONObject(lf.get().getResponseBody());
            results.put("data", urls);
            results.put("objectId", response.getString("objectId"));
        } catch (JSONException | InterruptedException | ExecutionException jSONException) {
        }
        return results;
    }
    public HashMap<String, String> associateFile(HashMap<String, Object> data, final String pointerClass, final String pointer)
    {
        HashMap<String, String> result = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            List<Callable<Response>> callables = new ArrayList<>();
            ArrayList<String> url_and_names = (ArrayList<String>) data.get("data");
            final String objectId = (String) data.get("objectId");
            for (final String jsonData : url_and_names) {
                Callable<Response> callable = new Callable<Response>() {
                    JSONObject json = new JSONObject(jsonData);
                    String url = json.getString("url");
                    String name = json.getString("name");
                    String fileExtension = null;

                    @Override
                    public Response call() throws Exception {
                        if (FilenameUtils.getExtension(name).equalsIgnoreCase("pdf")) {
                            fileExtension = "PDFFiles";
                        } else {
                            fileExtension = "Images";
                        }
                        ListenableFuture<Response> lf = asyncHttpClient.preparePost(MyUtils.URL + fileExtension)
                                .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                                .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                                .setHeader("Content-type", "application/json")
                                .setBody(MyUtils.getInstance().buildBody(url, name, name.substring(name.indexOf("_") + 1), pointerClass, pointer, objectId))
                                .execute(new AsyncCompletionHandler<Response>() {
                                    @Override
                                    public Response onCompleted(Response rspns) throws Exception {
                                        return rspns;
                                    }
                                });
                        return lf.get();
                    }
                };
                callables.add(callable);
            }
            List<Future<Response>> responses = executor.invokeAll(callables);
            executor.shutdown();
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException interruptedException) {interruptedException.printStackTrace();}
        finally
        {
            try 
            {
                executor.shutdown();
                if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {executor.shutdownNow();}
            } catch (InterruptedException ex) {Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);}
        }
        result.put("objectId", (String)data.get("objectId"));
        result.put("PointerClass", pointerClass);
        result.put("Pointer", pointer);
        return result;
    }
    public String associateNotes(HashMap<String, String> data, ArrayList<NotesEntity> notesList)
    {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Callable<Response>> callables = new ArrayList<>();
        String objectId = data.get("objectId");
        String pointerClass = data.get("PointerClass");
        String pointer = data.get("Pointer");
        for(final NotesEntity notes : notesList)
        {
            Callable<Response> callable = new Callable<Response>() 
            {
                @Override
                public Response call() throws Exception 
                {
                    ListenableFuture<Response> lf = asyncHttpClient.preparePost(MyUtils.URL + "Notes")
                                .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                                .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                                .setHeader("Content-type", "application/json")
                                .setBody(MyUtils.getInstance().buildBodyNoteClass(notes.getRemarks(), pointerClass, pointer, objectId))
                                .execute(new AsyncCompletionHandler<Response>() 
                                {
                                    @Override
                                    public Response onCompleted(Response rspns) throws Exception 
                                    {
                                        return rspns;
                                    }
                                });
                    return lf.get();
                }
            };
            callables.add(callable);
        }
        try {
            responses = executor.invokeAll(callables);
        } catch (InterruptedException ex) {Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);}
        finally{
            try {
                executor.shutdown();
                if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {executor.shutdownNow();}
            } catch (InterruptedException interruptedException) {interruptedException.printStackTrace();}
        }
        return "Successful";
    }

}
