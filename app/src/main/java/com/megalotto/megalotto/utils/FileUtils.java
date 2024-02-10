package com.megalotto.megalotto.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileUtils {
    private static final boolean DEBUG = false;
    static final String TAG = "FileUtils";

    private FileUtils() {
    }

    public static boolean isLocalStorageDocument(Uri uri) {
        return LocalStorageProvider.AUTHORITY.equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String[] projection = {"_data"};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow("_data");
                return cursor.getString(column_index);
            } else if (cursor != null) {
                cursor.close();
                return null;
            } else {
                return null;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getPath(Context context, Uri uri) {
        boolean isKitKat = Build.VERSION.SDK_INT >= 19;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isLocalStorageDocument(uri)) {
                return DocumentsContract.getDocumentId(uri);
            }
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                String docId2 = DocumentsContract.getDocumentId(uri);
                String[] split2 = docId2.split(":");
                String type = split2[0];
                Uri contentUri2 = null;
                if ("image".equals(type)) {
                    contentUri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String[] selectionArgs = {split2[1]};
                return getDataColumn(context, contentUri2, "_id=?", selectionArgs);
            }
        } else if (FirebaseAnalytics.Param.CONTENT.equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public static class LocalStorageProvider extends DocumentsProvider {
        public static final String AUTHORITY = "com.ianhanniballake.localstorage.documents";
        private final String[] DEFAULT_ROOT_PROJECTION = {"root_id", "flags", "title", "document_id", "icon", "available_bytes"};
        private final String[] DEFAULT_DOCUMENT_PROJECTION = {"document_id", "_display_name", "flags", "mime_type", "_size", "last_modified"};

        @Override
        public Cursor queryRoots(String[] projection) {
            MatrixCursor result = new MatrixCursor(projection != null ? projection : this.DEFAULT_ROOT_PROJECTION);
            File homeDir = Environment.getExternalStorageDirectory();
            MatrixCursor.RowBuilder row = result.newRow();
            row.add("root_id", homeDir.getAbsolutePath());
            row.add("document_id", homeDir.getAbsolutePath());
            row.add("flags", 3);
            row.add("available_bytes", Long.valueOf(homeDir.getFreeSpace()));
            return result;
        }

        @Override
        public String createDocument(String parentDocumentId, String mimeType, String displayName) {
            File newFile = new File(parentDocumentId, displayName);
            try {
                newFile.createNewFile();
                return newFile.getAbsolutePath();
            } catch (IOException e) {
                Log.e(LocalStorageProvider.class.getSimpleName(), "Error creating new file " + newFile);
                return null;
            }
        }

        @Override
        public AssetFileDescriptor openDocumentThumbnail(String documentId, Point sizeHint, CancellationSignal signal) throws FileNotFoundException {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(documentId, options);
            int targetHeight = sizeHint.y * 2;
            int targetWidth = sizeHint.x * 2;
            int height = options.outHeight;
            int width = options.outWidth;
            options.inSampleSize = 1;
            if (height > targetHeight || width > targetWidth) {
                int halfHeight = height / 2;
                int halfWidth = width / 2;
                while (true) {
                    if (halfHeight / options.inSampleSize <= targetHeight && halfWidth / options.inSampleSize <= targetWidth) {
                        break;
                    }
                    options.inSampleSize *= 2;
                }
            }
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(documentId, options);
            FileOutputStream out = null;
            try {
                try {
                    File tempFile = File.createTempFile("thumbnail", null, getContext().getCacheDir());
                    out = new FileOutputStream(tempFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e(LocalStorageProvider.class.getSimpleName(), "Error closing thumbnail", e);
                    }
                    return new AssetFileDescriptor(ParcelFileDescriptor.open(tempFile, 268435456), 0L, -1L);
                } catch (Throwable e2) {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e3) {
                            Log.e(LocalStorageProvider.class.getSimpleName(), "Error closing thumbnail", e3);
                        }
                    }
                    throw e2;
                }
            } catch (IOException e4) {
                Log.e(LocalStorageProvider.class.getSimpleName(), "Error writing thumbnail", e4);
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e5) {
                        Log.e(LocalStorageProvider.class.getSimpleName(), "Error closing thumbnail", e5);
                    }
                }
                return null;
            }
        }

        @Override
        public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder) {
            File[] listFiles;
            MatrixCursor result = new MatrixCursor(projection != null ? projection : this.DEFAULT_DOCUMENT_PROJECTION);
            File parent = new File(parentDocumentId);
            for (File file : parent.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    includeFile(result, file);
                }
            }
            return result;
        }

        @Override
        public Cursor queryDocument(String documentId, String[] projection) {
            MatrixCursor result = new MatrixCursor(projection != null ? projection : this.DEFAULT_DOCUMENT_PROJECTION);
            includeFile(result, new File(documentId));
            return result;
        }

        private void includeFile(MatrixCursor result, File file) {
            MatrixCursor.RowBuilder row = result.newRow();
            row.add("document_id", file.getAbsolutePath());
            row.add("_display_name", file.getName());
            String mimeType = getDocumentType(file.getAbsolutePath());
            row.add("mime_type", mimeType);
            int flags = file.canWrite() ? 6 : 0;
            if (mimeType.startsWith("image/")) {
                flags |= 1;
            }
            row.add("flags", Integer.valueOf(flags));
            row.add("_size", Long.valueOf(file.length()));
            row.add("last_modified", Long.valueOf(file.lastModified()));
        }

        @Override
        public String getDocumentType(String documentId) {
            File file = new File(documentId);
            if (file.isDirectory()) {
                return "vnd.android.document/directory";
            }
            int lastDot = file.getName().lastIndexOf(46);
            if (lastDot >= 0) {
                String extension = file.getName().substring(lastDot + 1);
                String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                if (mime != null) {
                    return mime;
                }
                return "application/octet-stream";
            }
            return "application/octet-stream";
        }

        @Override
        public void deleteDocument(String documentId) {
            new File(documentId).delete();
        }

        @Override
        public ParcelFileDescriptor openDocument(String documentId, String mode, CancellationSignal signal) throws FileNotFoundException {
            File file = new File(documentId);
            boolean isWrite = mode.indexOf(119) != -1;
            if (isWrite) {
                return ParcelFileDescriptor.open(file, 805306368);
            }
            return ParcelFileDescriptor.open(file, 268435456);
        }

        @Override
        public boolean onCreate() {
            return true;
        }
    }
}
