import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
 

//Test class
public class HelloLog {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String userInput = "${jndi:http://localhost/AAAA/BBBB}";

        // passing user input into the logger, it a log4j critical vuln
        logger.info("Test: "+userInput);

        // %m{nolookups} has no effect for the following line
        logger.printf(Level.INFO,"Test: %s", userInput);
    }

   public static byte[] bad1(String password) throws NoSuchAlgorithmException {
     // ruleid: use-of-md5
     MessageDigest md5Digest = MessageDigest.getInstance("MD5");
     md5Digest.update(password.getBytes());
     byte[] hashValue = md5Digest.digest();
     return hashValue;
   }
}
