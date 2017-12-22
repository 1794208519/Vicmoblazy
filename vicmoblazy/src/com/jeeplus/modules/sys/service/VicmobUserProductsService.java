package com.jeeplus.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.VicmobUserProductsDao;
import com.jeeplus.modules.sys.entity.VicmobUserProducts;

/**
 * 用户产品表Service
 * 
 * @author Jeezy
 * @version 2017-11-03
 */
@Service
@Transactional(readOnly = true)
public class VicmobUserProductsService extends CrudService<VicmobUserProductsDao, VicmobUserProducts> {

}
