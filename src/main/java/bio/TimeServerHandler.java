package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * 时间服务处理逻辑类.
 *
 * @author hzchendou
 * @date 18-10-16
 * @since 1.0
 */
public class TimeServerHandler implements Runnable {

    public static final String QUERY_TIME_ORDER = "Query time order";

    private Socket socket;

    /**
     * 构造函数
     *
     * @param socket
     */
    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * 处理逻辑
     */
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("The time server receive order:" + body);
                currentTime = QUERY_TIME_ORDER.equalsIgnoreCase(body) ?
                        new Date(System.currentTimeMillis()).toString() :
                        "Bad Order";
                out.println(currentTime);
            }
        } catch (Exception ex) {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
