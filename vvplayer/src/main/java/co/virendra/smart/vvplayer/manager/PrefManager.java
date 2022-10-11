package co.virendra.smart.vvplayer.manager;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Virenddra on 10/10/2022
 */

public class PrefManager
{
    private SharedPreferences preferences;
    private Context context;

    private static PrefManager prefManager;

    public PrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("vvPlayer", MODE_PRIVATE);
    }

    public static PrefManager getInstance(Context context)
    {
        if(prefManager == null){
            prefManager = new PrefManager(context);
        }

        return prefManager;
    }

    public void setCryptoKey(String key)
    {
        preferences.edit().putString("cryptoKey",key) .commit();
    }

    public String getCryptoKey()
    {
        return preferences.getString("cryptoKey", "");
    }

}
