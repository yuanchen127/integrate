package org.ike.integrate.controller;

import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.req.Info;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
//@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
//@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Info info;

    @Before
    public void setUp() throws Exception {
        log.info("-----before start-----");
        ServletContext servletContext = mockMvc.getDispatcherServlet().getServletContext();
        WebStatFilter webStatFilter = new WebStatFilter();
        webStatFilter.init(new MockFilterConfig(servletContext));
        log.info("-----before end-----");
    }

    @Test
    public void request_post_body() throws Exception {
        log.info("-----test-----");
        String var = "qwe";
        info = new Info().setName(var);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                .post("/test/param/case")
                .content(JSON.toJSONBytes(info))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
        String resultContent = action.andReturn().getResponse().getContentAsString();
        assertEquals(var.toUpperCase(), parseJson2Info(resultContent).getName());
    }

    @Test
    public void request_get_param() throws Exception {
        String desc = "a";
        String name = "b";
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                .get("/test/param")
                .param("name", name)
                .param("desc", desc)
                .accept(MediaType.ALL_VALUE));
        String resultContent = action.andReturn().getResponse().getContentAsString();
        assertEquals(desc.toUpperCase()+name.toUpperCase(), resultContent);
    }

    @Test
    public void request_get_param_url() throws Exception {
        String var = "abc";
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                .get("/test/param/case/"+var)
                .accept(MediaType.ALL_VALUE));
        String resultContent = action.andReturn().getResponse().getContentAsString();
        assertEquals(var.toUpperCase(), resultContent);
    }

    @Test
    public void request_get_param_1() throws Exception {
        String var = "a";
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders
                .get("/test/param/obj")
                .param("name", var)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL));
        String resultContent = action.andReturn().getResponse().getContentAsString();
        assertEquals(var.toUpperCase(), resultContent);
    }



    private Info parseJson2Info(String json) {
    	return JSON.parseObject(json, Info.class);
    }

}
