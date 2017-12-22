package com.jeeplus.modules.lazyearn.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.lazyearn.entity.AutoAccount;
import com.jeeplus.modules.lazyearn.entity.AutoControl;
import com.jeeplus.modules.lazyearn.entity.VicCodeText;
import com.jeeplus.modules.lazyearn.service.AutoControlService;
import com.jeeplus.modules.lazyearn.service.VicCodeTextService;
import com.jeeplus.modules.sys.utils.MySqlUtils;

/**
 * 智能回复管理
 * 
 * @author Stephen
 * @version 2017-9-27
 */
@Controller
@RequestMapping(value = "${adminPath}/lazyearn/IntelligentManager")
public class VicCodeTextController extends BaseController {
	@Autowired
	private VicCodeTextService vicCodeTextService;
	@Autowired
	private AutoControlService autoControlService;

	/**
	 * 跳转智能回复list页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "jumpList")
	public String list(HttpServletRequest request, Model model) {
		return "modules/lazyearn/lazyEarnManager/IntelligentList";
	}

	/**
	 * 智能回复分页查询
	 * 
	 * @param vicCodeText
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public Page<VicCodeText> findList(VicCodeText vicCodeText, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String title = request.getParameter("searchFund");// 搜索时带的关键字
		String selectedDevice = request.getParameter("selectedDevice");// 选择的设备号
		System.out.println("vicCodeText----------" + title);
		System.out.println("vicCodeText----------selectedDevice" + selectedDevice);
		if (title != null) {
			String str = MySqlUtils.specialStr(title);
			System.out.println("vicCodeText111----------" + str);
			vicCodeText.setKeyword(str);
			vicCodeText.setText(str);
			// vicCodeText.setDevices(str);
		}
		if (selectedDevice != null && !selectedDevice.equals("") && !selectedDevice.equals("null")
				&& !selectedDevice.equals("0")) {
			String str1 = MySqlUtils.specialStr(selectedDevice);
			System.out.println("vicCodeText111----------selectedDevice" + str1);
			vicCodeText.setDevices(str1);
		} else {
			vicCodeText.setDevices(null);
		}
		System.out.println("vic--" + vicCodeText.getKeyword());
		System.out.println("vic---" + vicCodeText.getDevices());
		Page<VicCodeText> page = vicCodeTextService.findPage(new Page<VicCodeText>(request, response), vicCodeText);
		return page;
	}

	/**
	 * 编辑或保存智能回复
	 * 
	 * @param vicCodeText
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public int save(VicCodeText vicCodeText, Model model, HttpServletRequest request, HttpServletResponse response) {
		String keyword = vicCodeText.getKeyword().trim();//去除前后空格
		int result = 0;
		String texts = request.getParameter("textIds");
		String codeTextIds = request.getParameter("codeTextIds");
		String type = request.getParameter("type");
		String device = request.getParameter("selectedDevice");
		String dataShareFlag = request.getParameter("switchCheck");
		System.out.println("vicCodeKeyword---" + keyword);
		System.out.println("vicCodeTexts---" + texts);
		System.out.println("vicCodeType---" + type);
		System.out.println("vicCodeIds---" + codeTextIds);
		System.out.println("vicCodeSelectedDevices---" + device);
		System.out.println("vicCodeDataShareFlag---" + dataShareFlag);
		String[] textList = texts.split(",");
		String[] codeTextIdList = codeTextIds.split(",");
		System.out.println("codeTextIdList---" + codeTextIdList.length);
		
		// 遍历当前回复内容
		for (int i = 0; i < textList.length; i++) {
			// 先获取当前关键词下的内容数量
			List<VicCodeText> currentList = findIntelligentResponse(vicCodeText, device,model, request, response);
			System.out.println("当前关键词下内容数量--" + currentList.size());
			if (currentList.size() < 5) {
				VicCodeText vicCodeText1 = new VicCodeText();
				vicCodeText1.setText(textList[i].trim());//去除前后空格
				vicCodeText1.setType(type);
				// 如果要添加的长度不为0（1、只添加一条时，长度为1;2、编辑时添加），判断一条时，是否为空
				if (codeTextIdList.length != 0 && !codeTextIdList[0].equals("")) {
					if (codeTextIdList.length <= i) {
						// 创建时间
						Date now = new Date();
						System.out.println("date---" + now);
						vicCodeText1.setCreatime(stampToDate(now));
						System.out.println("stampToDate(now)---" + stampToDate(now));
					} else {
						vicCodeText1.setCodeTextId(Integer.parseInt(codeTextIdList[i]));
					}
				} else {// 如果要添加的长度为0（1、添加多条时;）或者添加一条时，等于空
					// 创建时间
					Date now = new Date();
					System.out.println("date---" + now);
					vicCodeText1.setCreatime(stampToDate(now));
					System.out.println("stampToDate(now)1---" + stampToDate(now));
				}
				// 默认为关闭
				vicCodeText1.setDataShareFlag(false);
				if (dataShareFlag != null) {
					if (Integer.parseInt(dataShareFlag) == 0) {
						vicCodeText1.setDataShareFlag(false);
					} else if (Integer.parseInt(dataShareFlag) == 1) {
						vicCodeText1.setDataShareFlag(true);
					}
				}
				vicCodeText1.setKeyword(keyword);
				vicCodeText1.setDevices(device);
				System.out.println("vicCodeKeyword---保存的" + vicCodeText1.getKeyword());
				System.out.println("vicCodeDevices---保存的" + vicCodeText1.getDevices());
				System.out.println("vicCodeType---保存的" + vicCodeText1.getType());
				System.out.println("vicCodeText---保存的" + vicCodeText1.getText());
				System.out.println("vicCodeId111---保存的" + vicCodeText1.getCodeTextId());
				result = vicCodeTextService.saveData(vicCodeText1);
			} else if(currentList.size() == 5 && codeTextIdList.length != 0 && !codeTextIdList[i].equals("")) {
				VicCodeText vicCodeText2 = new VicCodeText();
				vicCodeText2.setText(textList[i].trim());//去除前后空格
				vicCodeText2.setType(type);
				vicCodeText2.setCodeTextId(Integer.parseInt(codeTextIdList[i]));
				// 默认为关闭
				vicCodeText2.setDataShareFlag(false);
				if (dataShareFlag != null) {
					if (Integer.parseInt(dataShareFlag) == 0) {
						vicCodeText2.setDataShareFlag(false);
					} else if (Integer.parseInt(dataShareFlag) == 1) {
						vicCodeText2.setDataShareFlag(true);
					}
				}
				vicCodeText2.setKeyword(keyword);
				vicCodeText2.setDevices(device);
				System.out.println("vicCodeKeyword---保存的" + vicCodeText2.getKeyword());
				System.out.println("vicCodeDevices---保存的" + vicCodeText2.getDevices());
				System.out.println("vicCodeType---保存的" + vicCodeText2.getType());
				System.out.println("vicCodeText---保存的" + vicCodeText2.getText());
				System.out.println("vicCodeId111---保存的" + vicCodeText2.getCodeTextId());
				result = vicCodeTextService.saveData(vicCodeText2);
			} else {
				result = -2;
			}
		}
		return result;
	}

	/**
	 * 跳转智能回复form表单
	 * 
	 * @param vicCodeText
	 * @param codeId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "jumpForm")
	public String jumpForm(VicCodeText vicCodeText, Integer codeId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("codeTextId---" + codeId);
		if (codeId != null) {
			vicCodeText.setCodeTextId(codeId);
			VicCodeText vicCodeText1 = vicCodeTextService.get(vicCodeText);
			model.addAttribute("vicCodeText", vicCodeText1);
		}
		return "modules/lazyearn/lazyEarnManager/IntelligentForm";
	}

	/**
	 * 删除1条智能回复
	 * 
	 * @param vicCodeText
	 * @param codeTextId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public int delete(VicCodeText vicCodeText, Integer codeTextId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		int result = 0;
		if (codeTextId != null) {
			vicCodeText.setCodeTextId(codeTextId);
			VicCodeText viCodeText1 = vicCodeTextService.get(vicCodeText);
			result = vicCodeTextService.deleteByCodeTextId(viCodeText1);
			System.out.println("删除智能回复" + result);
		}
		return result;
	}

	/**
	 * 批量删除智能回复
	 * 
	 * @param vicCodeText
	 * @param codeTextId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public int deleteAll(VicCodeText vicCodeText, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		int result = 0;
		String[] delitems = request.getParameter("delitems").split(",");
		for (int i = 0; i < delitems.length; i++) {
			vicCodeText.setCodeTextId(Integer.parseInt(delitems[i]));
			VicCodeText viCodeText1 = vicCodeTextService.get(vicCodeText);
			result = vicCodeTextService.deleteByCodeTextId(viCodeText1);
			System.out.println("批量删除智能回复" + result);
		}
		return result;
	}

	/*
	 * 将Date转换为时间
	 */
	public static String stampToDate(Date date) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 通过关键词查询相关记录
	 * 
	 * @param vicCodeText
	 * @param keyword
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findIntelligentResponse")
	@ResponseBody
	public List<VicCodeText> findIntelligentResponse(VicCodeText vicCodeText, String selectedDevice,Model model,
			HttpServletRequest request, HttpServletResponse response) {
		List<VicCodeText> list = new ArrayList<VicCodeText>();
		String keyword = vicCodeText.getKeyword();
		System.out.println("查询时选择的设备号========="+selectedDevice);
		if (keyword != "" && keyword != null && !keyword.equals("")) {
			vicCodeText.setKeyword(keyword);
			if(selectedDevice == null || ("").equals(selectedDevice) || selectedDevice == "null") {

			}else {				
				vicCodeText.setDevices(selectedDevice);
			}
			list = vicCodeTextService.findIntelligentResponse(vicCodeText);
			System.out.println("智能回复-------------" + list);
		}
		return list;
	}

