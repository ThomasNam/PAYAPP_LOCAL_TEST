package kr.co.udid.payapp.lt.model.payapp.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@Data
@Accessors (chain = true)
public class PayListBase
{
	/**
	 * 상품명
	 */
	private String goodName;

	private int goodPrice;

	private String sellerUserID;

	private String memPhone;

	private String memo;

	private String feedbackUrl;

	/**
	 * 변수 1
	 */
	private String var1;

	/**
	 * 변수 2
	 */
	private String var2;

	/**
	 * SMS 발송 여부
	 */
	private String sendSms;

	/**
	 * 결제 수단 선택
	 */
	private String openpaytype;

	private String reqAddr;

	private String returnUrl;
}
