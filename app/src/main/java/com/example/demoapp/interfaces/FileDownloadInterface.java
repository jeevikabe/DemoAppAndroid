package com.example.demoapp.interfaces;

import java.io.File;

/**
 * Created by User1 on 15-02-2018.
 */

public interface FileDownloadInterface
{
	File onDownloadComplete(File filePath, boolean b);

	void onDownloadProgress(long bytesDownloaded, long totalBytes);

    void onResponse(int statusCode, String response);
}
