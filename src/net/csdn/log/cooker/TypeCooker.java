package net.csdn.log.cooker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.csdn.log.bean.ModelBean;
import net.csdn.log.cooker.forklift.Tool;

public class TypeCooker {

/*	public void cook(ModelBean modelBean, String type, String locate,
			String value) {

		if ("multipe".equals(type)) {

			JSONArray array = JSONArray.parseArray(locate);

			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				this.cook(modelBean, obj.getString("type"),
						obj.getString("locate"), value);
			}

		} else if ("string".equals(type)) {

			Tool.cookLocate(modelBean, locate, value);

		} else if ("time".equals(type)) {

			if ("timestamp".equals(type)) {

			}

		}

	}*/

	public static void cook(ModelBean modelBean, JSONObject obj) {

		if ("multipe".equals(obj.getString("type"))) {

			JSONArray array = JSONArray.parseArray(obj.getString("locate"));

			for (int i = 0; i < array.size(); i++) {
				JSONObject obj2 = array.getJSONObject(i);
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

		}

	}

}
