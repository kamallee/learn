//package com.crazyfish.kamal.test.testnetty;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.*;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//import io.netty.handler.codec.http.websocketx.WebSocketFrame;
//import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import io.netty.util.CharsetUtil;
//import io.netty.util.internal.logging.InternalLogLevel;
//import io.netty.util.internal.logging.InternalLoggerFactory;
//
///**
// * @author lipengpeng
// * @desc
// * @date 2016-01-29 上午11:29
// */
//public class ChatServer {
//    public String test = "";
//    public static void main(String args[]) throws Exception{
//        ChatServer chatServer = new ChatServer();
//        chatServer.run(chatServer);
//    }
//    public void run(final ChatServer chatServer) throws Exception {
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new ChannelInitializer<SocketChannel>(){
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            HttpServerCodec sourceCodec = new HttpServerCodec();
//                            //HttpServerUpgradeHandler.UpgradeCodec upgradeCodec = new Http2ServerUpgradeCodec(new ChatHttp2Handler());
//                            //HttpServerUpgradeHandler upgradeHandler = new HttpServerUpgradeHandler(sourceCodec, Collections.singletonList(upgradeCodec), 65536);
//
//                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(sourceCodec);
//                            pipeline.addLast(new HttpObjectAggregator(65536)); //test webSocket and http/1*
//                            pipeline.addLast(new ChunkedWriteHandler());  //test webSocket and http/1*
//                            pipeline.addLast(new ChatServerHandler(chatServer));  //test webSocket and http/1*
//                            //pipeline.addLast(upgradeHandler);       //test HTTP/2
//                            //pipeline.addLast(new UserEventLogger());  //test HTTP/2
//                        }
//                    }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
//            ChannelFuture f = b.bind(8080).sync();
//            f.channel().closeFuture().sync();
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
//    /**
//     * Class that logs any User Events triggered on this channel.
//     */
//    private static class UserEventLogger extends ChannelHandlerAdapter {
//        @Override
//        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
//            System.out.println("User Event Triggered: " + evt);
//            ctx.fireUserEventTriggered(evt);
//        }
//    }
//    private static class ChatServerHandler extends SimpleChannelInboundHandler<Object> {
//        ChatServer chatServer;
//        public ChatServerHandler(ChatServer chatServer){
//            this.chatServer = chatServer;
//        }
//        @Override
//        protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
//            if(msg instanceof FullHttpRequest){
//                handleHttpRequest(ctx, (FullHttpRequest) msg);
//            }else if(msg instanceof WebSocketFrame){
//                handleWebSocketFrame(ctx, (WebSocketFrame) msg);
//            }
//        }
//        @Override
//        public void channelReadComplete(ChannelHandlerContext ctx){
//            ctx.flush();
//        }
//        private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request){
//            System.out.print(request);
//            if(!request.headers().contains("Upgrade")){
//                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(chatServer.test.getBytes()));
//                response.headers().set(CONTENT_TYPE, "text/plain");
//                response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
//                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                return;
//            }
//            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws://localhost:8080/ws", null, false);
//            factory.newHandshaker(request).handshake(ctx.channel(), request);
//        }
//        private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception{
//            if(! (frame instanceof TextWebSocketFrame)){
//                throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
//            }
//            String request = ((TextWebSocketFrame)frame).text();
//            chatServer.test = chatServer.test + request;
//            chatServer.test = chatServer.test.length() > 100?chatServer.test.substring(chatServer.test.length()-100, chatServer.test.length()):chatServer.test; //just for http test
//            ctx.channel().write(new TextWebSocketFrame(request));
//        }
//    }
//    private static class ChatHttp2Handler extends Http2ConnectionHandler{
//        private static final Http2FrameLogger logger = new Http2FrameLogger(InternalLogLevel.INFO, InternalLoggerFactory.getInstance(ChatHttp2Handler.class));
//        public static final String UPGRADE_RESPONSE_HEADER = "Http-To-Http2-Upgrade";
//        public ChatHttp2Handler(){
//            this(
//                    new DefaultHttp2Connection(true),
//                    new Http2InboundFrameLogger(new DefaultHttp2FrameReader(), logger),
//                    new Http2OutboundFrameLogger(new DefaultHttp2FrameWriter(), logger),
//                    new ChatHttp2FrameListener());
//        }
//        private ChatHttp2Handler(Http2Connection connection, Http2FrameReader frameReader,
//                                 Http2FrameWriter frameWriter, ChatHttp2FrameListener listener) {
//            super(connection, frameReader, frameWriter, listener);
//            listener.encoder(encoder());
//        }
//        /**
//         * Handles the cleartext HTTP upgrade event. If an upgrade occurred, sends a simple response via HTTP/2
//         * on stream 1 (the stream specifically reserved for cleartext HTTP upgrade).
//         */
//        @Override
//        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//            if (evt instanceof HttpServerUpgradeHandler.UpgradeEvent) {
//                // Write an HTTP/2 response to the upgrade request
//                Http2Headers headers =
//                        new DefaultHttp2Headers().status(new AsciiString("200"))
//                                .set(new AsciiString(UPGRADE_RESPONSE_HEADER), new AsciiString("true"));
//                encoder().writeHeaders(ctx, 1, headers, 0, true, ctx.newPromise());
//            }
//            super.userEventTriggered(ctx, evt);
//        }
//        @Override
//        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//            cause.printStackTrace();
//            ctx.close();
//        }
//    }
//    private static class ChatHttp2FrameListener extends Http2FrameAdapter {
//        private Http2ConnectionEncoder encoder;
//        public void encoder(Http2ConnectionEncoder encoder) {
//            this.encoder = encoder;
//        }
//        @Override
//        public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding,
//                              boolean endOfStream) throws Http2Exception {
//            if (endOfStream) {
//                sendResponse(ctx, streamId, data.retain());
//            }
//            return data.readableBytes() + padding;
//        }
//        @Override
//        public void onHeadersRead(ChannelHandlerContext ctx, int streamId,
//                                  Http2Headers headers, int streamDependency, short weight,
//                                  boolean exclusive, int padding, boolean endStream) throws Http2Exception {
//            final ByteBuf RESPONSE_BYTES = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8));
//            if (endStream) {
//                sendResponse(ctx, streamId, RESPONSE_BYTES.duplicate());
//            }
//        }
//        private void sendResponse(ChannelHandlerContext ctx, int streamId, ByteBuf payload) {
//            Http2Headers headers = new DefaultHttp2Headers().status(new AsciiString("200"));
//            encoder.writeHeaders(ctx, streamId, headers, 0, false, ctx.newPromise());
//            encoder.writeData(ctx, streamId, payload, 0, true, ctx.newPromise());
//            ctx.flush();
//        }
//    }
//}
