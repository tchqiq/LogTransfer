package net.csdn.log.formatter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import net.csdn.log.bean.ModelBean;
import net.csdn.log.cooker.TypeCooker;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class LogFormatter {

	private static Configuration config = new Configuration();

	private static FileSystem fs;

	static {
		try {
			fs = FileSystem.get(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

//		 if (args.length != 3) {
//		 System.out.println("args num wrong!");
//		 System.exit(0);
//		 }
//		
//		 String confPath = args[0];
//		 String inputPath = args[1];
//		 String outputPath = args[2];
		 String confPath = "d:\\work\\conf\\download_comment_action.json";
		 String inputPath = "d:\\work\\conf\\download_download.input";
		 String outputPath = "d:\\work\\conf\\download_comment_action.output";

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
		Path input = new Path(inputPath);
		Path output = new Path(outputPath);

		BufferedReader br = null;
		BufferedWriter bw = null;
		FSDataInputStream dis = null;
		FSDataOutputStream os = null;

		String splitter = obj.getString("splitter");
		JSONArray array = obj.getJSONArray("array");
		JSONArray def = obj.getJSONArray("default");
		ModelBean modelBean = new ModelBean(def);
		
		int count = 0;
		try {
			dis = fs.open(input);
			os = fs.create(output, true);
			br = new BufferedReader(new InputStreamReader(dis, "utf-8"));
			bw = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
			String line = null;
			int jsize = array.size();
			while ((line = br.readLine()) != null) {
				String[] strs = line.split(splitter);
				if (strs.length != jsize) {
					System.out.println("log clumns is not match the conf of json " + count + ":" + line);
					continue;
				}

				for (int i = 0; i < jsize; i++) {
					JSONObject joj = array.getJSONObject(i);
					joj.put("value", strs[i]);
					TypeCooker.cook(modelBean, joj);
				}

				bw.write(modelBean.toString());
				bw.newLine();

				if(++count % 100 == 0) {
					System.out.println("processed :" + count);
				}
			}
			
			System.out.println("total line :" + count);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (bw != null) {
					bw.flush();
					bw.close();
				}
				dis.close();
				br.close();
				if (fs != null) {
					fs.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static JSONObject getConfJson(String confPath) {
		JSONObject obj = null;
		FSDataInputStream dis = null;
		try {
			Path conf = new Path(confPath);
			dis = fs.open(conf);
			byte[] buff = new byte[1024];
			// 从字符串获取字节写入流
			int len = -1;
			StringBuilder resBuilder = new StringBuilder();
			while (-1 != (len = dis.read(buff))) {
				// 将字节数组转换为字符串
				resBuilder.append(new String(buff, 0, len));
			}
			obj = JSONObject.parseObject(resBuilder.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
