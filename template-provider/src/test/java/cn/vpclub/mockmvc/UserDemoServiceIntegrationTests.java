package cn.vpclub.mockmvc;

import cn.vpclub.Application;
import cn.vpclub.common.tools.utils.StringUtil;
import cn.vpclub.moses.user.api.UserProto;
import cn.vpclub.moses.user.api.UserServiceGrpc;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * Created by  vpclub on 16-12-16.
 * PackageName cn.vpclub.mockmvc
 * ModifyDate  16-12-16
 * Description (to do)
 * ProjectName moses-user-provider
 */
@Transactional
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(classes = {Application.class}, webEnvironment = DEFINED_PORT)
public class UserDemoServiceIntegrationTests extends BaseServiceIntergrationTest {

    private UserProto.UserVo.Builder requestBuilder = null;
    private UserProto.UserRequest.Builder userRequestBuilder = null;
    private UserProto.UserVo request = null;
    private UserProto.UserRequest userRequest = null;

    private UserServiceGrpc.UserServiceFutureStub serviceFutureStub = null;
    private UserServiceGrpc.UserServiceBlockingStub blockingStub = null;
    private Long timestamp = System.currentTimeMillis();

    private Long id = System.currentTimeMillis() / 1000;
    private String username = "133" + RandomStringUtils.randomNumeric(8);

    private Long appId = 10000034L;

    private String qq = RandomStringUtils.randomNumeric(9);
    private UserProto.UserResponse userResponse = null;
    @Autowired
    protected ManagedChannel channel;

    @Autowired
    private WebApplicationContext context;

    @BeforeMethod
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);

        serviceFutureStub = UserServiceGrpc.newFutureStub(channel);
        blockingStub = UserServiceGrpc.newBlockingStub(channel);
        requestBuilder = UserProto.UserVo.newBuilder();
        userRequestBuilder = UserProto.UserRequest.newBuilder();
        log.info("get futureStub ...");
    }

    @AfterMethod
    public void tearDown() throws Exception{
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Timestamp getTimestamp(){
        long millis = System.currentTimeMillis();

        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();
        return timestamp;
    }

    @Test
    public void addUser() throws Exception {
        //查询
        userRequest = userRequestBuilder
                .setAppId(appId)
                .setUsername(username)
                .setPassword("heheda")
                .build();
        UserProto.UserVo userVo = blockingStub.queryUserVo(userRequest);
        if (StringUtil.isNotEmpty(userVo.getUsername())) {
            userResponse = UserProto.UserResponse.newBuilder().build();
        } else {
            request = requestBuilder
                    .setAppId(appId)
                    .setUsername(username)
                    .setPhoneNumber(username)
                    .setPassword("heheda")
                    .setUpdateDate(timestamp)
                    .setRegisterDate(timestamp)
                    .setSalt("heheda")
                    .build();
            userResponse = blockingStub.addUserVo(request);
        }
        Assert.assertNotSame(userResponse.getReturnCode(), 1000);
    }

}
