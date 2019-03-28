package com.dawn.zuul;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 添加相应头,区分不同Zuul节点路径
 * 
 * @author admin
 *
 */
public class ResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        InetAddress addr = InetAddress.getLocalHost();
        response.addHeader("DSC-ZN", addr.getHostName());
        filterChain.doFilter(request, response);
    }

}