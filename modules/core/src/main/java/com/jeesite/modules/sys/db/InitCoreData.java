/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.db;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeesite.common.callback.MethodCallback;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.tests.BaseInitDataTests;
import com.jeesite.modules.sys.dao.RoleMenuDao;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.entity.Company;
import com.jeesite.modules.sys.entity.CompanyOffice;
import com.jeesite.modules.sys.entity.Config;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.DictType;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.EmployeePost;
import com.jeesite.modules.sys.entity.Log;
import com.jeesite.modules.sys.entity.Menu;
import com.jeesite.modules.sys.entity.Module;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.Post;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.RoleDataScope;
import com.jeesite.modules.sys.entity.RoleMenu;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.entity.UserDataScope;
import com.jeesite.modules.sys.entity.UserRole;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.service.CompanyService;
import com.jeesite.modules.sys.service.ConfigService;
import com.jeesite.modules.sys.service.DictDataService;
import com.jeesite.modules.sys.service.DictTypeService;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.service.MenuService;
import com.jeesite.modules.sys.service.ModuleService;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.service.PostService;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.UserService;

/**
 * 初始化核心表数据
 * @author ThinkGem
 * @version 2017-10-22
 */
public class InitCoreData extends BaseInitDataTests {

	@Override
	public void begin() {
		super.begin();
		excelFile = InitCoreData.class.getName().replaceAll("\\.", "/")+".xlsx";
	}
	
	/**
	 * 建表语句执行
	 */
	public void createTable() throws Exception{
		runScript("core.sql");
		runScript("job.sql");
	}

	/**
	 * 清理日志表
	 */
	public void initLog() throws Exception{
		clearTable(Log.class);
	}
	
	@Autowired
	private AreaService areaService;
	/**
	 * 区域、行政区划表
	 */
	public void initArea() throws Exception{
		clearTable(Area.class);
		initExcelData(Area.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					Area entity = (Area)params[1];
					entity.setIsNewRecord(true);
					areaService.save(entity);
					return null;
				}
				return null;
			}
		});
	}
	
	@Autowired
	private ConfigService configService;
	/**
	 * 参数配置表
	 */
	public void initConfig() throws Exception{
		clearTable(Config.class);
		initExcelData(Config.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					Config entity = (Config)params[1];
					entity.setId(IdGen.nextId());
					entity.setIsNewRecord(true);
					configService.save(entity);
					return null;
				}
				return null;
			}
		});
	}

	@Autowired
	private ModuleService moduleService;
	/**
	 * 系统模块表
	 */
	public void initModule() throws Exception{
		clearTable(Module.class);
		initExcelData(Module.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					Module entity = (Module)params[1];
					entity.setIsNewRecord(true);
					moduleService.save(entity);
					return null;
				}
				return null;
			}
		});
	}
	
	@Autowired
	private DictTypeService dictTypeService;
	@Autowired
	private DictDataService dictDataService;
	/**
	 * 系统字典、用户字典表
	 */
	public void initDict() throws Exception{
		clearTable(DictType.class);
		initExcelData(DictType.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					DictType entity = (DictType)params[1];
					entity.setId(IdGen.nextId());
					entity.setIsNewRecord(true);
					dictTypeService.save(entity);
					return null;
				}
				return null;
			}
		});

		clearTable(DictData.class);
		initExcelData(DictData.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					DictData entity = (DictData)params[1];
					entity.setIsNewRecord(true);
					dictDataService.save(entity);
					return null;
				}
				return null;
			}
		});
	}
	
	@Autowired
	private RoleService roleService;
	/**
	 * 角色表
	 */
	public void initRole() throws Exception{
		clearTable(Role.class);
		clearTable(RoleMenu.class);
		clearTable(RoleDataScope.class);
		initExcelData(Role.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					Role entity = (Role)params[1];
					entity.setIsNewRecord(true);
					roleService.save(entity);
					return null;
				}
				return null;
			}
		});
	}

	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleMenuDao roleMenuDao;
	/**
	 * 菜单表
	 */
	public void initMenu() throws Exception{
		clearTable(Menu.class);
		clearTable(RoleMenu.class);
		initExcelData(Menu.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					Menu entity = (Menu)params[1];
					entity.setIsNewRecord(true);
					menuService.save(entity);
					RoleMenu rm = new RoleMenu();
					rm.setMenuCode(entity.getMenuCode());
					rm.setRoleCode(Role.CORP_ADMIN_ROLE_CODE);
					roleMenuDao.insert(rm);
					return null;
				}
				return null;
			}
		});
	}
	
	@Autowired
	private UserService userService;
	/**
	 * 用户表
	 */
	public void initUser() throws Exception{
		clearTable(User.class);
		clearTable(UserRole.class);
		clearTable(UserDataScope.class);
		initExcelData(User.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					User entity = (User)params[1];
					entity.setIsNewRecord(true);
					entity.setPassword(UserService.encryptPassword(entity.getPassword()));
					userService.save(entity);
					return null;
				}
				return null;
			}
		});
	}
	
	@Autowired
	private OfficeService officeService;
	/**
	 * 组织机构、部门表
	 */
	public void initOffice() throws Exception{
		clearTable(Office.class);
		initExcelData(Office.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					Office entity = (Office)params[1];
					entity.setIsNewRecord(true);
					officeService.save(entity);
					return null;
				}
				return null;
			}
		});
	}

	@Autowired
	private CompanyService companyService;
	/**
	 * 公司表
	 */
	public void initCompany() throws Exception{
		clearTable(Company.class);
		clearTable(CompanyOffice.class);
		initExcelData(Company.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					Company entity = (Company)params[1];
					entity.setIsNewRecord(true);
					companyService.save(entity);
					return null;
				}
				return null;
			}
		});
	}

	@Autowired
	private PostService postService;
	/**
	 * 岗位表
	 */
	public void initPost() throws Exception{
		clearTable(Post.class);
		initExcelData(Post.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("save".equals(action)){
					Post entity = (Post)params[1];
					entity.setIsNewRecord(true);
					postService.save(entity);
					return null;
				}
				return null;
			}
		});
	}
	
	@Autowired
	private EmpUserService empUserService;
	/**
	 * 员工、用户表
	 */
	public void initEmpUser() throws Exception{
		clearTable(Employee.class);
		clearTable(EmployeePost.class);
		initExcelData(EmpUser.class, new MethodCallback() {
			@Override
			public Object execute(Object... params) {
				String action = (String)params[0];
				if("set".equals(action)){
					EmpUser entity = (EmpUser)params[1];
					String header = (String)params[2];
					String val = (String)params[3];
					if ("userRoleString".equals(header)){
						entity.setUserRoleString(val);
						return true;
					}else if ("employee.employeePosts".equals(header)){
						entity.getEmployee().setEmployeePosts(new String[]{val});
						return true;
					}
				}
				else if("save".equals(action)){
					EmpUser entity = (EmpUser)params[1];
					entity.setIsNewRecord(true);
					entity.setPassword(UserService.encryptPassword(entity.getPassword()));
					empUserService.save(entity);
					// 设置当前为管理员，否则无法保存用户角色关系
					entity.setCurrentUser(new User(User.SUPER_ADMIN_CODE));
					userService.saveAuth(entity);
					return null;
				}
				return null;
			}
		});
	}
}
