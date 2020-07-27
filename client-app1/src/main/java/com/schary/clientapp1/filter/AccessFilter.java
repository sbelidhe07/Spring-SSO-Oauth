package com.schary.clientapp1.filter;



import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.schary.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccessFilter implements Filter {

    private FilterConfig config;

    @Value("${services.auth}")
    private String authService;

    @Value("${filter.url-exclusion-list}")
    private List<String> urlExclusionList;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        final HttpServletRequest request = (HttpServletRequest) req;
        String requestUrl = request.getRequestURL().toString().toLowerCase();

        AtomicBoolean ignoreUrl = new AtomicBoolean(false);
        
        urlExclusionList.forEach((url) -> {
            if(requestUrl.contains(url.toLowerCase())){
                ignoreUrl.set(true);
            }
        });

        if(!ignoreUrl.get()){
            String token = new HttpClientUtil().getToken();

            System.out.println("in filter");
            System.out.println(token);

            if(token == null){
                //String authService = this.config.getInitParameter("services.auth");
                System.out.println(authService);
                request.getSession().setAttribute("redirect_uri", "http://localhost:8082/ui/");
                response.sendRedirect(authService);
            } else{
                chain.doFilter(req, res);
            }
            return;
        }
        chain.doFilter(req, res);
        return;   
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

         this.config=config;
    }
   
}