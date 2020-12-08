package jasper.websocket;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

/**
 * websocket服务端,多例的，一次websocket连接对应一个实例
 *  @ServerEndpoint 注解的值为URI,映射客户端输入的URL来连接到WebSocket服务器端
 */
@Component
@ServerEndpoint("/{name}/{toName}")
public class WebSocketServer {
	/** 用来记录当前在线连接数。设计成线程安全的。*/
    private static AtomicInteger onlineCount = new AtomicInteger(0);
  /** 用于保存uri对应的连接服务，{uri:WebSocketServer}，设计成线程安全的 */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketServerMAP = new ConcurrentHashMap<>();
    private Session session;// 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String name; //客户端消息发送者
    private String toName; //客户端消息接受者
    private String uri; //连接的uri
    public static int y=0;
    /**
     * 连接建立成功时触发，绑定参数
     * @param session
     *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param name 
     * @param toName
     * @throws IOException 
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name, @PathParam("toName") String toName) throws IOException {
        this.session = session;
        this.name = name;
        this.toName = toName;
        this.uri = session.getRequestURI().toString();
        WebSocketServer webSocketServer = webSocketServerMAP.get(uri);
        if(webSocketServer != null){ //同样业务的连接已经在线，则把原来的挤下线。
        	webSocketServer.session.getBasicRemote().sendText(uri + "重复连接被挤下线了");
        	webSocketServer.session.close();//关闭连接，触发关闭连接方法onClose()
        }
        webSocketServerMAP.put(uri, this);//保存uri对应的连接服务
        addOnlineCount(); // 在线数加1
        //sendMessage(webSocketServerMAP +"当前在线连接数:" + getOnlineCount());
        sendMessage("当前在线连接数:" + getOnlineCount());
        System.out.println(this + "有新连接加入！当前在线连接数：" + getOnlineCount());
    }

    /**
     * 连接关闭时触发，注意不能向客户端发送消息了
     * @throws IOException 
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketServerMAP.remove(uri);//删除uri对应的连接服务
        reduceOnlineCount(); // 在线数减1
        System.out.println("有一连接关闭！当前在线连接数" + getOnlineCount());
    }

    /**
     * 收到客户端消息后触发,分别向2个客户端（消息发送者和消息接收者）推送消息
     * 
     * @param message
     *            客户端发送过来的消息
     * @param session
     *            可选的参数
     * @throws IOException 
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        //服务器向消息发送者客户端发送消息
        this.session.getBasicRemote().sendText("发送给" + toName + "的消息:" + message);
        //获取消息接收者的客户端连接
        StringBuilder receiverUri = new StringBuilder("/");
        receiverUri.append(toName)
            .append("/")
            .append(name);
        WebSocketServer webSocketServer = webSocketServerMAP.get(receiverUri.toString());
        if(webSocketServer != null){
            webSocketServer.session.getBasicRemote().sendText("来自" +name + "的消息:" + message);
        }
        /*// 群发消息
        Collection<WebSocketServer> collection = webSocketServerMAP.values();
        for (WebSocketServer item : collection) {
            try {
                item.sendMessage("收到群发消息:" + message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }*/
    }

    /**
     * 通信发生错误时触发
     * 
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 向客户端发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 获取在线连接数
     * @return
     */
   public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 原子性操作，在线连接数加一
     */
         public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 原子性操作，在线连接数减一
     */
         public static void reduceOnlineCount() {
        onlineCount.getAndDecrement();
    }
         
         
         
         public static void main(String[] args) {
			
		}
    /**
     * @throws IOException 
     *     实时推送数据 
     */
         
     public static void time() throws IOException {
    	
         //获取消息接收者的客户端连接
         StringBuilder receiverUri = new StringBuilder("/");
         receiverUri.append("a")
             .append("/")
             .append("b");
         WebSocketServer webSocketServer = webSocketServerMAP.get(receiverUri.toString());
         if(webSocketServer != null){
        	y++;
			//webSocketServer.session.getBasicRemote().sendText("来自管理员的消息:" +y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1));
        	webSocketServer.session.getBasicRemote().sendText(y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1)+","+y*(int)(Math.random()*10+1));
         }
         /*// 群发消息
         Collection<WebSocketServer> collection = webSocketServerMAP.values();
         for (WebSocketServer item : collection) {
             try {
                 item.sendMessage("收到群发消息:" + message);
             } catch (IOException e) {
                 e.printStackTrace();
                 continue;
             }
         }*/
     }
    
}
