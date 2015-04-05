/**
 * 
 */
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.jboss.security.auth.login.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import services.TicketService;
import util.TokenUtil;
import valueobjects.TicketListVO;
import valueobjects.TicketVO;
import valueobjects.UserJsonVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@RestController
public class RestServiceController {

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(RestServiceController.class);
    
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private TokenUtil tokenUtil;
	
	@RequestMapping(value = "/tickets/{start}/{end}", method = RequestMethod.POST , headers="Accept=application/x-www-form-urlencoded", produces="application/json")
	public ResponseEntity<TicketListVO> getTickets(@PathVariable String start, @PathVariable String end) {
		List<TicketVO> ticketList = new ArrayList<TicketVO>();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = getDateFromString(start);
			endDate = getDateFromString(end);
		} catch (ParseException e) {
			LOG.warn("Exception: " + e);
		}
		ticketList = ticketService.getTicketsBetweenTimePeriod(startDate, endDate);
		ResponseEntity<TicketListVO> res = new ResponseEntity<TicketListVO>(new TicketListVO(ticketList), HttpStatus.OK);
    	return res;
		//return new TicketListVO(ticketList);
	}
	
	@RequestMapping(value = "/checkuser", method = RequestMethod.POST, headers="Accept=application/json" )
	public ResponseEntity<String> checkUser(@RequestBody UserJsonVO userJson) {
		try{
			UserDetails user = userDetailsService.loadUserByUsername(userJson.getUsername());
			GrantedAuthority auth = new SimpleGrantedAuthority("admin");
			if(!user.getAuthorities().contains(auth)
							|| !passwordEncoder.matches(userJson.getPassword(), user.getPassword())) {
				throw new UsernameNotFoundException("");
			}
			String token = tokenUtil.getToken(userJson);
			
			ResponseEntity<String> res = new ResponseEntity<String>(token, HttpStatus.OK);
	    	return res;
	    }catch(UsernameNotFoundException e){
	    	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	    }
		
		
	}

	/**Generates Date object from String.
	 * @param str String representing date
	 * @return Date
	 * @throws ParseException 
	 */
	private Date getDateFromString(String str) throws ParseException {
		Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		date = sdf.parse(str);
		return date;
	}
}
