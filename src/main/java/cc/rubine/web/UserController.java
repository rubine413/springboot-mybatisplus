package cc.rubine.web;

import javax.jws.soap.SOAPBinding.Use;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cc.rubine.entity.SysUser;
import cc.rubine.service.ISysUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Rubine
 * @since 2017-05-31
 */
@Controller
public class UserController {
	
	@Autowired
	private ISysUserService sysUserService;	
	
	@RequestMapping(value="/user/{id}")
	@ResponseBody
	public Object getId(@PathVariable long id){
		SysUser user = sysUserService.selectById(id);
		return JSON.toJSON(user);
	}
	
	@RequestMapping(value="/hello")
	@ResponseBody
	public String hello(){		
		return "Hello World!";
	}
}
