package com.ch.ssh;

/**
 * @author cj-ch
 * @date 2017/12/25 上午9:35
 */
public class ChilkatConnection {
    private String hostname;
    private Integer port = 22;
    private Integer idleTimeoutMils=15000;
    private Integer connectTimeoutMils=5000;
    private Integer readTimeoutMils=30000;
    private String privateKeyText;

}
