package com.studyFrame.networkIO.nettyDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class NettyServer {

    public static void main(String[] args) throws IOException {

        /**
         * 下面这行代码做了哪些事：
         *   1-会指定线程：若是调用空参构造，那线程数就是cpu核数*2；还可以通过构造函数来指定线程数；
         *   2-因为对于EventLoop来说只有一个线程，一个EventLoop对应一个Selector，所以上面的 线程数 = EventLoop = Selector
         *   3-EventLoop是线程池，只是这个线程池只有一个线程
         */
        NioEventLoopGroup group = new NioEventLoopGroup(2);

        /**
         * NioEventLoopGroup的next()方法，next() 第一次调用next得到第一个EventLoop，再调一次就得到下一个EventLoop
         */
        System.out.println(group.next());//io.netty.channel.nio.NioEventLoop@b684286
        System.out.println(group.next());//io.netty.channel.nio.NioEventLoop@880ec60
        System.out.println(group.next());//io.netty.channel.nio.NioEventLoop@b684286

        /**
         * 执行普通任务
         * group.next()是得到EventLoop
         */
        group.next().submit(() -> {
            System.out.println(Thread.currentThread().getName() + "线程执行任务");
        });

        /**
         * 执行定时任务
         * 2秒后开始执行，每间隔10秒执行一次，单位是秒
         */
        group.next().scheduleAtFixedRate(() -> {
            System.out.println("祝光泉");
        }, 2, 10, TimeUnit.SECONDS);

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(null);
                    }
                }
        );


    }
}
