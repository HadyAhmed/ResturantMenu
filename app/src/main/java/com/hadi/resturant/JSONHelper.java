package com.hadi.resturant;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hadi.resturant.model.DataItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * This Class will help in exporting and importing Menu Items to JSON files.
 */
public class JSONHelper {
    private static final String FILE_NAME = "menu_items.txt";
    private static final String TAG = "JSONHelperOutput";

    /**
     * This method will export the menu items in JSON Object and add it to a file.txt
     *
     * @param context      the current context
     * @param dataItemList the list of d{@link DataItem} which will be stored in the file.
     * @return a stored JSON file or a false if not successful.
     */
    public static boolean exportToJSON(Context context, List<DataItem> dataItemList) {
        DataItems menuData = new DataItems();
        menuData.setDataItems(dataItemList);

        Gson gson = new Gson();
        String jsonString = gson.toJson(menuData);
        Log.i(TAG, jsonString);

        FileOutputStream fileOutputStream = null;

        try {
            File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonString.getBytes());
            Toast.makeText(context, "File Exported Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        return false;
    }

    public static List<DataItem> importFromJSON(Context context) {
        return null;
    }

    static class DataItems {

        private List<DataItem> dataItems;

        public List<DataItem> getDataItems() {
            return dataItems;
        }

        public void setDataItems(List<DataItem> dataItems) {
            this.dataItems = dataItems;
        }
    }
}
