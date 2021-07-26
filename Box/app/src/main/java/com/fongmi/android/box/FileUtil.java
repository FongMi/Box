package com.fongmi.android.box;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.net.URLConnection;

public class FileUtil {

	private static final String VENDING = "com.android.vending";

	private static File getCachePath() {
		return App.get().getExternalCacheDir();
	}

	private static File getApkFile(String fileName) {
		return new File(getCachePath(), fileName);
	}

	public static void clearCache() {
		clearDir(getCachePath());
	}

	private static void clearDir(File dir) {
		if (dir == null) return;
		if (dir.isDirectory()) for (File file : dir.listFiles()) clearDir(file);
		dir.delete();
	}

	private static String getMimeType(String fileName) {
		String mimeType = URLConnection.guessContentTypeFromName(fileName);
		return TextUtils.isEmpty(mimeType) ? "*/*" : mimeType;
	}

	private static Uri getShareUri(File file) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.N ? Uri.fromFile(file) : FileProvider.getUriForFile(App.get(), App.get().getPackageName() + ".provider", file);
	}

	public static void download(String fileName) {
		FirebaseStorage.getInstance().getReference().child(fileName).getFile(getApkFile(fileName)).addOnSuccessListener((FileDownloadTask.TaskSnapshot taskSnapshot) -> openFile(getApkFile(fileName)));
		Notify.show("下載中...");
	}

	private static void openFile(File file) {
		Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
		intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, VENDING);
		intent.setDataAndType(getShareUri(file), getMimeType(file.getName()));
		App.get().startActivity(intent);
	}
}
