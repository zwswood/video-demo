package com.linrun.ssm.utils;

import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

/**
 * PC工具包
 * @author 骆焕
 * @createDate 2019-11-14
 */
@Log4j
public class PcUtils {

    /**
     * 获取客户端真实IP
     * 注意:
     * 1. 不采用getRemoteAddr函数获取的原因是通过Apache、Squid、Nginx等反向代理软件进行多次代理后不能获取客户端的真实IP
     * 2. 通过了多级反向代理后的X-Forwarded-For的值并不止一个,而是一串IP值,第一个ip才是真实ip
     * 3. 严谨的说,使用x-Forward_for插件或者burpsuit可以改包,伪造任意的IP地址,使一些管理员后台绕过对IP地址限制的访问
     * @param request
     * @return
     */
    public static String getIPAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        log.debug("获取x-forwarded-for值: " + ip);
        // 多次反向代理后会有多个ip值，第一个ip才是真实ip
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("获取Proxy-Client-IP值: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug("获取WL-Proxy-Client-IP值: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.debug("获取HTTP_CLIENT_IP值: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.debug("获取HTTP_X_FORWARDED_FOR值: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            log.debug("获取X-Real-IP值: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.debug("获取getRemoteAddr ip值: " + ip);
        }
        log.debug("获取客户端ip: " + ip);
        return ip;
    }
}
