package com.giscafer.schedule.scheduler;

import java.util.List;

import com.giscafer.utils.DataUtils;
import com.jfinal.core.Controller;

import data.general.DataService;
import data.general.QueryFilter;
import data.general.UpdateFilter;

/**
 * 
 * @ClassName: SchedulerController
 * @Description: TODO(排班管理Controller)
 * @author giscafer
 * @date 2015-11-15 上午12:31:15
 * 
 */
public class SchedulerController extends Controller {

	public DataService dataService = new DataService();

	/**
	 * 加载首页
	 */
	public void index() {
	}

	/**
	 * 查询人员分组信息
	 */
	public void getGroupPersonList() {
		QueryFilter queryFilter = new QueryFilter();
		if (getPara() == null) {
			queryFilter.setWhereString("1=1");
		} else {
			queryFilter.setWhereString(getPara());
		}
		System.out.println(getPara());// 传参方式分隔符为“/”
		queryFilter.setSelectFields("*");
		queryFilter.setOrderString("gid desc");
		List<GroupPerson> dictList = GroupPerson.me.getEntityList(queryFilter);
		String result = DataUtils.listToJsonStr(dictList, GroupPerson.me);
		renderJson(result);
	}

	/**
	 * 查询排班记录信息
	 */
	public void getSchedulerList() {
		QueryFilter queryFilter = new QueryFilter();
		if (getPara() == null) {
			queryFilter.setWhereString("1=1");
		} else {
			String visStart = getPara("visStart");
			String visEnd = getPara("visEnd");
			String where = "day>='" + visStart + "' and day<'" + visEnd + "'";
			queryFilter.setWhereString(where);
		}

		System.out.println("getRequest().getQueryString()"
				+ getRequest().getQueryString());// 传参方式分隔符为“/”
		queryFilter.setSelectFields("*");
		queryFilter.setOrderString("id ASC");
		List<Scheduler> dictList = Scheduler.me.getEntityList(queryFilter);
		String result = DataUtils.listToJsonStr(dictList, Scheduler.me);
		renderJson(result);
	}

	/**
	 * 更新排班
	 */
	public void updateSchedule() {
		int result = 0;
		UpdateFilter updateFilter = new UpdateFilter();
		// String pid=getPara("pid"); //人员id
		// String day=getPara("day"); //排班日期
		// String where="pid='"+pid+"' and day='"+day+"'";
		String setFields = getPara("setFields"); // 排班日期
		String whereString = getPara("whereString"); // 排班日期
		updateFilter.setWhereString(whereString);
		if (setFields == null)
			renderNull();
		updateFilter.setSetFields(setFields);
		result = dataService.update(Scheduler.tableName, updateFilter);
		renderJson(result);
	}
}
