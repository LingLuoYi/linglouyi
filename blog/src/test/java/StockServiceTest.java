import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linglouyi.blog.BlogApplication;
import com.linglouyi.blog.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

/**
 * @Date 2020/2/19
 * @Created liyi
 */
@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BlogApplication.class)
public class StockServiceTest {

    StockService stockService = new StockService();

    @Test
    public void stockList() throws Exception {
        Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));
        for (String log : loggers) {
            Logger logger = (Logger) LoggerFactory.getLogger(log);
            logger.setLevel(Level.INFO);
            logger.setAdditive(false);
        }
        List<String> list = new LinkedList<>();
        JSONArray jsonArray = stockService.stockList();
        for (Object obj:jsonArray) {
            JSONObject jsonObject = JSON.parseObject(obj.toString());
            log.debug("SYMBOL---->{}",jsonObject.get("symbol"));
            list.add(jsonObject.get("symbol").toString());
        }
        stockService.stockOld(list);
    }
}
