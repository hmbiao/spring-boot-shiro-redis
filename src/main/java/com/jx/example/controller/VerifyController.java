package com.jx.example.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jx.example.util.RandomValidateCodeUtil;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月1日 下午3:32:32
 *
 */
@RestController
public class VerifyController {
	/**
	 * 生成验证码
	 */
	@RequestMapping(value = "/getVerify")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
	    try {
	        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
	        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expire", 0);
	        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
	        randomValidateCode.getRandcode(request, response);//输出验证码图片方法
	    } catch (Exception e) {
	    	System.out.println("获取验证码失败>>>>   ");
	    }
	}
	
	/**
	 * 忘记密码页面校验验证码
	 */
	@RequestMapping(value = "/checkVerify", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean checkVerify(@RequestBody Map<String, Object> requestMap, HttpSession session) {
	    try{
	        //从session中获取随机数
	        String inputStr = requestMap.get("inputStr").toString();
	        String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
	        if (random == null) {
	            return false;
	        }
	        if (random.equals(inputStr)) {
	            return true;
	        } else {
	            return false;
	        }
	    }catch (Exception e){
	    	System.out.println("验证码校验失败");
	        return false;
	    }
	}
}
