import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
 
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.net.http.HttpRequest;
import java.net.PasswordAuthentication;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import javax.jdo.PersistenceManagerFactory;

//Test class
public class HelloLog {

    private static final Logger logger = LogManager.getLogger();

    private PersistenceManagerFactory pmf;
    private String pw = "asdf";

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

   private static void bad1() {
     try {
            // ruleid: java-jwt-hardcoded-secret
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                .withIssuer("auth0")
                .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
   }

  public void run(){
    String b64token = "d293ZWU6d2Fob28=";
    String basictoken = "Basic d293ZWU6d2Fob28="

        var authClient = HttpClient
            .newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // ruleid: passwordauthentication-hardcoded-password
                    new PasswordAuthentication("postman", "password".toCharArray());

                    char[] asdf = "password".toCharArray()
                    // ruleid: passwordauthentication-hardcoded-password
                    new PasswordAuthentication("postman", asdf);

                    // ok: passwordauthentication-hardcoded-password
                    new PasswordAuthentication("postman", "password");
                }
            })
            .build();
    }

    /**
     * @throws SQLException
     */
    @Before
    public void setUp() throws SQLException {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("datanucleus.autoCreateSchema", true);
        props.put("datanucleus.rdbms.statementBatchLimit", 0);

        pmf = new JDOPersistenceManagerFactory(props);
        pmf.setConnectionDriverName(jdbcDriver.class.getName());
        pmf.setConnectionURL("jdbc:hsqldb:mem:testdb;hsqldb.sqllog=3");
        pmf.setConnectionUserName("SA");

        // ruleid: hardcoded-connection-password
        pmf.setConnectionPassword("asdf");

        // ruleid: hardcoded-connection-password
        pmf.setConnectionPassword(pw);

        // ok: hardcoded-connection-password
        pmf.setConnectionPassword("");
    }
}
