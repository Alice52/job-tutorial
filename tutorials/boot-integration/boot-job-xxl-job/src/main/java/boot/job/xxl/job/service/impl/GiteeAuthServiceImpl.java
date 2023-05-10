package boot.job.xxl.job.service.impl;

import boot.job.xxl.job.service.GiteeAuthService;
import boot.job.xxl.job.utils.GiteeHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zack <br>
 * @create 2021-05-23<br>
 * @project integration <br>
 */
@Slf4j
@Service
public class GiteeAuthServiceImpl implements GiteeAuthService {

    private static final String REPO_LIST_API = "https://gitee.com/api/v5/user/repos";
    @Resource private GiteeHttpUtils httpUtils;

    @Override
    public List<String> listRepos() {

        String s = httpUtils.get(REPO_LIST_API, new HashMap<>(1));

        log.info("repos: {}", s);

        return new ArrayList<>();
    }
}
