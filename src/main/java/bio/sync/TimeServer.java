package bio.sync;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bio.TimeServerHandler;

/**
 * 阻塞式时间服务器.
 *
 * @author hzchendou
 * @date 18-10-16
 * @since 1.0
 */
public class TimeServer {


    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException ex) {

            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server start in port:" + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (server != null) {
                System.out.println("The Time Server close");
                server.close();
                server = null;
            }
        }
    }
}
