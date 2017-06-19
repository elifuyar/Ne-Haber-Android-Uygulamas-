package com.example.user.nehaber;

import com.example.user.nehaber.models.Category;
import com.example.user.nehaber.models.News;
import com.example.user.nehaber.models.Newses;
import com.example.user.nehaber.models.NewsesArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Request {

	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_TITLE = "title";
	static final String KEY_LINK = "link";
	static final String KEY_IMAGE = "image";
	static final String KEY_DESC = "description";
	static final String KEY_PUBDATE = "pubDate";

	public static Newses getNews(Category category, Newses newses) {
		News lastNews = null;
		String URL = category.getUrl();
		
		NewsesArrayList _newses= new NewsesArrayList();
		if (newses.size()>0) {
			lastNews = newses.get(newses.size()-1);
		}
		try {

			URL url=new URL(URL);
			DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder=dFactory.newDocumentBuilder();

			Document document=dBuilder.parse(new InputSource(url.openStream()));
			document.getDocumentElement().normalize();
			
			NodeList nodeListCountry=document.getElementsByTagName("item");
			
			for (int i = 0; i < nodeListCountry.getLength(); i++) {
				
				Node node=nodeListCountry.item(i);
				Element elementMain=(Element) node;
				
				News news = new News();

				if (!Validations.isEmpty(getTag(elementMain, KEY_TITLE)))
					news.setTitle(getTag(elementMain, KEY_TITLE));
				if (!Validations.isEmpty(getTag(elementMain, KEY_LINK)))
					news.setLink(getTag(elementMain, KEY_LINK));
				if (!Validations.isEmpty(getTag(elementMain, KEY_IMAGE)))
					news.setImage(getTag(elementMain, KEY_IMAGE));
				String desc = "";
				if (!Validations.isEmpty(getTag(elementMain, KEY_DESC)))
					desc = getTag(elementMain, KEY_DESC);
				if (!Validations.isEmpty(desc))
					if (desc.contains("</a>"))
						desc = desc.substring(desc.indexOf("</a>") + 4);

				news.setDescription(desc);
				if (!Validations.isEmpty(getTag(elementMain, KEY_PUBDATE)))
					news.setPubDate(getTag(elementMain, KEY_PUBDATE));
				news.setCategoryId(category.getId());
				news.setId(UUID.randomUUID());

				
				if(lastNews == null || !lastNews.getLink().equals(news.getLink()))
					_newses.add(news);
				else 
					break;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.reverse(_newses);
		for (News news : _newses) {
			newses.add(news);
		}
		return newses;
	}
	
	private static String getTag(Element elementMain,String tag) {
		try {
			NodeList spotListText=elementMain.getElementsByTagName(tag);
			Element spotText=(Element) spotListText.item(0);
			return spotText.getChildNodes().item(0).getNodeValue();
		} catch (Exception e) {
			return "";
		}
	}
}