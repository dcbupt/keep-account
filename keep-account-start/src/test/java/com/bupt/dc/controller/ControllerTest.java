// 建议包名和启动类包名一致
package com.bupt.dc.controller;

import com.bupt.dc.control.controller.ExceptionController;
import com.bupt.dc.control.controller.RouteController;
import com.bupt.dc.control.controller.UserAuthController;
import com.bupt.dc.config.properties.SystemProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {MockServletContext.class})
public class ControllerTest {
    private MockMvc mvc;

    @InjectMocks
    RouteController routeController;

    @InjectMocks
    UserAuthController userAuthController;

    @InjectMocks
    ExceptionController exceptionController;

    @Mock
    SystemProperties systemProperties;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(
            routeController,
            userAuthController,
            exceptionController).build();
    }

    // TODO: 2018/12/20 不支持注入Value注解配置的值 
    @Test
    public void getHello() throws Exception {
        Mockito.when(systemProperties.getAuthorName()).thenReturn("test1");
        Mockito.when(systemProperties.getProjectName()).thenReturn("test2");

        mvc.perform(get("/hello").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("Hello World")));
    }

    @Test
    public void testUserController() throws Exception {
        //  	测试UserController
        RequestBuilder request = null;

        // 1、get查一下user列表，应该为空
        request = get("/users/");
        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("[]")));

        // TODO: 2018/12/21 字符串无法转换为LocalDate型
        // 这种写法不支持@RequestBody（可以用postman测试，content-type为application/json），只支持@ModelAttribute
        // 2、post提交一个user
        request = post("/users/")
            .param("id", "1")
            .param("name", "测试大师")
            .param("age", "20")
            .param("birthday", LocalDate.now().toString());
        mvc.perform(request)
            //				.andDo(MockMvcResultHandlers.print())
            .andExpect(content().string(equalTo("success")));

        // 3、get获取user列表，应该有刚才插入的数据
        request = get("/users/");
        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));

        // 4、put修改id为1的user
        request = put("/users/1")
            .param("name", "测试终极大师")
            .param("age", "30");
        mvc.perform(request)
            .andExpect(content().string(equalTo("success")));

        // 5、get一个id为1的user
        request = get("/users/1");
        mvc.perform(request)
            .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));

        // 6、del删除id为1的user
        request = delete("/users/1");
        mvc.perform(request)
            .andExpect(content().string(equalTo("success")));

        // 7、get查一下user列表，应该为空
        request = get("/users/");
        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("[]")));

    }
}
