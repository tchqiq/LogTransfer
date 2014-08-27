package net.csdn.log.bean;

import java.util.ArrayList;
import java.util.List;

import net.csdn.log.cooker.TypeCooker;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ModelBean {
	
	private String IP;
	private String record_time;
	private String addr;
	private String pid;
	private String sub_pid;
	private String source_user;
	private String record_user;
	private String role;
	private String action;
	private String sub_action;
	private String version;
	private JSONObject data;
	private JSONObject src_data;
	
	JSONObject dataJson = new JSONObject();
	JSONObject srcJson = new JSONObject();
	
	public ModelBean(JSONArray def) {
		
		for (int i = 0; i < def.size(); i++) {
			JSONObject obj = def.getJSONObject(i);
			TypeCooker.cook(this, obj);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(
				"\001200\001POST /push/");
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject data = new JSONObject();
		JSONObject src = new JSONObject();
		
		if(this.data != null) {
			data.put("data", this.data);
			list.add(data);
		}
		if(this.src_data != null) {
			src.put("src_data", this.src_data);
			list.add(src);
		}
		
		sb.insert(0, record_time + "\001" + IP);
		sb.append(addr);
		sb.append("?pid=" + pid);
		sb.append("&sub_pid=" + sub_pid);
		sb.append("&source_user=" + source_user);
		sb.append("&record_user=" + record_user);
		sb.append("&role=" + role);
		sb.append("&action=" + action);
		sb.append("&sub_action=" + sub_action);
		sb.append("&version=" + version);
		sb.append(" HTTP/1.1\001-\001-\001");
		sb.append(JSONObject.toJSONString(list));
		sb.append("\001-");

		return sb.toString();
	}



	public String getRecord_time() {
		return record_time;
	}

	public void setRecord_time(String record_time) {
		this.record_time = record_time;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSub_pid() {
		return sub_pid;
	}

	public void setSub_pid(String sub_pid) {
		this.sub_pid = sub_pid;
	}

	public String getSource_user() {
		return source_user;
	}

	public void setSource_user(String source_user) {
		this.source_user = source_user;
	}

	public String getRecord_user() {
		return record_user;
	}

	public void setRecord_user(String record_user) {
		this.record_user = record_user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSub_action() {
		return sub_action;
	}

	public void setSub_action(String sub_action) {
		this.sub_action = sub_action;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(String key,String value) {
		dataJson.put(key,value);
		this.data = dataJson;
	}

	public JSONObject getSrc_date() {
		return src_data;
	}

	public void setSrc_date(String key,String value) {
		srcJson.put(key, value);
		this.src_data = srcJson;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

}
