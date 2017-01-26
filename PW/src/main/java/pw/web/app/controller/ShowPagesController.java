package pw.web.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pw.web.app.dao.AppPropertyDAO;
import pw.web.app.dao.LabelDAO;
import pw.web.app.dao.PostDAO;
import pw.web.app.model.AppProperty;
import pw.web.app.model.Label;
import pw.web.app.model.Post;

@Controller
public class ShowPagesController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		
		return "redirect:/posts/page/1";
	}
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String showPosts(Locale locale, Model model, HttpServletRequest request) {
		
		return "redirect:/posts/page/1";
	}
	
	@RequestMapping(value = "/posts/page/{number}", method = RequestMethod.GET)
	public String showPaginatedPosts(HttpServletRequest request, Model model,
		@PathVariable("number") int number,
		@RequestParam(name="count", required=false) Integer count,
		@RequestParam(value="id", required = false) Integer labelId,
		RedirectAttributes redirectAttributes) {
		
		if(count == null) {
			count = 10;
		}
		
		int offset = number * count - count;
		
		if(offset < 0) {
			offset = 0;
		}
		
		if(number == 1) {
			AppPropertyDAO appPropertyDAO = new AppPropertyDAO();
			
			String introText = appPropertyDAO.getAppProperty(AppProperty.INTRO).getValue();
			
			model.addAttribute("introText", introText);
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

		return "home";
	}
	
	@RequestMapping(value = "/aboutTheAuthor", method = RequestMethod.GET)
	public String aboutTheAuthor(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		AppProperty appProperty = new AppProperty();
		
		AppPropertyDAO appPropertyDAO = new AppPropertyDAO();
		
		appProperty = appPropertyDAO.getAppProperty(AppProperty.ABOUT_THE_AUTHOR);
		
		String aboutTheAuthorText = appProperty.getValue();
		
		model.addAttribute("aboutTheAuthorText", aboutTheAuthorText);
		
		return "about-the-author";
	}
	
}
