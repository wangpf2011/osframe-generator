package com.wf.ssm.gen.dao;

import com.wf.ssm.common.persistence.mybatis.CrudDao;
import com.wf.ssm.common.persistence.mybatis.annotation.MyBatisDao;
import com.wf.ssm.gen.entity.GenTemplate;

/**
 * 代码模板DAO接口
 * @author wangpf
 * @version 2017-02-20
 */
@MyBatisDao
public interface GenTemplateDao extends CrudDao<GenTemplate> {
	
}
