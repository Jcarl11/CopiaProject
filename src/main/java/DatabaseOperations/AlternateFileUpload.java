package DatabaseOperations;

import java.util.concurrent.Callable;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;

public class AlternateFileUpload implements Callable<Response>
{
    String APP_ID = "4GCD5XK7GucFbTKnJa0fonFEBlAh3azBS3Gh0NNd";
    String REST_API_KEY = "RYznH1yrJ3DVly2f02aEMkZJNwmPVdDBUQyqRT6H";
    String URL = "https://concipiotektura.back4app.io/classes/Contractors";
    String URL_FILE = "https://concipiotektura.back4app.io/files/";
    AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
    byte[] data;
    String filename;
    
    @Override
    public Response call() throws Exception 
    {
        ListenableFuture<Response> lf = asyncHttpClient.preparePost(URL_FILE + getFilename())
                                .addHeader("X-Parse-Application-Id", APP_ID)
                                .setHeader("X-Parse-REST-API-Key", REST_API_KEY)
                                .setHeader("Content-type", "application/octet-stream")
                                .setBody(getData())
                                .execute(new AsyncCompletionHandler<Response>() 
                                {
                                    @Override
                                    public Response onCompleted(Response rspns) throws Exception 
                                    {
                                        System.out.println("Done");
                                        return rspns;
                                    }
                            });
        return lf.get();
    }
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
