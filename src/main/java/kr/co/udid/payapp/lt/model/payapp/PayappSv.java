package kr.co.udid.payapp.lt.model.payapp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.udid.payapp.lt.model.payapp.data.PayappRequestResult;
import kr.co.udid.payapp.lt.model.payapp.domain.PayList;
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

	Page<PayList> findAll (Pageable page);

	PayList findUrl (String url);

	/**
	 * 카드 결제 완료
	 * @param no
	 * @return
	 */
	boolean cardAccountComplete (long no);

	/**
	 * 결제 취소
	 * @param no
	 * @return
	 */
	boolean cancelAccount (long no);
}
