package nettyDemo;/**
 * Created by user on 2019/1/7.
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName TomClient
 * @Description TODO
 * @Autor user
 * @Date 2019/1/7 16:43
 * @Version 1.0
 **/
public class TomClient {

    private String ip;
    private int port;

    public TomClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() throws InterruptedException {
        //声明一个线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //客户端启动类，必备
        try {
            Bootstrap boot = new Bootstrap();
            boot.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(ip,port))
                    .handler(new TomClientHandle());

            //链接建立之前一直阻塞
            ChannelFuture future =  boot.connect().sync();
            //channel关闭之前一直阻塞
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new TomClient("127.0.0.1",9999).start();
    }
}
