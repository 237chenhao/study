import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by chenhao on 2017/3/7.
 */
public class test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String url="http://errors.angularjs.org/1.5.6/$injector/unpr?p0=20170307142132Provider%20%3C-%2020170307142132";
        System.out.println(URLDecoder.decode(url,"utf-8"));
    }
}
