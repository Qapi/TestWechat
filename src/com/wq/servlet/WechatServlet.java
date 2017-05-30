package com.wq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.wq.po.TextMessage;
import com.wq.util.CheckUtil;
import com.wq.util.MessageUtil;

public class WechatServlet extends HttpServlet {
   @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   //΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce������
	    String signature=req.getParameter("signature");
		String timestamp=req.getParameter("timestamp");//ʱ���
		String nonce=req.getParameter("nonce");//�����
		String echostr=req.getParameter("echostr");//����ַ���
		PrintWriter out=resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
   
   @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   req.setCharacterEncoding("UTF-8");
	   resp.setCharacterEncoding("UTF-8");
	   //��������xml��������Ϣ�����ٽ���Ϣ����תΪxml����΢�ź�̨
	   PrintWriter out=resp.getWriter();
	   try {
			Map<String,String> map=MessageUtil.xmlToMap(req);
			String fromUserName=map.get("FromUserName");
			String toUserName=map.get("ToUserName");
			String msgType=map.get("MsgType");
			String content=map.get("Content");
			String message=null;
			if(MessageUtil.MSGTYPE_TEXT.equals(msgType)){
				if("1".equals(content)){//�ؼ����ж�
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstSubMenu());
				}else if("2".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondSubMenu());
				}else if("?".equals(content)||"��".equals(content)){//�ؼ����ж�
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else{
					System.out.println(content+"---cont");
					message=MessageUtil.initText(toUserName, fromUserName, "�������"+content+"���޷�����!");
				}
			}else if(MessageUtil.MSGTYPE_EVENT.equals(msgType)){
				//�¼�������ȥϸ���ж�
				String eventType=map.get("Event");
				if(MessageUtil.EVENT_SUBSCRIBE.equals(eventType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}else{
				message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
			}
			out.print(message);
			System.out.println(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
}
