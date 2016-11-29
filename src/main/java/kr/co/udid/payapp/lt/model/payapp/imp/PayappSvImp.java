package kr.co.udid.payapp.lt.model.payapp.imp;

import org.springframework.stereotype.Service;

import java.util.Date;

import javax.transaction.Transactional;

import kr.co.udid.payapp.lt.lib.SecureRandomUtil;
import kr.co.udid.payapp.lt.lib.Util;
import kr.co.udid.payapp.lt.model.common.CommonSeqSv;
import kr.co.udid.payapp.lt.model.common.types.CommonSeqType;
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

		String url = makeUrl ();

		payList.setUrl (url);

		int mulNo = commonSeqSv.createNumber (CommonSeqType.MUL_NO);

		payList.setMulNo (String.valueOf (mulNo));

		payListRepository.save (payList);

		PayappRequestResult result = new PayappRequestResult ();

		result.setState ("1");
		result.setErrorMessage ("");
		result.setMulNo (payList.getMulNo ());

		String payUrl = payappProperty + "p/" + url;

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
}
