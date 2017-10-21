import org.springframework.format.datetime.DateFormatter;

import java.io.*;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Created by chenhao on 2017/3/7.yyyy-MM-dd hh:mm:ss
 */
public class test {
    public static void main(String[] args) throws IOException, ParseException {
        String f = "/Users/cj-ch/";
        File[] files = new File(f).listFiles(File::isHidden);
        System.out.println(Arrays.toString(files));
    }
}
