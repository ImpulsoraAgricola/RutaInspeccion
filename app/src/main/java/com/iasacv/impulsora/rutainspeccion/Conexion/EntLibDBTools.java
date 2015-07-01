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

    //Sentencia SQL para crear la tabla de Ciclos
    private static String sqlIGMCICLO = "CREATE TABLE [IGMCICLO] (" +
            "[CICLOCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[CICLONOM] VARCHAR(50)  NULL," +
            "[CICLOINI] DATE  NULL," +
            "[CICLOFIN] DATE  NULL," +
            "[CICLONOA] VARCHAR(50)  NULL," +
            "[CICLOSTS] VARCHAR(1)  NULL," +
            "[CICLOUSO] VARCHAR(1)  NULL);";

    //Sentencia SQL para crear la tabla de Tipo de articulo
    private static String sqlBACTIART = "CREATE TABLE [BACTIART] (" +
            "[TIARTCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[TIARTNOM] VARCHAR(50)  NOT NULL," +
            "[TIARTSTS] CHAR(1)  NOT NULL," +
            "[TIARTUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Tipo de inspeccion
    private static String sqlBACTIINS = "CREATE TABLE [BACTIINS] (" +
            "[TIINSCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[TIINSNOM] VARCHAR(50)  NOT NULL," +
            "[TIINSSTS] CHAR(1)  NOT NULL," +
            "[TIINSUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Etapa fenologica
    private static String sqlBACETAFE = "CREATE TABLE [BACETAFE] (" +
            "[ETAFECVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[ETAFENOM] VARCHAR(50)  NOT NULL," +
            "[ETAFESTS] CHAR(1)  NOT NULL," +
            "[ETAFEUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Potencial de rendimiento
    private static String sqlBACPOTRE = "CREATE TABLE [BACPOTRE] (" +
            "[POTRECVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[POTRENOM] VARCHAR(50)  NOT NULL," +
            "[POTRESTS] CHAR(1)  NOT NULL," +
            "[POTREUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Estado maleza
    private static String sqlBACESTMA = "CREATE TABLE [BACESTMA] (" +
            "[ESTMACVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[ESTMANOM] VARCHAR(50)  NOT NULL," +
            "[ESTMASTS] CHAR(1)  NOT NULL," +
            "[ESTMAUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Estado plaga
    private static String sqlBACESTPL = "CREATE TABLE [BACESTPL] (" +
            "[ESTPLCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[ESTPLNOM] VARCHAR(50)  NOT NULL," +
            "[ESTPLSTS] CHAR(1)  NOT NULL," +
            "[ESTPLUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Estado enfermedad
    private static String sqlBACESTEN = "CREATE TABLE [BACESTEN] (" +
            "[ESTENCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[ESTENNOM] VARCHAR(50)  NOT NULL," +
            "[ESTENSTS] CHAR(1)  NOT NULL," +
            "[ESTENUSO] CHAR(1)  NOT NULL);";

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
        db.execSQL(sqlIGMCICLO);
        db.execSQL(sqlBACTIART);
        db.execSQL(sqlBACTIINS);
        db.execSQL(sqlBACETAFE);
        db.execSQL(sqlBACPOTRE);
        db.execSQL(sqlBACESTMA);
        db.execSQL(sqlBACESTPL);
        db.execSQL(sqlBACESTEN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable();
    }

    public void dropTable(){
        myDataBase = this.getReadableDatabase();
        //Se elimina la version anterior de las tablas
        myDataBase.execSQL("DROP TABLE IF EXISTS IGMCICLO");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACTIART");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACTIINS");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACETAFE");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACPOTRE");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACESTMA");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACESTPL");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACESTEN");

        //Se crea la nueva version de las tabla
        myDataBase.execSQL(sqlIGMCICLO);
        myDataBase.execSQL(sqlBACTIART);
        myDataBase.execSQL(sqlBACTIINS);
        myDataBase.execSQL(sqlBACETAFE);
        myDataBase.execSQL(sqlBACPOTRE);
        myDataBase.execSQL(sqlBACESTMA);
        myDataBase.execSQL(sqlBACESTPL);
        myDataBase.execSQL(sqlBACESTEN);
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
