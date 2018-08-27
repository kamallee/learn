package com.crazyfish.kamal.test.testnetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by kamal on 16/1/5.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        try{
            //ByteBuf buf = (ByteBuf)msg;
            //byte[] req = new byte[buf.readableBytes()];
            //buf.readBytes(req);
            //String body = new String(req,"UTF-8");
            String body = (String)msg;
            System.out.println("time server rec order:" + body);
            String currentTIme = "QUERY TIME ORDER".equalsIgnoreCase(body)?
                    new java.util.Date(System.currentTimeMillis()).toString():"BADORDER";
            currentTIme = currentTIme + "我是" + System.getProperty("line.separator");
            ByteBuf resp = Unpooled.copiedBuffer(currentTIme.getBytes());
            //ctx.write(resp);
            ctx.writeAndFlush(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        ctx.close();
    }
}
