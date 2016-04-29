package wff.com.androidsummary;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by wufeifei on 2016/4/20.
 */
public class MyContentProvider extends ContentProvider {
    private static final String TAG = MyContentProvider.class.getName();
    private static final String AUTHORITIES = "wff.com.androidsummary.provider";
    private DbHelper dbHelper;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITIES, "student", 99);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext(), "mydb");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int type = uriMatcher.match(uri);
        Log.i(TAG, "uri 匹配" + type);
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public MyContentProvider() {
        super();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }

    @Nullable
    @Override
    public Uri canonicalize(Uri url) {
        return super.canonicalize(url);
    }

    @Nullable
    @Override
    public Uri uncanonicalize(Uri url) {
        return super.uncanonicalize(url);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        return super.openFile(uri, mode);
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode, CancellationSignal signal) throws FileNotFoundException {
        ParcelFileDescriptor.open(new File(""),ParcelFileDescriptor.MODE_READ_ONLY);
        return super.openFile(uri, mode, signal);
    }

    @Nullable
    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
        return super.openAssetFile(uri, mode);
    }

    @Nullable
    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String mode, CancellationSignal signal) throws FileNotFoundException {
        return super.openAssetFile(uri, mode, signal);
    }

    @Nullable
    @Override
    public String[] getStreamTypes(Uri uri, String mimeTypeFilter) {
        return super.getStreamTypes(uri, mimeTypeFilter);
    }

    @Nullable
    @Override
    public AssetFileDescriptor openTypedAssetFile(Uri uri, String mimeTypeFilter, Bundle opts) throws FileNotFoundException {
        return super.openTypedAssetFile(uri, mimeTypeFilter, opts);
    }

    @Nullable
    @Override
    public AssetFileDescriptor openTypedAssetFile(Uri uri, String mimeTypeFilter, Bundle opts, CancellationSignal signal) throws FileNotFoundException {
        return super.openTypedAssetFile(uri, mimeTypeFilter, opts, signal);
    }

    @NonNull
    @Override
    public <T> ParcelFileDescriptor openPipeHelper(Uri uri, String mimeType, Bundle opts, T args, PipeDataWriter<T> func) throws FileNotFoundException {
        return super.openPipeHelper(uri, mimeType, opts, args, func);
    }

    @Override
    protected boolean isTemporary() {
        return super.isTemporary();
    }

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        super.attachInfo(context, info);
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        return super.applyBatch(operations);
    }

    @Nullable
    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        return super.call(method, arg, extras);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }
}
