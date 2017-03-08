package com.ch.study.internert;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by chenhao on 2017/3/1.
 */
public class ConnectionTest {
    public static void main(String[] args) throws IOException {
        String targetFile= "C:\\Users\\chenhao\\Desktop\\client_domain.txt";
        File file = new File(targetFile);
        List<String> lines = Files.readAllLines(file.toPath());
        System.out.println("===========================一共："+lines.size()+"个");
        int[] counts=new int[1];
        counts[0]=0;
        lines.forEach(s -> {
            System.out.println(s);
            HttpURLConnection connection = null;
            try {
                URL url = new URL(s);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.setUseCaches(false);
                int code = connection.getResponseCode();
                System.out.println(s+"  :  "+code);
                if(200 == code){
                    counts[0]++;
                }
                connection.disconnect();
            } catch (MalformedURLException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }

        });
        System.out.println("======可用："+counts[0]);
        System.out.println("======不可用："+(lines.size()-counts[0]));
    }
}
