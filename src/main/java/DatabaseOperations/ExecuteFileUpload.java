
package DatabaseOperations;

import DatabaseOperations.AlternateUpload;
import Entities.NotesEntity;
import MiscellaneousClasses.MyUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.json.JSONObject;

public class ExecuteFileUpload 
{
    AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
    ExecutorService executor = Executors.newFixedThreadPool(5);
    List<Callable<Response>> callables;
    List<Future<Response>> responses;
    private ExecuteFileUpload(){}
    private static ExecuteFileUpload instance = null;
    public static ExecuteFileUpload getInstance()
    {
        if(instance == null)
            instance = new ExecuteFileUpload();
        return instance;
    }
    
    public List<Future<Response>> execute(ArrayList<File> files)
    {
        callables = new ArrayList<Callable<Response>>();
        for(final File file : files)
        {
            try {
                final byte[] data = Files.readAllBytes(file.toPath());
                Callable<Response> callable = new Callable<Response>() 
                {
                    @Override
                    public Response call() throws Exception 
                    {
                        ListenableFuture<Response> lf = asyncHttpClient.preparePost(MyUtils.URL_FILE + file.getName())
                                .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                                .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                                .setHeader("Content-type", "application/octet-stream")
                                .setBody(data)
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
            } catch (IOException ex) {
                Logger.getLogger(AlternateUpload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            responses = executor.invokeAll(callables);
        } catch (InterruptedException ex) {
            Logger.getLogger(AlternateUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responses;      
    }
    public List<Future<Response>> associateFile(ArrayList<String> data, final String searchClass, final String objectId, final String pointer)
    {
        callables = new ArrayList<Callable<Response>>();
        
        for(String json : data)
        {
            JSONObject jsonData = new JSONObject(json);
            final String url = jsonData.getString("url");
            final String name = jsonData.getString("name");
            Callable<Response> callable = new Callable<Response>() 
            {
                @Override
                public Response call() throws Exception 
                {
                    String fileClass = new String();
                    if(MyUtils.getInstance().getFileType(name).equalsIgnoreCase("pdf"))
                    {
                        fileClass = "PDFFiles";
                    }
                    else if(MyUtils.getInstance().getFileType(name).equalsIgnoreCase("Image"))
                    {
                        fileClass = "Images";
                    }
                    ListenableFuture<Response> lf = asyncHttpClient.preparePost(MyUtils.URL + fileClass)
                    .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                    .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                    .setHeader("Content-type", "application/json")
                    .setBody(buildBody(url, name, searchClass, objectId, name.substring(name.indexOf("_") + 1), pointer))
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
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecuteFileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responses;
    }
    
    public List<Future<Response>> associateNotes(final String objectId, final String pointer, ArrayList<NotesEntity> notesList, final String searchClass)
    {
        callables = new ArrayList<>();
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
                            .setBody(buildBodyNoteClass(notes.getRemarks(), searchClass, pointer, objectId))
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
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecuteFileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responses;
    }
    public void shutdownExecutor()
    {
        try {
            executor.shutdown();
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
    private String buildBody(String url, String name, String searchClass, String objectId, String fileName, String pointername)
    {
        JSONObject json = new JSONObject();
        JSONObject file = new JSONObject();
        JSONObject pointer = new JSONObject();
        file.put("__type", "File");
        file.put("url", url);
        file.put("name", name);
        pointer.put("__type", "Pointer");
        pointer.put("className", searchClass);
        pointer.put("objectId", objectId);
        json.put("Name", fileName);
        json.put("Files", file);
        json.put(pointername, pointer);
        return json.toString();
    }
    private String buildBodyNoteClass(String remark, String searchClass, String pointerName, String objectId)
    {
        JSONObject json = new JSONObject();
        JSONObject pointer = new JSONObject();
        pointer.put("__type", "Pointer");
        pointer.put("className", searchClass);
        pointer.put("objectId", objectId);
        json.put("Remark", remark.trim().toUpperCase());
        json.put(pointerName, pointer);
        return json.toString();
    }
}
