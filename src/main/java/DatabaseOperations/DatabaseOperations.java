package DatabaseOperations;

import Entities.ComboboxDataEntity;
import Entities.NotesEntity;
import MiscellaneousClasses.MyUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FilenameUtils;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.LoginCallback;

public class DatabaseOperations 
{
    HashMap<String, String> statuses;
    AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
    List<Future<Response>> responses;
    volatile boolean finished = false;
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
    public List<Future<Response>> associateNotes(HashMap<String, String> data, ArrayList<NotesEntity> notesList)
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
        return responses;
    }
    public ObservableList<NotesEntity> retrieveNotes(String referenceClass, String objectId, String pointer)
    {
        finished = false;
        ObservableList<NotesEntity> notesList = FXCollections.observableArrayList();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(referenceClass);
        query.whereEqualTo("objectId", objectId);
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Notes");
                    query2.include(pointer);
                    query2.whereMatchesQuery(pointer, query);
                    query2.findInBackground(new FindCallback<ParseObject>() 
                    {
                        @Override
                        public void done(List<ParseObject> list, ParseException parseException) 
                        {
                            if(parseException == null && list != null)
                            {
                                for(ParseObject objects : list)
                                {
                                    NotesEntity notes = new NotesEntity();
                                    notes.setObjectId(objects.getObjectId());
                                    notes.setRemarks(objects.getString("Remark"));
                                    notes.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy").format(objects.getCreatedAt()));
                                    notes.setUpdatedAt(new SimpleDateFormat("dd/MM/yyyy").format(objects.getUpdatedAt()));
                                    notesList.add(notes);
                                }
                                finished = true;
                            }
                            else
                            {
                                finished = true;
                            }
                        }
                    });
                }
                else
                {
                    finished = true;
                }
            }
        });
        while(finished == false)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return notesList;
    }
    public Response retrieveCombobox()
    {
        ArrayList<ComboboxDataEntity> result = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ComboboxData");
        query.findInBackground(new  FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    for(ParseObject objects : list)
                    {
                        ComboboxDataEntity combobox = new ComboboxDataEntity();
                        combobox.setObjectId(objects.getObjectId());
                        combobox.setTitle(objects.getString("Title"));
                        combobox.setCategory(objects.getString("Category"));
                        combobox.setField(objects.getString("Field"));
                        result.add(combobox);
                    }
                }
            }
        });
        ListenableFuture<Response> lf = asyncHttpClient.prepareGet(MyUtils.URL + "ComboboxData")
                                    .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                                    .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                                    .setHeader("Content-type", "application/json")
                                    .execute(new AsyncCompletionHandler<Response>() 
                                    {
                                        @Override
                                        public Response onCompleted(Response rspns) throws Exception 
                                        {
                                            return rspns;
                                        }
                                    });  
        Response response = null;
        try {
            response = lf.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    public Response updateNote(String objectId, String remarks)
    {
        ListenableFuture<Response> lf = asyncHttpClient.preparePut(MyUtils.URL + "Notes/" + objectId)
                            .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                            .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                            .setHeader("Content-type", "application/json")
                            .setBody(new JSONObject().put("Remark", remarks).toString())
                            .execute(new AsyncCompletionHandler<Response>() 
                            {
                                @Override
                                public Response onCompleted(Response rspns) throws Exception 
                                {
                                    return rspns;
                                }
                            });
        Response response = null;
        try {
            response = lf.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    public Response updateRecord(JSONObject entity,String targetClass)
    {
        Response response = null;
        System.out.println(entity.toString());
            ListenableFuture<Response> lf = asyncHttpClient.preparePut(MyUtils.URL + targetClass + "/"  + entity.getString("objectId"))
                            .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                            .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                            .setHeader("Content-type", "application/json")
                            .setBody(entity.toString())
                            .execute(new AsyncCompletionHandler<Response>() 
                            {
                                @Override
                                public Response onCompleted(Response rspns) throws Exception 
                                {
                                    return rspns;
                                }
                            });
            try {
                response = lf.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return response;
    }
    public HashMap<String, Object> findRecord(String objectId, String searchClass, String pointer)
    {
        setFinished(false);
        statuses = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put("Pointer", pointer);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(searchClass);
        query.getInBackground(objectId, new GetCallback<ParseObject>() 
        {
            @Override
            public void done(ParseObject t, ParseException parseException) 
            {
                if(t != null && parseException == null)
                {
                    statuses.put("findRecord", "Successful");
                    data.put("ParseQuery", query);
                    setFinished(true);
                }
                else if(parseException != null)
                {
                    statuses.put("findRecord", "Exception");
                    setFinished(true);
                }
                else if(t == null)   
                {
                    statuses.put("findRecord", "empty");
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    public HashMap<String, Object> deleteImages(HashMap<String, Object> data)
    {
        setFinished(false);
        HashMap<String, Object> imagesResponse = data;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Images");
        query.include(String.valueOf(data.get("Pointer")));
        query.whereMatchesQuery(String.valueOf(data.get("Pointer")), (ParseQuery<ParseObject>)data.get("ParseQuery"));
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(list != null && parseException == null)
                {
                    statuses.put("deleteImages", "Successful");
                    for(ParseObject records : list)
                    {
                        try {
                            records.delete();
                        } catch (ParseException ex) {Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    setFinished(true);
                }
                else if(parseException != null)
                {
                    statuses.put("deleteImages", "Exception");
                    setFinished(true);
                }
                else if(list == null)   
                {
                    statuses.put("deleteImages", "empty");
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imagesResponse;
    }
    public HashMap<String, Object>deleteNotes(HashMap<String, Object> data)
    {
        setFinished(false);
        HashMap<String, Object> notesResponse = data;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notes");
        query.include((String) data.get("Pointer"));
        query.whereMatchesQuery((String) data.get("Pointer"), (ParseQuery<ParseObject>) data.get("ParseQuery"));
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(list != null && parseException == null)
                {
                    statuses.put("deleteNotes", "Successful");
                    for(ParseObject records : list)
                    {
                        try {
                            records.delete();
                        } catch (ParseException ex) {
                            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    setFinished(true);
                }
                else if(parseException != null)
                {
                    statuses.put("deleteNotes", "Exception");
                    setFinished(true);
                }
                else if(list == null)   
                {
                    statuses.put("deleteNotes", "empty");
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return notesResponse;
    }
    public HashMap<String, Object> deletePdf(HashMap<String, Object> data)
    {
        setFinished(false);
        HashMap<String, Object> pdfResponse = data;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PDFFiles");
        query.include((String) data.get("Pointer"));
        query.whereMatchesQuery((String) data.get("Pointer"), (ParseQuery<?>) data.get("ParseQuery"));
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(list != null && parseException == null)
                {
                    statuses.put("deletePdf", "Successful");
                    for(ParseObject records : list)
                    {
                        try {
                            records.delete();
                        } catch (ParseException ex) {
                            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    setFinished(true);
                }
                else if(parseException != null)
                {
                    statuses.put("deletePdf", "Exception");
                    setFinished(true);
                }
                else if(list == null)   
                {
                    statuses.put("deletePdf", "empty");
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pdfResponse;
    }
    public HashMap<String, String> deleteRecord(HashMap<String, Object> data, String objectId, String searchClass)
    {
        setFinished(false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(searchClass);
        query.getInBackground(objectId, new GetCallback<ParseObject>() 
        {
            @Override
            public void done(ParseObject t, ParseException parseException) 
            {
                if(parseException == null && t != null)
                {
                    statuses.put("deleteRecord", "Successful");
                    try {
                        t.delete();
                    } catch (ParseException ex) {
                        Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setFinished(true);
                }
                else if(parseException != null)
                {
                    statuses.put("deleteRecord", "Exception");
                    setFinished(true);
                }
                else if(t == null)   
                {
                    statuses.put("deleteRecord", "empty");
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return statuses;
    }
    public String deleteSingleNote(String objectId)
    {
        setFinished(false);
        int statusCode = 0;
        ListenableFuture<Response> lf = asyncHttpClient.prepareDelete(MyUtils.URL + "Notes/" + objectId)
                    .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                    .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                    .execute(new AsyncCompletionHandler<Response>() 
                    {
                        @Override
                        public Response onCompleted(Response rspns) throws Exception 
                        {
                            return rspns;
                        }
                    });
        try {
            Thread.sleep(2000);
            statusCode = lf.get().getStatusCode();
        } catch (InterruptedException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(statusCode);
    }
    
    public Response retrieveUser(String username, String password)
    {
        System.out.println("Initalized");
        ListenableFuture<Response> lf = asyncHttpClient.prepareGet(MyUtils.URL_BASE + "login")
                        .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                        .setHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                        .setHeader("X-Parse-Revocable-Session", MyUtils.IRREVOCABLE_SESSION)
                        .addQueryParam("username", username)
                        .addQueryParam("password", password)
                        .execute(new AsyncCompletionHandler<Response>() 
                        {
                            @Override
                            public Response onCompleted(Response rspns) throws Exception 
                            {
                                return rspns;
                            }
                        });
        Response response = null;
        try {
            response = lf.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    public Response checkLoggedIn(String session)
    {
        ListenableFuture<Response> lf = asyncHttpClient.prepareGet(MyUtils.URL + "users/me")
                .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                .addHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                .addHeader("X-Parse-Session-Token", session)
                .execute(new AsyncCompletionHandler<Response>() 
                {
                    @Override
                    public Response onCompleted(Response rspns) throws Exception 
                    {
                        return rspns;
                    }
                });
        Response response = null;
        try {
            response = lf.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    public Response logoutUser(String session)
    {
        ListenableFuture<Response> lf = asyncHttpClient.preparePost(MyUtils.URL_BASE + "logout")
                .addHeader("X-Parse-Application-Id", MyUtils.APP_ID)
                .addHeader("X-Parse-REST-API-Key", MyUtils.REST_API_KEY)
                .addHeader("X-Parse-Session-Token", session)
                .execute(new AsyncCompletionHandler<Response>() 
                {
                    @Override
                    public Response onCompleted(Response rspns) throws Exception 
                    {
                        return rspns;
                    }
                });
        Response response = null;
        try {
            response = lf.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
