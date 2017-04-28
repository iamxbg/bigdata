package bigdata.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

public class D3Util {
	
	public static BSONObject convertToSankeyChart(Map<String, Long> map){

		int counter=-1;
		
		BSONObject chart=new BasicBSONObject();
		
		BasicBSONList nodes=new BasicBSONList();
		
		BasicBSONList links=new BasicBSONList();
		
		Map<String, Integer> tempMap=new HashMap<>();
		
		long totalCount=0l;
		Map<String, Long> countMap=new HashMap<>();
		
		for(String key:map.keySet()){
			System.out.println(key);
			String source=key.split("@")[0];
			
			if(!countMap.containsKey(source)){
				countMap.put(source, 0l);
			}
			countMap.put(source, countMap.get(source)+map.get(key));
			totalCount+=map.get(key);
		}
		
		
		for(String key:map.keySet()){
			
			System.out.println(key);
			String source=key.split("@")[0];
			String target=key.split("@")[1];
			
			
			
			if(!tempMap.containsKey(source)) {
				tempMap.put(source,++counter);
				BSONObject node=new BasicBSONObject();
				node.put("node", counter);
				node.put("name", source);
				nodes.add(node);
			}
			
			if(!tempMap.containsKey(target)) {
				tempMap.put(target,++counter);
				BSONObject node=new BasicBSONObject();
				node.put("node", counter);
				node.put("name", target);
				nodes.add(node);
			}
			
			BSONObject link=new BasicBSONObject();
					link.put("source", tempMap.get(source));
					link.put("target", tempMap.get(target));
					link.put("value", map.get(key));
					link.put("partRatio", new BigDecimal(100.0*map.get(key)/countMap.get(source)).setScale(5, RoundingMode.HALF_UP)+" %");
					link.put("totalRatio",new BigDecimal(100.0*map.get(key)/totalCount).setScale(5, RoundingMode.HALF_UP)+" %");
					links.add(link);
			
		}
	
			chart.put("nodes", nodes);
			chart.put("links", links);
			
		return chart;
	}
}
