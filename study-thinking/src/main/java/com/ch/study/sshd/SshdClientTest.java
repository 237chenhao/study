package com.ch.study.sshd;

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
import java.util.concurrent.TimeUnit;

/**
 * @author cj-ch
 * @date 2018/5/17 下午7:13
 */
public class SshdClientTest {

    public static void main(String[] args) {
        SshClient client = SshClient.setUpDefaultClient();
        // override any default configuration...
//        client.setSomeConfiguration(...);
//        client.setOtherConfiguration(...);
        client.start();

        try(ClientSession session = client.connect("root","47.97.162.91",22).verify(5, TimeUnit.SECONDS).getSession()){
            //密码验证
            session.addPasswordIdentity("SxVaKLeaT3343440n6TfRrSYi");
            System.out.println("----------");
            AuthFuture verify = session.auth().verify(5, TimeUnit.SECONDS);
            System.out.println(verify.isSuccess());
            System.out.println(session.isOpen());
            ChannelShell channel = session.createShellChannel();
            channel.setOut(new NoCloseOutputStream(System.out));
            channel.setErr(new NoCloseOutputStream(System.err));
            String command = "echo $USER \n" +
                    "pwd \n" +
                    "ls \n" +
                    "ll \n" +
                    "exit \n";
            channel.setIn(new ByteArrayInputStream(command.getBytes()));
            channel.open();

            channel.waitFor(Arrays.asList(ClientChannelEvent.CLOSED),0);
            System.out.println(channel.getExitSignal());
            System.out.println(channel.getExitStatus());
            System.out.println(channel.getChannelState());
//            channel.close(false);
//            session.close(false);
//            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
