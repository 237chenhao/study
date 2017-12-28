import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.IniFileReader;
import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * @author cj-ch
 * @date 2017/12/14 下午11:05
 */
@Slf4j
public class fastdfs_penggleTest {
    static TrackerClient tracker;
    static StorageServer storageServer;
    static TrackerServer trackerServer;
    static StorageClient client;
    static{
        try {
            Properties props = new Properties();
//            props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, "47.96.145.187:22122");
            props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, "118.116.7.28:22122");
//            props.put(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, "1");
//            props.put(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, "1");
            ClientGlobal.initByProperties(props);
            System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());

             tracker = new TrackerClient();
             trackerServer = tracker.getConnection();

//            storageServer = tracker.getStoreStorage(trackerServer);

             client = new StorageClient(trackerServer, storageServer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
//        List<Path> collect = Files.find(Paths.get("/Users/cj-ch/Downloads/www"), 1, (path, basicFileAttributes) -> {
//            return !path.endsWith(".DS_Store") && !basicFileAttributes.isDirectory();
//        }).collect(Collectors.toList());
//        System.out.println("集合大小:"+collect.size());
//        test(collect);
//
//        Thread.currentThread().join();

        String[] results = client.upload_file("/Users/cj-ch/Downloads/www/BpFEr.jpg", "jpg", null);
        System.out.println(Arrays.toString(results));
        //http://47.96.145.187/group1/M00/00/17/rBB8yloyl8uAd76gAAKjrLXgBgs594.jpg
    }

    static void test(List<Path> pathList){
        LinkedBlockingQueue<Path> linkedBlockingQueue = new LinkedBlockingQueue<>(pathList);
        LinkedBlockingQueue<String> urls = new LinkedBlockingQueue<>();

        int count = 50;
        while(--count >0){
            new Thread(() -> {
                while(true){
                    Path poll = linkedBlockingQueue.poll();
//                    System.out.println(poll);
                    if(poll == null ){
                        return;
                    }
                    try {
                        String url = upload(Files.readAllBytes(poll), poll.toString());
//                        urls.add("http://47.96.145.187/"+url);
//                        url ="http://47.96.145.187/"+url;
                        System.out.println("<img src=\""+url+"\">");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        urls.forEach(System.out::println);
    }
    static List<Path> urlList(){
        String filepath = "/Users/cj-ch/Documents/cj/werty.jpeg";
        String filename = UUID.randomUUID() + ".jpeg";
        List<Path> collect = null;
        try {
            collect = Files.find(Paths.get("/Users/cj-ch/Documents/cj"), 1, (path, basicFileAttributes) -> {
                return !path.endsWith(".DS_Store") && !basicFileAttributes.isDirectory();
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return collect;
    }
    static String upload(byte [] bytes,String filename){
        try {
//            String extension = FilenameUtils.getExtension(filename);

            String suffix = StringUtils.substringAfterLast(FilenameUtils.removeExtension(filename), "/");


            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();

            StorageClient client = new StorageClient(trackerServer, null);
            String[] results = client.upload_file(bytes, suffix, null);

//            StorePath path = storageClient.uploadFile(bytes, suffix);
//            String fullpath = path.getFullPath();
            String fullpath = "http://47.96.145.187/"+results[0]+"/"+results[1];
            if(!fullpath.contains(suffix)){
                System.out.println("!!!!!!!!!!!!");
                log.warn("传递后缀:{},原始图片:{},上传返回的路径:{}",suffix,filename,fullpath);
                System.out.println("!!!!!!!!!!!!");
            }

            trackerServer.close();

            return fullpath;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("上传{}}失败了",filename);
        }
        return null;
    }
}
