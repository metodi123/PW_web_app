package pw.web.app.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pw.web.app.model.Admin;
import pw.web.app.model.User;
import pw.web.app.model.UserType;
import pw.web.app.service.PasswordProcessingService;
import pw.web.app.service.UserValidationService;

@Controller
public class LoggingController {
	
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String loginAdmin(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("userType") String userType) {
		
		if(userType.equals(UserType.Admin.toString())) {
			Admin àdmin = new Admin();
			
			String hashedPassword = null;
			
			try {
				hashedPassword = PasswordProcessingService.getHashedPassword(password, username);
			} catch (Exception e1) {
				e1.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			àdmin.setUsername(username);
			àdmin.setPassword(hashedPassword);
			
			try {
				if(UserValidationService.isUserValid(àdmin)) {
					request.getSession().invalidate();
					request.getSession().setAttribute(User.CURRENT_USER, àdmin);
					return "redirect:/admin";
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "InvalidUser");
				return "admin-home";
			} 
		}
		
		if(userType.equals(UserType.Admin.toString())) {
			model.addAttribute("message", "InvalidUser");
			return "admin-home";
		}
		else {
			return "home";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		if(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin) {
			request.getSession().invalidate();
			return "redirect:/admin/login";
		}
		else {
			request.getSession().invalidate();
			return "redirect:/";
		}
	}

}