	/**
	 * 查找所有的主设备号
	 * 
	 * @param autoControl
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findMainDevices")
	@ResponseBody
	public List<AutoControl> findMainDevices(AutoControl autoControl, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		List<AutoControl> list = new ArrayList<AutoControl>();
		list = autoControlService.findList(autoControl);
		List<AutoControl> showList = new ArrayList<AutoControl>();
		// 定义HashSet，用于去重
		HashSet<String> set = new HashSet<>();
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i).getMaindevices());
		}
		// 遍历set，重新赋值
		for (String s : set) {
			System.out.println("maindevcies----" + s);
			AutoControl autoControl2 = new AutoControl();
			autoControl2.setMaindevices(s);
			showList.add(autoControl2);
		}
		return showList;
	}

	/**
	 *查找所有的分控设备号
	 * 
	 * @param autoControl
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findChildDevices")
	@ResponseBody
	public List<AutoControl> findChildDevices(AutoControl autoControl, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		List<AutoControl> list = new ArrayList<AutoControl>();
		list = autoControlService.findList(autoControl);
		List<AutoControl> showList = new ArrayList<AutoControl>();
		
		// 定义HashSet，用于去重
		HashSet<String> set = new HashSet<>();
		
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i).getPartdevices());
		}
		
		// 遍历set，重新赋值
		for (String s : set) {
			System.out.println("maindevcies----" + s);
			AutoControl autoControl2 = new AutoControl();
			autoControl2.setPartdevices(s);
			showList.add(autoControl2);
		}
		return showList;
	}
	/**
	 * 动态匹配每次查找15个的子设备号
	 * 
	 * @param autoControl
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findChildNumDevices")
	@ResponseBody
	public List<AutoControl> findChildNumDevices(AutoControl autoControl, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		List<AutoControl> list = new ArrayList<AutoControl>();
		list = autoControlService.findNumList(autoControl);
		List<AutoControl> showList = new ArrayList<AutoControl>();
		//定义HashSet，用于去重
		HashSet<String> set = new HashSet<>();
		for(int i = 0;i<list.size();i++) {
			set.add(list.get(i).getPartdevices());
		}
		//遍历set，重新赋值
		for(String s : set) {
			System.out.println("maindevcies----"+s);
			AutoControl autoControl2 = new AutoControl();
			autoControl2.setPartdevices(s);
			showList.add(autoControl2);
		}
		return showList;
	}
}