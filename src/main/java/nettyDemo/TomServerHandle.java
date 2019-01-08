package nettyDemo;/**
 * Created by user on 2019/1/7.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

/**
 * @ClassName TomServerHandle
 * @Description TODO
 * @Autor user
 * @Date 2019/1/7 17:35
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class TomServerHandle extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        ctx.write(byteBuf);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//写出并刷出所有的数据
                .addListener(ChannelFutureListener.CLOSE);//当flush完成后，关闭连接
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
