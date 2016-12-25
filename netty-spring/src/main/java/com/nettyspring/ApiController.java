package com.nettyspring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
@RestController
@RequestMapping("/api")
public class ApiController {

	@RequestMapping("/get")
	public String get() {
		return "ok";
	}
}
