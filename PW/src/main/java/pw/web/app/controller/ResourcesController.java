package pw.web.app.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pw.web.app.dao.FileResourceDAO;
import pw.web.app.dao.ImageResourceDAO;
import pw.web.app.model.Admin;
import pw.web.app.model.FileResource;
import pw.web.app.model.ImageResource;
import pw.web.app.model.User;

@Controller
@RequestMapping(value = "/admin")
public class ResourcesController {
	
	@RequestMapping(value="/images/{name:.+}", method = RequestMethod.GET)
	public void showImage(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		ImageResource imageResource = new ImageResource();
		
		ImageResourceDAO imageResourceDAO = new ImageResourceDAO();
		
		imageResource = imageResourceDAO.getImage(name);

		InputStream in = new ByteArrayInputStream(imageResource.getData());
		try {
			response.getOutputStream().write(imageResource.getData());
			in.close();
		} catch (IOException e) {
			redirectAttributes.addAttribute("message", "DatabaseError");
			try {
				response.sendRedirect("redirect:/error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/images/deleteImage", method = RequestMethod.POST)
	public String deleteImage(HttpServletRequest request,
		@RequestParam("name") String name,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		ImageResourceDAO imageResourceDAO = new ImageResourceDAO();
		
		imageResourceDAO.deleteImage(name);
		
		return "redirect:/admin/images";
	}

	@RequestMapping(value="/files/{name:.+}", method = RequestMethod.GET)
	public void showFile(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		FileResource fileResource = new FileResource();
		
		FileResourceDAO fileResourceDAO = new FileResourceDAO();
		
		fileResource = fileResourceDAO.getFile(name);

		InputStream in = new ByteArrayInputStream(fileResource.getData());
		try {
			response.getOutputStream().write(fileResource.getData());
			in.close();
		} catch (IOException e) {
			redirectAttributes.addAttribute("message", "DatabaseError");
			try {
				response.sendRedirect("redirect:/error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/files/deleteFile", method = RequestMethod.POST)
	public String deleteFile(HttpServletRequest request,
		@RequestParam("name") String name,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		FileResourceDAO fileResourceDAO = new FileResourceDAO();
		
		fileResourceDAO.deleteFile(name);
		
		return "redirect:/admin/files";
	}
	
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public String showImages(Locale locale, Model model, HttpServletRequest request) {

		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		return "redirect:/admin/images/page/1";
	}
	
	@RequestMapping(value = "/images/page/{number}", method = RequestMethod.GET)
	public String showPaginatedImages(HttpServletRequest request, Model model,
		@PathVariable("number") int number,
		@RequestParam(name="count", required=false) Integer count) {
		
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
		
		List<ImageResource> images = new ArrayList<ImageResource>();
		List<ImageResource> allImages = new ArrayList<ImageResource>();
		
		ImageResourceDAO imageResourceDAO = new ImageResourceDAO();
		
		images = imageResourceDAO.getPaginatedImages(offset, count);
		allImages = imageResourceDAO.getAllImages();
		
		model.addAttribute("images", images);
		
		int nextPageNumber = 0;
		int previousPageNumber = 0;
		
		if(allImages.size() > offset + count) {
			nextPageNumber = number + 1;
		}
		
		if(number != 1) {
			previousPageNumber = number - 1;
		}
		
		model.addAttribute("nextPageNumber", nextPageNumber);
		model.addAttribute("previousPageNumber", previousPageNumber);
		model.addAttribute("count", count);
		
		String currentUrl = request.getRequestURL().toString();

		StringBuilder stringBuilder = new StringBuilder();
		
		String[] urlParts = currentUrl.split("/");
		
		int i=0;
		
		while(i < urlParts.length - 2) {
			if (i < urlParts.length - 3) {
				stringBuilder.append(urlParts[i] + "/");
			}
			else {
				stringBuilder.append(urlParts[i]);
			}
			i++;
		}
		
		String resourceUrl = stringBuilder.toString();
		
		model.addAttribute("resourceUrl", resourceUrl);

		return "admin-show-images";
	}
	
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public String showFiles(Locale locale, Model model, HttpServletRequest request) {

		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		return "redirect:/admin/files/page/1";
	}
	
	@RequestMapping(value = "/files/page/{number}", method = RequestMethod.GET)
	public String showPaginatedFiles(HttpServletRequest request, Model model,
		@PathVariable("number") int number,
		@RequestParam(name="count", required=false) Integer count) {
		
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
		
		List<FileResource> files = new ArrayList<FileResource>();
		List<FileResource> allFiles = new ArrayList<FileResource>();
		
		FileResourceDAO fileResourceDAO = new FileResourceDAO();
		
		files = fileResourceDAO.getPaginatedFiles(offset, count);
		allFiles = fileResourceDAO.getAllFiles();
		
		model.addAttribute("files", files);
		
		int nextPageNumber = 0;
		int previousPageNumber = 0;
		
		if(allFiles.size() > offset + count) {
			nextPageNumber = number + 1;
		}
		
		if(number != 1) {
			previousPageNumber = number - 1;
		}
		
		model.addAttribute("nextPageNumber", nextPageNumber);
		model.addAttribute("previousPageNumber", previousPageNumber);
		model.addAttribute("count", count);
		
		String currentUrl = request.getRequestURL().toString();

		StringBuilder stringBuilder = new StringBuilder();
		
		String[] urlParts = currentUrl.split("/");
		
		int i=0;
		
		while(i < urlParts.length - 2) {
			if (i < urlParts.length - 3) {
				stringBuilder.append(urlParts[i] + "/");
			}
			else {
				stringBuilder.append(urlParts[i]);
			}
			i++;
		}
		
		String resourceUrl = stringBuilder.toString();
		
		model.addAttribute("resourceUrl", resourceUrl);

		return "admin-show-files";
	}
	
	@RequestMapping(value="/images/uploadImage", method=RequestMethod.POST, consumes = {"multipart/form-data"})
    public String imageUpload(HttpServletRequest request, HttpServletResponse response,
    	@RequestPart("image") MultipartFile multipartFile,
    	RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
        	
		ImageResourceDAO imageResourceDAO = new ImageResourceDAO();
		
		try {
			imageResourceDAO.uploadImage(multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/admin/images";
	}
	
	@RequestMapping(value="/files/uploadFile", method=RequestMethod.POST, consumes = {"multipart/form-data"})
    public String fileUpload(HttpServletRequest request, HttpServletResponse response,
    	@RequestPart("file") MultipartFile multipartFile,
    	RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.CURRENT_USER) instanceof Admin)) {
			return "redirect:/error403";
		}
        	
		FileResourceDAO fileResourceDAO = new FileResourceDAO();
		
		try {
			fileResourceDAO.uploadFile(multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/admin/files";
	}
	
}
