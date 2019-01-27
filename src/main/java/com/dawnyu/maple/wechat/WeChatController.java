package com.dawnyu.maple.wechat;

import com.dawnyu.maple.utils.MessageUtil;
import com.dawnyu.maple.utils.XmlUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 微信接口访问控制层
 * @author dawn
 */
@RestController
@RequestMapping("wx")
public class WeChatController {

    @PostMapping("accept/message")
    public String handleMessage(HttpServletRequest req) {
        String message = "success";
        try {
            //把微信返回的xml信息转义成map
            Map<String, String> map = XmlUtil.xmlToMap(req);
            //消息来源用户标识
            String fromUserName = map.get("FromUserName");
            //消息目的用户标识
            String toUserName = map.get("ToUserName");
            //消息类型
            String msgType = map.get("MsgType");
            //消息内容
            String content = map.get("Content");

            String eventType = map.get("Event");
            //如果为事件类型
            if (MessageUtil.MSGTYPE_EVENT.equals(msgType)) {
                //处理订阅事件
                if (MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)) {
                    message = MessageUtil.subscribeForText(toUserName, fromUserName);
                    //处理取消订阅事件
                } else if (MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)) {
                    message = MessageUtil.unsubscribe(toUserName, fromUserName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    @GetMapping("accept/message")
    public void bindMessage(HttpServletRequest req, HttpServletResponse response) throws IOException{
        System.out.println("接受微信服务器验证");
        PrintWriter out = response.getWriter();
        // todo 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String echostr = req.getParameter("echostr");
        out.print(echostr);
        out.close();
        out = null;
    }
}
