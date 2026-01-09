package com.ntg.JobHunter.util;

import com.ntg.JobHunter.domain.RestResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// Auto cast to last response
@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        ServletServerHttpResponse servletResponse =
                (ServletServerHttpResponse) response;

        int status = servletResponse.getServletResponse().getStatus();
        if(body instanceof String) {
            return body;
        }
        RestResponse<Object> res = new RestResponse<>();
        // gán statusCode cho response cuối cùng,
        // statusCode ở Controller là gán cho gói tin chứ k phải response
        res.setStatusCode(status);
        /*
           - Nếu lỗi thì trả về dữ liệu luôn vì đã format rồi
           - Nếu thành công thì gán thêm trường Data và message vì ở Controller đã
             gán statusCode, giờ lấy body đưa vào Data thôi.
        */


        if (status >= 400) {
            return body;
        } else {
            res.setMessage("call api success");
            res.setData(body);
        }

        return res;
    }
}
