import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author cj-ch
 * @date 2018/2/1 上午11:37
 */
public class HttpTest {

    public static void main(String[] args) throws IOException {
        List<String> list = Files.readAllLines(Paths.get("/Users/cj-ch/Downloads/tttttttttt.txt"), StandardCharsets.UTF_8);

        File file = new File("/Users/cj-ch/Downloads/去除会员菜单.sql");

        try(CloseableHttpClient aDefault = HttpClients.custom()
            .build();

        ){
            tt(aDefault,file,"10036");
//            list.stream()
//                    .map(s -> StringUtils.trim(s))
//                    .forEach(s -> {
////                        System.out.println(s);
//                        try {
//                            tt(aDefault,file,s);
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//
//                    });
        }
    }

    private static void tt(CloseableHttpClient aDefault,File file,String productId) throws UnsupportedEncodingException {
//        HttpPost post = new HttpPost("http://control.chuangjiangx.com/api/db_info/doLoadSql?JSESSIONID=43FAA7AF5B6215469463CD21B996A4CC");
        HttpPost post = new HttpPost("https://www.baidu.com");
        FileBody fileBody = new FileBody(file);
        HttpEntity entity = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addPart("file", fileBody)
                .addTextBody("dbId", "1")
                .addTextBody("productId", productId)
                .setCharset(StandardCharsets.UTF_8)
                .setContentType(ContentType.create(ContentType.MULTIPART_FORM_DATA.getMimeType(), "utf-8"))
                .build();
        post.setEntity(entity);
        try(CloseableHttpResponse execute = aDefault.execute(post);){


            System.out.println(execute.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(execute.getEntity(),"utf-8"));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
