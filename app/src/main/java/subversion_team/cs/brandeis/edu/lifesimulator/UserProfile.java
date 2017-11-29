package subversion_team.cs.brandeis.edu.lifesimulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by xichen on 11/1/17.
 * Modified by amgoncalves on 11/28/2017
 */

@SuppressWarnings("deprecation")
public class UserProfile extends AppCompatActivity {

    private static DatabaseHelper db;
    private AchievementCursorAdapter adapter;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        initDB();
    }

    protected void initDB() {
        db = new DatabaseHelper(this);
        userEmail = db.getCurrentUserEmail();
        Log.d("CURRENT EMAIL", "Email = " + userEmail);
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT " +
                        DatabaseHelper.COL_ID + ", " +
                        DatabaseHelper.A_COL_NAME + ", " +
                        DatabaseHelper.A_COL_DESCRIPTION + ", " +
                        DatabaseHelper.A_COL_ICON + ", " +
                        DatabaseHelper.A_COL_EARNED +
                        " FROM " + DatabaseHelper.A_TABLE_NAME,
                null);
        startManagingCursor(cursor);
        String[] from = { DatabaseHelper.A_COL_NAME, DatabaseHelper.A_COL_DESCRIPTION };
        int[] to = new int[]{ R.id.achievement_name, R.id.achievement_description };
        adapter = new AchievementCursorAdapter(this, R.layout.achievement_entry, cursor, from, to);
        ListView achievementListView = (ListView) findViewById(R.id.achievement_listview);
        achievementListView.setAdapter(adapter);
    }

    private class AchievementCursorAdapter extends SimpleCursorAdapter {
        Context context;
        LayoutInflater inflator;
        Cursor cursor;

        public AchievementCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
            this.cursor = c;
            this.context = context;
            this.inflator = LayoutInflater.from(context);
            cursor.moveToFirst();
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View view = inflator.inflate(R.layout.achievement_entry, null);
            return view;
        }

        @Override
        public void bindView(View view, final Context context, final Cursor cursor) {
            TextView nameTextView = (TextView) view.findViewById(R.id.achievement_name);
            TextView descriptionTextView = (TextView) view.findViewById(R.id.achievement_description);
            CheckBox hasEarnedCheckBox = (CheckBox) view.findViewById(R.id.achievement_hasEarned);

            String acheviementName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.A_COL_NAME));
            if (nameTextView != null)
                nameTextView.setText(acheviementName);
            if (descriptionTextView != null)
                descriptionTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.A_COL_DESCRIPTION)));
            if (hasEarnedCheckBox != null) {
                //hasEarnedCheckBox.setChecked(DatabaseHelper.convertEarned(cursor.getColumnIndex(DatabaseHelper.A_COL_EARNED)));
                hasEarnedCheckBox.setChecked(db.hasUserEarnedAchievement(userEmail, acheviementName));
                hasEarnedCheckBox.setEnabled(false);
            }

            cursor.moveToNext();
        }
    }
}
