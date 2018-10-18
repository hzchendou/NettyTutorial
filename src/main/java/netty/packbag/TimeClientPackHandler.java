package netty.packbag;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import netty.time.TimeServerHandler;

/**
 * 客户端打包处理器.
 *
 * @author hzchendou
 * @date 18-10-17
 * @since 1.0
 */
public class TimeClientPackHandler extends ChannelHandlerAdapter {

    private final byte[] req;

    private int count;

    public TimeClientPackHandler() {
        req = (TimeServerHandler.QUERY_TIME_ORDER + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.copiedBuffer(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Now is:" + body + "; the counter is" + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        cause.printStackTrace();
    }
}
