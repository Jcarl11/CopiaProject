package DatabaseOperations;

import io.netty.handler.codec.http.HttpHeaders;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.http.entity.StringEntity;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.asynchttpclient.handler.ProgressAsyncHandler;
import org.json.JSONObject;

public class UploadFile 
{
    String APP_ID = "4GCD5XK7GucFbTKnJa0fonFEBlAh3azBS3Gh0NNd";
    String REST_API_KEY = "RYznH1yrJ3DVly2f02aEMkZJNwmPVdDBUQyqRT6H";
    String URL = "https://concipiotektura.back4app.io/files/";
    String URL_FILES = "https://concipiotektura.back4app.io/classes/";
    RequestBuilder requestBuilder = new RequestBuilder("POST");
    Request request;
    AsyncHttpClient client;
    
    public void insertImage(byte[] data, String filename, String objectId)
    {
        try
        {
            client = Dsl.asyncHttpClient();
            request = requestBuilder
                    .setUrl(URL + filename)
                    .addHeader("X-Parse-Application-Id", APP_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .addHeader("Content-type", "application/octet-stream")
                    .setBody(data)
                    .build();
            
            ListenableFuture<Response> lf = client.executeRequest(request, new ProgressAsyncHandler<Response>() 
            {
                Response.ResponseBuilder builder = new Response.ResponseBuilder();
                @Override
                public AsyncHandler.State onHeadersWritten() 
                {
                    System.out.println("onHeadersWritten");
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public AsyncHandler.State onContentWritten() 
                {
                    System.out.println("onContentWritten");
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public AsyncHandler.State onContentWriteProgress(long l, long l1, long l2) 
                {
                    System.out.println("onContentWriteProgress - " + "long l: " + l + " long l1: " + l1 + " long l2: " + l2);
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public AsyncHandler.State onStatusReceived(HttpResponseStatus hrs) throws Exception 
                {
                    System.out.println("onStatusReceived");
                    builder.accumulate(hrs);
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public AsyncHandler.State onHeadersReceived(HttpHeaders hh) throws Exception 
                {
                    System.out.println("onHeadersReceived");
                    builder.accumulate(hh);
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart hrbp) throws Exception 
                {
                    System.out.println("onBodyPartReceived");
                    builder.accumulate(hrbp);
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public void onThrowable(Throwable thrwbl) {
                }

                @Override
                public Response onCompleted() throws Exception 
                {
                    return builder.build();
                }
            });
            
            Response response = lf.get();
            if(response.getStatusCode() == 201)
            {
                System.out.println("Goes Here");
                JSONObject jsonObject = new JSONObject(response.getResponseBody().toString());
                associateFile(jsonObject.getString("url"), jsonObject.getString("name"),objectId, filename);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void associateFile(String url, String name, String objectId, String rawname)
    {
        try
        {
            JSONObject pdf = new JSONObject();
            pdf.put("__type", "File");
            pdf.put("name", name);
            pdf.put("url", url);
            
            JSONObject pointer = new JSONObject();
            pointer.put("__type", "Pointer");
            pointer.put("className", "Specifications");
            pointer.put("objectId", objectId);
            
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("Files",pdf);
            jsonobject.put("SpecificationsPointer", pointer);
            StringEntity entity = new StringEntity(jsonobject.toString());
            request = requestBuilder
                    .setUrl(URL_FILES + "Specifications_PDFFiles")
                    .addHeader("X-Parse-Application-Id", APP_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .addHeader("Content-type", "application/json")
                    .setBody(entity.toString())
                    .build();
          
            ListenableFuture<Response> lf = client.executeRequest(request, new AsyncHandler<Response>() 
            {
                Response.ResponseBuilder builder = new Response.ResponseBuilder();
                @Override
                public AsyncHandler.State onStatusReceived(HttpResponseStatus hrs) throws Exception 
                {
                    System.out.println("onStatusReceived");
                    builder.accumulate(hrs);
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public AsyncHandler.State onHeadersReceived(HttpHeaders hh) throws Exception 
                {
                    System.out.println("onHeadersReceived");
                    builder.accumulate(hh);
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart hrbp) throws Exception 
                {
                    System.out.println("onBodyPartReceived");
                    builder.accumulate(hrbp);
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public void onThrowable(Throwable thrwbl) {
                }

                @Override
                public Response onCompleted() throws Exception 
                {
                    return builder.build();
                }
            });
            Response response = lf.get();
            if(response.getStatusCode() == 201)
            {
                JOptionPane.showMessageDialog(null, "Successful");
            }
            else
            {
                System.out.println(response.getStatusCode());
                System.out.println(response.getResponseBody());
                JOptionPane.showMessageDialog(null, "Not uploaded");
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(UploadFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
