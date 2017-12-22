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
 * 图片上传
 * @author YeJR
 *
 */
@Controller
@RequestMapping(value = "/fileUpload")
public class UploadController extends BaseController {

	@RequestMapping(value = "saveFile")
	@ResponseBody
	public String saveFile(HttpServletRequest request, Model model) {
		// 文件网络路径
		String networkPath = "";

		String savePicPath = request.getParameter("savePicPath");// 保存图片去服务器的路径
		String upType = request.getParameter("upType");

		String typePakage = "";

		if (upType.equals("upImg")) {
			typePakage = "image";
		} else if (upType.equals("upMusic")) {
			typePakage = "music";
		} else if (upType.equals("upVideo")) {
			typePakage = "video";
		}

		try {
			InputStream imgStr = request.getInputStream();

			String name = request.getParameter("name");// 图片的名字
			int lastIndex = name.lastIndexOf(".");
			name = String.valueOf(System.currentTimeMillis()) + name.substring(lastIndex);

			Date data = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataPakage = dateFormat.format(data);// 日期

			// 本地的路径
			String path = "E:"+"/earn" +"/"+ typePakage +"/" + dataPakage;
//			String path = savePicPath + "/earn" +"/"+ typePakage +"/" + dataPakage;// 服务器所需路径
			File file = new File(path, name);

			// 将文件存到当前controller所在服务器
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

			// 拼网络路径
			networkPath = path + "/" + name;
//			int index = networkPath.indexOf("vicmob");
//			networkPath = "/" + networkPath.substring(index);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return networkPath;
	}

}
