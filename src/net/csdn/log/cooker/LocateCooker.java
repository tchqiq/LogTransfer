package net.csdn.log.cooker;

import com.alibaba.fastjson.JSONObject;

import net.csdn.log.bean.ModelBean;
import net.csdn.log.cooker.forklift.Tool;

/**
 * 处理日志字段摆放位置
 * @author heqi
 *
 */
public class LocateCooker {

	public void cookParam(ModelBean modelBean, JSONObject obj) {
		
		Tool.cookBean(modelBean, obj);
		
	}

	public void cookData(ModelBean modelBean, JSONObject obj) {
		modelBean.setData(obj.getString("name"), obj.getString("value"));
		
	}

	public void cookSrcData(ModelBean modelBean, JSONObject obj) {
		modelBean.setSrc_date(obj.getString("name"), obj.getString("value"));
		
	}


}
