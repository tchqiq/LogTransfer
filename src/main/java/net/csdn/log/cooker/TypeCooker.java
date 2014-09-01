package net.csdn.log.cooker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;

import net.csdn.log.bean.ModelBean;
import net.csdn.log.cooker.forklift.Tool;

public class TypeCooker {


	public static void cook(ModelBean modelBean, JSONObject obj) {

		if ("multiple".equals(obj.getString("type"))) {

			JSONArray array = JSONArray.parseArray(obj.getString("locate"));

			for (int i = 0; i < array.size(); i++) {
				JSONObject obj2 = array.getJSONObject(i);
				obj2.put("value",obj.getString("value"));
				cook(modelBean, obj2);
			}

		} else if ("string".equals(obj.getString("type"))) {

			Tool.cookLocate(modelBean, obj);

		} else if ("time".equals(obj.getString("type"))) {

			if ("timestamp".equals(obj.getString("original"))) {
				
				SimpleDateFormat sdf = new SimpleDateFormat(obj.getString("target"));
				String date = sdf.format(new Date(obj.getLongValue("value")*1000L));
				obj.put("value", date);
				Tool.cookLocate(modelBean, obj);
				
			} else {
				SimpleDateFormat sdfOrige = new SimpleDateFormat(obj.getString("original"));
				SimpleDateFormat sdfTarget = new SimpleDateFormat(obj.getString("target"));
				String date = null;
				try {
					date = sdfTarget.format(sdfOrige.parse(obj.getString("value")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				obj.put("value", date);
				Tool.cookLocate(modelBean, obj);
				
			}

		} else if ("mapping".equals(obj.getString("type"))) {
			JSONObject jmap = obj.getJSONObject("mapping");
			String value = jmap.getString(obj.getString("value"));
				
			obj.put("value", value);
			Tool.cookLocate(modelBean, obj);
				
		}

	}

}
