package kr.co.udid.payapp.lt.model.payapp;

import kr.co.udid.payapp.lt.model.payapp.data.PayappRequestResult;
import kr.co.udid.payapp.lt.model.payapp.domain.PayListBase;

/**
 * Created by RED on 2016-11-29.
 */
public interface PayappSv
{
	/**
	 * 결제 요청을 한다
	 * @param payListBase
	 * @return
	 */
	PayappRequestResult request (PayListBase payListBase);
}
