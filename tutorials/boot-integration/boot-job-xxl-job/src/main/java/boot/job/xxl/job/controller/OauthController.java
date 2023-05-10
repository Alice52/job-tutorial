package boot.job.xxl.job.controller;

import boot.job.xxl.job.service.GiteeAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zack <br>
 * @create 2021-05-23<br>
 * @project integration <br>
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Resource private GiteeAuthService giteeAuthService;

    @RequestMapping("/gitee-repos")
    public void login() {

        giteeAuthService.listRepos();
    }
}
