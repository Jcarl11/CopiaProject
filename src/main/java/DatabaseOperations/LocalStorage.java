package DatabaseOperations;

import Entities.ClientEntity;
import Entities.ComboboxDataEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LocalStorage 
{
    private String DATABASE_NAME = "jdbc:sqlite:LocalStorage.db";
    private PreparedStatement statement = null;
    private Connection connection = null;
    private ResultSet resultSet = null;
    private LocalStorage(){}
    private static LocalStorage instance = null;
    public static LocalStorage getInstance()
    {
        if(instance == null)
        {
            instance = new LocalStorage();
        }
        return instance;
    }
    
    private void initializeDB()
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_NAME);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void initialize_Client()
    {
        try
        {
            initializeDB();
            String sql = "CREATE TABLE IF NOT EXISTS CLIENT"
                    + "(objectId varchar(50) not null,"
                    + "Representative varchar(255),"
                    + "Position varchar(255),"
                    + "Company varchar(255),"
                    + "Industry varchar(255),"
                    + "Type varchar(255),"
                    + "PRIMARY KEY(objectId));";
            statement = connection.prepareStatement(sql);
            int result = statement.executeUpdate();
            if(result > 0)
            {
                System.out.println("Client Table Created");
            }
            else
            {
                System.out.println("Something went wrong");
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            closeConnections();
        }
    }
    public void initialize_local_ComboboxData()
    {
        try{
            initializeDB();
            System.out.println("Initializing ComboboxData");
            
            String sql = "CREATE TABLE IF NOT EXISTS COMBOBOXDATA"
                    + "(objectId varchar(50) not null,"
                    + "Title varchar(255),"
                    + "Category varchar(255),"
                    + "Field varchar(255),"
                    + "PRIMARY KEY(objectId))";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
         
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            closeConnections();
            System.out.println("Finishing ComboboxData init");
        }
    }
    
    public boolean insert_local_ComboboxData(ComboboxDataEntity comboboxData)
    {
        boolean isSuccessful = false;
        try
        {
            initializeDB();
            String objectId = comboboxData.getObjectId().toUpperCase();
            String title = comboboxData.getTitle().toUpperCase();
            String category = comboboxData.getCategory().toUpperCase();
            String field = comboboxData.getField().toUpperCase();
            String sql = "INSERT INTO COMBOBOXDATA(objectId, Title, Category, Field) VALUES(?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, objectId);
            statement.setString(2, title);
            statement.setString(3, category);
            statement.setString(4, field);
            int result = statement.executeUpdate();
            if(result>0)
            {
                isSuccessful= true;
            }
            else
            {
                System.out.println("result: " + result);
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            closeConnections();
        }
        return isSuccessful;
    }
    
    public ArrayList<ComboboxDataEntity> retrieve_local_ComboboxData(String category)
    {
        ArrayList<ComboboxDataEntity> data = new ArrayList<>();
        try
        {
            initializeDB();
            String sql = "SELECT * FROM COMBOBOXDATA WHERE CATEGORY = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, category.toUpperCase());
            resultSet = statement.executeQuery();
            
            while(resultSet.next())
            {
                ComboboxDataEntity entity = new ComboboxDataEntity();
                entity.setObjectId(resultSet.getString("objectId"));
                entity.setTitle(resultSet.getString("Title"));
                entity.setCategory(resultSet.getString("Category"));
                entity.setField(resultSet.getString("Field"));
                data.add(entity);
            }
        }
        catch(Exception ex){}
        finally{closeConnections();}
        
        return data;
    }
    
    public boolean insert_Client(ClientEntity client)
    {
        boolean isSuccessful = false;
        try
        {
            initializeDB();
            String objectId = client.getObjectID().toUpperCase();
            String representative = client.getRepresentative().toUpperCase();
            String position = client.getPosition().toUpperCase();
            String company = client.getCompany_Name().toUpperCase();
            String industry = client.getIndustry().toUpperCase();
            String type = client.getType().toUpperCase();
            String sql = "INSERT INTO CLIENT(objectId, Representative, Position, Company, Industry, Type) VALUES(?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, objectId);
            statement.setString(2, representative);
            statement.setString(3, position);
            statement.setString(4, company);
            statement.setString(5, industry);
            statement.setString(6, type);
            int result = statement.executeUpdate();
            if(result>0)
            {
                isSuccessful = true;
                System.out.println("inserted to local db");
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            closeConnections();
        }
        return isSuccessful;
    }
    
    public ArrayList<String> retrieve_local_Categories()
    {
        ArrayList<String> data = new ArrayList<>();
        try
        {
            initializeDB();
            String sql = "SELECT DISTINCT CATEGORY FROM COMBOBOXDATA";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                data.add(resultSet.getString("Category"));
            }
        }
        catch(Exception ex){ex.printStackTrace();}
        finally{closeConnections();}
        
        return data;
    }
    
    public ArrayList<String> retrieve_local_Categories_CONSTANTS()
    {
        ArrayList<String> data = new ArrayList<>();
        try
        {
            initializeDB();
            String sql = "SELECT CATEGORY FROM COMBOBOXDATA WHERE CATEGORY = 'CONSULTANTS' OR CATEGORY = 'SPECIFICATIONS'";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                data.add(resultSet.getString("Category"));
            }
        }
        catch(Exception ex){ex.printStackTrace();}
        finally{closeConnections();}
        
        return data;
    }
    
    public boolean insert_constants()
    {
        boolean isSuccessful = false;
        try
        {
            initializeDB();
            String sql = "INSERT INTO COMBOBOXDATA(objectId, Title, Category, Field) VALUES(?,?,?,?),(?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "yjX2CWfxZz");
            statement.setString(2, null);
            statement.setString(3, "CONSULTANTS");
            statement.setString(4, null);
            statement.setString(5, "FACbGp156f");
            statement.setString(6, null);
            statement.setString(7, "SPECIFICATIONS");
            statement.setString(8, null);
            int result = statement.executeUpdate();
            if(result>0)
            {
                isSuccessful= true;
            }
            else
            {
                System.out.println("result: " + result);
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            closeConnections();
        }
        return isSuccessful;
    }
    
    private void closeConnections()
    {
        try
        {
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
            if(resultSet != null)
                resultSet.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
