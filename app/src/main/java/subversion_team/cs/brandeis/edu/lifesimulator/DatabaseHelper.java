package subversion_team.cs.brandeis.edu.lifesimulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhengyangzhou on 11/14/17.
 * Edited by amgoncalves on 11/27/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "contacts.db";

    public static final String COL_ID = "_id";

    // Sessions table
    private static final String SESSION_TABLE_NAME = "session";
    private static final String SESSION_COL_CURRENT_USER_EMAIL = "current_user_email";

    private static final String[] SESSION_COLS = { COL_ID, SESSION_COL_CURRENT_USER_EMAIL };

    // Contacts table
    private static final String USERS_TABLE_NAME = "contacts";
    private static final String USERS_COL_EMAIL = "email";
    private static final String USERS_COL_PASS = "pass";

    private static final String[] USERS_COLS = { COL_ID, USERS_COL_EMAIL, USERS_COL_PASS };

    // Join Table recording all achievements a user has obtained
    private static final String JOIN_TABLE_NAME = "achievements";
    protected static final String JOIN_COL_USER_ID = "user_id";
    protected static final String  JOIN_COL_ACHIEVEMENT_ID = "achievement_id";

    private static final String[] COLS_JOIN = { COL_ID, JOIN_COL_USER_ID, JOIN_COL_ACHIEVEMENT_ID };

    // Achievements Table
    public static final String A_TABLE_NAME = "achievementslist";
    //public static final String A_COL_ID = "_id"; // achievement's primary key
    public static final String A_COL_NAME = "name"; // name of the achievement
    public static final String A_COL_DESCRIPTION = "description"; // short description of how to earn the achievement
    public static final String A_COL_ICON = "icon"; // string to image location
    public static final String A_COL_EARNED = "earned"; // integer, has the player earned this (1) or not (0)?

    private static final String[] A_COLUMNS = { COL_ID, A_COL_NAME, A_COL_DESCRIPTION, A_COL_ICON, A_COL_EARNED };

    // Achievement Data
    private List<String> allAchievementNames;
    public static final String a1Name = "n00b";
    public static final String a2Name = "First Generation";

    SQLiteDatabase db;

    // Builds Users table
    private static final String CREATE_USER_TABLE = "create table " + USERS_TABLE_NAME + "( "
            + COL_ID + " integer primary key autoincrement, "
            + USERS_COL_EMAIL + " text not null, "
            + USERS_COL_PASS + " text not null);";

    // Builds Users table
    private static final String CREATE_SESSION_TABLE = "create table " + SESSION_TABLE_NAME + "( "
            + COL_ID + " integer primary key autoincrement, "
            + SESSION_COL_CURRENT_USER_EMAIL + " text not null);";

    // Builds Join table
    private static final String CREATE_JOIN_TABLE = "create table " + JOIN_TABLE_NAME + "( "
            + COL_ID + " integer primary key autoincrement, "
            + JOIN_COL_USER_ID + " text not null, "
            + JOIN_COL_ACHIEVEMENT_ID + " integer not null, "
            + "UNIQUE( " + JOIN_COL_USER_ID + ", " + JOIN_COL_ACHIEVEMENT_ID + ") "
            + "ON CONFLICT REPLACE);";

    // Builds Achievements table
    private static final String CREATE_ACHIEVEMENT_TABLE = "CREATE TABLE " + A_TABLE_NAME + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            A_COL_NAME + " TEXT UNIQUE, " +
            A_COL_DESCRIPTION + " TEXT UNIQUE, " +
            A_COL_ICON + " TEXT, " +
            A_COL_EARNED + " INTEGER DEFAULT 0" +
            ")";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SESSION_TABLE);
        db.execSQL(CREATE_JOIN_TABLE);
        db.execSQL(CREATE_ACHIEVEMENT_TABLE);
        this.db = db;
        addDefaultSession(db);
        List<Achievement> toAdd = cannedAchievements();
        for (Achievement a : toAdd)
            db.insert(A_TABLE_NAME, null,  buildCannedAChievementColumns(a));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String q1 = "DROP TABLE IF EXISTS " + USERS_TABLE_NAME;
        String q2 = "DROP TABLE IF EXISTS " + JOIN_TABLE_NAME;
        String q3 = "DROP TABLE IF EXISTS " + A_TABLE_NAME;
        String q4 = "DROP TABLE IF EXISTS " + SESSION_TABLE_NAME;
        db.execSQL(q1);
        db.execSQL(q2);
        db.execSQL(q3);
        db.execSQL(q4);
        this.onCreate(db);
    }

    /**
     * Inserts a new user into the Users database table.
     *
     * @param c  The contact object.
     */
    public void insertContact(Contact c){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COL_EMAIL, c.getEmail());
        values.put(USERS_COL_PASS, c.getPass());
        db.insert(USERS_TABLE_NAME,null,values);
        db.close();
    }

    /**
     * Adds a single session to the database using a placeholder instead of an email ("default").
     * Should only be done upon table creation.
     */
    private void addDefaultSession(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(SESSION_COL_CURRENT_USER_EMAIL, "default");
        db.insert(SESSION_TABLE_NAME, null, cv);
    }

    /**
     * Get the current user's email address from the session's table.
     * @return  String containing the current user's email address.
     */
    public String getCurrentUserEmail() {
        db = this.getReadableDatabase();
        Cursor c = db.query(
                SESSION_TABLE_NAME,
                SESSION_COLS,
                COL_ID + " = ?",
                new String[] { String.valueOf(1) },
                null,
                null,
                null,
                null
        );
        c.moveToFirst();
        return c.getString(1);
    }

    /**
     * Updates the email address stored in the sessions table.
     * @param email
     */
    public void updateCurrentUser(String email) {
        db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(SESSION_COL_CURRENT_USER_EMAIL, email);
        db.update(
                SESSION_TABLE_NAME,
                args,
                COL_ID + " = 1",
                null);
    }

    // Get Achievement's primary key id using achievement name
    private int getAchievementPrimaryKey(String achievementName) {
        db = this.getReadableDatabase();
        Cursor achievementCursor = db.query(
                A_TABLE_NAME,
                A_COLUMNS,
                A_COL_NAME + " = ?",
                new String[] { String.valueOf(achievementName) },
                null,
                null,
                null,
                null
        );
        if (achievementCursor == null)
            return -1;
        else if (achievementCursor.getCount() == 0)
            return -1;
        achievementCursor.moveToFirst();
        return achievementCursor.getInt(0);
    }

    // Get user's primary key id using email
    private int getUserPrimaryKey(String userEmail) {
        db = this.getReadableDatabase();
        Cursor userCursor = db.query(
                USERS_TABLE_NAME,
                USERS_COLS,
                USERS_COL_EMAIL + " = ?",
                new String[] { String.valueOf(userEmail) },
                null,
                null,
                null,
                null
        );
        if (userCursor == null)
            return -1;
        else if (userCursor.getCount() == 0)
            return -1;
        userCursor.moveToFirst();
        return userCursor.getInt(0);
    }

    /**
     * Add an entry to the achievement Join Table.  User's email is used to
     * lookup user's primary key ID, achievement's name is used to lookup the
     * achievement's primary key ID.
     *
     * @param achievementName  The name of the achievement.
     * @param userEmail  The user's email.
     */
    public void addAchievement(String achievementName, String userEmail) {
        int userID = getUserPrimaryKey(userEmail);
        int achievementID = getAchievementPrimaryKey(achievementName);
        if (userID == -1 || achievementID == -1)
            Log.d("DATABSE HELPER", "Not found, userID = " + userID + ", achID = " + achievementID);
        else {
            db = this.getWritableDatabase();
            ContentValues cols = new ContentValues();
            cols.put(JOIN_COL_USER_ID, userID);
            cols.put(JOIN_COL_ACHIEVEMENT_ID, achievementID);
            db.insert(JOIN_TABLE_NAME, null, cols);
            Log.d("DATABASE HELPER", "user " + userEmail + " earned " + achievementName);
            db.close();
        }
    }

    /**
     *
     *
     * @param userEmail
     * @param achievementName
     * @return
     */
    public boolean hasUserEarnedAchievement(String userEmail, String achievementName) {
        int userID = getUserPrimaryKey(userEmail);
        int achievementID = getAchievementPrimaryKey(achievementName);
        db = this.getReadableDatabase();
        Cursor cursor = db.query(
                JOIN_TABLE_NAME,
                COLS_JOIN,
                JOIN_COL_USER_ID + " = ? AND " + JOIN_COL_ACHIEVEMENT_ID + " = ?",
                new String[] { String.valueOf(userID), String.valueOf(achievementID) },
                null,
                null,
                null,
                null
        );
        return cursor != null;
    }

    /**
    public String getAchievement(String email){
        db = this.getReadableDatabase();
        String query = "SELECT " + JOIN_COL_USER_ID + ", " + JOIN_COL_ACHIEVEMENT_ID + " FROM " + JOIN_TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do {
                a = cursor.getString(0);
                if(a.equals(email)){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }*/

    public boolean isEmailExisted(String email){
        db = this.getReadableDatabase();
        String query = "SELECT " + USERS_COL_EMAIL + " FROM " + USERS_TABLE_NAME + " WHERE " + USERS_COL_EMAIL + " = ?";
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
        String query = "SELECT " + USERS_COL_EMAIL + ", " + USERS_COL_PASS + " FROM " + USERS_TABLE_NAME;
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

    /**
     * Builds a ContentValues object of columns from the Achievements table
     * for use in the Achievements ListView.
     *
     * @param achievement  An achievement object.
     * @return  The ContentValues object.
     */
    private ContentValues buildCannedAChievementColumns(Achievement achievement) {
        ContentValues cols = new ContentValues();
        cols.put(A_COL_NAME, achievement.getName());
        cols.put(A_COL_DESCRIPTION, achievement.getDescription());
        cols.put(A_COL_ICON, achievement.getIcon());
        cols.put(A_COL_EARNED, convertEarned(achievement.hasEarned()));
        return cols;
    }

    /**
     * List of canned achievements.
     *
     * @return  The list of achievements.
     */
    public List<Achievement> cannedAchievements() {
        Achievement a1 = new Achievement(a1Name, "Create a new user account, wow, that was easy, wasn't it?", "n/a", false);
        Achievement a2 = new Achievement(a2Name, "Start one game of life.", "n/a", false);

        List<Achievement> achievements = new LinkedList<Achievement>();
        achievements.add(a1);
        achievements.add(a2);

        allAchievementNames = new LinkedList<String>();
        allAchievementNames.add(a1Name);
        allAchievementNames.add(a2Name);

        return achievements;
    }

    /**
     * Column "earned" in table achievements is an integer.  This function
     * converts a boolean value into 1 or 0.
     *
     * Has earned = True = 1
     * Has not earned = False = 0
     *
     * @param hasEarned  A boolean value
     * @return  The integer conversion.
     */
    public static int convertEarned(boolean hasEarned) {
        return hasEarned ? 1 : 0;
    }

    /**
     * Column "earned" in table achievements is an integer.  This function
     * converts an value into true or false.
     *
     * Has earned = 1 = True
     * Has not earned = 0 = False
     *
     * @param hasEarned  An integer value.
     * @return  The boolean conversion.
     */
    public static boolean convertEarned(int hasEarned) {
        return (hasEarned == 1) ? true : false;
    }

    /**
     * Use to add new achievements to the database.  "Earned" should be
     * 0 (default).
     *
     * @param achievement  An Achievement object.
     */
    public void addAchievement(Achievement achievement) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(A_TABLE_NAME, null, buildCannedAChievementColumns(achievement));
        db.close();
    }

    /**
     * Fetches a record from the achievement table and converts it into
     * an Achievement object.
     *
     * @param index  The record's primary key.
     * @return  An Achievement conversion of the record.
     */
    public Achievement getAchievement(int index) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                A_TABLE_NAME,
                A_COLUMNS,
                COL_ID + " = ?",
                new String[] { String.valueOf(index) },
                null,
                null,
                null,
                null
        );
        if (cursor != null)
            cursor.moveToFirst();
        Achievement achievement = new Achievement();
        achievement.setID(Integer.parseInt(cursor.getString(0)));
        achievement.setName(cursor.getString(1));
        achievement.setDescription(cursor.getString(2));
        achievement.setIcon(cursor.getString(3));
        achievement.setEarned(convertEarned(Integer.parseInt(cursor.getString(4))));
        return achievement;
    }

    /**
     * Fetches all achievement records from the achievement table as a list of
     * Achievement objects.
     *
     * @return  A list of all records from the achievement table represented as
     * Achievement objects.
     */
    public List<Achievement> getAllAchievements() {
        List<Achievement> achievements = new LinkedList<Achievement>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + A_TABLE_NAME, null);
        Achievement achievement = null;
        if (cursor.moveToFirst()) {
            do {
                achievement = new Achievement();
                achievement.setID(Integer.parseInt(cursor.getString(0)));
                achievement.setName(cursor.getString(1));
                achievement.setDescription(cursor.getString(2));
                achievement.setIcon(cursor.getString(3));
                achievement.setEarned(convertEarned(Integer.parseInt(cursor.getString(4))));
                achievements.add(achievement);
            } while (cursor.moveToNext());
        }
        return achievements;
    }
}
