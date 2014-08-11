package com.github.mirreck.yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class YamlNode {
	
	private final Map<String, YamlNode> valueMap;
	private final List<YamlNode> valueList;
	private final Object value;
	
	public Map<String, YamlNode> getValueMap() {
		return valueMap;
	}
	public List<YamlNode> getValueList() {
		return valueList;
	}
	public Object getValue() {
		return value;
	}
	public boolean isMap() {
		return map;
	}
	public boolean isList() {
		return list;
	}
	public boolean isLeaf() {
		return leaf;
	}
	private final boolean map;
	private final boolean list;
	private final boolean leaf;
	
	public YamlNode(Map<String, Object> value){
		this.valueList = null;
		this.valueMap = new HashMap<String, YamlNode>();
		for (Entry<String, Object> entry : value.entrySet()) {
			this.valueMap.put(entry.getKey(), new YamlNode(entry.getValue()));
		}
		this.value = null;
		this.leaf = false;
		this.list = false;
		this.map = true;
	}
	public YamlNode(List<Object> value){
		this.valueList = new ArrayList<YamlNode>();
		for (Object object : value) {
			this.valueList.add(new YamlNode(object));
		}
		this.list = true;
		this.valueMap = null;
		this.value = null;
		this.leaf = false;
		this.map = false;
	}
	public YamlNode(Object value){
		this.valueList = null;
		this.valueMap = null;
		this.value = value;
		this.leaf = true;
		this.list = false;
		this.map = false;
		
	}
}
