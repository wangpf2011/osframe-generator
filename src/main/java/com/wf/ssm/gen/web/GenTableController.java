package com.wf.ssm.gen.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wf.ssm.common.persistence.Page;
import com.wf.ssm.common.utils.StringUtils;
import com.wf.ssm.common.web.BaseController;
import com.wf.ssm.core.sys.entity.User;
import com.wf.ssm.core.sys.utils.UserUtils;
import com.wf.ssm.gen.entity.GenTable;
import com.wf.ssm.gen.service.GenTableService;
import com.wf.ssm.gen.util.GenUtils;

/**
 * 业务表Controller
 * @author wangpf
 * @version 2017-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genTable")
public class GenTableController extends BaseController {

	@Autowired
	private GenTableService lnGenTableService;
	
	@ModelAttribute
	public GenTable get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return lnGenTableService.get(id);
		}else{
			return new GenTable();
		}
	}
	
	@RequiresPermissions("gen:genTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(GenTable lnGenTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			lnGenTable.setCreateBy(user);
		}
        Page<GenTable> page = lnGenTableService.find(new Page<GenTable>(request, response), lnGenTable); 
        model.addAttribute("page", page);
		return "modules/gen/genTableList";
	}

	@RequiresPermissions("gen:genTable:view")
	@RequestMapping(value = "form")
	public String form(GenTable lnGenTable, Model model) {
		// 获取物理表列表
		List<GenTable> tableList = lnGenTableService.findTableListFormDb(new GenTable());
		model.addAttribute("tableList", tableList);
		// 验证表是否存在
		if (StringUtils.isBlank(lnGenTable.getId()) && !lnGenTableService.checkTableName(lnGenTable.getName())){
			addMessage(model, "下一步失败！" + lnGenTable.getName() + " 表已经添加！");
			lnGenTable.setName("");
		}
		// 获取物理表字段
		else{
			lnGenTable = lnGenTableService.getTableFormDb(lnGenTable);
		}
		model.addAttribute("lnGenTable", lnGenTable);
		model.addAttribute("config", GenUtils.getConfig());
		return "modules/gen/genTableForm";
	}

	@RequiresPermissions("gen:genTable:edit")
	@RequestMapping(value = "save")
	public String save(GenTable lnGenTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, lnGenTable)){
			return form(lnGenTable, model);
		}
		// 验证表是否已经存在
		if (StringUtils.isBlank(lnGenTable.getId()) && !lnGenTableService.checkTableName(lnGenTable.getName())){
			addMessage(model, "保存失败！" + lnGenTable.getName() + " 表已经存在！");
			lnGenTable.setName("");
			return form(lnGenTable, model);
		}
		lnGenTableService.save(lnGenTable);
		addMessage(redirectAttributes, "保存业务表'" + lnGenTable.getName() + "'成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}
	
	@RequiresPermissions("gen:genTable:edit")
	@RequestMapping(value = "delete")
	public String delete(GenTable lnGenTable, RedirectAttributes redirectAttributes) {
		lnGenTableService.delete(lnGenTable);
		addMessage(redirectAttributes, "删除业务表成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}

}
