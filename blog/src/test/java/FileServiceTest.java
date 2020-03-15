import com.linglouyi.blog.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * @Date 2020/3/10
 * @Created liyi
 */
@Slf4j
public class FileServiceTest {

    private FileService fileService = new FileService();

    @Test
    public void fileListTest() throws IOException {
        fileService.fileList();
    }
}
