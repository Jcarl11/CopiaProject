package DatabaseOperations;

import Entities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.apache.commons.io.FilenameUtils;
import org.asynchttpclient.Response;
import org.parse4j.ParseObject;
public class TaskExecute
{
    Task<?> myTask;
    DatabaseOperations databaseOperations = new DatabaseOperations();
    private TaskExecute(){}
    private static TaskExecute instance = null;
    public static  TaskExecute getInstance()
    {
        if(instance == null)
            instance = new TaskExecute();
        return instance;
    }

    public void clientInsertRecord(final ClientEntity clientEntity, final String pointerClass, final String pointer)
    {
        myTask = new Task<String>() 
        {
            @Override
            protected String call() throws Exception 
            {
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->databaseOperations.uploadFile(clientEntity.getFileToUpload()))
                        .thenApply(urls -> databaseOperations.uploadRecord(urls, clientEntity.buildJSON(), pointerClass))
                        .thenApplyAsync(data -> databaseOperations.associateFile(data, pointerClass, pointer))
                        .thenApplyAsync(data -> databaseOperations.associateNotes(data, clientEntity.getNotes()));
                
                return completableFuture.get();
            }
        };
        new Thread(myTask).start();
    }
    
    public void suppliersInsertRecord(final SuppliersEntity suppliersEntity, final String pointerClass, final String pointer)
    {
        myTask = new Task<String>() 
        {
            @Override
            protected String call() throws Exception 
            {
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->databaseOperations.uploadFile(suppliersEntity.getFileToUpload()))
                        .thenApply(urls -> databaseOperations.uploadRecord(urls, suppliersEntity.buildJSON(), pointerClass))
                        .thenApplyAsync(data -> databaseOperations.associateFile(data, pointerClass, pointer))
                        .thenApplyAsync(data -> databaseOperations.associateNotes(data, suppliersEntity.getNotes()));
                return completableFuture.get();
            }
        };
        new Thread(myTask).start();
    }
    public void contractorsInsertRecord(final ContractorsEntity contractorsEntity, final String pointerClass, final String pointer)
    {
        myTask = new Task<String>() 
        {
            @Override
            protected String call() throws Exception 
            {
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->databaseOperations.uploadFile(contractorsEntity.getFileToUpload()))
                        .thenApply(urls -> databaseOperations.uploadRecord(urls, contractorsEntity.buildJSON(), pointerClass))
                        .thenApplyAsync(data -> databaseOperations.associateFile(data, pointerClass, pointer))
                        .thenApplyAsync(data -> databaseOperations.associateNotes(data, contractorsEntity.getNotes()));
                return completableFuture.get();
            }
        };
        new Thread(myTask).start();
    }
    
    public void consultantsInsertRecord(final ConsultantsEntity consultantsEntity, final String pointerClass, final String pointer)
    {
        myTask = new Task<String>() 
        {
            @Override
            protected String call() throws Exception 
            {
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->databaseOperations.uploadFile(consultantsEntity.getFileToUpload()))
                        .thenApply(urls -> databaseOperations.uploadRecord(urls, consultantsEntity.buildJSON(), pointerClass))
                        .thenApplyAsync(data -> databaseOperations.associateFile(data, pointerClass, pointer))
                        .thenApplyAsync(data -> databaseOperations.associateNotes(data, consultantsEntity.getNotes()));
                return completableFuture.get();
            }
        };
        new Thread(myTask).start();
    }
    public void specificationsInsertRecord(final SpecificationsEntity specificationsEntity, final String pointerClass, final String pointer)
    {
        myTask = new Task<String>() 
        {
            @Override
            protected String call() throws Exception 
            {
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->databaseOperations.uploadFile(specificationsEntity.getFileToUpload()))
                        .thenApply(urls -> databaseOperations.uploadRecord(urls, specificationsEntity.buildJSON(), pointerClass))
                        .thenApplyAsync(data -> databaseOperations.associateFile(data, pointerClass, pointer))
                        .thenApplyAsync(data -> databaseOperations.associateNotes(data, specificationsEntity.getNotes()));
                return completableFuture.get();
            }
        };
        new Thread(myTask).start();
    }
    public void retrieveNotes(String referenceClass, String objectId, String pointer)
    {
        myTask = new Task<ObservableList<NotesEntity>>() 
        {
            @Override
            protected ObservableList<NotesEntity> call() throws Exception 
            {
                ObservableList<NotesEntity> notesList = databaseOperations.retrieveNotes(referenceClass, objectId, pointer);
                return notesList;
            }
        };
        new Thread(myTask).start();
    }
    public void updateNotes(String objectId, String remark, String referenceClass, String pointer)
    {
        myTask = new Task<Response>() 
        {
            @Override
            protected Response call() throws Exception 
            {
                return databaseOperations.updateNote(objectId, remark);
            }
        };
        new Thread(myTask).start();
    }
    public Task<?> getTask()
    {
        return myTask;
    }
    private String getFileType(String filePath)
    {
        String type = "";
        String extension = FilenameUtils.getExtension(filePath).toLowerCase();
        if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("jpeg"))
        {
            type = "Image";
        }
        else if(extension.equalsIgnoreCase("pdf"))
        {
            type = "pdf";
        }
        return type;
    }
    
}
