package MiscellaneousClasses;

import java.util.prefs.Preferences;
import org.json.JSONObject;

public class UserPreferences 
{
    private UserPreferences(){}
    private static UserPreferences instance = null;
    public static UserPreferences getInstance()
    {
        if(instance == null)
            instance = new UserPreferences();
        return instance;
    }
    private Preferences preference = Preferences.userRoot().node("MiscellaneousClasses.UserPreferences");
    
    public void userData(JSONObject data)
    {
        preference.put("objectId", data.getString("objectId"));
        preference.put("sessionToken", data.getString("sessionToken"));
        preference.put("username", data.getString("username"));
    }
    public void clearPreference()
    {
        preference.remove("objectId");
        preference.remove("sessionToken");
        preference.remove("username");
    }
    public Preferences getPreference() 
    {
        return preference;
    }
}
