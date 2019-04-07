/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ditto;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.User;
import info.bliki.html.HTML2WikiConverter;
import info.bliki.html.wikipedia.ToWikipedia;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

/**
 *
 * @author Elizabeth
 */
public class Website {

    public static ArrayList<String> researchPackage(String keywords) {
        ArrayList<String> List = new ArrayList<String>();
        List.add("Beginning Data Packet. Keywords: " + keywords);
        List.add("*********************************************************************");
        Convert.combineArrays(List, site("Wolf", keywords));
        List.add("************************************");
        Convert.combineArrays(List, site("Ae", keywords));
        List.add("************************************");
        Convert.combineArrays(List, site("Ted", keywords));
        List.add("************************************");
        ArrayList<String> temp = site("WikiLink", keywords);
        if (!temp.get(0).contains("No data from:")) {
            for (int i = 4; i < 17; i += 3) {
                Convert.combineArrays(List, site("Wiki", temp.get(i).substring(4)));
                List.add("************************************");
            }
            Convert.combineArrays(List, topX(temp, 3, 18));
        }
        List.add("************************************");
        Convert.combineArrays(List, site("Youtube", keywords));
        List.add("************************************");
        Convert.combineArrays(List, site("FB", keywords));
        return List;

    }

    public static ArrayList<String> topX(ArrayList<String> List, int Y, int X) {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < Y; i++) {
            temp.add(List.get(i));
        }
        for (int i = X; i < List.size(); i++) {
            temp.add(List.get(i));
        }

