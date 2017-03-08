package com.ch.study.internert;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhao on 2017/2/25.
 */
public class DownloadUtils {
    private String path;
    private String targetFile;
    private int threadNum;
    private DownloadTask[] downloadTasks;
    private long totalSize;
    private static final String accept_pro= "image/git,image/jpeg"
            +"application/x-shockwave-flash,application/xaml+xml,"
            +"application/vnd.ms-xpsdocument,application/x-ms-xbap,"
            +"application/x-ms-application,application/vnd.ms-excel,"
            +"application/vnd.ms-powerpoint,application/msword,*/*";
    public DownloadUtils(String path, String targetFile, int threadNum) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
        this.downloadTasks = new DownloadTask[threadNum];
    }
    public void download() throws IOException {
        ExecutorService exec = Executors.newCachedThreadPool();
        URL url = new URL(path);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Accept",accept_pro);
        urlConnection.setRequestProperty("Accept-Language","zh-CN");
        urlConnection.setRequestProperty("Charset","UTF-8");
        urlConnection.setRequestProperty("Connection","Keep-Alive");
        totalSize = urlConnection.getContentLength();
        urlConnection.disconnect();
        int currentPartSize = (int) (totalSize/threadNum+1);
        RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile,"rw");
        randomAccessFile.setLength(totalSize);
        randomAccessFile.close();

        for(int i=0;i<threadNum;i++){
            int start = i*currentPartSize;
            RandomAccessFile file = new RandomAccessFile(targetFile,"rw");
            file.seek(start);
            downloadTasks[i] = new DownloadTask(start,currentPartSize,file);
            exec.submit(downloadTasks[i]);
        }
    }

    public int getCompleteRate(){
        int sumSize = 0;
        for(int i=0;i<threadNum;i++){
            sumSize+=downloadTasks[i].downloadLen;
        }
        return (int) (sumSize*100/totalSize);
    }

    private class DownloadTask implements Runnable{
        private long startPos;
        private long size;
        private RandomAccessFile part;
        private long downloadLen;
        public DownloadTask(long startPos,long size,RandomAccessFile part){
            this.startPos = startPos;
            this.size =size;
            this.part =part;
        }
        @Override
        public void run() {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept",accept_pro);
                conn.setRequestProperty("Accept-Language","zh-CN");
                conn.setRequestProperty("Charset","UTF-8");
                conn.setRequestProperty("Connection","Keep-Alive");
                InputStream is = conn.getInputStream();
                is.skip(this.startPos);
                byte[] bytes = new byte[1024];
                int hasRead=0;
                while(downloadLen<size &&(hasRead=is.read(bytes))!=-1 ){
                    part.write(bytes,0,hasRead);
                    downloadLen+=hasRead;
                }
                part.close();
                is.close();
                System.out.println(Thread.currentThread().getName()+"finish");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
