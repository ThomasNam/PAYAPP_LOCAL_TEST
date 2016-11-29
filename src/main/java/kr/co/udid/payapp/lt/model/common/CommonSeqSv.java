package kr.co.udid.payapp.lt.model.common;

import org.springframework.dao.DataAccessException;

import kr.co.udid.payapp.lt.model.common.types.CommonSeqType;

public interface CommonSeqSv
{
	/**
	 * 번호를 생성한다.
	 * @param commonSeqType : 구분값1
	 * @return : 번호
	 */
	int createNumber (CommonSeqType commonSeqType) throws DataAccessException;
	
	/**
	 * 번호를 생성한다.
	 * @param commonSeqType : 구분값1
	 * @param type2 : 구분값2
	 * @return : 번호
	 */
	int createNumber (CommonSeqType commonSeqType, String type2) throws DataAccessException;
}

