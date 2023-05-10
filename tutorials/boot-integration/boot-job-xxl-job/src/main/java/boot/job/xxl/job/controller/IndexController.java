package boot.job.xxl.job.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zack <br>
 * @create 2021-05-04 19:16 <br>
 * @project integration <br>
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    String index() {
        return "xxl job executor running.";
    }
}
