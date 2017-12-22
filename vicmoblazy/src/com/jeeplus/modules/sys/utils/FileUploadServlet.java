package com.jeeplus.modules.sys.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jeeplus.modules.sys.utils.ConfigurationFileHelper;
import com.jeeplus.modules.tools.utils.AesEncrypt;

public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public FileUploadServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	boolean successFlag = false;
    	
    	FileItem file = null;
        InputStream in = null;
		
		System.out.println("~~~~~~~~~進入上傳圖片的接口~~~~~~~~~~~~~~~");
		String savePicPath = ConfigurationFileHelper.getSavePicPath();
		
		String minaId = "";
		String appId = "";		
		String picName = "";
		String proName = "";
		
		// 文件网络路径
		String networkPath = "";
			
		String typePakage = "image";


		
		try {
			
			request.setCharacterEncoding("UTF-8"); 

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);//判断是否是表单文件类型
			System.out.println("isMultipart:"+isMultipart);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
	        //解决上传文件名的中文乱码  
	        upload.setHeaderEncoding("UTF-8"); 
	        // 1. 得到 FileItem 的集合 items  
	        List<FileItem> items = upload.parseRequest(request);
	        System.out.println("------------"+upload);
	        System.out.println("items.size():"+items.size());
	        System.out.println("items:"+items);
	        // 2. 遍历 items:  
	        for (FileItem item : items) {  
	            String name = item.getFieldName();  
	            System.out.println("name:"+name);
	            // 若是一个一般的表单域, 打印信息  
	            if (item.isFormField()) {  
	                String value = item.getString("utf-8");  
	                if("minaId".equals(name)){
	                	minaId = value;
	                }else if("appId".equals(name)){
	                	appId = value;
	                }else if("picName".equals(name)){
	                	picName = value;
	                	System.out.println("picName:"+picName);
	                }else{
	                	proName = value;
	                	System.out.println("proName:"+proName);
	                }
	            }else {
	                if("image".equals(name)){
	                	file = item;
	                }
	            }  
	        }
	        System.out.println("file:"+file);
	        
	        try {
				minaId=AesEncrypt.aesDecryption(appId, minaId);
				if(!minaId.equals("0")&&minaId!=null&&!minaId.equals("")){
					if(file!=null){
						
						savePicPath = savePicPath + proName +"/web";// 保存图片去服务器的路径
						
			        	in = file.getInputStream();
			        	
			        	int lastIndex = picName.lastIndexOf(".");
						picName = String.valueOf(System.currentTimeMillis()) + picName.substring(lastIndex);
			        	
						Date data = new Date(System.currentTimeMillis());
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
						String dataPakage = dateFormat.format(data);// 日期
			        	
						// 本地的路径
						//String path = "d:/img/";
						String path = savePicPath + "/minaId_" + minaId + "/"+ typePakage +"/" + dataPakage;// 服务器所需路径
						File fileTarget = new File(path, picName);
						
						// 将文件存到当前controller所在服务器
						File pakageFile = new File(path);
						if (!pakageFile.exists() && !pakageFile.isDirectory()) {
							pakageFile.mkdirs();// 创建文件夹
						}
						
						FileOutputStream os = new FileOutputStream(fileTarget);
			        	
						byte[] b = new byte[1024];
						int byteRead = 0;
						while ((byteRead = in.read(b)) != -1) {
							os.write(b, 0, byteRead);
						}
						in.close();
						os.flush();
						os.close();
						
						// 拼网络路径
						networkPath = path + "/" + picName;
						int index = networkPath.indexOf("vicmob");
						networkPath = "/" + networkPath.substring(index);
						
						successFlag = true;
			        }
												
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();  
		
		if(successFlag){			
	        out.write(networkPath);			
		}else{
			out.write("upLoadImgFail");		
		}      
          
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

