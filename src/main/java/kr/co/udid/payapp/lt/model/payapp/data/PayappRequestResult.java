package kr.co.udid.payapp.lt.model.payapp.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PayappRequestResult extends PayappBaseResult
{
	private String mulNo;

	private String payUrl;

	private long payNo;
}
