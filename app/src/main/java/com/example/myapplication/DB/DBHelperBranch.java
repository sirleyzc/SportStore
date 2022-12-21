package com.example.myapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.myapplication.Entities.Branch;

public class DBHelperBranch extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DBHelperBranch(Context context){
        super(context, "SportStore.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE BRANCHES("+
                "id TEXT PRIMARY KEY,"+
                "name VARCHAR,"+
                "phone TEXT,"+
                "image TEXT"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
    }

    //METODOS CRUD
    public void insertBranch(Branch branch){
        String sql = "INSERT INTO BRANCHES VALUES(?, ?, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, branch.getId());
        statement.bindString(2, branch.getName());
        statement.bindString(3, branch.getPhone());
        statement.bindString(5, branch.getImage());

        statement.executeInsert();
    }

    public Cursor getBranches(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BRANCHES", null);
        return  cursor;
    }

    public Cursor getBranchById(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BRANCHES WHERE id = "+id, null);
        return  cursor;
    }

    public void deleteBranchById(String id){
        sqLiteDatabase.execSQL("DELETE FROM BRANCHES WHERE id = "+id);
    }

    public void updateBranch(String id, String name, String phone, String price, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("image", image);

        sqLiteDatabase.update("BRANCHES", contentValues, "id = ?", new String[]{id});
    }

}

