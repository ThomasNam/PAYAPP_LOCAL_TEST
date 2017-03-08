package kr.co.udid.payapp.lt.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import kr.co.udid.payapp.lt.model.payapp.PayappSv;
import kr.co.udid.payapp.lt.model.payapp.data.PayappRequestResult;
import kr.co.udid.payapp.lt.model.payapp.domain.PayList;
import kr.co.udid.payapp.lt.model.payapp.domain.PayListBase;
import lombok.RequiredArgsConstructor;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@Controller
@RestController
@RequiredArgsConstructor
public class PayappController
{
	private final PayappSv payappSv;

	@PostMapping("/oapi/apiLoad.html")
	public String payLoad (
		String cmd
		, HttpServletRequest request
	)
	{
		try
		{
			// 결제 요청
			if ("payrequest".equals (cmd))
			{
				return payRequest (request);
			}
			else if ("paycancel".equals (cmd))
			{
				return payCancel (request);
			}

		}
		catch (Exception e)
		{
			return "state=0&errorMessage=ERROR";
		}

		return "state=0&errorMessage=ERROR";
	}

	private String payRequest (HttpServletRequest request) throws UnsupportedEncodingException
	{
		String userid = request.getParameter ("userid");
		String goodname = request.getParameter ("goodname");
		String recvphone = request.getParameter ("recvphone");
		String price = request.getParameter ("price");
		String memo = request.getParameter ("memo");
		String reqaddr = request.getParameter ("reqaddr");
		String returnurl = request.getParameter ("returnurl");
		String openpaytype = request.getParameter ("openpaytype");

		String feedbackurl = request.getParameter ("feedbackurl");
		String var1 = request.getParameter ("var1");
		String var2 = request.getParameter ("var2");
		String smsuse = request.getParameter ("smsuse");

		PayListBase base = new PayListBase ();

		base.setFeedbackUrl (feedbackurl).setGoodName (goodname).setSellerUserID (userid).setMemPhone (recvphone);
		base.setGoodPrice (Integer.parseInt (price)).setMemo (memo).setReqAddr (reqaddr).setReturnUrl (returnurl);
		base.setVar1 (var1).setVar2 (var2).setSendSms (smsuse).setOpenpaytype (openpaytype);

		PayappRequestResult result = payappSv.request (base);

		return String.format ("state=%s&errorMessage=%s&mul_no=%s&payurl=%s",
			result.getState (), URLEncoder.encode (result.getErrorMessage (), "UTF-8"), result.getMulNo (), URLEncoder.encode (result.getPayUrl (), "UTF-8"));
	}

	private String payCancel (HttpServletRequest request) throws UnsupportedEncodingException
	{
		String userid = request.getParameter ("userid");
		String linkkey = request.getParameter ("linkkey");
		String mul_no = request.getParameter ("mul_no");

		PayList payList = payappSv.findMul (mul_no);

		if (payList == null)
			return String.format ("state=%s&errorMessage=%s", "0", URLEncoder.encode ("존재 하지 않는 결제건입니다", "UTF-8"));

		if (payappSv.cancelAccount (payList.getNo ()))
			return String.format ("state=%s&errorMessage=%s", "1", URLEncoder.encode ("취소완료", "UTF-8"));
		else
			return String.format ("state=%s&errorMessage=%s", "0", URLEncoder.encode ("취소에 실패했습니다", "UTF-8"));
	}

}
