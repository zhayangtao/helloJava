package javanio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by shliangyan on 2017/4/28.
 */
public class SelectorClient {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;

    public SelectorClient(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        //打开一个选择器
        selector = Selector.open();
        //打开一个通道
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        //注册到通道上
        socketChannel.register(selector, SelectionKey.OP_READ);
        //监听来自服务的的响应
        //TODO
    }

    public void writeDataToServer(String message) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes("UTF-8"));
        socketChannel.write(writeBuffer);
    }

    public static void main(String[] args) throws IOException {
        SelectorClient client = new SelectorClient("localhost", 6666);
        client.writeDataToServer("I am a client");
    }
}
