package com.drizzle.windownote;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Created by drizzle on 16/3/25.
 */
public class NoteUtils {
	private static Gson mGson;
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor mEditor;

	public NoteUtils getInstance(Context context) {
		mSharedPreferences = context.getSharedPreferences(Tag.PERFENCE_NAME, Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();
		mGson = new Gson();
		return this;
	}

	/**
	 * 存在则修改,不存在则新建
	 */
	public static void saveNote(NoteBean noteBean) {
		String cache = mSharedPreferences.getString(Tag.PERFENCE_NAME, "[]");
		List<NoteBean> noteBeans = mGson.fromJson(cache, new TypeToken<List<NoteBean>>() {
		}.getType());
		if (noteBeans.contains(noteBean)) {
			noteBeans.remove(noteBean);
		}
		noteBeans.add(0, noteBean);
		cache = mGson.toJson(noteBeans);
		mEditor.putString(Tag.PERFENCE_NAME, cache);
		mEditor.apply();
	}

	public static void removeNote(NoteBean noteBean) {
		String cache = mSharedPreferences.getString(Tag.PERFENCE_NAME, "[]");
		List<NoteBean> noteBeans = mGson.fromJson(cache, new TypeToken<List<NoteBean>>() {
		}.getType());
		if (noteBeans.contains(noteBean)) {
			noteBeans.remove(noteBean);
		}
		cache = mGson.toJson(noteBeans);
		mEditor.putString(Tag.PERFENCE_NAME, cache);
		mEditor.apply();
	}

	public static NoteBean getNote(int position) {
		String cache = mSharedPreferences.getString(Tag.PERFENCE_NAME, "[]");
		List<NoteBean> noteBeans = mGson.fromJson(cache, new TypeToken<List<NoteBean>>() {
		}.getType());
		return noteBeans.get(position);
	}

	public static List<NoteBean> getNoteList(){
		String cache = mSharedPreferences.getString(Tag.PERFENCE_NAME, "[]");
		List<NoteBean> noteBeans = mGson.fromJson(cache, new TypeToken<List<NoteBean>>() {
		}.getType());
		return noteBeans;
	}
}
