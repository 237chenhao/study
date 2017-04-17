import org.springframework.format.datetime.DateFormatter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by chenhao on 2017/3/7.yyyy-MM-dd hh:mm:ss
 */
public class test {
    public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
        String url="http://errors.angularjs.org/1.5.6/$injector/unpr?p0=20170307142132Provider%20%3C-%2020170307142132";
        System.out.println(URLDecoder.decode(url,"utf-8"));

        System.out.println("appId=SDFdsfdskjvhdsfisdFDSFDSFd&total_fee=10000&sign=7c739541c3323a8de1e83b9a0b2e268a&mch_id=7551000001&mch_order_number=201703231829511000946556&body=商品描述&auth_code=sdfdsfdfsdfdsfddd4444\n".length());
        String str = "2017-04-02 11:39:04";
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.parse(str));
    }
}
