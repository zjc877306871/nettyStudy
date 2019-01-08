package nettyDemo;/**
 * Created by user on 2019/1/7.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @ClassName TomClientHandle
 * @Description TODO
 * @Autor user
 * @Date 2019/1/7 17:03
 * @Version 1.0
 **/
public class TomClientHandle extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("客户端接受的数据="+ byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //注意Unpooled.copiedBuffer
        //写出的数据   一定是byteBuf封装的
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Netty",
                CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
