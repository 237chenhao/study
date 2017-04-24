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
        int c=10,t=1000,i=0;
        while(c<t){
            c=c+(c>>1);
            i++;
            System.out.println(c);
        }
        System.out.println("-----");
        System.out.println(c);
        System.out.println(i);
    }
}
