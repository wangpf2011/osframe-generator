package com.wf.ssm.gen.dao;

import java.util.List;
import com.wf.ssm.common.persistence.mybatis.CrudDao;
import com.wf.ssm.common.persistence.mybatis.annotation.MyBatisDao;
import com.wf.ssm.gen.entity.GenTable;
import com.wf.ssm.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author wangpf
 * @version 2017-02-20
 */
@MyBatisDao
public interface GenDataBaseDictDao extends CrudDao<GenTableColumn> {

	/**
	 * 查询表列表
	 * @param genTable
	 * @return
	 */
	List<GenTable> findTableList(GenTable genTable);

	/**
	 * 获取数据表字段
	 * @param genTable
	 * @return
	 */
	List<GenTableColumn> findTableColumnList(GenTable genTable);
	
	/**
	 * 获取数据表主键
	 * @param genTable
	 * @return
	 */
	List<String> findTablePK(GenTable genTable);
	
}
