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
@RequestMapping(value = "/certUpload")
public class UploadCertController extends BaseController {

	@RequestMapping(value = "saveCert")
	@ResponseBody
	public String saveFile(HttpServletRequest request, Model model) {
		// 证书路径
		String certPath = "";
		//小程序ID
		String minaId = request.getParameter("minaId");
		//保存
		String saveCertPath = request.getParameter("saveCertPath");


		try {
			InputStream imgStr = request.getInputStream();

			String name = request.getParameter("name");// 图片的名字
			int lastIndex = name.lastIndexOf(".");
			name = "apiclient_cert_" + minaId + name.substring(lastIndex);

			Date data = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataPakage = dateFormat.format(data);// 日期

			// 本地的路径
			String path = saveCertPath + "/cert/" + dataPakage;// 服务器所需路径
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
			certPath = path + "/" + name;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return certPath;
	}

}
