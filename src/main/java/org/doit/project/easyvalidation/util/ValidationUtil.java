package org.doit.project.easyvalidation.util;

import java.util.Collection;
import java.util.Map;

import org.doit.project.easyvalidation.consts.Empty;

public class ValidationUtil {

	private ValidationUtil() {}
	
	public static boolean isNull(Object object){
		return (object == null);
	}
	
	public static boolean isNullOrEmpty(String string){		
		return (!isNull(string) && string.isEmpty());		
	}
	
	public static boolean isNullOrEmpty(Number number){
		return (!isNull(number) && number.equals(Empty.INTEGER));
	}
	
	public static boolean isNullOrEmpty(Collection<?> collection){
		return (!isNull(collection) && collection.isEmpty());
	}
	
	public static boolean isNullOrEmpty(Map<?, ?> map){
		return (!isNull(map) && map.isEmpty());
	}
}
