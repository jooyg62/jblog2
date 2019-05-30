package com.cafe24.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.vo.FileVo;


@Service
public class FileUploadService {
	
	private static final Log LOG = LogFactory.getLog( FileUploadService.class );
	
	public static final String FILE_SAVE_PATH = "/jblog-uploads";
	
	public FileVo restore(MultipartFile multipartFile) {
		FileVo fileVo = null;
		
		if(multipartFile == null || multipartFile.isEmpty()) {
			return null;
		}
		
		try {
			
			fileVo = new FileVo();
			
			String originalFilename = multipartFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			String saveFileName = getSaveFileName(extName);
			
			LOG.info("################" + originalFilename);
			LOG.info("################" + extName);
			LOG.info("################" + saveFileName);
			
			fileVo.setOriginalFilename(originalFilename);
			fileVo.setExtName(extName);
			fileVo.setSaveFileName(saveFileName);
			fileVo.setUrl(FILE_SAVE_PATH + "/" + saveFileName);
			
			byte[] fileBytes = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(FILE_SAVE_PATH + "/" + saveFileName);
			
			os.write(fileBytes);
			os.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileVo;
	}
	
	/**
	 * 저장할 파일명을 가져온다.
	 * @param extName 확장자(.jpg, .png, ... etc)
	 * @return
	 */
	private String getSaveFileName(String extName) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int mon = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int mills = calendar.get(Calendar.MILLISECOND);
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(year)
					.append(mon)
					.append(day)
					.append(hour)
					.append(min)
					.append(mills);
		
		return stringBuffer.toString() + "." +extName;
	}
}
