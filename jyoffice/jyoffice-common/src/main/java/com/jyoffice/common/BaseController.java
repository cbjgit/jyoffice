package com.jyoffice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BaseController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected void addMessage(Model model, String message) {
		model.addAttribute("message", message);
	}

	protected void addMessage(RedirectAttributes redirect, String message) {
		redirect.addFlashAttribute("message", message);
	}

	protected static String STATE_VALID = "2";
	protected static String STATE_SUCCESS = "1";
	protected static String STATE_ERROR = "-1";
}
