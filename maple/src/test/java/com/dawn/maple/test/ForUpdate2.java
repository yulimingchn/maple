package com.dawn.maple.test;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * @author yuliming
 */
public class ForUpdate2 implements Runnable {

    private CountDownLatch countDownLatch;

    public ForUpdate2(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        Connection connection = null;
        String url = "jdbc:mysql://192.168.4.111:3306/vueblog2?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "Dawn_Test123";
        try {
            connection = ConnectionUtils.createConnection(url,user,password);
            Thread.sleep(2000);
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM lost_update where id =1 FOR UPDATE ");
            ps.executeQuery();
            connection.commit();
            System.out.println("test 2 finnish");
            countDownLatch.countDown();
        }catch (Exception e){
            try {
                connection.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
