package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author rufeng
 * @time 2022-03-06 10:03
 * @package com.rufeng.healthman.controller
 * @description 覆盖springboot默认的错误处理器
 */
@Controller
@Slf4j
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends AbstractErrorController {
    private static final Set<ErrorAttributeOptions.Include> ALL_ERROR_ATTRIVUTES = new HashSet<>(4);

    static {
        ALL_ERROR_ATTRIVUTES.add(ErrorAttributeOptions.Include.BINDING_ERRORS);
        ALL_ERROR_ATTRIVUTES.add(ErrorAttributeOptions.Include.STACK_TRACE);
        ALL_ERROR_ATTRIVUTES.add(ErrorAttributeOptions.Include.MESSAGE);
        ALL_ERROR_ATTRIVUTES.add(ErrorAttributeOptions.Include.EXCEPTION);
    }

    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResponse<Void> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()) {
            return ApiResponse.clientError();
        } else if (status.is5xxServerError()) {
            return ApiResponse.serverError();
        }

        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.of(ALL_ERROR_ATTRIVUTES));
        log.warn(attributes.toString());
        return ApiResponse.unknownError();
    }
}
