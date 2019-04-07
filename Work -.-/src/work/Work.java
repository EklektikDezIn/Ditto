/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package work;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.omg.Dynamic.Parameter;

/**
 *
 * @author Micah
 */
public class Work {

    /**
     * @param args the command line arguments
     */
    // define our strings
    private static final String FACEBOOK_API_KEY = "xxxxx";
    private static final String FACEBOOK_APPLICATION_SECRET = "xxxxx";
    private FacebookClient facebookClient;
    private String facebookAuthToken = null;
    private String facebookSessionKey = null;

    public void main(String[] args) throws FacebookException {
        facebookClient = new DefaultFacebookClient(FACEBOOK_API_KEY, FACEBOOK_APPLICATION_SECRET);

        // I always call facebookAuthorise
        // First time users would click a button that calls facebookConnect() first to grab the session key.
        facebookAuthorise();
    }

    private void facebookConnect() throws FacebookException {
        // auth.createToken returns a string
        // http://wiki.developers.facebook.com/index.php/Auth.createToken
        facebookAuthToken = facebookClient.execute("auth.createToken", String.class);
        String url = "http://www.facebook.com/login.php"
                + "?api_key=" + FACEBOOK_API_KEY
                + "&fbconnect=true" + "&v=1.0"
                + "&connect_display=page" + "&session_key_only=true"
                + "&req_perms=read_stream,publish_stream,offline_access"
                + "&auth_token=" + facebookAuthToken;

        // Here we launch a browser with the above URL so the user can login to Facebook, grant our requested permissions and send our token for pickup later
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();

            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI(url));
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (URISyntaxException use) {
                    use.printStackTrace();
                }
            }
        }
    }

    private void facebookAuthorise() throws FacebookException {
        // I saved my session key to a .properties file for future use so the user doesn't keep have to authorising my app!
        // auth.getSession returns JSON so we need to decode it just to grab the session key
        // http://wiki.developers.facebook.com/index.php/Auth.getSession
        // Here we pickup the session key and define the authToken we used above in facebookConnect()
        JSONObject json = new JSONObject(facebookClient.execute("auth.getSession", String.class, Parameter.with("auth_token", facebookAuthToken)));
        facebookSessionKey = json.getString("session_key");

        // An example call, you can literally use anything from the REST API
        Long uid = facebookClient.("users.getLoggedInUser", facebookSessionKey, Long.class);
        System.out.println("FB User ID: " + uid);
    }
}

