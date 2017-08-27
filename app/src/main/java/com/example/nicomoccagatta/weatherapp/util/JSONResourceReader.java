package com.example.nicomoccagatta.weatherapp.util;

/**
 * Created by alejandro on 8/27/17.
 */

import java.io.*;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * An object for reading from a JSON resource file and constructing an object from that resource file using Gson.
 * Got from: https://stackoverflow.com/questions/6349759/using-json-file-in-android-app-resources/6349913#6349913
 */
public class JSONResourceReader {

    // === [ Private Data Members ] ============================================

    // Our JSON, in string form.
    private String jsonString;
    private static final String LOGTAG = JSONResourceReader.class.getSimpleName();

    // === [ Public API ] ======================================================

    /**
     * Read from a resources file and create a {@link JSONResourceReader} object that will allow the creation of other
     * objects from this resource.
     *
     * @param resources An application {@link Resources} object.
     * @param id        The id for the resource to load, typically held in the raw/ folder.
     */
    public JSONResourceReader(Resources resources, int id) {
        try {
            InputStream resourceReader = resources.openRawResource(id);
            Writer writer = new StringWriter();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceReader, "UTF-8"));
                String line = reader.readLine();
                while (line != null) {
                    writer.write(line);
                    line = reader.readLine();
                }
            } catch (Exception e) {
                Log.e(LOGTAG, "Unhandled exception while using JSONResourceReader", e);
            } finally {
                try {
                    resourceReader.close();
                } catch (Exception e) {
                    Log.e(LOGTAG, "Unhandled exception while using JSONResourceReader", e);
                }
            }

            jsonString = writer.toString();
        } catch (Resources.NotFoundException e) {
            Log.e("JSON RESOURCE READER","Resource not found");
        }
    }

    /**
     * Build an object from the specified JSON resource using Gson.
     *
     * @param type The type of the object to build.
     * @return An object of type T, with member fields populated using Gson.
     */
    public <T> T constructUsingGson(Class<T> type) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, type);
    }
}
