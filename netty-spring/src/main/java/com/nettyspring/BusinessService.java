package com.nettyspring;

import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-25
 */
@Service
public class BusinessService {

	public String dealInput(String input) {
		return "Re: " + input;
	}
}
