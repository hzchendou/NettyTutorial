package netty.packbag;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 时间服务器逻辑处理器.
 *
 * @author hzchendou
 * @date 18-10-17
 * @since 1.0
 */
public class TimeServerPackHandler extends ChannelHandlerAdapter {

    public static final String QUERY_TIME_ORDER = "Query time order";

    private int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("The time server receive order:" + body + "; the counter is" + (++count));
        String currentTime = QUERY_TIME_ORDER.equalsIgnoreCase(body) ? new Date().toString() : "Bad Order";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}
