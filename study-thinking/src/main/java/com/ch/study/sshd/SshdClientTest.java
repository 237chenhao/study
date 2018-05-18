package com.ch.study.sshd;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.future.AuthFuture;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.util.io.InputStreamWithChannel;
import org.apache.sshd.common.util.io.IoUtils;
import org.apache.sshd.common.util.io.NoCloseInputStream;
import org.apache.sshd.common.util.io.NoCloseOutputStream;

import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author cj-ch
 * @date 2018/5/17 下午7:13
 */
@Slf4j
public class SshdClientTest {

    public static void main(String[] args) {
        try(
                SshClient client = SshClient.setUpDefaultClient();
                // override any default configuration...
//        client.setSomeConfiguration(...);
//        client.setOtherConfiguration(...);
        ){
            client.start();

            try(
                    ClientSession session = client.connect("root","47.97.162.91",22)
                            .verify(5, TimeUnit.SECONDS).getSession()
            ){
                //密码验证
                session.addPasswordIdentity("SxVaKLeaT3343440n6TfRrSYi");
                AuthFuture verify = session.auth().verify(5, TimeUnit.SECONDS);
                log.info("会话是否打开:{}",session.isOpen());
                log.info("验证登录:{}",verify.isSuccess());
                try(
                        ChannelShell channel = session.createShellChannel();
                        ByteArrayOutputStream errOut = new ByteArrayOutputStream(2048);
                        ByteArrayOutputStream logOut = new ByteArrayOutputStream(2048);
                ){

                    String command = "set -e\n" +
                            "echo $USER \n" +
                            "pwd \n" +
                            "sleep 1 \n" +
                            "pid=$(ps aux | grep java | grep 'com.cloudrelation.partner.platform.task.dynamic.main.TaskDynamicApp' | awk '{print $2}') \n" +
                            "echo $pid \n" +
                            "exit \n";
                    channel.setIn(new ByteArrayInputStream(command.getBytes()));
                    channel.setOut(logOut);
                    channel.setErr(errOut);
                    channel.open();

                    Set<ClientChannelEvent> clientChannelEvents = channel.waitFor(Arrays.asList(ClientChannelEvent.CLOSED), 2000);
                    log.info("=============日志=================");
                    log.info("out 日志:\n{}",logOut.toString("utf-8"));
                    log.info("======================");
                    log.info("err 日志:{}",errOut.toString("utf-8"));
                    log.info("=============日志结束=================");
                    log.info("clientChannelEvents:{}",clientChannelEvents);
                    log.info("channel.getExitStatus():{}",channel.getExitStatus());
                    log.info("channel.getChannelState():{}",channel.getChannelState());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
