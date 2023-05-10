//package boot.job.xxl.job.task;
//
//import boot.tools.email.service.IMailService;
//import cn.hutool.http.HttpUtil;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Arrays;
//
///**
// * @author zack <br>
// * @create 2021-05-16<br>
// * @project integration <br>
// */
//@Slf4j
//@Component
//public class EmailJob {
//
//    @Resource private IMailService mailService;
//
//    @Value("${free-api.poems}")
//    private String poemsUrl;
//
//    @XxlJob("poems")
//    public void poemsJobHandler() throws Exception {
//        XxlJobHelper.log("execute poems handler");
//        String s = HttpUtil.get(poemsUrl);
//        JSONObject jsonObject = JSONUtil.parseObj(s);
//        JSONObject data = JSONUtil.parseObj(jsonObject.get("data"));
//
//        mailService.sendImageMail(
//                Arrays.asList("1252068782@qq.com", "1035950489@qq.com"),
//                data.get("category"),
//                data.get("content"));
//    }
//}
