package upsApp;

import common.MyBatisUtil;
import messaging.Server;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(MyApplication.class, args);
        int maxRetries = 60;
        int retryInterval = 5000; // 等待间隔为 5 秒
        boolean databaseStarted = false;
        System.out.println("Waiting for PostgreSQL database to start...");
        while (maxRetries > 0 && !databaseStarted) {
            try {
                SqlSession sqlSession = MyBatisUtil.getSqlSession();
                sqlSession.close();
                databaseStarted = true;
            } catch (Exception e) {
                System.out.println("PostgreSQL database is not yet ready, retrying in " + retryInterval / 1000 + " seconds...");
                Thread.sleep(retryInterval);
                maxRetries--;
            }
        }
        if (databaseStarted) {
            System.out.println("PostgreSQL database started successfully!");
        } else {
            System.out.println("Failed to connect to PostgreSQL database after several attempts.");
            return;
        }
        Server server = new Server();
        server.start();
    }
}
