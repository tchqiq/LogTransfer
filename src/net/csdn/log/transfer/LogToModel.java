package net.csdn.log.transfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicates;
import com.google.common.io.CharStreams;

public class LogToModel {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("args num wrong!");
			System.exit(0);
		}

		String inputPath = args[0];
		String outputPath = args[1];
		String confPath = args[2];

		JSONArray jarr = getConfJson(confPath);

		transfer(inputPath, outputPath, jarr);
	}

	private static void transfer(String inputPath, String outputPath,
			JSONArray jarr) {

		BufferedReader br = null;
		BufferedWriter bw = null;

		try {
			br = new BufferedReader(new FileReader(new File(inputPath)));
			String line = null;
			int jsize = jarr.size();
			while ((line = br.readLine()) != null) {
				String[] strs = line.split("\t");
				if (strs.length != jsize) {
					System.out
							.println("log clumns is not match the conf of json :"
									+ line);
					continue;
				}

				for (int i = 0; i < jsize; i++) {

				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static JSONArray getConfJson(String confPath) {
		JSONArray jarr = null;
		try {
			File file = new File("confPath");
			FileReader reader = new FileReader(file);
			int fileLen = (int) file.length();
			char[] chars = new char[fileLen];
			reader.read(chars);
			String txt = String.valueOf(chars);
			jarr = JSONObject.parseArray(txt);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return jarr;
	}
}
