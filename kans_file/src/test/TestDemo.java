package test;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestDemo {
	


	public static void main(String[] args) {
		
/*		List<fileInfo> fileInfo = new ArrayList<FileInfo>();
		
		
		FileInfo fileInfo = new FileInfo();
		
		fileInfo.setCode("60000001");
		fileInfo.setPath("/市场文件/文案及全成分");*/
		
		
		List<String> tempList = new ArrayList<String>();
		String abcStr = "[{\"code\":\"60000001\",\"fileName\":\"test223.excel\",\"oaId\":\"001\",\"type\":\"1\",\"requestId\":\"111\"},{\"code\":\"60000002\",\"fileName\":\"test323.excel\",\"oaId\":\"002\",\"type\":\"2\",\"requestId\":\"111\"}]";
		JSONArray json = JSONArray.fromObject(abcStr); 
		if(json.size()>0){
			  for(int i=0;i<json.size();i++){
			    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
			    System.out.println("code="+ job.get("code")+",fileName="+job.get("fileName")+",oaId="+job.get("oaId")+",type="+job.get("type")+",requestId="+job.get("requestId")) ;  // 得到 每个对象中的属性值
			    tempList.add((String)job.get("code"));
			  }
		}
		
		for (int i = 0; i < tempList.size(); i++) {
			System.out.println("遍历后的集合:"+tempList.get(i));
		}
	}
}
