package com.ch.text;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cj-ch
 * @date 2017/12/28 上午9:24
 */
public class ChineseToPinyin {
    public static final HanyuPinyinOutputFormat HANYU_PINYIN_OUTPUT_FORMAT;

    static {
        HANYU_PINYIN_OUTPUT_FORMAT = new HanyuPinyinOutputFormat();
        HANYU_PINYIN_OUTPUT_FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        HANYU_PINYIN_OUTPUT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE); //不带声调
        HANYU_PINYIN_OUTPUT_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 汉字转拼音
     *
     * @param text
     * @return
     */
    public static final String toHanYuPinyinString(String text) {
        try {
            return PinyinHelper.toHanYuPinyinString(text, HANYU_PINYIN_OUTPUT_FORMAT, "", true);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Path filePath = Paths.get("/Users/cj-ch/Downloads/技术部人员名单.txt");
        String createTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .format(LocalDateTime.now());
        try(Stream<String> stream = Files.readAllLines(filePath).stream();){
            List<User> userList = stream.filter(s -> StringUtils.isNotBlank(s))
                    .map(s -> {
                        User user = new User();
                        user.setName(s);
                        user.setUsername(toHanYuPinyinString(s));
                        user.setCreateTime(createTime);
                        user.setPassword(DigestUtils.md5Hex(user.getUsername()));
                        return user;
                    })
                    .collect(Collectors.toList());
            String json = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(userList);
            System.out.println(json);
        }
    }

    @Data
    private static class User{
        private String name;
        private String username;
        private String password;
        private String createTime;
    }
}
