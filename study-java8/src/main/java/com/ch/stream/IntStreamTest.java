package com.ch.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by admin on 2017/10/21.
 */
public class IntStreamTest {
    public static void main(String[] args)  {
//        IntStream.rangeClosed(1, 1000).boxed()
//                .flatMap(value -> IntStream.rangeClosed(value + 1, 1000)
//                        .filter(value1 -> Math.sqrt(value * value + value1 * value1) % 1 == 0)
//                        .mapToObj(value1 -> new int[]{value, value1, (int) Math.sqrt(value * value + value1 * value1)}))
//                .filter(ints -> ints[2]<50)
//                .forEach(ints -> System.out.println(Arrays.toString(ints)));
//        System.out.println(Math.sqrt(81.00D)%1);

        try {
            Stream<String> lines = Files.lines(Paths.get("d:/", "log_network.txt"), StandardCharsets.UTF_8);
//            lines.forEach(System.out::println);
            long count = lines.flatMap(s -> Arrays.stream(s.split("")))
                    .distinct()
                    .count();
            System.out.println(count);
        } catch (IOException e) {
            System.out.println("!!!ERROR");
            e.printStackTrace();
        }
    }
}
