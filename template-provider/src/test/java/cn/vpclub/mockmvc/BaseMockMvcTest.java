package cn.vpclub.mockmvc;


import cn.vpclub.Application;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Created by Administrator on 2016/3/11.
 * 基础测试 抽象类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = DEFINED_PORT)
@Slf4j
public abstract class BaseMockMvcTest {
    protected ManagedChannel channel;

    @Before
    public void setup() {
        channel = ManagedChannelBuilder.forAddress("localhost", 7575)
                .usePlaintext(true)
                .build();
    }

    @After
    public void tearDown() throws Exception{
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Timestamp getTimestamp(){
        long millis = System.currentTimeMillis();

        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();
        return timestamp;
    }
}
