package javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by shliangyan on 2017/4/28.
 */
public class SelectorServer {
    //服务器监听的端口
    private static final int PORT = 6666;
    //处理数据的缓存区
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    //欢迎消息
    private static final String GREETING = "Welcome to here.";

    public static void main(String[] args) {

    }

    private void start(String[] args) {
        int port = PORT;
        if (args.length == 1) {
            port = Integer.valueOf(args[0]);
        }
        System.out.println("listening on port" + port);
        Iterator<SelectionKey> iterator = null;
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverSocketChannel.socket();
            //绑定地址
            SocketAddress address = new InetSocketAddress(port);
            //创建需要注册的选择器
            Selector selector = Selector.open();
            serverSocket.bind(address);

            //配置为非阻塞
            serverSocketChannel.configureBlocking(false);

            //注册通道到选择器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int n = selector.select();
                if (n == 0) {
                    continue;
                }
                //获取 SelectionKey 的迭代器对象
                iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    //获取这个key关联的通道
                    SelectionKey key = iterator.next();
                    //判断感兴趣的事件类型
                    if(key.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel channel = server.accept();

                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
