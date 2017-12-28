package com.ch.ssh;

import com.chilkatsoft.CkSsh;
import com.chilkatsoft.CkStringBuilder;

/**
 * @author cj-ch
 * @date 2017/12/20 下午6:30
 */
public class ChilkatExample {
    static {
        try {
            /**
             * 需要指定library.path
             * -Djava.library.path=/Users/cj-ch/Downloads/chilkatjava-9.5.0-jdk8-macosx
             */
            System.loadLibrary("chilkat");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String argv[]) {
        //  Important: It is helpful to send the contents of the
        //  ssh.LastErrorText property when requesting support.

        CkSsh ssh = new CkSsh();

        //  Any string automatically begins a fully-functional 30-day trial.
        boolean success = ssh.UnlockComponent("30-day trial");
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Connect to an SSH server:
        String hostname;
        int port;

        //  Hostname may be an IP address or hostname:
        hostname = "121.43.177.113";
        port = 22;

        success = ssh.Connect(hostname, port);
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Wait a max of 5 seconds when reading responses..
        ssh.put_IdleTimeoutMs(15000);
        ssh.put_ConnectTimeoutMs(5000);
        ssh.put_ReadTimeoutMs(15000);

//        CkSshKey ckSshKey = new CkSshKey();
//        String pri = ckSshKey.loadText("/Users/cj-ch/IdeaProjects/customer-center/customer-center-web/src/main/resources/profile/prod/ssh-key/id_rsa");
//        ckSshKey.FromOpenSshPrivateKey(pri);
//        success = ssh.AuthenticatePk("root",ckSshKey);
        //  Authenticate using login/password:
        success = ssh.AuthenticatePw("root", "H7oBmI8F0XZjEKGeHJYfxPaTYrRp4p");
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Open a session channel.  (It is possible to have multiple
        //  session channels open simultaneously.)
        int channelNum;
        channelNum = ssh.OpenSessionChannel();
        if (channelNum < 0) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Some SSH servers require a pseudo-terminal
        //  If so, include the call to SendReqPty.  If not, then
        //  comment out the call to SendReqPty.
        //  Note: The 2nd argument of SendReqPty is the terminal type,
        //  which should be something like "xterm", "vt100", "dumb", etc.
        //  A "dumb" terminal is one that cannot process escape sequences.
        //  Smart terminals, such as "xterm", "vt100", etc. process
        //  escape sequences.  If you select a type of smart terminal,
        //  your application will receive these escape sequences
        //  included in the command's output.  Use "dumb" if you do not
        //  want to receive escape sequences.  (Assuming your SSH
        //  server recognizes "dumb" as a standard dumb terminal.)
        String termType = "dumb";
        int widthInChars = 120;
        int heightInChars = 40;
        //  Use 0 for pixWidth and pixHeight when the dimensions
        //  are set in number-of-chars.
        int pixWidth = 0;
        int pixHeight = 0;
        success = ssh.SendReqPty(channelNum, termType, widthInChars, heightInChars, pixWidth, pixHeight);
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Start a shell on the channel:
        success = ssh.SendReqShell(channelNum);
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        CkStringBuilder sbCommands = new CkStringBuilder();
        sbCommands.Append("date\n");
        sbCommands.Append("echo $PATH\n");
        sbCommands.Append("aaa=~/application\n");
        sbCommands.Append("cd $aaa\n");
        sbCommands.Append("projet_name=agent\n");
        sbCommands.Append("ps aux | grep monitor | grep \"$projet_name\" | awk '{print $2}' \n");

        //  Start a command in the remote shell.  This example
        //  will send a "ls" command to retrieve the directory listing.
        success = ssh.ChannelSendString(channelNum, sbCommands.getAsString(), "ansi");
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Send an EOF.  This tells the server that no more data will
        //  be sent on this channel.  The channel remains open, and
        //  the SSH client may still receive output on this channel.
        success = ssh.ChannelSendEof(channelNum);
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Read whatever output may already be available on the
        //  SSH connection.  ChannelReadAndPoll returns the number of bytes
        //  that are available in the channel's internal buffer that
        //  are ready to be "picked up" by calling GetReceivedText
        //  or GetReceivedData.
        //  A return value of -1 indicates failure.
        //  A return value of -2 indicates a failure via timeout.

        //  The ChannelReadAndPoll method waits
        //  for data to arrive on the connection usingi the IdleTimeoutMs
        //  property setting.  Once the first data arrives, it continues
        //  reading but instead uses the pollTimeoutMs passed in the 2nd argument:
        //  A return value of -2 indicates a timeout where no data is received.
        int n;
        int pollTimeoutMs = 2000;
        n = ssh.ChannelReadAndPoll(channelNum, pollTimeoutMs);
        if (n < 0) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Close the channel:
        success = ssh.ChannelSendClose(channelNum);
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Perhaps we did not receive all of the commands output.
        //  To make sure,  call ChannelReceiveToClose to accumulate any remaining
        //  output until the server's corresponding "channel close" is received.
        success = ssh.ChannelReceiveToClose(channelNum);
        if (success != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Let's pickup the accumulated output of the command:
        String cmdOutput = ssh.getReceivedText(channelNum, "ansi");
        if (ssh.get_LastMethodSuccess() != true) {
            System.out.println(ssh.lastErrorText());
            return;
        }

        //  Display the remote shell's command output:
        System.out.println(cmdOutput);

        //  Disconnect
        ssh.Disconnect();

    }
}
