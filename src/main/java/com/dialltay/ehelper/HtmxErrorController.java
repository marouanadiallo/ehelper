package com.dialltay.ehelper;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

import java.util.Map;


@ControllerAdvice
public class HtmxErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public View handleError(Exception ex, HtmxResponse response ) {
        response.setReswap(HtmxReswap.none());
        return FragmentsRendering
                .fragment("commons/fragments :: errorMessage", Map.of("message", ex.getMessage()))
                .build();
    }
}
