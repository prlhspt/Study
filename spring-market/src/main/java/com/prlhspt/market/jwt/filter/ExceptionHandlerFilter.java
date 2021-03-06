package com.prlhspt.market.jwt.filter;

import com.prlhspt.market.jwt.dto.ErrorDto;
import com.prlhspt.market.exception.UsernameFromTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (UsernameFromTokenException ex){
            log.error("exception exception handler filter");
            setErrorResponse(INTERNAL_SERVER_ERROR,response,ex);
        }catch (RuntimeException ex){
            log.error("runtime exception exception handler filter");
            setErrorResponse(INTERNAL_SERVER_ERROR,response,ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response,Throwable ex){
        response.setStatus(status.value());
        response.setContentType("application/json");
        ErrorDto errorDto = new ErrorDto(INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        try{
            String json = errorDto.convertToJson(errorDto);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}