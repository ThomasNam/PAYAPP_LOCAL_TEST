package kr.co.udid.payapp.lt.model.payapp.imp;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import kr.co.udid.payapp.lt.lib.SecureRandomUtil;
import kr.co.udid.payapp.lt.lib.StrLib;
import kr.co.udid.payapp.lt.lib.Util;
import kr.co.udid.payapp.lt.model.common.CommonSeqSv;
import kr.co.udid.payapp.lt.model.payapp.PayappProperty;
import kr.co.udid.payapp.lt.model.payapp.PayappSv;
import kr.co.udid.payapp.lt.model.payapp.data.PayappRequestResult;
import kr.co.udid.payapp.lt.model.payapp.domain.PayList;
import kr.co.udid.payapp.lt.model.payapp.domain.PayListBase;
import kr.co.udid.payapp.lt.model.payapp.imp.repository.PayListRepository;
import lombok.RequiredArgsConstructor;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PayappSvImp implements PayappSv
{

	final private PayListRepository payListRepository;

	final private PayappProperty payappProperty;

	final private CommonSeqSv commonSeqSv;

	@Override
	public PayappRequestResult request (PayListBase payListBase)
	{
		PayList payList = new PayList ();

		Util.myCopyProperties (payListBase, payList);

		payList.setPayType ((short) 0);
		payList.setOstate (0);
		payList.setRegDate (new Date ());

		if (StrLib.isEmptyStr (payList.getReqAddr ()))
			payList.setReqAddr ("1");

		String url = makeUrl ();

		payList.setUrl (url);

		// int mulNo = commonSeqSv.createNumber (CommonSeqType.MUL_NO);

		long mulNo = new Date ().getTime ();

		payList.setMulNo (String.valueOf (mulNo));

		payListRepository.save (payList);

		PayappRequestResult result = new PayappRequestResult ();

		result.setState ("1");
		result.setErrorMessage ("");
		result.setMulNo (payList.getMulNo ());

		String payUrl = payappProperty.getUrl () + "p/" + url;

		result.setPayUrl (payUrl);

		return result;
	}

	private String makeUrl ()
	{
		String url;

		//대문자, 소문자, 숫자로 이루어진 6글자 PK
		url = SecureRandomUtil.getKey (6);

		return url;
	}

	@Override
	public Page<PayList> findAll (Pageable page)
	{
		return payListRepository.findAll (page);
	}

	@Override
	public boolean cardAccountComplete (long no)
	{
		PayList payList = payListRepository.findOne (no);

		if (payList == null)
			return false;

		payList.setPayType ((short) 1);
		payList.setPayInfo1 ("테스트카드");
		payList.setPayInfo2 ("36061122****6626");
		payList.setPayDate (new Date ());
		payList.setOstate (4);

		feedbackWork (payList);

		return true;
	}

	@Override
	public boolean mobileAccountComplete (long no)
	{
		PayList payList = payListRepository.findOne (no);

		if (payList == null)
			return false;

		payList.setPayType ((short) 2);
		payList.setPayDate (new Date ());
		payList.setOstate (4);

		feedbackWork (payList);

		return true;
	}

	@Override
	public boolean cancelAccount (long no)
	{
		PayList payList = payListRepository.findOne (no);

		if (payList == null)
			return false;

		if (payList.getOstate () == 4)
			payList.setOstate (9);
		else
			payList.setOstate (8);

		payList.setCancelDate (new Date ());

		feedbackWork (payList);

		return true;
	}

	private void feedbackWork (PayList payList)
	{
		if (!StrLib.isEmptyStr (payList.getFeedbackUrl ()))
		{
			String feedbackUrl = payList.getFeedbackUrl ();

			if (!feedbackUrl.startsWith ("http://") && !feedbackUrl.startsWith ("https://"))
				feedbackUrl = "http://" + feedbackUrl;

			HttpClient client = HttpClientBuilder.create ().build ();
			HttpPost post = new HttpPost (feedbackUrl);

			// add header
			post.setHeader ("User-Agent", "Payapp Java Module");

			List<NameValuePair> urlParameters = new ArrayList<> ();

			/*
			vccode	국제전화 국가번호
			score	DM Score (currency가 usd이고 결제성공일 때 DM 점수)
			vbank	은행명 (가상계좌 결제일 경우)
			vbankno	입금계좌번호 (가상계좌 결제일 경우)
			*/

			urlParameters.add (new BasicNameValuePair ("userid", payList.getSellerUserID ()));
			urlParameters.add (new BasicNameValuePair ("linkkey", "linkkey"));
			urlParameters.add (new BasicNameValuePair ("linkval", "linkval"));

			urlParameters.add (new BasicNameValuePair ("reqaddr", payList.getReqAddr ()));

			urlParameters.add (new BasicNameValuePair ("goodname", payList.getGoodName ()));
			urlParameters.add (new BasicNameValuePair ("price", String.valueOf (payList.getGoodPrice ())));
			urlParameters.add (new BasicNameValuePair ("recvphone", payList.getMemPhone ()));

			urlParameters.add (new BasicNameValuePair ("pay_type", String.valueOf (payList.getPayType ())));
			urlParameters.add (new BasicNameValuePair ("pay_state", String.valueOf (payList.getOstate ())));
			urlParameters.add (new BasicNameValuePair ("pay_memo", ""));
			urlParameters.add (new BasicNameValuePair ("currency", "krw"));

			urlParameters.add (new BasicNameValuePair ("memo", payList.getMemo ()));

			urlParameters.add (new BasicNameValuePair ("var1", payList.getVar1 ()));
			urlParameters.add (new BasicNameValuePair ("var2", payList.getVar2 ()));
			urlParameters.add (new BasicNameValuePair ("mul_no", payList.getMulNo ()));
			urlParameters.add (new BasicNameValuePair ("payurl", payappProperty.getUrl () + "p/" + payList.getUrl ()));
			urlParameters.add (new BasicNameValuePair ("feedbacktype", "0"));
			urlParameters.add (new BasicNameValuePair ("pay_addr", ""));

			DateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

			urlParameters.add (new BasicNameValuePair ("reqdate", df.format (payList.getRegDate ())));

			if (payList.getPayType () == 4)
			{
				urlParameters.add (new BasicNameValuePair ("csturl", payappProperty.getUrl () + "c/" + payList.getUrl ()));
				urlParameters.add (new BasicNameValuePair ("card_name", payList.getPayInfo1 ()));
				urlParameters.add (new BasicNameValuePair ("pay_date", df.format (payList.getPayDate ())));
			}

			post.setEntity (new UrlEncodedFormEntity (urlParameters, Charset.forName ("UTF-8")));

			try
			{
				HttpResponse response = client.execute (post);

				BufferedReader rd = new BufferedReader (new InputStreamReader (response.getEntity ().getContent ()));

				// 결과를 받아온다.
				String responseStr = null;

				StringBuilder result = new StringBuilder ();

				while ((responseStr = rd.readLine ()) != null)
				{
					result.append (responseStr).append ("\n");
				}

				rd.close ();
			}
			catch (Exception ignored)
			{

			}
		}
	}

	@Override
	public PayList findUrl (String url)
	{
		return payListRepository.findByUrl (url);
	}
}
