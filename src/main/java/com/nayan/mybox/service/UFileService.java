package com.nayan.mybox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.nayan.mybox.MyUtils;
import com.nayan.mybox.models.UFile;

@Service
public class UFileService {
		
	
	@Autowired
	private GridFsTemplate template;
	
	@Autowired
	private GridFsOperations operations;


	public void storeFile(MultipartFile uploadedFile,ObjectId userid) throws IOException {
		
		DBObject fmeta = new BasicDBObject();
		
		fmeta.put("file_name", uploadedFile.getOriginalFilename());
		fmeta.put("file_size", uploadedFile.getSize());
		fmeta.put("owner", userid);
		
		 template.store(uploadedFile.getInputStream(), 
						uploadedFile.getOriginalFilename(),
						uploadedFile.getContentType(),fmeta);
		
	}
	
	
	public UFile getFileById(String id){
		GridFSFile file  = template.findOne(new Query(Criteria.where("_id").is(id)));
	
		UFile f = new UFile();
		GridFsResource r = operations.getResource(file);
		
		try {
			f.setData(r.getInputStream());
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			return f;
		}
		f.setContenType(r.getContentType());
		f.setName(file.getFilename());
		f.setSize( file.getLength());
		
		return f;
	}
	
	public List<UFile> GetAllByOwner(ObjectId owner) {
		
		List<GridFSFile> gfsFiles = new ArrayList<GridFSFile>();
		template.find(new Query(Criteria.where("metadata.owner").is(owner))).into(gfsFiles);
		
		List<UFile> file = new ArrayList<>();
		
			gfsFiles.stream().forEach(t -> {
				file.add(MyUtils.GridFStoUfile(t));
			});
			
			return file;
	}
}
