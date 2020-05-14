package com.mzlalalal.ctrl;

import com.mzlalalal.model.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mzlalalal
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class ApiController {

    @RequestMapping("/paramCheck")
    public Ret paramCheck(){

        return Ret.ok();
    }
}
