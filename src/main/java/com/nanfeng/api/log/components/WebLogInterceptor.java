package com.nanfeng.api.log.components;

import com.alibaba.fastjson.JSON;
import com.nanfeng.api.log.bo.WebLog;
import com.nanfeng.api.log.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author nanfeng
 */
@Slf4j
public class WebLogInterceptor implements HandlerInterceptor {

    private static final String ATTRIBUTE_START_TIME = "startTime";

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(ATTRIBUTE_START_TIME, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WebLog webLog = new WebLog();

        long startTime = (long) request.getAttribute(ATTRIBUTE_START_TIME);
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;

        String queryString = request.getQueryString();
        Map<String, String[]> parameters = request.getParameterMap();
        String parameterString = MapUtils.isEmpty(parameters) ? Strings.EMPTY : JSON.toJSONString(parameters);
        String params = StringUtils.joinWith("&", queryString, parameterString);

        webLog.setMethod(request.getMethod())
                .setUri(request.getRequestURI())
                .setUrl(request.getRequestURL().toString())
                .setIp(IpUtils.getIpAddress(request))
                .setBasePath(contextPath)
                .setParameter(params)
                .setStartTime(startTime)
                .setSpendTime(spendTime)
                .setUsername(Strings.EMPTY)
                .setResult(Strings.EMPTY);

        log.info("[webLog] {}", JSON.toJSONString(webLog));
    }

}
