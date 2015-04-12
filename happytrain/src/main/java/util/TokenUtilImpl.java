/**
 * 
 */
package util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import valueobjects.UserJsonVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Service
public class TokenUtilImpl implements TokenUtil {

	private static final String SEPARATOR = ".";
	private static final String SEPARATOR_SPLITTER = "\\.";
	private static final String SECRET_KEY = "SoSecretKey";
	private Cipher cipher;
	private SecretKey mySecretKey;
	/**
	 * 
	 */
	public TokenUtilImpl() {
		
		try {
			
			mySecretKey = generateSecretKey();
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, mySecretKey);
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		} catch (InvalidKeyException e) {
		}
		
	}

	@Autowired
	private UserDetailsService userDetailsService;
	
	/* (non-Javadoc)
	 * @see util.TokenUtil#getToken(valueobjects.UserJsonVO)
	 */
	@Override
	public String getToken(UserJsonVO user) {
		byte[] hash = createHmac(user.getUsername().getBytes());
		final StringBuilder sb = new StringBuilder(170);
		byte[] username = user.getUsername().getBytes();
		sb.append(toBase64(username));
		sb.append(SEPARATOR);
		sb.append(toBase64(hash));
		return sb.toString();
	}

	private String toBase64(byte[] content) {
		return DatatypeConverter.printBase64Binary(content);
	}

	private byte[] fromBase64(String content) {
		return DatatypeConverter.parseBase64Binary(content);
	}
	
	private byte[] createHmac(byte[] content) {
		byte[] result = null;
		try {
			result = cipher.doFinal(content);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see util.TokenUtil#validate(java.lang.String)
	 */
	@Override
	public boolean validate(String token) {
		if (token.equals("")) return false;
		boolean validHash = false;
		final String[] parts = token.split(SEPARATOR_SPLITTER);
		if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
			try {
				final byte[] userBytes = fromBase64(parts[0]);
				final byte[] hash = fromBase64(parts[1]);

				 validHash = Arrays.equals(createHmac(userBytes), hash);
				
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		
		return validHash;
	}

	/* (non-Javadoc)
	 * @see util.TokenUtil#getUserFromToken(java.lang.String)
	 */
	@Override
	public UserDetails getUserFromToken(String token) {
		String login = getLoginFromToken(token); 
		UserDetails user = userDetailsService.loadUserByUsername(login);
		return user;
	}
	
	private String getLoginFromToken(String token) {
		String[] parts = token.split(SEPARATOR_SPLITTER);
		String login = new String(fromBase64(parts[0]));
		/*String temp = token.substring(1, token.length()-1);
		String[] arr = temp.split(" token ");*/
		return login;
	}

	public String getPassword(String encoded) {
		Cipher myCypherIn;
		String decoded = "";
		try {
			myCypherIn = Cipher.getInstance("AES");
			myCypherIn.init(Cipher.DECRYPT_MODE, mySecretKey);
			byte[] passBytes = fromBase64(encoded);
			decoded = new String(myCypherIn.doFinal(passBytes));
			
		} catch (Exception e) {
			return "";
		} 
		return decoded;
	}
	
	private SecretKey generateSecretKey() {
		byte[] key = null;
		try {
			key = SECRET_KEY.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e2) {
			return null;
		}
		return new SecretKeySpec(key, "AES");
		
	}
}
