package com.dawn.maple.websocket;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dawn
 */
@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class MyWebSocket {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyWebSocket.class);

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;

    /**新：使用map对象，便于根据userId来获取对应的WebSocket*/
    private static ConcurrentHashMap<String,MyWebSocket> websocketList = new ConcurrentHashMap<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    /**接收sid*/
    private String userId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        websocketList.put(userId, this);
        LOGGER.info("websocketList->" + JSON.toJSONString(websocketList));
        //webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        LOGGER.info("有新窗口开始监听:" + userId + ",当前在线人数为" + getOnlineCount());
        this.userId = userId;
        try {
            sendMessage(userId + "大家好");
        } catch (IOException e) {
            LOGGER.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        websocketList.remove(this);
        //在线数减1
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

        //群发消息
        for (Map.Entry<String, MyWebSocket> item : websocketList.entrySet()) {
            try {
                item.getValue().sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用*/
     @OnError
     public void onError(Session session, Throwable error) {
     System.out.println("发生错误");
     error.printStackTrace();
     }


     public void sendMessageById(String message,String userId) throws IOException {
     this.session.getBasicRemote().sendText(message);
     //this.session.getAsyncRemote().sendText(message);
     }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) {
        for (Map.Entry<String, MyWebSocket> item : websocketList.entrySet()) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (userId == null) {
                    item.getValue().sendMessage(message);
                } else if (item.getKey().equals(userId)) {
                    LOGGER.info("推送消息到窗口" + userId + "，推送内容:" + message);
                    item.getValue().sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }


}
