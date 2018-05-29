package top.rizix.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by robin on 18-4-21.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_SCHEDULE = "create table Schedule ("
            + "id integer primary key autoincrement, "
            + "schedule_date text, "
            + "t01 text," + "t02 text," + "t03 text," + "t04 text,"
            + "t11 text," + "t12 text," + "t13 text," + "t14 text,"
            + "t21 text," + "t22 text," + "t23 text," + "t24 text,"
            + "t31 text," + "t32 text," + "t33 text," + "t34 text,"
            + "t41 text," + "t42 text," + "t43 text," + "t44 text,"
            + "t51 text," + "t52 text," + "t53 text," + "t54 text,"
            + "t61 text," + "t62 text," + "t63 text," + "t64 text,"
            + "t71 text," + "t72 text," + "t73 text," + "t74 text,"
            + "t81 text," + "t82 text," + "t83 text," + "t84 text,"
            + "t91 text," + "t92 text," + "t93 text," + "t94 text,"
            + "t101 text," + "t102 text," + "t103 text," + "t104 text,"
            + "t111 text," + "t112 text," + "t113 text," + "t114 text,"
            + "t121 text," + "t122 text," + "t123 text," + "t124 text,"
            + "t131 text," + "t132 text," + "t133 text," + "t134 text,"
            + "t141 text," + "t142 text," + "t143 text," + "t144 text,"
            + "t151 text," + "t152 text," + "t153 text," + "t154 text,"
            + "t161 text," + "t162 text," + "t163 text," + "t164 text,"
            + "t171 text," + "t172 text," + "t173 text," + "t174 text,"
            + "t181 text," + "t182 text," + "t183 text," + "t184 text,"
            + "t191 text," + "t192 text," + "t193 text," + "t194 text,"
            + "t201 text," + "t202 text," + "t203 text," + "t204 text,"
            + "t211 text," + "t212 text," + "t213 text," + "t214 text,"
            + "t221 text," + "t222 text," + "t223 text," + "t224 text,"
            + "t231 text," + "t232 text," + "t233 text," + "t234 text)";

    public static final String CREATE_EVENT = "create table Event ("
            + "id integer primary key autoincrement, "
            + "event text)";


    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCHEDULE);
        db.execSQL(CREATE_EVENT);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Schedule");
        db.execSQL("drop table if exists Event");
        onCreate(db);
    }
}
