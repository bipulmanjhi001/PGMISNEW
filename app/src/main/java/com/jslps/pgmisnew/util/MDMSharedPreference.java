package com.jslps.pgmisnew.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MDMSharedPreference {

    private static SharedPreferences mPref;
    private static MDMSharedPreference mRef;
    private Editor mEditor;

	/*
     * public static String CUSTOMER_ID = "customerId"; public static String
	 * CUSTOMER_TYPE = "type";
	 */

    /**
     * Singleton method return the instance *
     */
    public static MDMSharedPreference getInstance(Context context) {
        if (mRef == null) {
            mRef = new MDMSharedPreference();
            mPref = context.getSharedPreferences(
                    "MDMPreference", 0);
            return mRef;
        }
        return mRef;
    }

    /**
     * Put long value into sharedpreference *
     */
    public void putLong(String key, long value) {
        try {
            mEditor = mPref.edit();
            mEditor.putLong(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get long value from sharedpreference *
     */
    public long getLong(String key) {
        try {
            long lvalue;
            lvalue = mPref.getLong(key, 0);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Put int value into sharedpreference *
     */
    public void putInt(String key, int value) {
        try {
            mEditor = mPref.edit();
            mEditor.putInt(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get int value from sharedpreference *
     */
    public int getInt(String key) {
        try {
            int lvalue;
            lvalue = mPref.getInt(key, 0);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Put String value into sharedpreference *
     */
    public void putString(String key, String value) {
        try {
            mEditor = mPref.edit();
            mEditor.putString(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get String value from sharedpreference *
     */
    public String getString(String key) {
        try {
            String lvalue;
            lvalue = mPref.getString(key, "");
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Put String value into sharedpreference *
     */
    public void putBoolean(String key, Boolean value) {
        try {
            mEditor = mPref.edit();
            mEditor.putBoolean(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get String value from sharedpreference *
     */
    public Boolean getBoolean(String key) {
        try {
            Boolean lvalue;
            lvalue = mPref.getBoolean(key, false);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void saveHashMap(String mapName, Map<String, Boolean> inputMap) {
        // SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        if (mPref != null) {
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            Editor editor = mPref.edit();
            editor.remove(mapName).commit();
            editor.putString(mapName, jsonString);
            editor.commit();
        }
    }

    public Map<String, Boolean> getHashMap(String mapName) {
        Map<String, Boolean> outputMap = new HashMap<String, Boolean>();
        // SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        try {
            if (mPref != null) {
                String jsonString = mPref.getString(mapName, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    Boolean value = (Boolean) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }

    public void saveStringHashMap(String mapName, Map<String, String> inputMap) {
        // SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        if (mPref != null) {
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            Editor editor = mPref.edit();
            editor.remove(mapName).commit();
            editor.putString(mapName, jsonString);
            editor.commit();
        }
    }

    public Map<String, String> getStringHashMap(String mapName) {
        Map<String, String> outputMap = new HashMap<String, String>();
        // SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        try {
            if (mPref != null) {
                String jsonString = mPref.getString(mapName, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }

    /**
     * Put List value into shared preference *
     */
    public void putCoursesList(String key, ArrayList arr) {
        try {
            mEditor = mPref.edit();
            Set<String> set = new HashSet<String>();
            set.addAll(arr);
            mEditor.putStringSet(key, set);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get long value from sharedpreference *
     */
    public ArrayList<String> getCoursesList(String key) {
        ArrayList<String> sample = null;
        try {
            Set<String> set = mPref.getStringSet(key, null);
            sample = new ArrayList<String>(set);

            return sample;
        } catch (Exception e) {
            e.printStackTrace();
            return sample;
        }
    }


    /**
     *
     */
    public void clearAllPreferenceData() {
        Editor editor = mPref.edit();
        editor.clear();
        editor.commit();
    }

}
