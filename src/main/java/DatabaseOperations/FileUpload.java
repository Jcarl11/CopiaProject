package DatabaseOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class FileUpload extends Thread
{
    String APP_ID = "4GCD5XK7GucFbTKnJa0fonFEBlAh3azBS3Gh0NNd";
    String REST_API_KEY = "RYznH1yrJ3DVly2f02aEMkZJNwmPVdDBUQyqRT6H";
    String URL = "https://concipiotektura.back4app.io/files/";
    String URL_FILES = "https://concipiotektura.back4app.io/classes/";
    volatile boolean running = true;
    int iterations = 0;
    File imageFile;
    String fileName;
    String objectId;
    String field;
    String pointerClass;
    public FileUpload(File imageFile, String fileName,String objectId, String field, String pointerClass)
    {
        this.imageFile = imageFile;
        this.fileName = fileName;
        this.objectId = objectId;
        this.field = field;
        this.pointerClass = pointerClass;
    }
    public void terminate()
    {
        running = false;
    }
    
    @Override
    public void run() 
    {
        insertImage();
    }

    private void insertImage()
    {
        try
        {
            byte[] imageFile_data = Files.readAllBytes(imageFile.toPath());
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(URL + fileName);
            httpPost.setHeader("X-Parse-Application-Id", APP_ID);
            httpPost.setHeader("X-Parse-REST-API-Key", REST_API_KEY);
            httpPost.setHeader("Content-type", "application/octet-stream");
            httpPost.setEntity(new ByteArrayEntity(imageFile_data));
            
            HttpResponse response = httpClient.execute(httpPost);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            
            if(response.getStatusLine().getStatusCode() == 201) //if successful
            {
                System.out.println(stringBuffer);
                JSONObject data = new JSONObject(stringBuffer.toString());
                if(FilenameUtils.getExtension(fileName).equalsIgnoreCase("pdf"))
                {
                    if(pointerClass == "Specifications")
                    {
                        associateFile(String.valueOf(data.get("url")), String.valueOf(data.get("name")), fileName, "Specifications_PDFFiles", objectId, field,pointerClass);
                    }
                    else
                    {
                        associateFile(String.valueOf(data.get("url")), String.valueOf(data.get("name")), fileName, "PDFFiles", objectId, field,pointerClass);
                    }
                    
                }
                else
                {
                    associateFile(String.valueOf(data.get("url")), String.valueOf(data.get("name")), fileName, "Images", objectId, field,pointerClass);
                }
            }
            else
            {
                System.out.println("Something wrong");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void associateFile(String url, String name, String rawname, String db_class, String objectId, String field, String pointerClass)
    {
        try
        {
            System.out.println(objectId);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(URL_FILES + db_class);
            httpPost.setHeader("X-Parse-Application-Id", APP_ID);
            httpPost.setHeader("X-Parse-REST-API-Key", REST_API_KEY);
            httpPost.setHeader("Content-type", "application/json");
            
            JSONObject image = new JSONObject();
            image.put("__type", "File");
            image.put("name", name);
            image.put("url", url);
            
            JSONObject pointer = new JSONObject();
            pointer.put("__type", "Pointer");
            pointer.put("className", pointerClass);
            pointer.put("objectId", objectId);
            
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("Name", rawname);
            jsonobject.put("Files",image);
            jsonobject.put(field, pointer);
            
            StringEntity entity = new StringEntity(jsonobject.toString());
            httpPost.setEntity(entity);
            
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine().getStatusCode());
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            System.out.println(stringBuffer.toString());
            
        }catch(Exception ex){ex.printStackTrace();}
    }
}
