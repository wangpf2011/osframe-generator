package com.wf.ssm.gen.dao;

import com.wf.ssm.common.persistence.mybatis.CrudDao;
import com.wf.ssm.common.persistence.mybatis.annotation.MyBatisDao;
import com.wf.ssm.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author wangpf
 * @version 2017-02-20
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
