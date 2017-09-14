package xyz.ratapp.ilx.data.repository.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by timtim on 08/09/2017.
 */

class PreferencesManager {

    private final static String PREFS = "ilx.preferences";

    private SharedPreferences prefs;
    private String sessionId = null;
    private String domainName = null;
    private Boolean firstStart = null;

    private void loadDataFromPrefs(Context context) {
        if(prefs == null) {
            prefs = context.getSharedPreferences(
                    PREFS, Context.MODE_PRIVATE);
        }

        sessionId = prefs.getString("session_id", "");
        domainName = prefs.getString("domain_name", "");
    }

    boolean isFirstStart(Context context) {
        if(firstStart == null) {
            loadDataFromPrefs(context);
            firstStart = sessionId.isEmpty() ||
                    domainName.isEmpty();
        }

        return firstStart;
    }

    void savePrefs(Context context, String sessionId,
                   String domainName) {
        if(prefs == null) {
            prefs = context.getSharedPreferences(
                    PREFS, Context.MODE_PRIVATE);
            this.sessionId = sessionId;
            this.domainName = domainName;
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("session_id", sessionId);
        editor.putString("domain_name", domainName);
        editor.apply();
    }

    String getSessionId() {
        return sessionId;
    }

    String getDomainName() {
        return domainName;
    }
}
