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

    //Sentencia SQL para crear la tabla de Sistema de produccion
    private static String sqlBACSIPRO = "CREATE TABLE [BACSIPRO] (" +
            "[SIPROCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[SIPRONOM] VARCHAR(50)  NOT NULL," +
            "[SIPROSTS] CHAR(1)  NOT NULL," +
            "[SIPROUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Arreglo topologico
    private static String sqlBACARTOP = "CREATE TABLE [BACARTOP] (" +
            "[ARTOPCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[ARTOPNOM] VARCHAR(50)  NOT NULL," +
            "[ARTOPSTS] CHAR(1)  NOT NULL," +
            "[ARTOPUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Tipo de riego
    private static String sqlBACTIRIE = "CREATE TABLE [BACTIRIE] (" +
            "[TIRIECVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[TIRIENOM] VARCHAR(50)  NOT NULL," +
            "[TIRIESTS] CHAR(1)  NOT NULL," +
            "[TIRIEUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Condiciones de desarrollo
    private static String sqlBACCONDI = "CREATE TABLE [BACCONDI] (" +
            "[CONDICVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[CONDINOM] VARCHAR(50)  NOT NULL," +
            "[CONDISTS] CHAR(1)  NOT NULL," +
            "[CONDIUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Recomendaciones
    private static String sqlBACRECOM = "CREATE TABLE [BACRECOM] (" +
            "[RECOMCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[RECOMNOM] VARCHAR(50)  NOT NULL," +
            "[RECOMSTS] CHAR(1)  NOT NULL," +
            "[RECOMUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Plagas
    private static String sqlBLCPLAGA = "CREATE TABLE [BLCPLAGA] (" +
            "[PLAGACVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[PLAGANOM] VARCHAR(50)  NOT NULL," +
            "[PLAGASTS] CHAR(1)  NOT NULL," +
            "[PLAGAUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Malezas
    private static String sqlBPCMALEZ = "CREATE TABLE [BPCMALEZ] (" +
            "[MALEZCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[MALEZNOM] VARCHAR(50)  NOT NULL," +
            "[MALEZSTS] CHAR(1)  NOT NULL," +
            "[MALEZUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Malezas
    private static String sqlBECENFER = "CREATE TABLE [BECENFER] (" +
            "[ENFERCVE] INTEGER  NOT NULL PRIMARY KEY," +
            "[ENFERNOM] VARCHAR(50)  NOT NULL," +
            "[ENFERSTS] CHAR(1)  NOT NULL," +
            "[ENFERUSO] CHAR(1)  NOT NULL);";

    //Sentencia SQL para crear la tabla de Planeacion detalle
    private static String sqlBATPLADE = "CREATE TABLE [BATPLADE] (" +
            "[USUARCVE] INTEGER  NOT NULL," +
            "[USUARNOM] VARCHAR(150)  NOT NULL," +
            "[CICLOCVE] INTEGER  NOT NULL," +
            "[PLANEFEC] DATE  NOT NULL," +
            "[PLADEFOL] INTEGER  NOT NULL," +
            "[TIINSCVE] INTEGER  NOT NULL," +
            "[TIARTCVE] INTEGER  NOT NULL," +
            "[PERSOCVE] INTEGER  NOT NULL," +
            "[PERSONOM] VARCHAR(250)  NOT NULL," +
            "[PRODUCVE] INTEGER  NOT NULL," +
            "[PRODUNOM] VARCHAR(250)  NOT NULL," +
            "[PREDICVE] INTEGER  NOT NULL," +
            "[PREDINOM] VARCHAR(50)  NOT NULL," +
            "[PREDILAT] FLOAT  NOT NULL," +
            "[PREDILON] FLOAT  NOT NULL," +
            "[LOTESCVE] INTEGER  NOT NULL," +
            "[LOTESNOM] VARCHAR(50)  NOT NULL," +
            "[LOTESLAT] FLOAT  NOT NULL," +
            "[LOTESLON] FLOAT  NOT NULL," +
            "[PLADEASE] INTEGER  NOT NULL," +
            "[ARTICNOC] VARCHAR(50)  NOT NULL," +
            "[PLADEACO] INTEGER  NOT NULL," +
            "[ARTICNOS] VARCHAR(50)  NOT NULL," +
            "[PLADESTS] VARCHAR(1)  NOT NULL," +
            "[PLADEUSO] VARCHAR(1)  NOT NULL,\n" +
            "PRIMARY KEY ([USUARCVE],[CICLOCVE],[PLANEFEC],[PLADEFOL]));";

    //Sentencia SQL para crear la tabla de Ruta de inspeccion
    private static String sqlBATRUINS = "CREATE TABLE [BATRUINS] (\n" +
            "[USUARCVE] INTEGER  NOT NULL,\n" +
            "[CICLOCVE] INTEGER  NOT NULL,\n" +
            "[PLANEFEC] DATE  NOT NULL,\n" +
            "[PLADEFOL] INTEGER  NOT NULL,\n" +
            "[RUINSFEI] DATETIME  NOT NULL,\n" +
            "[RUINSFEF] DATETIME  NULL,\n" +
            "[RUINSREC] VARCHAR(1)  NULL,\n" +
            "[SIPROCVE] INTEGER  NULL,\n" +
            "[ARTOPCVE] INTEGER  NULL,\n" +
            "[RUINSSIA] VARCHAR(1)  NULL,\n" +
            "[RUINSSUA] VARCHAR(1)  NULL,\n" +
            "[RUINSMAN] VARCHAR(1)  NULL,\n" +
            "[ETAFECVE] INTEGER  NULL,\n" +
            "[RUINSEXP] VARCHAR(1)  NULL,\n" +
            "[CONDICVE] INTEGER  NULL,\n" +
            "[RUINSORD] VARCHAR(1)  NULL,\n" +
            "[RUINSREG] VARCHAR(1)  NULL,\n" +
            "[RUINSUSA] VARCHAR(1)  NULL,\n" +
            "[RUINSHOR] VARCHAR(1)  NULL,\n" +
            "[RUINSAGU] VARCHAR(1)  NULL,\n" +
            "[RUINSINU] VARCHAR(1)  NULL,\n" +
            "[RUINSPOB] VARCHAR(1)  NULL,\n" +
            "[RUINSPRO] VARCHAR(1)  NULL,\n" +
            "[RUINSALT] VARCHAR(1)  NULL,\n" +
            "[RUINSAPL] VARCHAR(1)  NULL,\n" +
            "[RUINSTEM] VARCHAR(1)  NULL,\n" +
            "[RUINSFIT] VARCHAR(1)  NULL,\n" +
            "[RUINSPLA] VARCHAR(1)  NULL,\n" +
            "[MALEZCVE] INTEGER  NULL,\n" +
            "[ESTMACVE] INTEGER  NULL,\n" +
            "[PLAGACVE] INTEGER  NULL,\n" +
            "[ESTPLCVE] INTEGER  NULL,\n" +
            "[ENFERCVE] INTEGER  NULL,\n" +
            "[ESTENCVE] INTEGER  NULL,\n" +
            "[POTRECVE] INTEGER  NULL,\n" +
            "[RUINSSTS] VARCHAR(1)  NULL,\n" +
            "[RUINSUSO] VARCHAR(1)  NULL,\n" +
            "PRIMARY KEY ([USUARCVE],[CICLOCVE],[PLANEFEC],[PLADEFOL]));";

    //Sentencia SQL para crear la tabla de relacion Riego por Ruta de inspeccion
    private static String sqlBARRIEGO = "CREATE TABLE [BARRIEGO] (\n" +
            "[USUARCVE] INTEGER  NOT NULL,\n" +
            "[CICLOCVE] INTEGER  NOT NULL,\n" +
            "[PLANEFEC] DATE  NOT NULL,\n" +
            "[PLADEFOL] INTEGER  NOT NULL,\n" +
            "[TIRIECVE] INTEGER  NULL,\n" +
            "[RIEGOCAP] INTEGER  NULL,\n" +
            "[RIEGOOTR] VARCHAR(50)  NULL,\n" +
            "PRIMARY KEY ([USUARCVE],[CICLOCVE],[PLANEFEC],[PLADEFOL]));";

    //Sentencia SQL para crear la tabla de relacion Riego por Ruta de inspeccion
    private static String sqlBARRECOM = "CREATE TABLE [BARRECOM] (\n" +
            "[USUARCVE] INTEGER  NOT NULL,\n" +
            "[CICLOCVE] INTEGER  NOT NULL,\n" +
            "[PLANEFEC] DATE  NOT NULL,\n" +
            "[PLADEFOL] INTEGER  NOT NULL,\n" +
            "[RECOMCVE] INTEGER  NULL,\n" +
            "[RECOMOTR] VARCHAR(50)  NULL,\n" +
            "PRIMARY KEY ([USUARCVE],[CICLOCVE],[PLANEFEC],[PLADEFOL]));";

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
        db.execSQL(sqlBACSIPRO);
        db.execSQL(sqlBACARTOP);
        db.execSQL(sqlBACTIRIE);
        db.execSQL(sqlBACCONDI);
        db.execSQL(sqlBACRECOM);
        db.execSQL(sqlBLCPLAGA);
        db.execSQL(sqlBPCMALEZ);
        db.execSQL(sqlBECENFER);
        db.execSQL(sqlBATRUINS);
        db.execSQL(sqlBARRIEGO);
        db.execSQL(sqlBARRECOM);
        db.execSQL(sqlBATPLADE);
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
        myDataBase.execSQL("DROP TABLE IF EXISTS BACSIPRO");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACARTOP");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACTIRIE");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACCONDI");
        myDataBase.execSQL("DROP TABLE IF EXISTS BACRECOM");
        myDataBase.execSQL("DROP TABLE IF EXISTS BLCPLAGA");
        myDataBase.execSQL("DROP TABLE IF EXISTS BPCMALEZ");
        myDataBase.execSQL("DROP TABLE IF EXISTS BECENFER");
        myDataBase.execSQL("DROP TABLE IF EXISTS BATPLADE");
        myDataBase.execSQL("DROP TABLE IF EXISTS BATRUINS");
        myDataBase.execSQL("DROP TABLE IF EXISTS BARRIEGO");
        myDataBase.execSQL("DROP TABLE IF EXISTS BARRECOM");

        //Se crea la nueva version de las tabla
        myDataBase.execSQL(sqlIGMCICLO);
        myDataBase.execSQL(sqlBACTIART);
        myDataBase.execSQL(sqlBACTIINS);
        myDataBase.execSQL(sqlBACETAFE);
        myDataBase.execSQL(sqlBACPOTRE);
        myDataBase.execSQL(sqlBACESTMA);
        myDataBase.execSQL(sqlBACESTPL);
        myDataBase.execSQL(sqlBACESTEN);
        myDataBase.execSQL(sqlBACSIPRO);
        myDataBase.execSQL(sqlBACARTOP);
        myDataBase.execSQL(sqlBACTIRIE);
        myDataBase.execSQL(sqlBACCONDI);
        myDataBase.execSQL(sqlBACRECOM);
        myDataBase.execSQL(sqlBLCPLAGA);
        myDataBase.execSQL(sqlBPCMALEZ);
        myDataBase.execSQL(sqlBECENFER);
        myDataBase.execSQL(sqlBATPLADE);
        myDataBase.execSQL(sqlBATRUINS);
        myDataBase.execSQL(sqlBARRIEGO);
        myDataBase.execSQL(sqlBARRECOM);
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

    public Cursor executeQuery(String query) {
        Cursor cursor = null;
        try {
            myDataBase = this.getWritableDatabase();
            myDataBase.execSQL(query);
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
