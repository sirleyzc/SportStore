package com.example.myapplication.Services;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.myapplication.Entities.Branch;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BranchService {
    public ArrayList<Branch> cursorToArray(Cursor cursor){
        ArrayList<Branch> list = new ArrayList<>();
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                Branch branch = new Branch(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        Double.parseDouble(cursor.getString(4)),
                        Double.parseDouble(cursor.getString(5))
                );
                list.add(branch);
            }
        }
        return list;
    }
    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
