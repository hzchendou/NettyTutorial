package nio;

/**
 * 非阻塞时间服务器.
 *
 * @author hzchendou
 * @date 18-10-16
 * @since 1.0
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8081;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
