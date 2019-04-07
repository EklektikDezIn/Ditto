/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eliza;

import java.util.Scanner;

/**
 *
 * @author Micah Source code found at
 * https://www.youtube.com/watch?v=a8RUmnPL8aQ&app=desktop **Code Modified.
 */
public class ELIZA {

    static final String ENTER = System.getProperty("line.separator") + "D-> ";
    static String[][] specific = {
        //standard greetings
        {"hi", "hello", "hola", "howdy", "hey", "heyy", "yo", "ditto"},
        {"Hi", "Hello", "Hola", "Howdy", "Hey", "Yo"},
        {"", "!"},
        //how are you
        {"how are you", "how are you doing", "how you doing", "how is everything for you","how goes it"},
        {"Good", "Doing well", "Quite well", "Average", "Meh"},
        {"", ENTER + "You?", ENTER + "And yourself?"},
        //What's up
        {"what's up", "what are you doing", "sup"},
        {"Nothing much", "The Ceiling!", "Just Chillin'", "Cleaning my room", "Finshing My Homework"},
        {"", ENTER + ":P", ENTER + "You?", ENTER + "And yourself?"},
        //comments
        {"cool", "nice", ":)", "I see", "nvm", "(y)", "haha", "lol","it's ok","ok","yay"},
        {":)", ":P"},
        {""},
        //yes
        {"yes"},
        {"no", "NO", "NO!!!!!!!","ok","fine"},
        {""},
        //no
        {"no"},
        {"Yes", "YES", "YES!!!!!!!","ok","fine"},
        {""},
        //pleas
        {"please", "aw come on", ":)", "=]","come on", "why not"},
        {"Fine", ":P", "No", "Nope", "You convinced me.", "Just this once"},
        {""},
        
    };
    static String[][] general = {
        //respond positively
        {":)", ":d", "xd", "^_^","=]","=d"},
        {":)", "haha", "lol", "Sweet!", "Cool!", "XD", ":D"},
        {""},
        //respond negatively
        {"-.-", "-_-", ":/", ":(", ":'(","=/","=(",},
        {"Sorry", "Forgive me."},
        {""},
        //neutral
        {":p"},
        {":p"},
        {""},
        //confusion
        {".?"},
        {"O:)","O:D"},
        {""},
        //frustration
        {"gahh","argh","arghh","grr"},
        {":/", ":(", ":'("},
        {""},
        //disgust
        {"poop"},
        {"That is horribly disgusting.","Eww","Gross."},
        {""}
    };
        
    static String[] defaults = 
        {"I'm afraid I don't understand", "Could you clarify?", "Let's discuss something else.", "May I help you with something?","Can we go do some math problems?"};
    

    public static void main(String[] args) {
        while (true) {
            launchE();
        }
    }

    public static String fix(String phrase) {
        String[][] spellcheck = {
            {"hey", "heyy", "heey"},
            {"hola", "ola", "olaa"},
            {" are ", " r "},
            {" you ", " u ","ya"},
            {"doing", "doin", "doin'", "dooin"},
            {"cool", "kul"},
            {"the", "teh"},
            {"ok","okay"," k "},
            {"come on","c'mon","cmon"}
               
        };
        phrase = phrase.trim();
        while (phrase.length()>0 && (phrase.charAt(phrase.length() - 1) == '!'
                || phrase.charAt(phrase.length() - 1) == '.'
                || phrase.charAt(phrase.length() - 1) == '?')) {
            phrase = phrase.substring(0, phrase.length() - 1);
        }
        phrase = " " + phrase;
        phrase = phrase.replaceAll("\\.", "");
        phrase = phrase.replaceAll(":P", "");
        phrase = phrase.replaceAll(":D", "");
        phrase = phrase.replaceAll(":\\)", "");
        phrase = phrase.replaceAll(":/", "");
        phrase = phrase.replaceAll(":\\(", "");
        phrase = phrase.replaceAll(":'\\(", "");
        phrase = phrase.replaceAll("\\*", "");
        phrase = phrase.replaceAll("'", "");

        

        for (int i = 0; i < phrase.length() - 2; i++) {
            Character c1 = phrase.charAt(i);
            Character c2 = phrase.charAt(i + 1);
            Character c3 = phrase.charAt(i + 2);
            if (c1.equals(c2) && c2.equals(c3)) {
                String temp = c1.toString() + c2.toString() + c3.toString();
                phrase = phrase.replaceFirst(temp, temp.substring(1));
                i--;
            }
        }


        for (int i = 0; i < spellcheck.length; i++) {
            for (int j = 1; j < spellcheck[i].length; j++) {
                if (phrase.contains(spellcheck[i][j])) {
                    phrase = phrase.replaceFirst(spellcheck[i][j], spellcheck[i][0]);
                    j = 0;
                }
            }
        }
        phrase = phrase.trim();
        if (!phrase.isEmpty() && (phrase.replaceAll("h", "").replaceAll("a", "").trim().isEmpty() || phrase.replaceAll("h", "").replaceAll("e", "").trim().isEmpty())) {
            phrase = "haha";
        }
        return phrase;
    }

    public static void launchE() {
        Scanner inpt = new Scanner(System.in);
        String quote = inpt.nextLine();
        String backup = quote;
        quote = fix(quote).trim();
        byte response = 0;
        /*
         0:we're searching through specific[][] for matches
         1:we didn't find anything
         2:we did find something
         */
        //-----check for matches----
        int j = 0;//which group we're checking
        while (response == 0) {
            if (inArray(quote.toLowerCase(), specific[j * 3])) {
                response = 2;
                int r = (int) Math.floor(Math.random() * specific[(j * 3) + 1].length);
                int s = (int) Math.floor(Math.random() * specific[(j * 3) + 2].length);
                System.out.println("D-> " + specific[(j * 3) + 1][r] + specific[(j * 3) + 2][s]);
            }
            j++;
            if (j * 3 == specific.length && response == 0) {
                response = 1;
                int k = 0;//which group we're checking
                while (response == 1) {
                    if (inArrayprime(backup.toLowerCase(), general[k * 3])) {
                        response = 2;
                        int r = (int) Math.floor(Math.random() * general[(k * 3) + 1].length);
                        int s = (int) Math.floor(Math.random() * general[(k * 3) + 2].length);
                        System.out.println("D-> " + general[(k * 3) + 1][r] + general[(k * 3) + 2][s]);
                    }
                    k++;

                    if (k * 3 == general.length && response == 1) {
                        response = 2;
                        int r = (int) Math.floor(Math.random() * defaults.length);
                        System.out.println("D-> " + defaults[r]);
                    }
                }
            }
        }
    }

    public static boolean inArray(String in, String[] str) {
        boolean match = false;
        for (int i = 0; i < str.length; i++) {
            
            if (in.equals(str[i])) {
                match = true;
            }
        }
        return match;
    }
    
    public static boolean inArrayprime(String in, String[] str) {
        boolean match = false;
        for (int i = 0; i < str.length; i++) {
            
            if (in.equals(str[i])) {
                match = true;
            }
        }
        return match;
    }
}