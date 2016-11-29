package kr.co.udid.payapp.lt.model.payapp.data;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@Data
@Accessors(chain = true)
public class PayappBaseResult
{
	private String state;

	private String errorMessage;


}
