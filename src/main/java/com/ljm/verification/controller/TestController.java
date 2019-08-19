package com.ljm.verification.controller;

import com.ljm.verification.util.RandomValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    /*
     * 去登陆页面
     */
    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    /*
     * 登录页面生成验证码
     */
    @RequestMapping(value = "/getVerify.htm")
    public void getVerify(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            RandomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 验证输入的验证码是否正确
     */
    @RequestMapping("/checkValidate")
    @ResponseBody
    public String checkValidate(@RequestParam("verification")Object yzm, HttpSession session,HttpServletRequest request) throws Exception{
        Object result=session.getAttribute("verification");
        //验证码只有半分钟的有效期，超时返回-1
        if(session.getAttribute("verification")==null){
            return "-1";
        }
        //如果为字母验证码或者    1表示通告,0表示验证失败
        if(result instanceof String){
            if(yzm.toString().toUpperCase().equals(result.toString().toUpperCase())){
                return "1";
            }
            return "0";
            //如果为数字验证码
        }else{
            if(result.toString().equals(yzm)){
                return "1";
            }
        }
        return "0";
    }
}
