
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.VicmobTemplateDao;
import com.jeeplus.modules.sys.entity.VicmobTemplate;


/**
 * 模板消息Service
 * @author squirrel
 * @version 2017-10-12 */
@Service
@Transactional(readOnly = true)
public class VicmobTemplateService extends CrudService<VicmobTemplateDao,VicmobTemplate> {
	
	@Autowired
	private VicmobTemplateDao vicmobTemplateDao;
	/**
	 * 查询一条模板ID(发送模板消息使用)
	 */
	public VicmobTemplate findEntity(int inform_type,int minaId) {
		VicmobTemplate vicmobTemplate = new VicmobTemplate();
		vicmobTemplate.setInformType(inform_type);
		vicmobTemplate.setMinaId(minaId);
		return vicmobTemplateDao.findTemplate(vicmobTemplate);
	}
	
	@Override
	public List<VicmobTemplate> findList(VicmobTemplate entity) {
		// TODO Auto-generated method stub
		return super.findList(entity);
	}
	
	/**
	 * 插入或者更新方法
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void saveData(List<VicmobTemplate> list) {
		for (int i = 0; i < list.size(); i++) {
			VicmobTemplate vicmobTemplate = list.get(i);
			if (vicmobTemplate.getTemplateId() != null) {
				update(vicmobTemplate);
			} else {
				save(vicmobTemplate);
			}
		}
	}
	
}