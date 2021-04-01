package com.project.love_data;

import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTests {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Test
    public void testDataSource() throws Exception {
        // Get the configured data source
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        // View Configuration Data Source Information
        System.out.println(dataSource);
        System.out.println(dataSource.getClass().getName());
        System.out.println(dataSourceProperties);
    }

}
