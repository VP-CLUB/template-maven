package cn.vpclub.mockmvc;

import cn.vpclub.Application;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Created by Administrator on 2017/1/16.
 */
@Transactional
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(classes = {Application.class}, webEnvironment = DEFINED_PORT)
public abstract class BaseServiceIntergrationTest extends AbstractTestNGSpringContextTests {

    protected Long timestamp = System.currentTimeMillis();
    protected Long id = System.currentTimeMillis() / 1000;
    protected String phone = "133" + RandomStringUtils.randomNumeric(8);
    protected Long appId = 10000034L;

    protected Descriptors.Descriptor descriptor = null;
    @Autowired
    protected ManagedChannel channel;

    @Autowired
    protected WebApplicationContext context;

    @BeforeMethod
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    public void tearDown() throws Exception {
        channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
    }

    public Timestamp getTimestamp() {
        long millis = System.currentTimeMillis();

        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();
        return timestamp;
    }

}
