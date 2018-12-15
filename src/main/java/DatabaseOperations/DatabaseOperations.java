
package DatabaseOperations;

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
    
    public ArrayList<String> uploadFile(ArrayList<File> files)
    {
        ArrayList<String> jsonResponses = new ArrayList<>();
        try {
            ExecutorService executor = Executors.newFixedThreadPool(5);
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
            List<Future<Response>> responses = executor.invokeAll(callables);
            executor.shutdown();
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
            for (Future<Response> futures : responses) {
                jsonResponses.add(futures.get().getResponseBody());
            }
        } 
        catch (InterruptedException interruptedException) {interruptedException.printStackTrace();} 
        catch (ExecutionException executionException) {executionException.printStackTrace();}
        return jsonResponses;
    }
    public HashMap<String, Object> uploadRecord(ArrayList<String> urls)
    {
        HashMap<String, Object> results = new HashMap<>();
        try {
            JSONObject json = new JSONObject();
            json.put("Representative", "JUAN DELACRUZ");
            json.put("Position", "VP");
            json.put("Company", "NO COMPANY");
            json.put("Specialization", "JAVA");
            json.put("Industry", "CONSTRUCTION");
            json.put("Classification", "AAA");
            ListenableFuture<Response> lf = asyncHttpClient.preparePost(MyUtils.URL + "Contractors")
                    .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                    .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                    .setHeader("Content-type", "application/json")
                    .setBody(json.toString())
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
}
