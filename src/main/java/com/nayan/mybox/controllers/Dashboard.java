package com.nayan.mybox.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nayan.mybox.models.UFile;
import com.nayan.mybox.models.User;
import com.nayan.mybox.service.UFileService;


@Controller
public class Dashboard {
	
	Logger ulogs = LoggerFactory.getLogger(Dashboard.class);
	
	@Autowired
	UFileService fileService;
	
	
	
	
	@GetMapping("/dashboard")
	public String GetUploadForm(Model model,@AuthenticationPrincipal User user) {
			
		
		List<UFile>fileList = fileService.GetAllByOwner(user.getId());
		model.addAttribute("files",fileList);
		return "dashboard";
	}
	
	@PostMapping("/dashboard/upload")
	public String setUploadedFile(@RequestParam("ufile") MultipartFile file,@AuthenticationPrincipal User user) {
		
		try {
		 fileService.storeFile(file,user.getId());
		} catch (IOException e) {
			ulogs.error(e.getMessage());
		}
		return "redirect:/dashboard";
	}
	
	@GetMapping("/dashboard/download/{id}")
	public ResponseEntity<InputStreamResource> getFile(@PathVariable("id")String id) {
		
		ulogs.info("Id of file:- "+id);
		
		UFile f = fileService.getFileById(id);
		
		if (f.getData() != null) {
			return ResponseEntity.ok()
					  .contentType(MediaType.parseMediaType(f.getContenType()))
					  .contentLength(f.getSize())
					  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+f.getName())
					  .body(new InputStreamResource(f.getData()));
		}
		return (ResponseEntity<InputStreamResource>) ResponseEntity.notFound();
	}
	
}
