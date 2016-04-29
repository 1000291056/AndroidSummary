package wff.com.androidsummary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;

/**
 * Created by wufeifei on 2016/4/21.
 */
public class DbImpl implements DbDao {
    private DbHelper dbHelper;
    private Context context;

    public DbImpl(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    @Override
    public long insert(String name, int age) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("age", age);
            return db.insert("student", null, contentValues);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.update("student", getContentValues(student), "id=?", new String[]{String.valueOf(student.getId())});
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    @Override
    public Student query(int id) {
        Student student = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(true, "student", null, "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
            while (cursor.moveToNext()) {
                student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex("id")));
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                student.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return student;
    }

    private ContentValues getContentValues(Object object) {
        ContentValues contentValues = new ContentValues();
        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field f :
                fields) {
            f.setAccessible(true);
            try {
//                f.getType() == int.class.;
                contentValues.put(f.getName(), f.get(object).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return contentValues;


    }
}
