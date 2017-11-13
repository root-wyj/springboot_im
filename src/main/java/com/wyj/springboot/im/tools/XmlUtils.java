package com.wyj.springboot.im.tools;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description: xml解析工具类
 * @author heyong
 * @date 2017年7月19日
 */
public class XmlUtils {
	private static Logger logger = LoggerFactory.getLogger(XmlUtils.class);

	public static JSONObject xml2Json(String xmlStr) {
		if (isEmpty(xmlStr)) {
			return null;
		}

		Document doc;
		try {
			doc = DocumentHelper.parseText(xmlStr);
			JSONObject json = new JSONObject();
			dom4j2Json(doc.getRootElement(), json);
			return json;
		} catch (DocumentException e) {
			logger.error("解析xml失败：" + xmlStr, e);
		}
		return null;
	}

	private static void dom4j2Json(Element element, JSONObject json) {
		// 如果是属性
		for (Object o : element.attributes()) {
			Attribute attr = (Attribute) o;
			if (!isEmpty(attr.getValue())) {
				json.put("@" + attr.getName(), attr.getValue());
			}
		}

		@SuppressWarnings("unchecked")
		List<Element> chdEl = element.elements();
		if (chdEl.isEmpty() && !isEmpty(element.getText())) {// 如果没有子元素,只有一个值
			json.put(element.getName(), element.getText());
		}

		for (Element e : chdEl) {// 有子元素
			if (!e.elements().isEmpty()) {// 子元素也有子元素
				JSONObject chdjson = new JSONObject();
				dom4j2Json(e, chdjson);
				Object o = json.get(e.getName());
				if (o != null) {
					JSONArray jsona = null;
					if (o instanceof JSONObject) {// 如果此元素已存在,则转为jsonArray
						JSONObject jsono = (JSONObject) o;
						json.remove(e.getName());
						jsona = new JSONArray();
						jsona.add(jsono);
						jsona.add(chdjson);
					}
					if (o instanceof JSONArray) {
						jsona = (JSONArray) o;
						jsona.add(chdjson);
					}
					json.put(e.getName(), jsona);
				} else {
					if (!chdjson.isEmpty()) {
						json.put(e.getName(), chdjson);
					}
				}

			} else { // 子元素没有子元素
				for (Object o : element.attributes()) {
					Attribute attr = (Attribute) o;
					if (!isEmpty(attr.getValue())) {
						json.put("@" + attr.getName(), attr.getValue());
					}
				}

				if (!e.getText().isEmpty()) {
					json.put(e.getName(), e.getText());
				}
			}
		}
	}

	private static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty() || "null".equals(str)) {
			return true;
		}
		return false;
	}
}
