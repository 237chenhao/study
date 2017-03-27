package mvc;

import com.ch.study.config.MvcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by chenhao on 2017/3/6.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
//@ContextConfiguration(value = {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
public class SpringMVCTest {
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    ProductService productService;
    @Test
    public void test1()  {
        System.out.println("***********************");

    }
}
