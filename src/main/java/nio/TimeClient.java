package nio;

/**
 * 非阻塞时间客户端.
 *
 * @author hzchendou
 * @date 18-10-16
 * @since 1.0
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        new Thread(new TimeClientHandler("127.0.0.1", port), "TimeClient-001").start();
    }
}
