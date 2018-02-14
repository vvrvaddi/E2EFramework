/**
 * 
 */
package dl.automation.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class StringUtils {
	public static List<String> splitParams(String delimiter, String params) {
		if (params.contains("::")) {
			return Arrays.asList(params);// .collect(Collectors.toList());;
		} else {

			return Arrays.stream(params.split(":")).map(new Function<String, String>() {

				@Override
				public String apply(String t) {
					String trimmed = t.trim();
					if (trimmed.startsWith("(")) {
						if (trimmed.endsWith(")")) {
							return trimmed.substring(1, trimmed.length() - 1);
						} else {
							return null;
						}
					} else {
						return trimmed;
					}
				}
			}).collect(Collectors.toList());
		}
	}

	public static List<String> splitParamTypes(String delimiter, String paramTypes) {
		return Arrays.stream(paramTypes.split(":")).map(new Function<String, String>() {

			@Override
			public String apply(String t) {
				String trimmed = t.trim();
				if (trimmed.startsWith("(")) {
					if (trimmed.endsWith(")")) {
						return trimmed.substring(1, trimmed.length());
					} else {
						return trimmed;
					}
				} else {
					return trimmed;
				}
			}
		}).collect(Collectors.toList());
	}



	public static List<String> splitDataSets(String source) {

	 ArrayList<String> val =new ArrayList<String>();
     if(source.contains("-")){
       String[] arr=source.split("-");
       int lb=Integer.parseInt(arr[0]);
       int ub=Integer.parseInt(arr[1]); 
       for(int i=lb;i<=ub;i++){
     	  val.add(Integer.toString(i));  
       }
     }else if(source.contains(",")){
       String[] arr=source.split(",");
       for(String s:arr){
     	  val.add(s);
       }
     }else{
     	val.add(source);
     }
    
            return val;
	}






















}
