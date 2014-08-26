package net.csdn.log.transfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.csdn.log.bean.ModelBean;
import net.csdn.log.cooker.TypeCooker;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class LogFormatter {

	public static void main(String[] args) {
//		if (args.length != 3) {
//			System.out.println("args num wrong!");
//			System.exit(0);
//		}
 
//		String inputPath = args[0];
//		String inputPath = args[0];
//		String outputPath = args[1];
		String confPath = "d:\\work\\conf\\download_download.json";
		String inputPath = "d:\\work\\conf\\download_download.input";
		String outputPath = "d:\\work\\conf\\download_download.output";

		JSONObject obj = getConfJson(confPath);

		transfer(inputPath, outputPath, obj);
	}

	/**
	 * @param inputPath
	 * @param outputPath
	 * @param obj
	 */
	private static void transfer(String inputPath, String outputPath,
			JSONObject obj) {

		BufferedReader br = null;

		String splitter = obj.getString("splitter");
		JSONArray array = obj.getJSONArray("array");
		JSONArray def = obj.getJSONArray("default");
		ModelBean modelBean = new ModelBean(def);
		try {
			br = new BufferedReader(new FileReader(new File(inputPath)));
			String line = null;
			int jsize = array.size();
			while ((line = br.readLine()) != null) {
				String[] strs = line.split(splitter);
				if (strs.length != jsize) {
					System.out
							.println("log clumns is not match the conf of json :"
									+ line);
					continue;
				}

				for (int i = 0; i < jsize; i++) {
					JSONObject joj = array.getJSONObject(i);
					joj.put("value", strs[1]);
					TypeCooker.cook(modelBean, joj);
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static JSONObject getConfJson(String confPath) {
		JSONObject obj = null;
		try {
			File file = new File(confPath);
			FileReader reader = new FileReader(file);
			int fileLen = (int) file.length();
			char[] chars = new char[fileLen];
			reader.read(chars);
			String txt = String.valueOf(chars);
			obj = JSONObject.parseObject(txt);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
