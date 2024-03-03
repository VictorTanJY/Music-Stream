package com.example.musicstream.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fjor on 14/2/17.
 */

public final class AppUtil
{
    public static void popMessage(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getResourceId(Context context, View view)
    {
        String id = context.getResources().getResourceEntryName(view.getId());

        return id;
    }

    public static int getImageIdFromDrawable(Context context, String imageName)
    {
        int imageID = context.getResources().getIdentifier(imageName,"drawable", context.getPackageName());

        return imageID;
    }

    public static Bitmap getBitmapFromAsset(Context context, String image)
    {
        AssetManager assetManager = context.getAssets();
        InputStream stream = null;

        try
        {
            stream = assetManager.open(image);

            return BitmapFactory.decodeStream(stream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static String loadJsonFromAsset(String filename, Context context)
    {
        String json = null;

        try
        {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}