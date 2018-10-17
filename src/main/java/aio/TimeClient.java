package aio;

/**
 * 异步时间请求客户端.
 *
 * @author hzchendou
 * @date 18-10-17
 * @since 1.0
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        new Thread(new AsyncTimeClientHandler(port, "127.0.0.1"), "AIO-AsyncTimeClientHandler-001").start();
    }
}