        return temp;
    }

    public static ArrayList<String> siteData(String earl) {
        ArrayList<String> List = new ArrayList<String>();

        try {
            URL google = new URL(earl);
            URLConnection yc = google.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                try {
                    String temp = inputLine;
                    Convert.combineArrays(List, Convert.stringToArray(Jsoup.parse(temp).toString(), "\n"));
                } catch (Exception e) {
                }
            }
            in.close();
        } catch (Exception e) {
            List.add("Cannot connect to " + earl);
        }
        return List;
    }

    public static ArrayList<String> clickLinks(ArrayList<String> List) {
        ArrayList<String> listCombo = new ArrayList<String>();
        for (int i = 0; i < List.size(); i++) {
            Convert.combineArrays(listCombo, siteData(List.get(i)));
        }
        return listCombo;
    }

    public static ArrayList<String> site(String target, String word) {
        ArrayList<String> List = new ArrayList<String>();
        if (target.equals("Ae")) {
            Convert.combineArrays(List, siteData("http://academicearth.org/?s=" + word.toLowerCase().replaceAll(" ", "+")));
            if (List.size() == 1) {
                return List;
            } else {
                ArrayList<String> temp = shortenAe(cleanAe(List));
                if (temp.size() > 0) {
                    temp.add(0, "Data from: http://academicearth.org/?s=" + word.toLowerCase().replaceAll(" ", "+"));
                    temp.add(1, "**********");
                } else {
                    temp.add(0, "No data from: http://academicearth.org/?s=" + word.toLowerCase().replaceAll(" ", "+"));
                }
                temp.add(1, "Type: Video Lessons");

                return noBlanks(temp);
            }
        }
        if (target.equals("Wolf")) {
            Convert.combineArrays(List, siteData("http://www.wolframalpha.com/input/?i=" + word.toLowerCase().replaceAll(" ", "+")));
            if (List.size() == 1) {
                return List;
            } else {
                ArrayList<String> temp = shortenWolf(cleanWolf(List));
                if (temp.size() > 0) {
                    temp.add(0, "Data from: http://www.wolframalpha.com/input/?i=" + word.toLowerCase().replaceAll(" ", "+"));
                    temp.add(1, "**********");
                } else {
                    temp.add(0, "No data from: http://www.wolframalpha.com/input/?i=" + word.toLowerCase().replaceAll(" ", "+"));
                }
                temp.add(1, "Type: Statistics and Data");
                return noBlanks(temp);
            }
        }
        if (target.equals("Ted")) {
            Convert.combineArrays(List, siteData("http://www.ted.com/search?cat=ss_all&q=" + word.toLowerCase().replaceAll(" ", "+")));
            if (List.size() == 1) {
                return List;
            } else {
                ArrayList<String> temp = shortenTed(cleanTed(List));
                if (temp.size() > 0) {
                    temp.add(0, "Data from: http://www.ted.com/search?cat=ss_all&q=" + word.toLowerCase().replaceAll(" ", "+"));
                    temp.add(1, "**********");
                } else {
                    temp.add(0, "No data from: http://www.ted.com/search?cat=ss_all&q=" + word.toLowerCase().replaceAll(" ", "+"));
                }
                temp.add(1, "Type: Video Lessons");
                return noBlanks(temp);
            }
        }
        if (target.equals("WikiLink")) {
            Convert.combineArrays(List, siteData("http://en.wikipedia.org/w/index.php?title=Special:Search&search=" + word.toLowerCase().replaceAll(" ", "%20") + "&fulltext=Search&profile=default&redirs=1"));
            if (List.size() == 1) {
                return List;
            } else {
                ArrayList<String> temp = shortenWikiLink(cleanWikiLink(List));
                if (temp.size() > 0) {
                    temp.add(0, "Data from: http://www.wikipedia.org");
                    temp.add(1, "**********");
                } else {
                    temp.add(0, "No data from: http://www.wikipedia.org");
                }
                temp.add(1, "Type: Links");
                return noBlanks(temp);
            }
        }
        if (target.equals("Wiki")) {
            Convert.combineArrays(List, siteData(word));
            if (List.size() == 1) {
                return List;
            } else {

                ArrayList<String> temp = cleanWiki(List);
                if (temp.size() > 0) {
                    temp.add(0, "Data from: " + word);
                    temp.add(1, "**********");
                } else {
                    temp.add(0, "No data from: " + word);
                }
                temp.add(1, "Type: Information");
                return noBlanks(temp);
            }
        }
        if (target.equals("Youtube")) {
            Convert.combineArrays(List, siteData("http://www.youtube.com/results?search_query=" + word.toLowerCase().replaceAll(" ", "+")));
            if (List.size() == 1) {
                return List;
            } else {
                ArrayList<String> temp = shortenYoutube(cleanYoutube(List));
                if (temp.size() > 0) {
                    temp.add(0, "Data from: http://www.youtube.com/results?search_query=" + word.toLowerCase().replaceAll(" ", "+"));
                    temp.add(1, "**********");
                } else {
                    temp.add(0, "No data from: http://www.youtube.com/results?search_query=" + word.toLowerCase().replaceAll(" ", "+"));
                }
                temp.add(1, "Type: Video");
                return noBlanks(temp);
            }
        }
        if (target.equals("FB")) {
            Convert.combineArrays(List, fbConnect(word.toLowerCase()));
            if (List.size() == 1) {
                return List;
            } else {
                ArrayList<String> temp = List;
                if (temp.size() > 0) {
                    temp.add(0, "Data from: https://www.facebook.com/search/results.php?q=" + word.toLowerCase().replaceAll(" ", "%20") + "&type=users&init=quick&tas=0.5635571016464382");
                    temp.add(1, "**********");
                } else {
                    temp.add(0, "No data from: https://www.facebook.com/search/results.php?q=" + word.toLowerCase().replaceAll(" ", "%20") + "&type=users&init=quick&tas=0.5635571016464382");
                }
                temp.add(1, "Type: Information");
                return noBlanks(temp);
            }
        }
        return List;
    }

    public static ArrayList<String> noBlanks(ArrayList<String> arr1) {
        for (int i = 0; i < arr1.size(); i++) {
            if (StringUtils.isBlank(arr1.get(i))) {
                arr1.remove(i);
            }
        }
        return arr1;
    }

    public static ArrayList<String> cleanWolf(ArrayList<String> arr1) {
        for (int i = 0; i < arr1.size(); i++) {

            if (!arr1.get(i).contains("context.jsonArray.popups.pod_") || !arr1.get(i).contains("push")) {
                arr1.remove(i);
                i--;

            }
        }
        return arr1;
    }

    public static ArrayList<String> shortenWolf(ArrayList<String> arr1) {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < arr1.size(); i++) {
            arr1.set(i, arr1.get(i).substring(arr1.get(i).indexOf(";: &quot;") + 9, arr1.get(i).indexOf("&quot;mInput") - 7));
            Convert.combineArrays(temp, stringToArrayWolf(arr1.get(i)));
        }

        return temp;
    }

    public static ArrayList<String> stringToArrayWolf(String str) {
        ArrayList<String> temp = new ArrayList<String>();

        while (!str.equals("") && str.contains("\\n")) {
            temp.add(str.substring(0, str.indexOf("\\n")));
            str = str.substring(str.indexOf("\\n") + 2, str.length());
        }
        temp.add(str);
        return temp;
    }

    public static ArrayList<String> cleanAe(ArrayList<String> arr1) {
        for (int i = 0; i < arr1.size(); i++) {

            if (!arr1.get(i).contains("excerpt") && (!arr1.get(i).contains("title") || (!arr1.get(i).contains("youtube") && !arr1.get(i).contains("academicearth")))) {
                arr1.remove(i);
                i--;

            }
        }

        return arr1;

    }

    public static ArrayList<String> shortenAe(ArrayList<String> arr1) {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < arr1.size(); i++) {
            arr1.set(i, arr1.get(i).substring(arr1.get(i).indexOf("\"") + 1, arr1.get(i).length() - 4).replaceAll("excerpt\">", ""));
            if (arr1.get(i).contains("\">")) {
                arr1.set(i, arr1.get(i).substring(0, arr1.get(i).indexOf("\">")));
            }
            Convert.combineArrays(temp, stringToArrayAe(arr1.get(i)));

        }

        while (temp.size() > 0 && !temp.get(0).substring(0, 4).equals("http")) {
            temp.remove(0);
        }
        if (temp.size() > 0) {
            for (int i = 0; i < temp.size() / 3; i++) {
                if (!temp.get(i * 3).substring(0, 5).equals("http:") || temp.get(i * 3 + 1).substring(0, 5).equals("http:") || temp.get(i * 3 + 2).substring(0, 5).equals("http:")) {
                    temp.remove(i * 3);
                    temp.remove(i * 3);
                }
            }

            for (int i = 0; i < temp.size() / 3; i++) {
                String tempstr = temp.get(i * 3);
                temp.set(i * 3, temp.get(i * 3 + 1));
                temp.set(i * 3 + 1, temp.get(i * 3 + 2));
                temp.set(i * 3 + 2, tempstr);

            }
            for (int i = 0; i < temp.size() / 4; i++) {

                temp.set(i * 4 + 1, "    " + temp.get(i * 4 + 1));
                temp.set(i * 4 + 2, "    " + temp.get(i * 4 + 2));
                temp.add(i * 4 + 3, "");
            }
            temp.set(temp.size() - 2, "    " + temp.get(temp.size() - 2));
            temp.set(temp.size() - 1, "    " + temp.get(temp.size() - 1));
            return temp;
        }
        return temp;
    }

    public static ArrayList<String> stringToArrayAe(String str) {
        ArrayList<String> temp = new ArrayList<String>();

        while (!str.equals("") && str.contains("\" title=")) {
            temp.add(str.substring(0, str.indexOf("\" title=")));
            str = str.substring(str.indexOf("title=") + 7, str.length());
        }
        temp.add(str);
        return temp;
    }

    public static ArrayList<String> cleanTed(ArrayList<String> arr1) {
        for (int i = 0; i < arr1.size(); i++) {

            if ((!arr1.get(i).contains("title") || (!arr1.get(i).contains("http:") && !arr1.get(i).contains("/quotes/"))) && (!arr1.get(i).contains("class=\"desc"))) {
                arr1.remove(i);
                i--;

            }
        }

        return arr1;

    }

    public static ArrayList<String> shortenTed(ArrayList<String> arr1) {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i).contains("class=\"desc")) {
                arr1.set(i, arr1.get(i).substring(arr1.get(i).indexOf("desc\">") + 6, arr1.get(i).length() - 4).replaceAll("<b>", "").replaceAll("</b>", "").replaceAll("&quot;", ""));
                Convert.combineArrays(temp, stringToArrayTed(arr1.get(i)));

            } else if (arr1.get(i).contains("href=\"")) {
                arr1.set(i, arr1.get(i).substring(arr1.get(i).indexOf("href=\"") + 6, arr1.get(i).length() - 4).replaceAll("</a><", "").replaceAll("&quot;", ""));
                Convert.combineArrays(temp, stringToArrayTed(arr1.get(i)));
            }

        }
        if (temp.size() > 0) {
            for (int i = 0; i < temp.size() / 3; i++) {
                if (temp.get(i * 3).length() > 8) {
                    if (!temp.get(i * 3).substring(0, 4).equals("http") && !temp.get(i * 3).substring(0, 7).equals("/quotes")) {
                        temp.remove(i * 3);
                        i--;
                    }
                }
            }
            for (int i = 0; i < temp.size() / 3; i++) {
                String tempstr = temp.get(i * 3);
                temp.set(i * 3, temp.get(i * 3 + 1));
                temp.set(i * 3 + 1, temp.get(i * 3 + 2));
                temp.set(i * 3 + 2, tempstr);
            }
            for (int i = 0; i < temp.size() / 4; i++) {

                temp.set(i * 4 + 1, "    " + temp.get(i * 4 + 1));
                temp.set(i * 4 + 2, "    " + temp.get(i * 4 + 2));
                temp.add(i * 4 + 3, "");
            }
            temp.set(temp.size() - 2, "    " + temp.get(temp.size() - 2));
            temp.set(temp.size() - 1, "    " + temp.get(temp.size() - 1));
        }
        return temp;
    }

    public static ArrayList<String> stringToArrayTed(String str) {
        ArrayList<String> temp = new ArrayList<String>();

        while (!str.equals("") && str.contains("\">")) {
            temp.add(str.substring(0, str.indexOf("\">")));
            str = str.substring(str.indexOf("\">") + 2, str.length());
        }
        temp.add(str);
        return temp;
    }

    public static ArrayList<String> cleanWikiLink(ArrayList<String> arr1) {
        //arr1.remove(0);
        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i).contains("mw.config")) {
                arr1.remove(i);

            }

            if (!arr1.get(i).contains("/wiki/") && !arr1.get(i).contains("title")) {
                arr1.remove(i);
                i--;

            }
        }
        return arr1;
    }

    public static ArrayList<String> shortenWikiLink(ArrayList<String> arr1) {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < arr1.size(); i++) {
            arr1.set(i, arr1.get(i).substring(arr1.get(i).indexOf("href") + 6, arr1.get(i).length()));
            if (!arr1.get(i).substring(0, 6).equals("/wiki/")) {
                arr1.remove(i);
                i--;
            } else {
                Convert.combineArrays(temp, stringToArrayWikiLink(arr1.get(i)));
            }
        }
        if (temp.size() > 0) {
            for (int i = 0; i < temp.size() / 2; i++) {
                String tempstr = temp.get(i * 2);
                temp.set(i * 2, temp.get(i * 2 + 1));
                temp.set(i * 2 + 1, tempstr);
            }
            for (int i = 0; i < temp.size() / 3; i++) {
                temp.set(i * 3 + 1, "    https://en.wikipedia.org" + temp.get(i * 3 + 1));
                temp.add(i * 3 + 2, "");
            }
            temp.set(temp.size() - 1, "    https://en.wikipedia.org" + temp.get(temp.size() - 1));
        }
        return temp;
    }
    static boolean marker1, marker2;

    public static ArrayList<String> stringToArrayWikiLink(String str) {
        ArrayList<String> temp = new ArrayList<String>();

        while (!str.equals("") && str.contains("\" title=")) {
            if (str.substring(0, str.indexOf("\" title=")).equals("/wiki/Main_Page")) {
                marker1 = true;
            }
            if (str.substring(0, str.indexOf("\" title=")).equals("/wiki/Help:Searching")) {
                marker2 = true;
            } else {
                marker2 = false;
            }

            if (!marker1 && !marker2) {
                temp.add(str.substring(0, str.indexOf("\" title=")));
            }
            str = str.substring(str.indexOf("\" title=") + 9, str.length());
            if (!str.substring(0, 6).equals("/wiki/")) {
                str = str.substring(0, str.indexOf("\""));
            }

            if (!marker1 && !marker2) {
                temp.add(str);
            }
        }
        return temp;
    }

    public static ArrayList<String> cleanWiki(ArrayList<String> arr1) {
        int marker = Integer.MAX_VALUE;
        for (int i = 0; i < arr1.size(); i++) {

            arr1.set(i, shortenWiki(arr1.get(i)).replaceAll("\n", "").replaceAll("&quot;", "\"").replaceAll("\\[\\[", "").replaceAll("]]", "").replaceAll("'''", "").replaceAll("''", "\"").replaceAll(" \\[edit]", "").replaceAll("<sup>[\"dead link\"]</sup>", "").replaceAll("&deg;", "°"));
            if (arr1.get(i).equals("  NewPP limit report")) {
                marker = i - 1;
            }
            if ((arr1.get(i).equals(" ") || arr1.get(i).equals("")) || (i > marker) || (arr1.get(i).equals(" <br> <br>")) || (arr1.get(i).equals(" <br>")) || (arr1.get(i).equals(" v")) || (arr1.get(i).equals(" t")) || (arr1.get(i).equals(" e"))) {
                arr1.remove(i);
                i--;
            }


        }

        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i).length() > 6) {
                if (arr1.get(i).substring(0, 7).equals(" Image:")) {
                    arr1.remove(i);
                    i--;
                }
                if (arr1.get(i).length() > 13) {
                    if (arr1.get(i).substring(0, 14).equals(" This article ") || arr1.get(i).substring(0, 14).equals(" For a random ")) {
                        arr1.remove(i);
                        i--;
                    }
                }
            }
        }
        int markers = 0;
        for (int i = arr1.size() - 1; i >= 0; i--) {
            if (arr1.get(i).length() > 6) {
                if (arr1.get(i).substring(0, 7).equals(" search")) {
                    markers = i + 1;
                }

            }
            if (i < markers) {
                arr1.remove(i);
            }

        }
        return arr1;
    }

    public static String shortenWiki(String temp) {

        HTML2WikiConverter conv = new HTML2WikiConverter();
        conv.setInputHTML(temp);
        String result = conv.toWiki(new ToWikipedia());

        return result;


    }

    public static ArrayList<String> cleanYoutube(ArrayList<String> arr1) {
        for (int i = 0; i < arr1.size(); i++) {

            if (!arr1.get(i).contains("href=\"/watch?v=") && !arr1.get(i).contains("<b>...</b>") && !arr1.get(i).contains("data-translation-src=\"en\">")) {
                arr1.remove(i);
                i--;

            }
        }
        return arr1;
    }

    public static ArrayList<String> shortenYoutube(ArrayList<String> arr1) {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i).contains("href=\"/watch?v=")) {
                arr1.set(i, arr1.get(i).substring(arr1.get(i).indexOf("href=\"/watch?v=") + 6));
                //arr1.set(i,arr1.get(i).substring(0,arr1.get(i).indexOf("\"")));
                Convert.combineArrays(temp, stringToArrayYoutube(arr1.get(i)));
            }
        }
        int j = 0;
        for (int i = temp.size() - 1; i > 0; i--) {
            if ((j + 1) % 3 == 0) {
                temp.remove(i);
            }
            if ((j + 1) % 3 == 2) {
                temp.set(i, "    http://www.youtube.com/" + temp.get(i));
            }
            j++;

        }
        temp.remove(0);
        if (temp.size() > 0) {
            for (int i = 0; i < temp.size() / 2; i++) {
                String tempstr = temp.get(i * 2);
                temp.set(i * 2, temp.get(i * 2 + 1));
                temp.set(i * 2 + 1, tempstr);
            }
        }
        return temp;
    }

    public static ArrayList<String> stringToArrayYoutube(String str) {
        ArrayList<String> temp = new ArrayList<String>();
        while (!str.equals("") && str.contains("\" data-translation")) {
            temp.add(str.substring(0, str.indexOf("\" data-translation")));
            str = str.substring(str.indexOf("\" data-translation") + 28, str.length()).replaceAll("</a></h3>", "");
        }
        temp.add(str);
        return temp;
    }

    public static ArrayList<String> fbConnect(String ID) {
        ArrayList<String> temp = new ArrayList<String>();
        try {
            AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken("512025415532229", "9aa372d076e605b0d982a18f3f77d558");
            String ACT = accessToken.toString();
            ACT = ACT.substring(ACT.indexOf("Token=") + 6, ACT.indexOf(" expires"));
            FacebookClient fbClient;

            fbClient = new DefaultFacebookClient("CAAHRrzUCPsUBAGMAZBCpK5RkYmORaZCDcduy1oKHnuozf8uR2ZBvL8IwJn0HLfkhMZAAO6zbaWkWR45rd97eD6M9vilxLHFU8CxThcoYI0e2iNZBBpLwxjof8ufRdXKw6Ns0oKnYbGyduQ67ej6Vs");


            User me = fbClient.fetchObject(ID.toLowerCase().replaceAll(" ", "%20"), com.restfb.types.User.class);

            temp.add("Name: " + me.getFirstName() + " " + me.getMiddleName() + " " + me.getLastName());

            temp.add("Username: " + me.getUsername() + " - " + me.getLink());
            temp.add("Gender: " + me.getGender());
            temp.add("Home Town: " + me.getHometownName());
            try {
                String tempstr = me.getLocation().toString();
                temp.add("Last Known Current Location: " + tempstr.substring(tempstr.indexOf("name=") + 5, tempstr.indexOf(" type=")) + " - https://www.facebook.com/" + tempstr.substring(tempstr.indexOf("id=") + 3, tempstr.indexOf(" metadata=")));
            } catch (Exception e) {
            }


            temp.add("Timezone: " + me.getTimezone());
            temp.add("Email: " + me.getEmail());
            temp.add("Religion: " + me.getReligion());
            temp.add("Job: " + me.getWork());
            temp.add("Birthday: " + me.getBirthday());
            temp.add("Personal Website: " + me.getWebsite());
            temp.add("Politcal Party: " + me.getPolitical());
            temp.add("Educational Background: " + me.getEducation());
            temp.add("Known Languages: " + me.getLanguages());
            temp.add("Quotes: " + me.getQuotes());
            temp.add("Relationship Status: " + me.getRelationshipStatus());
            temp.add("Interested In: " + me.getInterestedIn());
            temp.add("Significant Other: " + me.getSignificantOther());
            temp.add("Favorite Athletes: " + me.getFavoriteAthletes());
            temp.add("Favorite Teams: " + me.getFavoriteTeams());
            temp.add("Sports: " + me.getSports());
            temp.add("Prefered Currency: " + me.getCurrency());
            temp.add("About: " + me.getAbout());
            temp.add("Bio: " + me.getBio());
            for (int i = 0; i < temp.size(); i++) {
                String tempst = temp.get(i);
                temp.set(i, tempst.replaceAll("null", "").replaceAll("\\[]", ""));
                tempst = temp.get(i);
                if (tempst.substring(tempst.indexOf(": ") + 2, tempst.length()).equals("")) {
                    temp.remove(i);
                    i--;
                }
            }
        } catch (Exception e) {
        }

        return temp;
    }
}
