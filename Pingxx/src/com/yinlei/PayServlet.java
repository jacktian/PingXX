package com.yinlei;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.sun.swing.internal.plaf.basic.resources.basic;

import sun.net.www.content.image.gif;

/**
 * Servlet implementation class PayServlet
 */
@WebServlet("/PayServlet")
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PayServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		//���
		PrintWriter pw = response.getWriter();
		
		Pingpp.apiKey="sk_test_uLCqfPjTGu9Gm5aHKOLq5qrP";
		
		//��ȡ�ͻ��˲���    amount    channel
		ServletInputStream sis = request.getInputStream();
		byte[]bs = new byte[1024];
		int len = -1;
		StringBuffer sb = new StringBuffer();
		while((len = sis.read(bs))!=-1){
			sb.append(new String(bs,0,len));
		}

		Gson gson = new Gson();
		PaymentRequest paymentRequest = gson.fromJson(sb.toString(), PaymentRequest.class);
		
	    Map<String, Object> chargeMap = new HashMap<String, Object>();
	    //ĳЩ������Ҫ���extra�����������������ӿ��ĵ�
	    chargeMap.put("amount", paymentRequest.amount);   //1ԪǮ
	    chargeMap.put("currency", "cny");  //�����
	    chargeMap.put("subject", "������һ��С��5");  //��Ʒ������
	    chargeMap.put("body", "��ɫ�����");  // ��������
	    chargeMap.put("order_no", "12345678902");   //�������
	    chargeMap.put("channel", paymentRequest.channel);  //֧������
	    chargeMap.put("client_ip", request.getRemoteAddr());  //�ͻ��˵�IP��ַ
	    Map<String, String> app = new HashMap<String, String>();
	    app.put("id", "app_nzz1i1rLGWzPmr94");   //Ӧ��ID
	    chargeMap.put("app", app);
	    try {
	        //����������
	        Charge charge = Charge.create(chargeMap);
	        System.out.println(charge);
	        //��ͻ������
	        pw.write(charge.toString());
	    } catch (PingppException e) {
	        e.printStackTrace();
	    }
	}

}
