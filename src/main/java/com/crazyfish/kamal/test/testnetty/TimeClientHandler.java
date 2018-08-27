package com.crazyfish.kamal.test.testnetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by kamal on 16/1/6.
 */
public class TimeClientHandler extends ChannelHandlerAdapter{
    private int counter;
    private final ByteBuf firstMessage;
    byte[] req;
    public TimeClientHandler(){
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        //ctx.writeAndFlush(firstMessage);
        ByteBuf mess = null;
        for(int i = 0;i < 100;i ++){
            mess = Unpooled.buffer(req.length);
            mess.writeBytes(req);
            ctx.writeAndFlush(mess);
        }
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
        //ByteBuf buf = (ByteBuf)msg;
        //byte[] req = new byte[buf.readableBytes()];
        //buf.readBytes(req);
        //String body = new String(req,"UTF-8");
        String body = (String)msg;//add
        System.out.println("No w is:" + body + ":counter" + ++counter);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        ctx.close();
    }
}
