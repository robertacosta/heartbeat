package emr.admin.controller;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import emr.acl.model.Principle;
import emr.acl.repository.PrincipleRepository;
 
@Controller
public class AdminController {
	
	@Autowired
	PrincipleRepository repo;
	
	DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
	
	//Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
 
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
 
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
 
		return model;
 
	}
 
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
 
		List<Principle> users = repo.findByRole("ROLE_USER");
		
		ModelAndView model = new ModelAndView();
		model.addObject("users", users);
		model.setViewName("admin");
 
		return model;
 
	}
	
	@RequestMapping(value = "/admin/create", method = RequestMethod.GET)
	public ModelAndView adminCreatePage() {		
		ModelAndView model = new ModelAndView();
		model.setViewName("create");
		return model;
	}
	
	@RequestMapping(value = "admin/user/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("user") Principle user, BindingResult result) {
		Logger logger = LoggerFactory.getLogger(AdminController.class);

		String password = RandomStringUtils.random(8, true, true);

		SendGrid sendgrid = new SendGrid("***REMOVED***", "***REMOVED***");
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(user.getEmail());
		email.setFrom("robert.j.acosta@gmail.com");
		email.setSubject("Welcome to Heartbeat EMR.  Your user credentials have been created!");
		email.setHtml(user.getFirstName());

		email.getSMTPAPI().addFilter("templates", "enable", 1);
		email.getSMTPAPI().addFilter("templates", "template_id", "51456924-4eb2-4cee-8609-157d56a6d26d");
		email.getSMTPAPI().addSubstitution("-username-", user.getUsername());
		email.getSMTPAPI().addSubstitution("-password-", password);
		try {
			sendgrid.send(email);
		} catch (SendGridException e) {
			logger.error("Failed to email user with creds: " + e.getMessage());
		}

		StandardPasswordEncoder encoder = new StandardPasswordEncoder("Marissa");
		String encodedPassword = encoder.encode(password);

	    user.setPassword(encodedPassword);
	    user.setLastPasswordChange(LocalDate.now().toString(formatter));
	    user.setRole("ROLE_USER");
	    user.setEnabled(true);
		repo.save(user);

	    return "redirect:/admin";
	}
	
	@RequestMapping(value = "/admin/user/delete", method = RequestMethod.POST)
	public String adminUserDelete(@RequestParam("userid") Long userid) {
		repo.delete(userid);
		
		return "redirect:/admin";
	}
	
	@RequestMapping(value = "/admin/user/reset", method = RequestMethod.POST)
	public String adminUserResetPassword(@RequestParam("userid") Long userid) {
		Principle user = repo.findOne(userid);
		user.setLastPasswordChange(LocalDate.now().toString(formatter));
		repo.save(user);
		
		return "redirect:/admin";
	}
 
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
 
	  ModelAndView model = new ModelAndView();
 
	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("username", userDetail.getUsername());
	  }
 
	  model.setViewName("403");
	  return model;
 
	}
}