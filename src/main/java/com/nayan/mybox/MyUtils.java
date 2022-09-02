package com.nayan.mybox;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.nayan.mybox.models.UFile;

public class MyUtils {

	public static UFile GridFStoUfile(GridFSFile gfs) {
		
		UFile f = new UFile();
		f.setId(gfs.getId().toString());
		f.setName(gfs.getFilename());
		f.setSize(gfs.getLength());
		
		return f;
	}
}
