import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author cj-ch
 * @date 2017/12/21 上午9:47
 */
public class Test1 {

    public static void main(String[] args) {
        String v1 = "v1.2.3";
        String v2 = "v1.1.1";

        System.out.println(v1.compareTo(v2));

        List<String> list = new ArrayList<>();
        List<String> collect = list.stream().filter(s -> s.length() > 9).collect(Collectors.toList());
        System.out.println(collect.size());
    }
}
