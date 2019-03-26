package com.dawnyu.maple.test;

import com.dawnyu.maple.MapleBlogApplication;
import com.dawnyu.maple.websocket.MyWebSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author dawn
 */
@RunWith(SpringJUnit4ClassRunner.class)//springJunit支持
@SpringBootTest(classes = MapleBlogApplication.class)
public class WebSocketTest {

    @Autowired
    private MyWebSocket myWebSocket;

    @Test
    public void testWebSocket() throws IOException {
        myWebSocket.sendMessage("大家好我是vae");
    }


}
