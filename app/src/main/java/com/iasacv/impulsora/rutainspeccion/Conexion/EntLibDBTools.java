package com.iasacv.impulsora.rutainspeccion.Conexion;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Administrator on 19/06/2015.
 */
public class EntLibDBTools extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios
    private static String sqlISMUSUAR = "CREATE TABLE [ISMUSUAR] (\n" +
            "[USUARCVE] INTEGER  NOT NULL PRIMARY KEY,\n" +
            "[USUARNOC] VARCHAR(50)  NULL,\n" +
            "[USUARRFC] VARCHAR(15)  NULL,\n" +
            "[USUARPWD] VARCHAR(100)  NULL,\n" +
            "[ROLESCVE] INTEGER  NULL,\n" +
            "[USUARFVP] DATE  NULL,\n" +
            "[USUARFAL] DATE  NULL,\n" +
            "[USUAREML] VARCHAR(200)  NULL,\n" +
            "[USUARSTS] VARCHAR(1)  NULL,\n" +
            "[USUARUSO] VARCHAR(1)  NULL\n" +
            ");";

    //Sentencia SQL para crear la tabla de Ciclos
    private static String sqlIGMCICLO = "CREATE TABLE [IGMCICLO] (" +
            "[CICLOCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[CICLONOM] VARCHAR(50)  NULL," +
            "[CICLOINI] DATE  NULL," +
            "[CICLOFIN] DATE  NULL," +
            "[CICLONOA] VARCHAR(50)  NULL," +
            "[CICLOSTS] VARCHAR(1)  NULL," +
            "[CICLOUSO] VARCHAR(1)  NULL);";

    //Sentencia SQL para crear la tabla de Ruta de inspeccion cabecero
    private static String sqlBRTINSCA = "CREATE TABLE [BRTINSCA] (" +
            "[USUARCVE] INTEGER  NOT NULL," +
            "[INSCAFCH] DATE  NULL," +
            "[CICLOCVE] INTEGER  NOT NULL," +
            "[INSCASTS] VARCHAR(1)  NULL," +
            "[INSCAUSO] VARCHAR(1)  NULL," +
            "PRIMARY KEY ([USUARCVE],[INSCAFCH],[CICLOCVE])" +
            ");";

    //Direccion de la base de datos
    private static String DB_PATH = "/data/data/com.iasacv.impulsora.rutainspeccion/databases/";
    private static String DB_NAME = "COSECHAS";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public EntLibDBTools(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void CreateDataBase() throws IOException{
        boolean dbExist = CheckDataBase();
        if(!dbExist)
        {
            this.getReadableDatabase();
        }
    }

    public boolean CheckDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //No existe la base de datos
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creacion de las tablas
        db.execSQL(sqlISMUSUAR);
        db.execSQL(sqlIGMCICLO);
        db.execSQL(sqlBRTINSCA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la version anterior de las tablas
        db.execSQL("DROP TABLE IF EXISTS ISMUSUAR");
        db.execSQL("DROP TABLE IF EXISTS IGMCICLO");
        db.execSQL("DROP TABLE IF EXISTS BRTINSCA");
        //Se crea la nueva version de la tabla
        db.execSQL(sqlISMUSUAR);
        db.execSQL(sqlIGMCICLO);
        db.execSQL(sqlBRTINSCA);
    }

    public void dropTable(){
        myDataBase = this.getReadableDatabase();
        //Se elimina la version anterior de las tablas
        myDataBase.execSQL("DROP TABLE IF EXISTS ISMUSUAR");
        myDataBase.execSQL("DROP TABLE IF EXISTS IGMCICLO");
        myDataBase.execSQL("DROP TABLE IF EXISTS BRTINSCA");
        //Se crea la nueva version de las tabla
        myDataBase.execSQL(sqlISMUSUAR);
        myDataBase.execSQL(sqlIGMCICLO);
        myDataBase.execSQL(sqlBRTINSCA);
    }

    public Cursor executeCursor(String query) {
        Cursor cursor = null;
        try {
            myDataBase = this.getWritableDatabase();
            cursor = myDataBase.rawQuery(query, null);
        } catch (Exception e) {
            throw e;
        }
        return  cursor;
    }

    public void insert(String query) {
        try {
            myDataBase = this.getWritableDatabase();
            myDataBase.beginTransactionNonExclusive();
            SQLiteStatement stmt = myDataBase.compileStatement(query);
            stmt.execute();
            stmt.clearBindings();
            myDataBase.setTransactionSuccessful();
            myDataBase.endTransaction();
            myDataBase.close();
        } catch (Exception e) {
            throw e;
        }
    }
}
