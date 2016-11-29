package kr.co.udid.payapp.lt.model.common.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.udid.payapp.lt.model.common.CommonSeqSv;
import kr.co.udid.payapp.lt.model.common.domain.CommonSeq;
import kr.co.udid.payapp.lt.model.common.domain.CommonSeqPk;
import kr.co.udid.payapp.lt.model.common.imp.repository.CommonSeqRepository;
import kr.co.udid.payapp.lt.model.common.types.CommonSeqType;

/**
 * Created by RED on 2015-05-07.
 */
@Service
public class CommonSeqSvImp implements CommonSeqSv
{
	@Autowired CommonSeqRepository commonSeqRepository;

	@Override
	@Transactional (propagation= Propagation.REQUIRES_NEW, readOnly=false, rollbackFor={ Exception.class, DataAccessException.class})
	public int createNumber (CommonSeqType commonSeqType) throws DataAccessException
	{
		return createNumber (commonSeqType, "");
	}

	@Override
	@Transactional (propagation= Propagation.REQUIRES_NEW, readOnly=false, rollbackFor={ Exception.class, DataAccessException.class})
	public int createNumber (CommonSeqType commonSeqType, String type2) throws DataAccessException
	{
		/**
		 * - 처리 절차
		 * 구분값1과 2를 조합하여 데이터베이스를 조회함
		 * 조회된 값에 +1 를 하여 번호를 생성함
		 * 저장된 값은 다시 데이터베이스에 업데이트 된다
		 * 만약 구분값 조합값이 존재 하지 않는다면 추가한다.
		 * 만든 번호를 리턴한다.
		 */

		CommonSeqPk pk = new CommonSeqPk (commonSeqType.toString (), type2);

		CommonSeq commonSeq = commonSeqRepository.findOne (pk);

		int curVal = 0;

		// null 이라면...
		if (commonSeq == null)
		{
			commonSeq = new CommonSeq (new CommonSeqPk (commonSeqType.toString (), type2));

			commonSeq.setCurVal (1);

			curVal = commonSeq.getCurVal ();

			commonSeqRepository.save (commonSeq);
		}
		else
		{
			curVal = commonSeq.getCurVal ();
			curVal++;

			commonSeq.setCurVal (curVal);

			commonSeqRepository.save (commonSeq);
		}

		return curVal;
	}
}
