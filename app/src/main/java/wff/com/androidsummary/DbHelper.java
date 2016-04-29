package wff.com.androidsummary;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wufeifei on 2016/4/20.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DBNAME = "my.db";
    private static final String STUDENT_TABLE = "student";
    private static final String CREATE_SCHOOL_TABLE = "CREATE TABLE  IF NOT EXISTS " + STUDENT_TABLE + " (" + "id INT AUTO_INCREMENT DEFAULT 0,name VARCHAR(125) NOT NULL UNIQUE,age INT DEFAULT 0 " + ")";

    public DbHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    public DbHelper(Context context, String name) {
        super(context, name, null, VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCHOOL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + STUDENT_TABLE);
        db.execSQL(CREATE_SCHOOL_TABLE);

    }
}
