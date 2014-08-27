package net.csdn.log.cooker.forklift;

import com.alibaba.fastjson.JSONObject;

import net.csdn.log.bean.ModelBean;
import net.csdn.log.cooker.LocateCooker;

public class Tool {

	public static void cookLocate(ModelBean modelBean, JSONObject obj) {

		LocateCooker locater = new LocateCooker();
		String locate = obj.getString("locate");
		
		if ("params".equals(locate)) {
			locater.cookParam(modelBean, obj);
		} else if ("data".equals(locate)) {
			locater.cookData(modelBean, obj);
		} else if ("src_data".equals(locate)) {
			locater.cookSrcData(modelBean, obj);
		}

	}
	
	public static void cookBean(ModelBean modelBean, JSONObject obj) {
		
		String name = obj.getString("name");
		String value = obj.getString("value");
		
		if("IP".equals(name)) {
			modelBean.setIP(value);
		} else if("record_time".equals(name)) {
			modelBean.setRecord_time(value);
		} else if("addr".equals(name)) {
			modelBean.setAddr(value);
		} else if("pid".equals(name)) {
			modelBean.setPid(value);
		} else if("sub_pid".equals(name)) {
			modelBean.setSub_pid(value);
		} else if("source_user".equals(name)) {
			modelBean.setSource_user(value);
		} else if("record_user".equals(name)) {
			modelBean.setRecord_user(value);
		} else if("role".equals(name)) {
			modelBean.setRole(value);
		} else if("action".equals(name)) {
			modelBean.setAction(value);
		} else if("sub_action".equals(name)) {
			modelBean.setSub_action(value);
		} else if("version".equals(name)) {
			modelBean.setVersion(value);
		}
		
	}

}
