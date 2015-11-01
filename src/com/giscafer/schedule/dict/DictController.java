package com.giscafer.schedule.dict;

import java.io.IOException;
import java.util.List;

import com.giscafer.schedule.query.QueryFilter;
import com.giscafer.utils.DataUtils;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class DictController extends Controller{

	public void index(){}
	/**
	 * 查询
	 * @throws IOException
	 */
	@ActionKey("/queryDict")
	public void queryDict() throws IOException{
		QueryFilter queryFilter=new QueryFilter();
		String whereStr="domainName='"+getPara("filter")+"'";
		queryFilter.setWhereString(whereStr);
		queryFilter.setSelectFields("dictCode,dictName");
		queryFilter.setOrderString("dictCode asc");
		List<Dict> dictList=Dict.me.find(queryFilter);
		String result=DataUtils.listToJsonStr(dictList, Dict.me);
		renderJson(result);
	}
}
