package aio;

/**
 * 异步时间服务器.
 *
 * @author hzchendou
 * @date 18-10-17
 * @since 1.0
 */
public class TimeServer {

    public static final String QUERY_TIME_ORDER = "Query time order";

    public static void main(String[] args) {
        int port = 8080;
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        Thread asyncTimeServerThread = new Thread(timeServer, "AIO-AsyncTimeServerHandler-001");
        asyncTimeServerThread.start();
    }
}
