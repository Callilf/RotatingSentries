package com.callil.rotatingsentries.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * The preferences allow to keep variable on the phone when the application is closed
 * 
 * @author Thomas
 */
public final class PrefsUtil {
	
	public class PreferencesConstants {
		public static final String DISPLAY_HITBOX = "displayHitbox";
		public static final String ANTIALIAS_TEXT = "antialiasText";
		public static final String RESCALING_ENABLED = "scalingEnabled";
		public static final String SOUND_ENABLED = "soundEnabled";
		public static final String PARTICLE_ENABLED = "particleEnabled";
		public static final String SLIDE_MOVE_ENABLED = "slideEnabled";
		public static final String KEY_POSITION_CENTER = "survival-kid-buttonPosition";
		public static final String KEY_POSITION_STRETCH = "survival-kid-buttonPosition-stretch";
		public static final String DIFFICULTY = "survival-kid-difficulty";
	}
	
	private static final String PREFERENCES_FILE = "com.callil.rotatingsentries.PREFERENCE_FILE";

	private static SharedPreferences sharedPref;
	
	private PrefsUtil() {
		// static class, the constructor can't be called
	}
	
	public static void initPreferences(Context context) {
		if (sharedPref == null) {
			sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
		}
	}
	
	/**
	 * Get a preference. Don't use this method to set value. Use setPrefs instead.
	 * 
	 * @return the preferences
	 */
	public static SharedPreferences getPrefs() {
		return sharedPref;
	}
	
	/**
	 * Set a preference
	 * 
	 * @param classe type of the preference : Boolean, String, Integer, Float, Long
	 * @param key String key of the preference. Use the classe Constants.PreferencesConstants
	 * @param value the value to put
	 */
	public static <T> void setPrefs(Class<T> classe, String key, T value) {
		SharedPreferences.Editor editor = sharedPref.edit();
		
		if (classe == Boolean.class || classe == boolean.class) {
			editor.putBoolean(key, (Boolean) value);
		}
		else if (classe == String.class) {
			editor.putString(key, (String) value);
		}
		else if (classe == Integer.class || classe == int.class) {
			editor.putInt(key, (Integer) value);
		}
		else if (classe == Float.class || classe == float.class) {
			editor.putFloat(key, (Float) value);
		}
		else if (classe == Long.class || classe == long.class) {
			editor.putLong(key, (Long) value);
		}
		else {
			throw new IllegalArgumentException("Could not put a pref with class " + classe);
		}
		// compatible only with android >= 11. Do not use
		//else if (classe.equals(Set.class)) {
		//	editor.putStringSet(key, (Set<String>) value);
		//}
		
		editor.commit();
	}
	
	/**
	 * Remove a preference
	 * 
	 * @param key the preference
	 */
	public static void removePref(String key) {
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.remove(key);
		editor.commit();
	}
	
}

