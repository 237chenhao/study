package com.ch.text;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cj-ch
 * @date 2017/12/20 上午10:30
 */
public class ReplaceTexts {

    public static final List<String> replaceAllLines(Path filePath, Function<String, String> replaceLink) throws IOException {
        try(Stream<String> stream = Files.readAllLines(filePath, StandardCharsets.UTF_8).stream();){
            return stream.map(s -> replaceLink.apply(s))
                    .collect(Collectors.toList());
        }
    }

    public static final String replaceText(String text,String pattern,String replacement){
        return text.replaceAll(pattern,replacement);
    }

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/cj-ch/Documents/四川电信内网打包项目/agent/webapp/WEB-INF/classes/systemProperty.properties";
        raplaceFileAllLines(filePath);

        filePath = "/Users/cj-ch/Documents/四川电信内网打包项目/bcrm-client-api/webapp/WEB-INF/classes/systemProperty.properties";
        raplaceFileAllLines(filePath);

        filePath = "/Users/cj-ch/Documents/四川电信内网打包项目/client-api/webapp/WEB-INF/classes/systemProperty.properties";
        raplaceFileAllLines(filePath);

        filePath = "/Users/cj-ch/Documents/四川电信内网打包项目/haipay-api/webapp/WEB-INF/classes/systemProperty.properties";
        raplaceFileAllLines(filePath);

        filePath = "/Users/cj-ch/Documents/四川电信内网打包项目/merchant/webapp/WEB-INF/classes/systemProperty.properties";
        raplaceFileAllLines(filePath);

        filePath = "/Users/cj-ch/Documents/四川电信内网打包项目/qrcode-pay-callback/webapp/WEB-INF/classes/systemProperty.properties";
        raplaceFileAllLines(filePath);

        filePath = "/Users/cj-ch/Documents/四川电信内网打包项目/task-dynamic/webapp/WEB-INF/classes/systemProperty.properties";
        raplaceFileAllLines(filePath);

        filePath = "/Users/cj-ch/Documents/四川电信内网打包项目/task-sms-starter/webapp/WEB-INF/classes/systemProperty.properties";
        raplaceFileAllLines(filePath);

    }

    private static void raplaceFileAllLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        File file = path.toFile();
        if(!file.exists() || file.isDirectory()){
            return;
        }
        Function<String,String> replaceLink = ((Function<String,String>)(
                s -> replaceText(s,"https://120.55.23.205","http://dx.test.chuangjiangx.com/bcrmclient"
                )))
                .andThen(s -> replaceText(s,"http://120.55.21.49","http://dx.test.chuangjiangx.com/callback"))
                .andThen(s -> replaceText(s,"https://pay.sc.189.cn","http://dx.test.chuangjiangx.com/merchant"))
                .andThen(s -> replaceText(s,"http://120.55.21.48","http://dx.test.chuangjiangx.com/bcrm"))
                .andThen(s -> replaceText(s,"http://120.55.21.50","http://dx.test.chuangjiangx.com/client"))
                .andThen(s -> replaceText(s,"http://116.62.235.11:8083","http://dx.test.chuangjiangx.com/callback"))
                .andThen(s -> replaceText(s,"http://116.62.235.11:8081","http://dx.test.chuangjiangx.com/merchant"))
                .andThen(s -> replaceText(s,"http://116.62.235.11:48088/","http://dx.test.chuangjiangx.com:48888/"))
                .andThen(s -> replaceText(s,"172.16.124.190:22122","10.183.11.51:22122"))
                .andThen(s -> replaceText(s,"redis.host=127.0.0.1","redis.host=10.183.11.55"));



        List<String> list = replaceAllLines(path, replaceLink);
        Files.write(path,list, StandardCharsets.UTF_8);
        Files.readAllLines(path)
        .forEach(System.out::println);
    }
}
