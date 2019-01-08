package nettyDemo;/**
 * Created by user on 2019/1/7.
 */


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName TomServe
 * @Description TODO
 * @Autor user
 * @Date 2019/1/7 17:22
 * @Version 1.0
 **/
public class TomServe {

    private int port;

    public TomServe(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        final TomServerHandle tomServerHandle = new TomServerHandle();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                           //将自定义的 handle 加载到服务端容器链中
                            socketChannel.pipeline().addLast(tomServerHandle);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync(); /*绑定到端口，阻塞等待直到连接完成*/
            future.channel().closeFuture().sync();/*阻塞，直到channel关闭*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final int port = 9999;
        new TomServe(port).start();
    }
}
