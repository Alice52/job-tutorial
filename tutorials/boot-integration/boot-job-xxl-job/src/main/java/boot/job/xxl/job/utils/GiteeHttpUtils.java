package boot.job.xxl.job.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.model.AuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zack <br>
 * @create 2021-05-23<br>
 * @project integration <br>
 */
@Slf4j
@Component
public class GiteeHttpUtils {

    private static String ACCESS_TOKEN = new String();

    @Value("${justauth.type.GITEE.client-id}")
    private String clientId;

    @Value("${justauth.type.GITEE.client-secret}")
    private String clientSecret;

    @Value("${justauth.type.GITEE.email}")
    private String email;

    @Value("${justauth.type.GITEE.password}")
    private String password;

    public String get(String urlString, Map<String, Object> paramMap) {

        paramMap.put("access_token", getAccessToken());
        try {
            return HttpUtil.get(urlString, paramMap);
        } catch (Exception e) {
            log.info("http get exception: {}", e);
            ACCESS_TOKEN = "";
            throw e;
        }
    }

    public String post(String urlString, Map<String, Object> paramMap) {

        paramMap.put("access_token", getAccessToken());

        try {
            return HttpUtil.post(urlString, paramMap);
        } catch (Exception e) {
            log.info("http post exception: {}", e);
            ACCESS_TOKEN = "";
            throw e;
        }
    }

    public String getAccessToken() {

        if (StrUtil.isBlank(ACCESS_TOKEN)) {
            return getAccessTokenByHttp().getAccessToken();
        }

        return ACCESS_TOKEN;
    }

    private AuthToken getAccessTokenByHttp() {
        Map<String, Object> map = new HashMap<>(6);
        map.put("grant_type", "password");
        map.put("username", email);
        map.put("password", password);
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("scope", "projects user_info issues notes");

        String response = HttpUtil.post(AuthDefaultSource.GITEE.accessToken(), map);
        JSONObject accessTokenObject = JSONObject.parseObject(response);

        return AuthToken.builder()
                .accessToken(accessTokenObject.getString("access_token"))
                .refreshToken(accessTokenObject.getString("refresh_token"))
                .scope(accessTokenObject.getString("scope"))
                .tokenType(accessTokenObject.getString("token_type"))
                .expireIn(accessTokenObject.getIntValue("expires_in"))
                .build();
    }
}
