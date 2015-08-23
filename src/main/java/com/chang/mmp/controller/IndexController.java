/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chang.mmp.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chang.mmp.common.InternetUtils;
import com.chang.mmp.common.StringUtils;
import com.chang.mmp.domain.BgImage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class IndexController {

	@RequestMapping("/")
	public ModelAndView welcome() {
		
		Document doc = InternetUtils.getContentByUrl("http://image.haosou.com/z?ch=wallpaper&listtype=hot");
		String res = doc.toString();
		String json = res.substring(res.indexOf("window.initData") + 17, res.indexOf(";window.__ch"));
		//System.out.println(json);
		JSONObject jsonObject = JSONObject.fromObject(json);
		JSONObject jsonObject1 = JSONObject.fromObject(jsonObject.get("data").toString());
		//System.out.println("count------" + jsonObject1.getString("count"));
		JSONArray imgList = jsonObject1.getJSONArray("list");
		List<BgImage> biList = new ArrayList<BgImage>();
		for (int i = 0; i < imgList.size(); i++) {
			BgImage bi = new BgImage();
			JSONObject jo = imgList.getJSONObject(i);
			bi.setId(String.valueOf(i));
			bi.setUrl(jo.getString("cover_imgurl"));
			System.out.println(jo.getString("cover_imgurl"));
			bi.setName(StringUtils.unicodeToString(jo.getString("group_title")));
			bi.setDesc(StringUtils.unicodeToString(jo.getString("group_desc")));
			if(i==0){
				bi.setActive("1");
			}else{
				bi.setActive("0");
			}
			biList.add(bi);
		}
		return new ModelAndView("index", "imgList", biList);
	}

}
