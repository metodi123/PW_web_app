package pw.web.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pw.web.app.dao.AdminDAO;
import pw.web.app.dao.AppPropertyDAO;
import pw.web.app.dao.LabelColorDAO;
import pw.web.app.dao.LabelDAO;
import pw.web.app.dao.PostDAO;
import pw.web.app.exception.InvalidAppPropertyParametersException;
import pw.web.app.exception.InvalidLabelParametersException;
import pw.web.app.exception.InvalidPostParametersException;
import pw.web.app.model.Admin;
import pw.web.app.model.AppProperty;
import pw.web.app.model.Label;
import pw.web.app.model.LabelColor;
import pw.web.app.model.Post;
import pw.web.app.model.User;
import pw.web.app.service.AppPropertyParametersValidationService;
import pw.web.app.service.LabelParametersValidationService;
import pw.web.app.service.PostParametersValidationService;

@Controller
@RequestMapping(value = "/admin")
public class ShowAdminPagesController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String homeAdmin(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

		if(request.getSession(false) == null) {
			return "admin-home";
		}
		else {
			if(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin) {
				return "redirect:/admin";
			}
			else {
				request.getSession().invalidate();
				return "admin-home";
			}
		}
		
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String profileAdmin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin/login";
		}
		else {
			AdminDAO adminDAO = new AdminDAO();
			
			Admin admin = new Admin();
			
			admin = (Admin) request.getSession(false).getAttribute(User.CURRENT_USER);
			
			try {
				admin = adminDAO.getUser(admin.getUsername());
			} catch (Exception e) {
				return "redirect:/admin/login";
			}
			
			request.getSession().setAttribute(User.CURRENT_USER, admin);
			
			return "admin-profile";
		}
	}
	
	@RequestMapping(value = "/posts/new", method = RequestMethod.GET)
	public String newPost(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		List<Label> labels = new ArrayList<Label>();
		
		LabelDAO labelDAO = new LabelDAO();
		
		try {
			labels = labelDAO.getAllLabels();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("labels", labels);
		
		return "admin-new-post";
	}
	
	@RequestMapping(value = "/posts/new/save", method = RequestMethod.POST)
	public String savePost(Model model, HttpServletRequest request,
		@RequestParam("title") String title,
		@RequestParam("postText") String postText,
		@RequestParam(required = false, value="label") Set<Integer> labelsIds,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		post.setTitle(title);
		post.setText(postText);
		
		if(labelsIds != null) {
			LabelDAO labelDAO = new LabelDAO();
			
			List<Label> labels = new ArrayList<Label>();
			
			for(Integer id : labelsIds) {
				labels.add(labelDAO.getLabel(id));
			}
			
			post.setLabels(new HashSet<Label>(labels));
		}
		
		try {
			PostParametersValidationService.validatePostParameters(post);
		} catch (InvalidPostParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/posts/new";
		}
		
		PostDAO postDAO = new PostDAO();
		
		try {
			postDAO.createPost(post);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/posts/new";
		}
		
		return "redirect:/admin/posts";
	}
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String showPosts(Locale locale, Model model, HttpServletRequest request) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		return "redirect:/admin/posts/page/1";
	}
	
	@RequestMapping(value = "/posts/page/{number}", method = RequestMethod.GET)
	public String showPaginatedPosts(HttpServletRequest request, Model model,
		@PathVariable("number") int number,
		@RequestParam(name="count", required=false) Integer count,
		@RequestParam(value="id", required = false) Integer labelId,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		if(count == null) {
			count = 10;
		}
		
		int offset = number * count - count;
		
		if(offset < 0) {
			offset = 0;
		}
		
		List<Post> posts = new ArrayList<Post>();
		List<Post> allPosts = new ArrayList<Post>();
		
		PostDAO postDAO = new PostDAO();
		
		if(labelId != null) {
			
			Label label = new Label();
			
			LabelDAO labelDAO = new LabelDAO();
			
			label = labelDAO.getLabel(labelId);
			
			for(Post post : label.getPosts()) {
				allPosts.add(post);
			}
			
			Collections.sort(allPosts, new Post());
			
			int i = 0;
			
			for(i=offset; i<offset+count; i++) {
				if(i<allPosts.size()) {
					posts.add(allPosts.get(i));
					System.out.println(posts.get(0).getTitle());
				}
			}
			
			model.addAttribute("id", labelId);
			
			model.addAttribute("posts", posts);
		}
		else {
			posts = postDAO.getPaginatedPosts(offset, count);
			allPosts = postDAO.getAllPosts();
	
			model.addAttribute("posts", posts);
		}
		
		int nextPageNumber = 0;
		int previousPageNumber = 0;
		
		if(allPosts.size() > offset + count) {
			nextPageNumber = number + 1;
		}
		
		if(number != 1) {
			previousPageNumber = number - 1;
		}
		
		model.addAttribute("nextPageNumber", nextPageNumber);
		model.addAttribute("previousPageNumber", previousPageNumber);
		model.addAttribute("count", count);
		
		List<Label> labels = new ArrayList<Label>();
		
		LabelDAO labelDAO = new LabelDAO();
		
		labels = labelDAO.getAllLabels();
		
		model.addAttribute("labels", labels);
		
		return "admin-posts";
	}
	
	@RequestMapping(value = "/posts/edit", method = RequestMethod.GET)
	public String editPost(Locale locale, Model model,
		@RequestParam("id") int id,
		HttpServletRequest request,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		PostDAO postDAO = new PostDAO();
		
		try {
			post = postDAO.getPost(id);
		} catch (Exception e) {
			return "redirect:/error404";
		}
		
		model.addAttribute("post", post);
		
		List<Label> labels = new ArrayList<Label>();
		
		LabelDAO labelDAO = new LabelDAO();
		
		try {
			labels = labelDAO.getAllLabels();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("labels", labels);
		
		return "admin-edit-post";
	}
	
	@RequestMapping(value = "/posts/edit", method = RequestMethod.POST)
	public String editPost(Model model, HttpServletRequest request,
		@RequestParam("id") int id,
		@RequestParam("title") String title,
		@RequestParam("postText") String postText,
		@RequestParam(required = false, value="label") Set<Integer> labelsIds,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Post post = new Post();
		
		post.setId(id);
		post.setTitle(title);
		post.setText(postText);
		
		if(labelsIds != null) {
			LabelDAO labelDAO = new LabelDAO();
			
			List<Label> labels = new ArrayList<Label>();
			
			for(Integer labelId : labelsIds) {
				labels.add(labelDAO.getLabel(labelId));
			}
			
			post.setLabels(new HashSet<Label>(labels));
		}
		
		try {
			PostParametersValidationService.validatePostParameters(post);
		} catch (InvalidPostParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/posts/edit";
		}
		
		PostDAO postDAO = new PostDAO();
		
		Post oldPost = new Post();
		
		try {
			oldPost = postDAO.getPost(id);
		} catch (Exception e) {
			return "redirect:/error404";
		}
		
		post.setDatePublished(oldPost.getDatePublished());
		
		try {
			postDAO.updatePost(post);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/posts/edit";
		}
		
		return "redirect:/admin/posts";
	}
	
	@RequestMapping(value = "/posts/delete", method = RequestMethod.POST)
	public String deletePost(HttpServletRequest request,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		PostDAO postDAO = new PostDAO();
		
		try {
			postDAO.deletePost(id);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/posts/edit";
		}

		return "redirect:/admin/posts";
	}
	
	@RequestMapping(value = "/labels", method = RequestMethod.GET)
	public String showLabels(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		List<LabelColor> labelColors = new ArrayList<LabelColor>();
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		
		try {
			labelColors = labelColorDAO.getAllLabelColors();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		List<Label> labels = new ArrayList<Label>();
		
		LabelDAO labelDAO = new LabelDAO();
		
		try {
			labels = labelDAO.getAllLabels();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("labelColors", labelColors);
		model.addAttribute("labels", labels);
		
		return "admin-show-labels";
	}
	
	@RequestMapping(value = "/labels/create", method = RequestMethod.POST)
	public String createLabel(HttpServletRequest request,
		@RequestParam("name") String name,
		@RequestParam("labelColor") String color,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		LabelColor labelColor = new LabelColor();
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		
		labelColor = labelColorDAO.getLabelColor(color);
		
		Label label = new Label();
		
		label.setText(name);
		label.setColor(labelColor);
		
		try {
			LabelParametersValidationService.validateLabelParameters(label);
		} catch (InvalidLabelParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels";
		}
		
		LabelDAO labelDAO = new LabelDAO();
		
		try {
		labelDAO.createLabel(label);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels";
		}

		return "redirect:/admin/labels";
	}
	
	@RequestMapping(value = "/labels/edit", method = RequestMethod.GET)
	public String editLabel(Model model, HttpServletRequest request,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		List<LabelColor> labelColors = new ArrayList<LabelColor>();
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		
		try {
			labelColors = labelColorDAO.getAllLabelColors();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		Label label = new Label();
		
		LabelDAO labelDAO = new LabelDAO();
		
		try {
			label = labelDAO.getLabel(id);
		} catch (Exception e1) {
			return "redirect:/error404";
		}
	
		model.addAttribute("labelColors", labelColors);
		model.addAttribute("label", label);

		return "admin-edit-label";
	}
	
	@RequestMapping(value = "/labels/edit", method = RequestMethod.POST)
	public String editLabel(HttpServletRequest request,
		@RequestParam("name") String name,
		@RequestParam("labelColor") String color,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		LabelColor labelColor = new LabelColor();
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		
		labelColor = labelColorDAO.getLabelColor(color);
		
		Label label = new Label();
		
		LabelDAO labelDAO = new LabelDAO();
		
		try {
			label = labelDAO.getLabel(id);
		} catch (Exception e1) {
			return "redirect:/error404";
		}
		
		label.setText(name);
		label.setColor(labelColor);

		try {
			LabelParametersValidationService.validateLabelParameters(label);
		} catch (InvalidLabelParametersException e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels";
		}
		
		try {
			labelDAO.updateLabel(label);
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/admin/labels";
			}

			return "redirect:/admin/labels";
	}
	
	@RequestMapping(value = "/labels/delete", method = RequestMethod.POST)
	public String deleteLabel(HttpServletRequest request,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		LabelDAO labelDAO = new LabelDAO();
		
		try {
			labelDAO.deleteLabel(id);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels";
		}

		return "redirect:/admin/labels";
	}
	
	@RequestMapping(value = "/labels/colors", method = RequestMethod.GET)
	public String showColors(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		List<LabelColor> labelColors = new ArrayList<LabelColor>();
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		
		try {
			labelColors = labelColorDAO.getAllLabelColors();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("labelColors", labelColors);
		
		return "admin-show-colors";
	}
	
	@RequestMapping(value = "/labels/colors/create", method = RequestMethod.POST)
	public String createColor(HttpServletRequest request,
		@RequestParam("name") String name,
		@RequestParam("value") String value,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		LabelColor labelColor = new LabelColor();
		
		labelColor.setName(name);
		labelColor.setValue(value);
		
		try {
			LabelParametersValidationService.validateParameter(name, LabelParametersValidationService.MAX_LENGTH);
			LabelParametersValidationService.validateParameter(value, LabelParametersValidationService.MAX_LENGTH);
		} catch (InvalidLabelParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels/colors";
		}
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		
		try {
			labelColorDAO.createLabelColor(labelColor);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels/colors";
		}

		return "redirect:/admin/labels/colors";
	}
	
	@RequestMapping(value = "/labels/colors/edit", method = RequestMethod.GET)
	public String editColor(Model model, HttpServletRequest request,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		LabelColor labelColor = new LabelColor();
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		
		try {
			labelColor = labelColorDAO.getLabelColor(id);
		} catch (Exception e1) {
			return "redirect:/error404";
		}
		
		model.addAttribute("labelColor", labelColor);

		return "admin-edit-color";
	}
	
	@RequestMapping(value = "/labels/colors/edit", method = RequestMethod.POST)
	public String editColor(HttpServletRequest request,
		@RequestParam("name") String name,
		@RequestParam("value") String value,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		LabelColor labelColor = new LabelColor();
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		
		try {
			labelColor = labelColorDAO.getLabelColor(id);
		} catch (Exception e1) {
			return "redirect:/error404";
		}
		
		labelColor.setName(name);
		labelColor.setValue(value);
		
		try {
			LabelParametersValidationService.validateParameter(name, LabelParametersValidationService.MAX_LENGTH);
			LabelParametersValidationService.validateParameter(value, LabelParametersValidationService.MAX_LENGTH);
		} catch (InvalidLabelParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels/colors";
		}
		
		try {
			labelColorDAO.updateLabelColor(labelColor);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels/colors";
		}
		
		return "redirect:/admin/labels/colors";
	}
	
	@RequestMapping(value = "/labels/colors/delete", method = RequestMethod.POST)
	public String deleteColor(HttpServletRequest request,
		@RequestParam("id") int id,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		LabelColorDAO labelColorDAO = new LabelColorDAO();
		//
		
		try {
			labelColorDAO.deleteLabelColor(id);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/labels/colors";
		}

		return "redirect:/admin/labels/colors";
	}
	
	@RequestMapping(value = "/intro", method = RequestMethod.GET)
	public String showIntro(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		AppProperty appProperty = new AppProperty();
		
		AppPropertyDAO appPropertyDAO = new AppPropertyDAO();
		
		appProperty = appPropertyDAO.getAppProperty(AppProperty.INTRO);
		
		String introText = appProperty.getValue();
		
		model.addAttribute("introText", introText);
		
		return "admin-intro";
	}
	
	@RequestMapping(value = "/intro/save", method = RequestMethod.POST)
	public String saveIntro(Model model, HttpServletRequest request,
		@RequestParam("postText") String postText,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		AppProperty appProperty = new AppProperty();
		
		appProperty.setKey(AppProperty.INTRO);
		appProperty.setValue(postText);
		
		try {
			AppPropertyParametersValidationService.validateAppPropertyParameters(appProperty);
		} catch (InvalidAppPropertyParametersException e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/intro";
		}
		
		AppPropertyDAO appPropertyDAO = new AppPropertyDAO();
		
		try {
			appPropertyDAO.updateAppProperty(appProperty);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/intro";
		}
		
		return "redirect:/admin/intro";
	}
	
	@RequestMapping(value = "/aboutTheAuthor", method = RequestMethod.GET)
	public String aboutTheAuthor(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		AppProperty appProperty = new AppProperty();
		
		AppPropertyDAO appPropertyDAO = new AppPropertyDAO();
		
		appProperty = appPropertyDAO.getAppProperty(AppProperty.ABOUT_THE_AUTHOR);
		
		String aboutTheAuthorText = appProperty.getValue();
		
		model.addAttribute("aboutTheAuthorText", aboutTheAuthorText);
		
		return "admin-about-the-author";
	}
	
	@RequestMapping(value = "/aboutTheAuthor/save", method = RequestMethod.POST)
	public String aboutTheAuthorSave(Model model, HttpServletRequest request,
		@RequestParam("postText") String postText,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		AppProperty appProperty = new AppProperty();
		
		appProperty.setKey(AppProperty.ABOUT_THE_AUTHOR);
		appProperty.setValue(postText);
		
		try {
			AppPropertyParametersValidationService.validateAppPropertyParameters(appProperty);
		} catch (InvalidAppPropertyParametersException e1) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/aboutTheAuthor";
		}
		
		AppPropertyDAO appPropertyDAO = new AppPropertyDAO();
		
		try {
			appPropertyDAO.updateAppProperty(appProperty);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/admin/aboutTheAuthor";
		}
		
		return "redirect:/admin/aboutTheAuthor";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}

		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}

		return "admin-change-password";
	}
	
}
