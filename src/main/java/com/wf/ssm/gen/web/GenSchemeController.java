package com.wf.ssm.gen.web;

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
import com.wf.ssm.gen.entity.GenScheme;
import com.wf.ssm.gen.service.GenSchemeService;
import com.wf.ssm.gen.service.GenTableService;
import com.wf.ssm.gen.util.GenUtils;

/**
 * 生成方案Controller
 * @author wangpf
 * @version 2017-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genScheme")
public class GenSchemeController extends BaseController {

	@Autowired
	private GenSchemeService lnGenSchemeService;
	
	@Autowired
	private GenTableService genTableService;
	
	@ModelAttribute
	public GenScheme get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return lnGenSchemeService.get(id);
		}else{
			return new GenScheme();
		}
	}
	
	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = {"list", ""})
	public String list(GenScheme lnGenScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			lnGenScheme.setCreateBy(user);
		}
        Page<GenScheme> page = lnGenSchemeService.find(new Page<GenScheme>(request, response), lnGenScheme); 
        model.addAttribute("page", page);
		
		return "modules/gen/genSchemeList";
	}

	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = "form")
	public String form(GenScheme lnGenScheme, Model model) {
		if (StringUtils.isBlank(lnGenScheme.getPackageName())){
			lnGenScheme.setPackageName("com.wf.ssm.modules");
		}
//		if (StringUtils.isBlank(genScheme.getFunctionAuthor())){
//			genScheme.setFunctionAuthor(UserUtils.getUser().getName());
//		}
		model.addAttribute("lnGenScheme", lnGenScheme);
		model.addAttribute("config", GenUtils.getConfig());
		model.addAttribute("tableList", genTableService.findAll());
		return "modules/gen/genSchemeForm";
	}

	@RequiresPermissions("gen:genScheme:edit")
	@RequestMapping(value = "save")
	public String save(GenScheme lnGenScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, lnGenScheme)){
			return form(lnGenScheme, model);
		}
		
		String result = lnGenSchemeService.save(lnGenScheme);
		addMessage(redirectAttributes, "操作生成方案'" + lnGenScheme.getName() + "'成功<br/>"+result);
		return "redirect:" + adminPath + "/gen/genScheme/?repage";
	}
	
	@RequiresPermissions("gen:genScheme:edit")
	@RequestMapping(value = "delete")
	public String delete(GenScheme lnGenScheme, RedirectAttributes redirectAttributes) {
		lnGenSchemeService.delete(lnGenScheme);
		addMessage(redirectAttributes, "删除生成方案成功");
		return "redirect:" + adminPath + "/gen/genScheme/?repage";
	}

}
