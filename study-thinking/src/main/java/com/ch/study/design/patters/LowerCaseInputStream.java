package com.ch.study.design.patters;

import java.io.*;

/**
 * Created by chenhao on 2017/3/5.
 */
public class LowerCaseInputStream extends FilterInputStream {
    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param in the underlying input stream, or <code>null</code> if
     *           this instance is to be created without an underlying stream.
     */
    public LowerCaseInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        return c == -1 ? c : Character.toLowerCase(c);
    }

    public static void main(String[] args) throws IOException {
        InputStream is = new LowerCaseInputStream(new FileInputStream("d:/log_network.txt"));
        int len=0;
        while((len=is.read())!=-1){
            System.out.print((char)len);
        }
        System.out.println();
        is.close();
    }
}
