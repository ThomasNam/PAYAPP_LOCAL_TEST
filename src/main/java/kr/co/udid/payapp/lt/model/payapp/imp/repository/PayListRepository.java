package kr.co.udid.payapp.lt.model.payapp.imp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.udid.payapp.lt.model.payapp.domain.PayList;

/**
 * Created by Thomas on 2016. 11. 29..
 */
public interface PayListRepository extends JpaRepository<PayList, Long>
{
	PayList findByUrl (String url);

	PayList findByMulNo (String mulNo);
}
