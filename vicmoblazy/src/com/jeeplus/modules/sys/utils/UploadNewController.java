package com.jeeplus.modules.sys.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.web.BaseController;

/**
 *  上传
 * @author YeJR
 *
 */
@Controller
@RequestMapping(value = "common/upload")
public class UploadNewController extends BaseController{
	/** 上传图片  */
	private String upTypeStr = "upImg";
	/** 上传音乐  */
	private String upMusicStr = "upMusic";
	/** 上传音频  */
	private String upVideoStr = "upVideo";
	/** 上传头像 */
	private String upTitleImgStr = "upTitleImg";
	
	@RequestMapping(value = "saveFile")
	@ResponseBody
	public String saveFile(HttpServletRequest request,Model model) {
		//文件网络路径
		String networkPath = "";
		//保存图片去服务器的路径	
		String minaId = request.getParameter("minaId");
		String savePath = request.getParameter("savePath");
		String upType = request.getParameter("upType");
		
		String typePakage = "";
		
		if(upTypeStr.equals(upType)) {
			typePakage = "image";
		} else if (upMusicStr.equals(upType)) {
			typePakage = "music";
		} else if (upVideoStr.equals(upType)) {
			typePakage = "video";
		} else if (upTitleImgStr.equals(upType)) {
			typePakage = "image";
		}
		
		try {
			InputStream imgStr = request.getInputStream();
			// 图片的名字
			String name = request.getParameter("name");
			int lastIndex = name.lastIndexOf(".");
			name = String.valueOf(System.currentTimeMillis()) + name.substring(lastIndex);
			
			Date data = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			// 日期
			String dataPakage = dateFormat.format(data);
			
			//服务器所需路径
			String path = savePath+"/minaId_" + minaId + "/"+ typePakage +"/"+ dataPakage;
			File file = new File(path, name);
			
			//将文件存到当前controller所在服务器
			File pakageFile = new File(path);
			if (!pakageFile.exists() && !pakageFile.isDirectory()) {
				pakageFile.mkdirs();// 创建文件夹
			}

			FileOutputStream os = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int byteRead = 0;
			while ((byteRead = imgStr.read(b)) != -1) {
				os.write(b, 0, byteRead);
			}
			os.flush();
			os.close();
			
			//拼网络路径
			networkPath = path + "/" + name;
			int index = networkPath.indexOf("vicmob");
			networkPath = "/" + networkPath.substring(index);
		} catch (IOException e) {
			e.printStackTrace();
		}	
				
		return networkPath;
	}
	
}
