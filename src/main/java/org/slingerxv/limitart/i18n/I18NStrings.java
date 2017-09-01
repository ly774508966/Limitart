/*
 * Copyright (c) 2016-present The Limitart Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.slingerxv.limitart.i18n;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slingerxv.limitart.util.StringUtil;

/**
 * 国际化字符串<br>
 * 通过以本国熟悉语言(如：中国)作Key来索引其他语言<br>
 * 我爱你=我爱你(在lang_zh_cn.properties中)<br>
 * 我爱你=i love u(在lang_en.properties中)
 * 
 * @author hank
 *
 */
public class I18NStrings {
	private static Logger log = LoggerFactory.getLogger(I18NStrings.class);
	private Map<Languages, Map<String, String>> langs = new HashMap<>();
	private Map<Languages, Properties> dumps = new HashMap<>();
	private static String DUMP_PATH = "lang_dumps";

	/**
	 * 通过二进制加载字符串键值对
	 * 
	 * @param lang
	 *            语言类型
	 * @param content
	 *            文件内容
	 * @throws IOException
	 */
	public void loadProperty(Languages lang, byte[] content) throws IOException {
		loadProperty(lang, new ByteArrayInputStream(content));
	}

	/**
	 * 通过文件加载
	 * 
	 * @param lang
	 * @param file
	 * @throws IOException
	 */
	public void loadProperty(Languages lang, File file) throws IOException {
		loadProperty(lang, new FileInputStream(file));
	}

	/**
	 * 通过输入流加载
	 * 
	 * @param lang
	 *            语言类型
	 * @param inputStream
	 *            输入流
	 * @throws IOException
	 */
	public void loadProperty(Languages lang, InputStream inputStream) throws IOException {
		Properties prop = new Properties();
		prop.load(inputStream);
		loadProperty(lang, prop);
		inputStream.close();
	}

	/**
	 * 通过Properties加载
	 * 
	 * @param lang
	 *            语言类型
	 * @param prop
	 *            键值对文件
	 * @throws IOException
	 */
	public void loadProperty(Languages lang, Properties prop) throws IOException {
		for (Entry<Object, Object> entry : prop.entrySet()) {
			add(lang, entry.getKey().toString(), entry.getValue().toString());
		}
		log.info("load lang {0} done,count:{1}", lang.name(), prop.size());
	}

	/**
	 * 添加指定语言的翻译
	 * 
	 * @param lan
	 *            语言类型
	 * @param key
	 *            语言key(通常为操作系统所在语言)
	 * @param content
	 *            内容
	 */
	public void add(Languages lang, String key, String content) {
		if (StringUtil.isEmptyOrNull(content)) {
			return;
		}
		if (!langs.containsKey(lang)) {
			langs.put(lang, new HashMap<>());
		}
		Map<String, String> map = langs.get(lang);
		if (map.containsKey(key)) {
			log.error("lang {0} key duplicated {2}", lang.name(), key);
			return;
		}
		map.put(key, content);
	}

	/**
	 * 获取指定语言的翻译
	 * 
	 * @param lan
	 * @param key
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String get(Languages lang, String key) throws FileNotFoundException, IOException {
		Objects.requireNonNull(lang, "lang");
		Objects.requireNonNull(key, "lang");
		if (!langs.containsKey(lang)) {
			log.error("language :" + lang.name() + " has no solution!");
			dumpUntraslatedKey(lang, key);
			return key;
		}
		Map<String, String> map = langs.get(lang);
		if (!map.containsKey(key)) {
			log.error("language :" + lang.name() + " has no key:" + key);
			dumpUntraslatedKey(lang, key);
			return key;
		}
		return map.get(key);
	}

	/**
	 * 记录未翻译的key到文件
	 * 
	 * @param lang
	 * @param key
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void dumpUntraslatedKey(Languages lang, String key) throws FileNotFoundException, IOException {
		checkDumpDir();
		if (!dumps.containsKey(lang)) {
			Properties properties = new Properties();
			dumps.put(lang, properties);
		}
		Properties properties = dumps.get(lang);
		if (!properties.containsKey(key)) {
			properties.setProperty(key, "?????");
			File file = new File("./" + DUMP_PATH + "/" + getFileName(lang));
			FileWriter fileWriter = new FileWriter(file);
			properties.store(fileWriter, null);
			fileWriter.close();
		}
	}

	private void checkDumpDir() {
		File file = new File("./" + DUMP_PATH);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * 获取相应语言的资源文件名
	 * 
	 * @param lang
	 * @return
	 */
	public String getFileName(Languages lang) {
		return "lang_" + lang.name() + ".properties";
	}
}
