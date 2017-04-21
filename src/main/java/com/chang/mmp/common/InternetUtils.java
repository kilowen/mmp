package com.chang.mmp.common;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InternetUtils {

	/**
	 * 根据jsoup方法获取htmlContent 加入简单的时间记录
	 * 
	 * @throws IOException
	 */
	public static Document getContentByUrl(String url) {
		//Boolean ifc = false;
		Document doc = null;
		for (int j = 1; j <= Constants.NET_CON_RETRYCOUNT; j++) {
			try {
				System.out.println("load URL-----start------>" + url);
				Date startdate = new Date();
				doc = Jsoup.connect(url)
						// .data("jquery", "java")
						.header("User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
						.cookie("__guid", "57328178.2434010087085396500.1440301426257.8203; PHPSESSID=rore0m6ggpengu9hl7ultg9em1; count=22")
						.timeout(60000).get();
				Date enddate = new Date();
				Long time = enddate.getTime() - startdate.getTime();
				System.out.println("Cost time------------->" + time);
				System.out.println("load URL-----end------>" + url);
				//ifc = true;
			} catch (IOException e) {
				System.out.println("load URL cause exception----------->" + url);
				e.printStackTrace();
				ProxyIp.setProxy();
			}
			/*if (ifc) {
				break;
			} else {
				continue;
			}*/
		}
		return doc;
	}

	public static void main(String[] args) {

		Document doc = getContentByUrl("http://image.haosou.com/z?ch=wallpaper&listtype=hot");
		String res = doc.toString();
		String json = res.substring(res.indexOf("initData")+10,res.indexOf("}]}}")+4);
		//String json = res.substring(res.indexOf("initData")+17,res.indexOf(";window.__ch"));
		System.out.println(json);
		JSONObject jsonObject = JSONObject.fromObject(json);
		JSONObject jsonObject1 = JSONObject.fromObject(jsonObject.get("data").toString());
		System.out.println("count------"+jsonObject1.getString("count"));
		JSONArray imgList = jsonObject1.getJSONArray("list");
		for(int i = 0;i<imgList.size();i++){
			JSONObject jo = imgList.getJSONObject(i);
			System.out.println(jo.getString("cover_imgurl"));
		}
		/*Element content = doc.getElementById("waterfall");
		System.out.println(content);
		String href = content.getElementsByTag("a").first().attr("href");
		System.out.println(href);*/
	}
}
