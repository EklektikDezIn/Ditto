/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookapi101;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

/**
 *
 * @author Micah
 */
public class FacebookAPI101 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FacebookClient fbClient = new DefaultFacebookClient("CAACEdEose0cBAK9dbP46ZC0iZCZAmiokjvMKZBZCZCsVsyoFySrcPdQDgKZBW51VZCNqo8Ir2rJANpluK19M0UYIvmc6EhZCL0ZADIVKs4MGZAjZAzRkzZC6mkGIQLrZBTL8HKBq7ovvZAWpcx3MxZCIPcJDLZBZCSqniCIN3Uiu4ZD");
        User me = fbClient.fetchObject("103095739731240", com.restfb.types.User.class);
        System.out.println(me.getAbout());
    }
}
