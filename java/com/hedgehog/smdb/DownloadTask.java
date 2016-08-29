package com.hedgehog.smdb;

public interface DownloadTask {

    public void downloadComplete(String response, String source);
}
