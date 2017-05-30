package com.wq.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.wq.po.TextMessage;

public class MessageUtil {
	//������Ϣ����
	public static final String MSGTYPE_TEXT = "text";
	public static final String MSGTYPE_NEWS = "news";
	public static final String MSGTYPE_IMAGE = "image";
	public static final String MSGTYPE_VOICE = "voice";
	public static final String MSGTYPE_MUSIC = "music";
	public static final String MSGTYPE_LOCATION = "location";
	public static final String MSGTYPE_LINK = "link";
	public static final String MSGTYPE_EVENT = "event";
	public static final String EVENT_SUBSCRIBE = "subscribe";
	public static final String EVENT_SCAN = "SCAN";
	public static final String EVENT_LOCATION = "location_select";
	public static final String EVENT_CLICK = "CLICK";
	public static final String EVENT_VIEW = "VIEW";
	public static final String EVENT_SCANCODE_PUSH = "scancode_push";
	
	/**
	 * 1.xml ת�� Map����
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static  Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map=new HashMap<String, String>();
		SAXReader reader=new SAXReader();
		InputStream ins=req.getInputStream();
		Document doc=reader.read(ins);
		Element root=doc.getRootElement();
		List<Element> list=root.elements();
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	/**
	 * 2.���ı���Ϣ����ת���ɡ�xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream stream=new XStream();
		stream.alias("xml", textMessage.getClass());
		return stream.toXML(textMessage);
		
	}
	/**
	 * 3.���˵�
	 */
	public static String menuText(){
		StringBuffer sb=new StringBuffer();
		sb.append("��ӭ����עС����ͬѧ,�밴�ղ˵���ʾ���в�����\n\n");
		sb.append("1.���ںŽ���\n");
		sb.append("2.�����߽���\n\n");
		sb.append("�ظ�\"��\"�����˲˵���");
		return sb.toString();
	}
	/**
	 * 4.����ı���Ϣ
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage textMessage=new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setContent(content);
		textMessage.setMsgType(MessageUtil.MSGTYPE_TEXT);
		textMessage.setCreateTime(String.valueOf(new Date().getTime()));
		return textMessageToXml(textMessage);
	}
	/**
	 * 5.��һ���Ӳ˵���1--���ںŽ���
	 */
	public static String firstSubMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("�˹��ں�Ϊ���Թ��ں�");
		return sb.toString();
	}
	
	/**
	 * 6.�ڶ����Ӳ˵���2--�����߽���
	 */
	public static String secondSubMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("�˿�����Ա��С���죬����˧��!");
		return sb.toString();
	}
}
