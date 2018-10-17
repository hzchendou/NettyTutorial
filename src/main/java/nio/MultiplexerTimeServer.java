package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路服用时间服务处理逻辑.
 *
 * @author hzchendou
 * @date 18-10-16
 * @since 1.0
 */
public class MultiplexerTimeServer implements Runnable {

    public static final String QUERY_TIME_ORDER = "Query time order";

    /**
     * 多路复用
     */
    private Selector selector;

    /**
     * 服务器SockIO通道
     */
    private ServerSocketChannel serverChannel;

    /**
     * 暂停
     */
    private volatile boolean stop;

    /**
     * 构造函数
     *
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            //设置非阻塞模式
            serverChannel.configureBlocking(false);
            //绑定本地端口
            serverChannel.socket().bind(new InetSocketAddress("127.0.0.1", port));
            //注册到selector中,监听新链接事件
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    /**
     * 运行
     */
    @Override
    public void run() {
        while (!this.stop) {
            try {
                //检查是否存在触发监听事件的channel通道,这个方法是阻塞方法,可以设置超时时间
                int num = selector.select(1000);
                //监听到的事件类型
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //关闭资源
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理输入流
     *
     * @param key
     * @throws IOException
     */
    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理新接入的请求消息
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //注册监听事件
                sc.register(selector, SelectionKey.OP_READ);
            }
            //可读取
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order:" + body);
                    String currentTime = QUERY_TIME_ORDER.equalsIgnoreCase(body) ?
                            new Date(System.currentTimeMillis()).toString() :
                            "Bad Order";
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }


    /**
     * 处理输出流
     *
     * @param channel
     * @param response
     */
    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}
