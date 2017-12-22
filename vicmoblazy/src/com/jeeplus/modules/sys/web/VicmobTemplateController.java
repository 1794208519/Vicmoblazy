package com.jeeplus.modules.sys.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.VicmobPayAccount;
import com.jeeplus.modules.sys.entity.VicmobTemplate;
import com.jeeplus.modules.sys.service.VicmobTemplateService;




@Controller
@RequestMapping(value = "${adminPath}/mina/template")
public class VicmobTemplateController extends BaseController {
	
	@Autowired
	private VicmobTemplateService vicmobTemplateService;
	

	
	@RequestMapping(value = "jump")
	public String jump(Model model, HttpServletRequest request , HttpServletResponse response) {
		
		return "modules/sys/sysTemplate";
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public int save(Model model, HttpServletRequest request , HttpServletResponse response) {
		try {
			String template1  = request.getParameter("template1");
			String template2  = request.getParameter("template2");
			String template3  = request.getParameter("template3");
			String template4  = request.getParameter("template4");
			String template5  = request.getParameter("template5");
			String template6  = request.getParameter("template6");
			String template7  = request.getParameter("template7");
			
			List<VicmobTemplate> list = new ArrayList<>();
			list.add(strToEntity(template1));
			list.add(strToEntity(template2));
			list.add(strToEntity(template3));
			list.add(strToEntity(template4));
			list.add(strToEntity(template5));
			list.add(strToEntity(template6));
			list.add(strToEntity(template7));
			
			vicmobTemplateService.saveData(list);
			
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	@RequestMapping(value = "findList")
	@ResponseBody
	public List<VicmobTemplate> findList(Model model, HttpServletRequest request , HttpServletResponse response) {
		Integer minaId = Integer.parseInt(request.getParameter("minaId"));
		System.out.println("========minaId=======" + minaId);
		VicmobTemplate vicmobTemplate = new VicmobTemplate();
		vicmobTemplate.setMinaId(minaId);
		List<VicmobTemplate> list = vicmobTemplateService.findList(vicmobTemplate);
		return list;
	}
	
	public VicmobTemplate strToEntity(String s) {
		VicmobTemplate wTemplate = new VicmobTemplate();
		String ss[] = s.split("&");
		for (int i = 0; i < ss.length; i++) {
			String wString[] = ss[i].split("=");
			if (wString.length > 1) {
				if (wString[0].equals("templateId")) {
					wTemplate.setTemplateId(Integer.parseInt(wString[1]));
				}
				if (wString[0].equals("minaId")) {
					wTemplate.setMinaId(Integer.parseInt(wString[1]));
				}
				if (wString[0].equals("informType")) {
					wTemplate.setInformType(Integer.parseInt(wString[1]));
				}
				if (wString[0].equals("informId")) {
					wTemplate.setInformId(wString[1]);
				}
			}
		}
		System.out.println(wTemplate);
		return wTemplate;
	}
}