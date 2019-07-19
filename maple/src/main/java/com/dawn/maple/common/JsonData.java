/**
 * 
 */
package com.dawn.maple.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.*;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Dawn
 * 2019-04-02
 */
public class JsonData extends JSON {

	public static final String toJSONString(Object object, boolean ignoreDate, SerializeFilter[] filters, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out);
            for (SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.config(SerializerFeature.WriteDateUseDateFormat, ignoreDate);

            setFilter(serializer, filters);

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }

	private static void setFilter(JSONSerializer serializer, SerializeFilter... filters) {
        for (SerializeFilter filter : filters) {
            setFilter(serializer, filter);
        }
    }

    private static void setFilter(JSONSerializer serializer, SerializeFilter filter) {
        if (filter == null) {
            return;
        }
        
        if (filter instanceof PropertyPreFilter) {
            serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
        }

        if (filter instanceof NameFilter) {
            serializer.getNameFilters().add((NameFilter) filter);
        }

        if (filter instanceof ValueFilter) {
            serializer.getValueFilters().add((ValueFilter) filter);
        }

        if (filter instanceof PropertyFilter) {
            serializer.getPropertyFilters().add((PropertyFilter) filter);
        }

        if (filter instanceof BeforeFilter) {
            serializer.getBeforeFilters().add((BeforeFilter) filter);
        }

        if (filter instanceof AfterFilter) {
            serializer.getAfterFilters().add((AfterFilter) filter);
        }
    }

    public static JSON toJson(List<?> list){
    	if(CollectionUtils.isEmpty(list)){
    		return new JSONArray();
    	}
    	
    	JSONArray jsonArray = new JSONArray(list.size());
    	
    	for(Object obj : list){
    		if(obj instanceof JsonStyle){
    			jsonArray.add(((JsonStyle)obj).toJson());
    		}
    		else {
    			jsonArray.add(toJSON(obj));
    		}
    	}
    	
    	return jsonArray;
    }
}
