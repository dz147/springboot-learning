package com.xiaoxiang.springboot.controller;

import com.xiaoxiang.springboot.facet.JdFacet;
import com.xiaoxiang.springboot.model.ApiResult;
import com.xiaoxiang.springboot.service.JwtTokenService;
import com.xiaoxiang.springboot.utils.JwtHeaderUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stephen
 */
@RestController
@RequestMapping("/app")
public class AppWxController extends ApiController {

    @Resource
    private JwtTokenService tokenService;

    @Resource
    private JdFacet facet;

    @GetMapping("/index")
    public ApiResult<String> index() {
        return success("hello world");
    }


    @GetMapping("/token")
    public ApiResult<String> token(@RequestParam String phone, HttpServletResponse response) {

        String token = tokenService.generateToken(phone);
        JwtHeaderUtil.setAuthHeader(response, token);
        return success(token);
    }

    @GetMapping("/save")
    @RequiresPermissions(logical = Logical.AND, value = {"user:add"})
    public ApiResult<String> saveUser() {
        return success("保存成功");
    }

    @GetMapping("/jd")
    public ApiResult<String> getStockState(@RequestParam String id){
         //facet.getProductCheck(id);
         return success("true");
    }
}
