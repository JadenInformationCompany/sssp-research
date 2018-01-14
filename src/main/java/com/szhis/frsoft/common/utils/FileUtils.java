package com.szhis.frsoft.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletResponse;

public class FileUtils {

	/**
	 * 下载文件
	 * @param response	输出对象
	 * @param fileName	服务器上的文件名
	 * @param viewName	下载到客户端，显示的文件名
	 * @param fileType	文件类型
	 * @return
	 */
	public final static boolean download(HttpServletResponse response,
		String fileName, String viewName, String fileType) {
		
		int extIndex = fileName.lastIndexOf(".");
		if(extIndex>0){
			if(fileType==null){
				fileType = fileName.substring(extIndex+1);
			}
			fileName = fileName.substring(0, extIndex);
		}
		//判断传值是否为 context-type，如果不是，则重新获取
		if(!StringUtils.isEmpty(fileType) && fileType.indexOf("/")==-1){
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			fileType = fileNameMap.getContentTypeFor("?."+fileType);
		}
		
        return download(response, new File(fileName), viewName, fileType);
    }
	
	public final static boolean download(HttpServletResponse response,
		File file, String viewName, String fileType) {
	    if(file.exists()){   		    	
	    	//清空response
	    	response.reset();
	    	
	    	response.setCharacterEncoding("utf-8");
	    	
	    	//文件类型	    	
	    	if(StringUtils.isEmpty(fileType)){	  
	    		response.setContentType("application/octet-stream");
	    	}else{
	    		response.setContentType(fileType);
	    	}
	    	
	        //设置文件长度(如果是Post请求，则这步不可少) 
	    	response.setHeader("content-Length", String.valueOf(file.length())); 
	        
	    	//下载时，显示的文件名
	    	if(!StringUtils.isEmpty(viewName)){
	    	    try {
					response.setHeader("Content-Disposition", "attachment; filename="+
						MimeUtility.encodeWord(viewName));//解决文件名中文乱码问题

						//java.net.URLEncoder.encode(viewName,"utf-8"));
				} catch (UnsupportedEncodingException e) {

				}  
	    	}
	    	   	
			try {
				InputStream inputStream = new FileInputStream(file);				
				OutputStream os = response.getOutputStream();
		        byte[] b = new byte[2048];
		        int length;
		        while ((length = inputStream.read(b)) > 0) {
		            os.write(b, 0, length);
		        }
		        os.close();
				inputStream.close();
			} catch (IOException e){
				e.printStackTrace();
			} 
	    	return true;
	    }else{
	    	return false;
	    }
	}

	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public final static boolean exists(String fileName){
		return new File(fileName).exists();
	}
	
	/**
	 * 根据source生成缩略图
	 * @param source 原始图片文件
	 * @param w 宽度
	 * @param h 高度
	 * @param descFile 目标文件
	 * @return
	 */
	public final static boolean thumbnailImage(File src, String descFile, int w, int h){
		if(!src.exists()){
			return false;
		}
		try {			
			// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
			Image img = ImageIO.read(src);
			int width = img.getWidth(null);
            int height = img.getHeight(null);
            if((width*1.0)/w < (height*1.0)/h){
                if(width > w){
                    h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));                    
                }
            } else {
                if(height > h){
                    w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                }
            }
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
            g.dispose();
            ImageIO.write(bi, "jpg", new File(descFile));
			return true;
		}catch (IOException e) {
			return false;
		}
	}
	
	public final static boolean thumbnailImage(String srcFile, String descFile, int w, int h){
		return thumbnailImage(new File(srcFile), descFile, w, h);
	}
	
	/**
	 * 截图
	 * @param srcImg
	 * @param output
	 * @param rect
	 * @return
	 */
	public final static boolean cutImage(File src, OutputStream desc, java.awt.Rectangle rect){
		if(!src.exists()){
			return false;
		}
		try {			
			// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
			Image img = ImageIO.read(src);			
            BufferedImage bi = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            g.drawImage(img, rect.x, rect.y, rect.width, rect.height, Color.LIGHT_GRAY, null);
            g.dispose();
            ImageIO.write(bi, "jpg", desc);
			return true;
		}catch (IOException e) {
			return false;
		}
	}
	
	public final static boolean cutImage(File src, String descFile, java.awt.Rectangle rect){
		if(!src.exists()){
			return false;
		}
		try {			
			// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
			Image img = ImageIO.read(src);			
            BufferedImage bi = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            g.drawImage(img, rect.x, rect.y, rect.width, rect.height, Color.LIGHT_GRAY, null);
            g.dispose();
            ImageIO.write(bi, "jpg", new File(descFile));
			return true;
		}catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * 获取文件扩展名
	 * @param fileName
	 * @return
	 */
	public final static String extractFileExt(String fileName){
		int extIndex = fileName.lastIndexOf(".");
		if(extIndex>0){
			return fileName.substring(extIndex+1);
		}else{
			return null;
		}
	}

}
