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

	JSONObject dataJson = new JSONObject();
	JSONObject srcJson = new JSONObject();
	
	public void cookParam(ModelBean modelBean, JSONObject obj) {
		
		Tool.cookBean(modelBean, obj);
		
	}

	public void cookData(ModelBean modelBean, JSONObject obj) {
		dataJson.put(obj.getString("name"), obj.getString("value"));
		modelBean.setData(dataJson);
		
	}

	public void cookSrcData(ModelBean modelBean, JSONObject obj) {
		srcJson.put(obj.getString("name"), obj.getString("value"));
		modelBean.setData(srcJson);
		
	}


}
