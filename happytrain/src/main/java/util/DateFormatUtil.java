/**
 * 
 */
package util;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface DateFormatUtil {
	Date getFullDateFromString(String str) throws ParseException;
	Date getShortDateFromString(String str) throws ParseException;
}
