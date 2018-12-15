package DatabaseOperations;

import Entities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import javafx.concurrent.Task;
import org.apache.commons.io.FilenameUtils;
import org.asynchttpclient.Response;
import org.parse4j.ParseObject;
public class AlternateUpload
{
    Task<List<Future<Response>>> myTask;
    ClientEntity clientEntity;
    InsertRecord insertRecord = new InsertRecord();
    private AlternateUpload(){}
    private static AlternateUpload instance = null;
    public static  AlternateUpload getInstance()
    {
        if(instance == null)
            instance = new AlternateUpload();
        return instance;
    }

    public void clientInsertRecord(final ClientEntity clientEntity, final String searchClass)
    {
        myTask = new Task<List<Future<Response>>>() 
        {
            
            @Override
            protected List<Future<Response>> call() throws Exception 
            {
                List<Future<Response>> myResponse = new ArrayList<>();
                ParseObject clientObject = insertRecord.clientInsert("Client", clientEntity);
                if(clientEntity.getNotes() != null)
                {
                    ExecuteFileUpload.getInstance().associateNotes(clientObject.getObjectId(), "ClientPointer", clientEntity.getNotes(),"Client");
                }
                if(clientEntity.getFileToUpload().size() > 0)
                {
                    ArrayList<String> jsonResponses = new ArrayList<>();
                    List<Future<Response>> response = ExecuteFileUpload.getInstance().execute(clientEntity.getFileToUpload());
                    for(Future<Response> responses : response)
                    {
                        String responseBody = responses.get().getResponseBody();
                        jsonResponses.add(responseBody);
                    }
                    myResponse = ExecuteFileUpload.getInstance().associateFile(jsonResponses, "Client", clientObject.getObjectId(), "ClientPointer");
                    
                }
                return myResponse;
            }
        };
        new Thread(myTask).start();
    }
    
    public void suppliersInsertRecord(final SuppliersEntity suppliersEntity, final String searchClass)
    {
        myTask = new Task<List<Future<Response>>>() 
        {
            @Override
            protected List<Future<Response>> call() throws Exception 
            {
                List<Future<Response>> myResponse = new ArrayList<>();
                ParseObject supplierObject = insertRecord.suppliersInsert("Suppliers", suppliersEntity);
                if(suppliersEntity.getNotes() != null)
                {
                    ExecuteFileUpload.getInstance().associateNotes(supplierObject.getObjectId(), "SuppliersPointer", suppliersEntity.getNotes(),"Suppliers");
                }
                if(suppliersEntity.getFileToUpload().size() > 0)
                {
                    ArrayList<String> jsonResponses = new ArrayList<>();
                    List<Future<Response>> response = ExecuteFileUpload.getInstance().execute(suppliersEntity.getFileToUpload());
                    for(Future<Response> responses : response)
                    {
                        System.out.println(responses.get().getResponseBody());
                        String responseBody = responses.get().getResponseBody();
                        jsonResponses.add(responseBody);
                    }
                    myResponse = ExecuteFileUpload.getInstance().associateFile(jsonResponses, "Suppliers", supplierObject.getObjectId(), "SuppliersPointer");
                    
                }
                return myResponse;
            }
        };
        new Thread(myTask).start();
    }
    public void contractorsInsertRecord(final ContractorsEntity contractorsEntity, final String searchClass)
    {
        myTask = new Task<List<Future<Response>>>() 
        {
            @Override
            protected List<Future<Response>> call() throws Exception 
            {
                List<Future<Response>> myResponse = new ArrayList<>();
                ParseObject contractorsObject = insertRecord.contractorsInsert("Contractors", contractorsEntity);
                if(contractorsEntity.getNotes() != null)
                {
                    ExecuteFileUpload.getInstance().associateNotes(contractorsObject.getObjectId(), "ContractorsPointer", contractorsEntity.getNotes(),"Contractors");
                }
                if(contractorsEntity.getFileToUpload().size() > 0)
                {
                    ArrayList<String> jsonResponses = new ArrayList<>();
                    List<Future<Response>> response = ExecuteFileUpload.getInstance().execute(contractorsEntity.getFileToUpload());
                    for(Future<Response> responses : response)
                    {
                        System.out.println(responses.get().getResponseBody());
                        String responseBody = responses.get().getResponseBody();
                        jsonResponses.add(responseBody);
                    }
                    myResponse = ExecuteFileUpload.getInstance().associateFile(jsonResponses, "Contractors", contractorsObject.getObjectId(), "ContractorsPointer");
                    
                }
                return myResponse;
            }
        };
        new Thread(myTask).start();
    }
    
    public void consultantsInsertRecord(final ConsultantsEntity consultantsEntity, final String searchClass)
    {
        myTask = new Task<List<Future<Response>>>() 
        {
            @Override
            protected List<Future<Response>> call() throws Exception 
            {
                List<Future<Response>> myResponse = new ArrayList<>();
                ParseObject consultantsObject = insertRecord.consultantsInsert("Consultants", consultantsEntity);
                if(consultantsEntity.getNotes() != null)
                {
                    ExecuteFileUpload.getInstance().associateNotes(consultantsObject.getObjectId(), "ConsultantsPointer", consultantsEntity.getNotes(),"Consultants");
                }
                if(consultantsEntity.getFileToUpload().size() > 0)
                {
                    ArrayList<String> jsonResponses = new ArrayList<>();
                    List<Future<Response>> response = ExecuteFileUpload.getInstance().execute(consultantsEntity.getFileToUpload());
                    for(Future<Response> responses : response)
                    {
                        System.out.println(responses.get().getResponseBody());
                        String responseBody = responses.get().getResponseBody();
                        jsonResponses.add(responseBody);
                    }
                    myResponse = ExecuteFileUpload.getInstance().associateFile(jsonResponses, "Consultants", consultantsObject.getObjectId(), "ConsultantsPointer");
                    
                }
                return myResponse;
            }
        };
        new Thread(myTask).start();
    }
    public void specificationsInsertRecord(final SpecificationsEntity specificationsEntity, final String searchClass)
    {
        myTask = new Task<List<Future<Response>>>() 
        {
            @Override
            protected List<Future<Response>> call() throws Exception 
            {
                List<Future<Response>> myResponse = new ArrayList<>();
                ParseObject specificationsObject = insertRecord.specificationsInsert("Specifications", specificationsEntity);
                if(specificationsEntity.getNotes() != null)
                {
                    ExecuteFileUpload.getInstance().associateNotes(specificationsObject.getObjectId(), "SpecificationsPointer", specificationsEntity.getNotes(),"Specifications");
                }
                if(specificationsEntity.getFileToUpload().size() > 0)
                {
                    ArrayList<String> jsonResponses = new ArrayList<>();
                    List<Future<Response>> response = ExecuteFileUpload.getInstance().execute(specificationsEntity.getFileToUpload());
                    for(Future<Response> responses : response)
                    {
                        System.out.println(responses.get().getResponseBody());
                        String responseBody = responses.get().getResponseBody();
                        jsonResponses.add(responseBody);
                    }
                    myResponse = ExecuteFileUpload.getInstance().associateFile(jsonResponses, "Specifications", specificationsObject.getObjectId(), "SpecificationsPointer");
                    
                }
                return myResponse;
            }
        };
        new Thread(myTask).start();
    }
    public Task<List<Future<Response>>> getTask()
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
