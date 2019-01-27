package com.dawnyu.maple.test;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * @author yuliming
 */
public class ForUpdate1 implements Runnable {

    private CountDownLatch countDownLatch;

    public ForUpdate1(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        Connection connection = null;
        String url = "jdbc:mysql://192.168.4.111:3306/vueblog2?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "Dawn_Test123";
        try {
            connection = ConnectionUtils.createConnection(url, user, password);
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("update lost_update set count = 1 where id =1");
            ps.executeUpdate();
            Thread.sleep(10000);
            connection.commit();
            System.out.println("test 1 finnish");
            countDownLatch.countDown();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
