package subversion_team.cs.brandeis.edu.lifesimulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhengyangzhou on 11/14/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";
    private static final String TABLE_NAME2 = "achievements";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "pass";
    protected static final String  COLUMN_achievement1 = "achievement1";
    private static String stupid= "Stupid test";

    SQLiteDatabase db;


    private static final String TABLE_CREATE = "create table "+ TABLE_NAME +"( "
            +COLUMN_ID+ " integer primary key autoincrement, "
            +COLUMN_EMAIL+" text not null, "
            + COLUMN_PASS +" text not null);";

    private static final String TABLE_CREATE2 = "create table "+ TABLE_NAME2 +"( "
            +COLUMN_ID+ " integer primary key autoincrement, "
            +COLUMN_EMAIL+" text not null, "
            + COLUMN_achievement1 +" text not null);";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE2);
        this.db = db;
    }

    public void insertContact(Contact c){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_PASS, c.getPass());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public void updateAchievement(String achievementName, String val, String email){
        db = this.getWritableDatabase();
        String whereClause = "email = " + "'" + email + "'";
        ContentValues values = new ContentValues();
        Log.d(stupid,val);
        values.put(achievementName, val);
        db.update(TABLE_NAME2,values,whereClause,null);
        db.close();
    }

    public String getAchievement(String email){
        db = this.getReadableDatabase();
        String query = "select email, achievement1 from "+ TABLE_NAME2;
        Cursor cursor = db.rawQuery(query,null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);
                if(a.equals(email)){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }

    public boolean isEmailExisted(String email){
        db = this.getReadableDatabase();
        String query = "select email from "+ TABLE_NAME + " where email = ?";
        Cursor cursor = db.rawQuery(query,new String[] {email});
//        String a  = null;
//        if(cursor.moveToFirst()){
//            do{
//                a = cursor.getString(0);
//                if(a.equals(email) && a != null){
//                    break;
//                }
//            }while(cursor.moveToNext());
//        }
        if(!cursor.moveToFirst()){
            return false;
        }else{
            Log.d("Test", DatabaseUtils.dumpCursorToString(cursor));
            return true;
        }
    }

    public String searchPass(String email){
        db = this.getReadableDatabase();
        String query = "select email, pass from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);
                if(a.equals(email)){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
