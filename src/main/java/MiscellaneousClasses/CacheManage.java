package MiscellaneousClasses;

import Entities.ComboboxDataEntity;
import com.google.common.cache.Cache;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class CacheManage 
{
    private Cache<String, ArrayList<ComboboxDataEntity>> cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(24, TimeUnit.HOURS).build();
    private CacheManage(){}
    private static CacheManage instance = null;
    
    public static CacheManage getInstance()
    {
        if(instance == null)
            instance = new CacheManage();
        
        return instance;
    }
    
    public void addClientComboboxCache(String id, ArrayList<ComboboxDataEntity> data)
    {
        cache.put(id, data);
    }
    public ArrayList<ComboboxDataEntity> getClientComboboxCache(String id)
    {
        ArrayList<ComboboxDataEntity> returnList = new ArrayList<>();
        returnList = cache.getIfPresent(id);
        return returnList;
    }
    public long getCacheSize()
    {
        return cache.size();
    }
    
}
