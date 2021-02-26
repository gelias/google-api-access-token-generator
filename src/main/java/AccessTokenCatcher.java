import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

public class AccessTokenCatcher {

    public static void main(String[] args) throws IOException {

        approachOne();

        approachTwo();




    }

    private static void approachTwo() {
        GoogleCredential credential =
                GoogleCredential.fromStream(new FileInputStream("MyProject-1234.json"));
        PrivateKey privateKey = credential.getServiceAccountPrivateKey();
        String privateKeyId = credential.getServiceAccountPrivateKeyId();

        long now = System.currentTimeMillis();

        try {
            Algorithm algorithm = Algorithm.RSA256(null, privateKey);
            String signedJwt = JWT.create()
                    .withKeyId(privateKeyId)
                    .withIssuer("123456-compute@developer.gserviceaccount.com")
                    .withSubject("123456-compute@developer.gserviceaccount.com")
                    .withAudience("https://firestore.googleapis.com/")
                    .withIssuedAt(new Date(now))
                    .withExpiresAt(new Date(now + 3600 * 1000L))
                    .sign(algorithm);
        }
    }

    private static void approachOne() {
        try {

            GoogleCredential credentials = GoogleCredential.fromStream(new FileInputStream("/Users/gelias/Downloads/guilhermeselias-675e9c1be02f.json"));
            System.out.println(new String(credentials.getServiceAccountPrivateKey().getEncoded(), Charset.forName("UTF-8")));
            credentials.createScoped(Arrays.asList("https://www.googleapis.com/auth/drive"));
            credentials.refreshToken();
            String accessToken = credentials.getAccessToken();
// OR
            //AccessToken refreshAccessToken = credentials.refreshAccessToken();

            System.out.println(accessToken);
            //System.out.println(refreshAccessToken);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
