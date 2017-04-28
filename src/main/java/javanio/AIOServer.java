package javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by shliangyan on 2017/4/28.
 */
public class AIOServer {
    public static final int PORT = 9888;
    private AsynchronousServerSocketChannel server;

    public AIOServer(AsynchronousServerSocketChannel server) throws IOException {
        this.server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
    }

    public void startWithFuture() throws ExecutionException, InterruptedException, TimeoutException {
        while (true) { // 循环接收客户端请求
            Future<AsynchronousSocketChannel> future = server.accept();
            AsynchronousSocketChannel socket = future.get();

            handleWithFuture(socket);
        }
    }

    public void handleWithFuture(AsynchronousSocketChannel channel) throws InterruptedException, ExecutionException, TimeoutException {
        ByteBuffer readBuf = ByteBuffer.allocate(2);
        readBuf.clear();

        while (true) {
            Integer integer = channel.read(readBuf).get(10, TimeUnit.SECONDS);
            System.out.println("read: " + integer);
            if (integer == -1) {
                break;
            }
            readBuf.flip();
            System.out.println("received: " + Charset.forName("UTF-8").decode(readBuf));
            readBuf.clear();
        }

    }

    public void startWithCompletionHandler() {
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                server.accept(null, this);
                //TODO
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
    }

    public void handleWithCompletionHandler(final AsynchronousSocketChannel channel) {
        final ByteBuffer buffer = ByteBuffer.allocate(4);
        final long timeout = 10L;
        channel.read(buffer, timeout, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("read: " + result);
                if(result == -1) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                buffer.flip();

            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });
    }
}
