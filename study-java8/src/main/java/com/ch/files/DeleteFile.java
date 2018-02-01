package com.ch.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * @author cj-ch
 * @date 2017/12/29 下午3:40
 */
public class DeleteFile {

    public static void main(String[] args) throws IOException {
        if(args.length < 1){
            return;
        }
        String dir = args[0];
        File dirFile = new File(dir);
        if(!dirFile.exists() || !dirFile.isDirectory()){
            return;
        }
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(30);
        FileTime from = FileTime.from(localDateTime.toInstant(ZoneOffset.ofHours(8)));
        deleteExpireFile(dirFile, from);

    }

    private static void deleteExpireFile(File dirFile,final FileTime from) throws IOException {
        if(dirFile.isDirectory() && dirFile.list().length <1){

            dirFile.delete();
            return;
        }

        Files.find(dirFile.toPath(),1,(path, basicFileAttributes) -> {
            FileTime fileTime = basicFileAttributes.lastModifiedTime();
            if(basicFileAttributes.isDirectory()){
                return true;
            }
            return from.compareTo(fileTime) > 0;
        })
            .forEach(path -> {
                if(Objects.equals(dirFile.toPath(),path)){
                    return ;
                }
                System.out.println(dirFile.getName()+">"+path);
                File f = path.toFile();
                if(f.exists()){
                    if(f.isFile()){
                        System.out.println("delete\t" + f.getName() );
                        f.delete();
                    }
                    if(f.isDirectory()){
                        try {
                            deleteExpireFile(f,from);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    }
}
