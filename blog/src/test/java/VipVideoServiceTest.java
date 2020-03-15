import com.linglouyi.blog.BlogApplication;
import com.linglouyi.blog.service.VipVideoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Date 2020/2/11
 * @Created liyi
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
public class VipVideoServiceTest {

//    @Autowired
    private VipVideoService vipVideoService = new VipVideoService();

    @Test
    public void videoUrl(){
        String url = "https://api.tv920.com/?url=https://www.iqiyi.com/v_19rw2if13k.html?vfrm=pcw_dianshiju";
        log.info("{}",vipVideoService);
        vipVideoService.videoUrl(url);
    }
}
