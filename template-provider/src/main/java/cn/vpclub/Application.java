package cn.vpclub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(
                Application.class);
        application.addListeners(
                new ApplicationPidFileWriter("app.pid"));
        application.run(args);
    }

//    public static void getPasswd() throws Exception {
//        String pwd = "vpclub.dev";
//        logger.info("encrypt = [" + ConfigTools.encrypt(pwd).trim() + "]");
//        logger.info("decrypt = [" + ConfigTools.decrypt(ConfigTools.encrypt(pwd).trim()).trim() + "]");
//    }
}

