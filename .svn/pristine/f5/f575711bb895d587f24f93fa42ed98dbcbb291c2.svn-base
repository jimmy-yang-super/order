package com.jixiang.argo.lvzheng.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {
	/**
	 * 过滤空的MAP
	 * @param map
	 * @return
	 */
	public static Map<String,Object> getMap(Map<String,Object> map){
		if(map == null || map.isEmpty()){
			return Collections.emptyMap();
		}
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Object> next = iterator.next();
			if(next.getValue() != null){
				map.put(next.getKey(), next.getValue());
			}else{
				map.put(next.getKey(), "");
			}
		}
		return map;
	}
}
