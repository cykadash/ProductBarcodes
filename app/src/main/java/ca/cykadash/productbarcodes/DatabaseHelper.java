package ca.cykadash.productbarcodes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String PRODUCT_TABLE  = "PRODUCT_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME  = "NAME";
    public static final String COLUMN_SKU  = "SKU";
    public static final String COLUMN_CONSOLE  = "CONSOLE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "product.db", null, 1);
    }

    // called the first time the database is accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PRODUCT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_SKU + " TEXT, " + COLUMN_CONSOLE + " TEXT)";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(ProductModel productModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, productModel.getName());
        cv.put(COLUMN_SKU, productModel.getSku());
        cv.put(COLUMN_CONSOLE, productModel.getConsole());

        long insert = db.insert(PRODUCT_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }

    }


    public boolean deleteOne(ProductModel productModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + PRODUCT_TABLE + " WHERE " + COLUMN_ID + " = " + productModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }


    }

    public List<ProductModel> getEveryone() {

        List<ProductModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = "SELECT * FROM " + PRODUCT_TABLE + " ORDER BY " + COLUMN_NAME + ", " + COLUMN_CONSOLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int productID =cursor.getInt(0);
                String productName = cursor.getString(1);
                String productSku = cursor.getString(2);
                String productConsole = cursor.getString(3);

                ProductModel newProduct = new ProductModel(productID, productName, productSku, productConsole);
                returnList.add(newProduct);

            } while (cursor.moveToNext());
        }
        else {
            // failure. nothing happens
        }

        // close cursor and db then return
        cursor.close();
        db.close();
        return returnList;
    }

    public List<ProductModel> getSearch(String s) {

        List<ProductModel> returnList = new ArrayList<>();

        // get data from database

        String queryString = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + COLUMN_NAME + " LIKE '%" + s + "%'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int productID = cursor.getInt(0);
                String productName = cursor.getString(1);
                String productSku = cursor.getString(2);
                String productConsole = cursor.getString(3);

                ProductModel newProduct = new ProductModel(productID, productName, productSku, productConsole);
                returnList.add(newProduct);

            } while (cursor.moveToNext());
        }
        else {
            // failure. nothing happens
        }

        // close cursor and db then return
        cursor.close();
        db.close();
        return returnList;
    }
}
