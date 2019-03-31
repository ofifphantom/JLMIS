package com.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtil {
	   public static String mapXmlString(Map<String,Object> map){  
	        StringBuffer sb = new StringBuffer("");  
	        sb.append("<xml>");  
	          
	        Set<String> set = map.keySet();  
	        for(Iterator<String> it=set.iterator(); it.hasNext();){  
	            String key = it.next();  
	            Object value = map.get(key);  
	            sb.append("<").append(key).append(">");  
	            sb.append(value);  
	            sb.append("</").append(key).append(">");  
	        }  
	        sb.append("</xml>");  
	        
	        return sb.toString();
	   }
}
