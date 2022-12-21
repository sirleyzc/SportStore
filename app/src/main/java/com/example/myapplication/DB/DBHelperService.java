package com.example.myapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.myapplication.Entities.Service;

public class DBHelperService extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DBHelperService(Context context){
        super(context, "Services.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE SERVICES("+
                "id TEXT PRIMARY KEY,"+
                "name VARCHAR,"+
                "description TEXT,"+
                "price VARCHAR,"+
                "image TEXT"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS SERVICES");
    }

    //METODOS CRUD
    public void insertService(Service service){
        String sql = "INSERT INTO SERVICES VALUES(?, ?, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, service.getId());
        statement.bindString(2, service.getName());
        statement.bindString(3, service.getDescription());
        statement.bindString(4, String.valueOf(service.getPrice()));
        statement.bindString(5, service.getImage());

        statement.executeInsert();
    }

    public Cursor getServices(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SERVICES", null);
        return  cursor;
    }

    public Cursor getServiceById(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SERVICES WHERE id = "+id, null);
        return  cursor;
    }

    public void deleteServiceById(String id){
        sqLiteDatabase.execSQL("DELETE FROM SERVICES WHERE id = "+id);
    }

    public void updateService(String id, String name, String description, String price, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("price", price);
        contentValues.put("image", image);

        sqLiteDatabase.update("SERVICES", contentValues, "id = ?", new String[]{id});
    }

}

