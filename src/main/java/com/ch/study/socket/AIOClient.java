package com.ch.study.socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhao on 2017/2/25.
 */
public class AIOClient {
    AsynchronousSocketChannel client;
    JFrame jFrame = new JFrame("多人聊天");
    JTextArea jTextArea = new JTextArea(15,48);
    JTextField jTextField = new JTextField(40);
    JButton jButton = new JButton("发送");
    public void init(){
        jFrame.setLayout(new BorderLayout());
        jTextArea.setEditable(false);
        jFrame.add(new JScrollPane(jTextArea),BorderLayout.CENTER);
        JPanel jPanel = new JPanel();
        jPanel.add(jTextField);
        jPanel.add(jButton);
        Action sendAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = jTextField.getText();
                if(content.trim().length()>0){
                    try {
                        client.write(StandardCharsets.UTF_8.encode(content)).get();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (ExecutionException e1) {
                        e1.printStackTrace();
                    }
                    jTextField.setText("");
                }
            }
        };
        jButton.addActionListener(sendAction);
        jTextField.getInputMap().put(KeyStroke.getKeyStroke('\n', InputEvent.CTRL_MASK),"send");
        jTextField.getActionMap().put("send",sendAction);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jPanel,BorderLayout.SOUTH);
        jFrame.pack();
        jFrame.setVisible(true);
    }
    public void connect() throws IOException, ExecutionException, InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ExecutorService exec = Executors.newCachedThreadPool();
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(exec,20);
        client = AsynchronousSocketChannel.open(group);
        client.connect(new InetSocketAddress("127.0.0.1",NServer.port)).get();
        jTextArea.append("与服务器建立连接成功\n");
        byteBuffer.clear();
        client.read(byteBuffer, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                byteBuffer.flip();
                jTextArea.append("有人说："+StandardCharsets.UTF_8.decode(byteBuffer)+"\n");
                byteBuffer.clear();
                client.read(byteBuffer,null,this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("读取服务器数据失败"+exc);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        AIOClient aioClient = new AIOClient();
        aioClient.init();
        aioClient.connect();
    }
}
