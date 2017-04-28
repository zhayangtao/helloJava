package javanio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by shliangyan on 2017/4/28.
 */
public class ChannelCopy {
    public static void main(String[] args) throws IOException {
        //创建一个源通道
        ReadableByteChannel sources = Channels.newChannel(System.in);
        //创建一个目标通道
        WritableByteChannel dest = Channels.newChannel(System.out);
        channelCopy(sources, dest);

        sources.close();
        dest.close();

    }

    /**
     * 通道拷贝
     * @param source
     * @param dest
     * @throws IOException
     */
    private static void channelCopy(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取缓冲区数据
        while (source.read(buffer) != -1){
            buffer.flip();
            while (buffer.hasRemaining()){
                dest.write(buffer);
            }
            buffer.clear();
        }
    }

    private static void channelCopy2(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (source.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()){
            dest.write(buffer);
        }
    }
}
