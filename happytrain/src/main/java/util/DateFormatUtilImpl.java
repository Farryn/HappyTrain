/**
 * 
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * @author Damir Tuktamyshev
 *
 */
@Service
public class DateFormatUtilImpl implements DateFormatUtil{

	/**
	 * 
	 */
	public DateFormatUtilImpl() {
	}

	/**Generates Date object from String.
	 * @param str String representing date
	 * @return Date
	 * @throws ParseException 
	 */
	public Date getFullDateFromString(String str) throws ParseException {
		Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy HH:mm");
		date = sdf.parse(str);
		return date;
	}
	
	/**Generates Date object from String.
	 * @param str String representing date
	 * @return Date
	 * @throws ParseException 
	 */
	public Date getShortDateFromString(String str) throws ParseException {
		Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		date = sdf.parse(str);
		return date;
	}
}
