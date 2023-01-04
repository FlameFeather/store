package com.example.store.security.handle;

import com.alibaba.fastjson.JSON;
import com.example.store.response.ResponseJson;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //自定义处理：返回一个包含错误信息的json数据
        ResponseJson<Void> jsonResult = ResponseJson.fail(ResponseJson.State.ERR_FORBIDDEN,"您无此权限！");
        String jsonResultString = JSON.toJSONString(jsonResult);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(jsonResultString);
    }
}
