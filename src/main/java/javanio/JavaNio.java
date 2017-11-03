package javanio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * Created by jovi on 2017/4/20.
 */
public class JavaNio {

    @Test
    public void test1() {
        try (RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw")) {
            FileChannel fileChannel = aFile.getChannel();

            //create buffer with capacity of 48 bytes
            ByteBuffer buffer = ByteBuffer.allocate(48);
            int bytesRead = fileChannel.read(buffer);
            //read into buffer
            while (bytesRead != -1) {
                System.out.println("Read" + bytesRead);
                buffer.flip();  // make buffer ready for read
                while (buffer.hasRemaining()) {
                    System.out.println((char) buffer.get());
                    //read 1 byte at a time
                }
                buffer.clear(); //meke buffer ready for writing
                bytesRead = fileChannel.read(buffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try {
            RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
            FileChannel fromChannel = fromFile.getChannel();
            RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
            FileChannel toChannel = toFile.getChannel();
            long position = 0;
            long count = fromChannel.size();

            toChannel.transferFrom(fromChannel, position, count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        try {
            Selector selector = Selector.open();
            int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        String result = baos.toString();
        baos.close();
        return result;
    }

    public void nioTest(){
        try( FileInputStream fis = new FileInputStream("nio-data.txt");
             FileOutputStream fos = new FileOutputStream("nio-data.txt");) {
            FileChannel fc = fis.getChannel();
            // create a new ByteBuffer
            ByteBuffer buffer = ByteBuffer.allocate(10);
            //read data
            fc.read(buffer);
            //reset buffer
            buffer.flip();
            while(buffer.hasRemaining()){
                byte b = buffer.get();
                System.out.println((char)b);
            }

            byte[] data = {83,111,109,101};

            fc = fos.getChannel();
            //write data into buffer
            for(int i = 0; i < data.length; i++) {
                buffer.put(data[i]);
            }
            buffer.flip();
            fc.write(buffer);
            fc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
