package kr.co.udid.payapp.lt.model.common.imp.repository;

import org.springframework.data.repository.CrudRepository;

import kr.co.udid.payapp.lt.model.common.domain.CommonSeq;
import kr.co.udid.payapp.lt.model.common.domain.CommonSeqPk;

/**
 * Created by RED on 2015-05-07.
 */
public interface CommonSeqRepository extends CrudRepository<CommonSeq, CommonSeqPk>
{
}
