package Ditto;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Date;
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static Ditto.Convert.combineArrays;
import com.sun.mail.imap.protocol.FLAGS;

/**
 *
 * @author Elizabeth
 */
public class Email extends MailBox {
    //Provides computer to Gmail server connections

    public String password;

    public Email(String UN, String Id, String Ad, String P) {
        //Creates a new Email object
        super(UN, Id, Ad);
        password = P;
    }

    @Override
    public String toString() {
        //Prints out a Email object as a String
        return "UserName: " + userName + " Password: " + password + " ID: " + ID + " Address: " + address;
    }

    public static boolean handShake(ArrayList<String> inpt, String inptaddress) {
        boolean pass1 = false;
        boolean pass2 = false;
        String IDtemp = "";
        String Usertemp = "";
        for (int i = 0; i < inpt.size(); i++) {
            if (inpt.get(i).contains("!!")) {
                if (inpt.get(i).contains("ID: ") && !pass1) {
                    IDtemp = inpt.get(i).substring(inpt.get(i).indexOf("ID: "));
                    pass1 = true;
                }
                if (inpt.get(i).contains("Sent by: ") && !pass2) {
                    Usertemp = inpt.get(i).substring(inpt.get(i).indexOf("Sent by: "));
                    pass2 = true;
                }
            }
        }
        if (!IDtemp.equals("") && !Usertemp.equals("")) {
            MailBox Mailtemp = new MailBox(Usertemp, IDtemp, inptaddress);
            for (int i = 0; i < Ditto.Mail.size(); i++) {
                if (Mailtemp.equals(Ditto.Mail.get(i))) {
                    return true;
                }
            }
            FolderC.writeToFile(System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "SystemFiles" + System.getProperty("file.separator") + "MailBox.txt", Mailtemp.toString(System.getProperty("line.separator")), true);
            return false;
        }
        return false;
    }

    public void sendEmails(String to, String subject, String messages) {
        //Sends an email to a specified target

        final String usernames = address;

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernames, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(address));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messages);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmails(String to, String subject, String messages, String fileloc) {
        //Sends an email with an attatched file to a specified target

        final String usernames = userName;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernames, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(address));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);


            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(messages);
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            DataSource source = new FileDataSource(fileloc);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(fileloc);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);


            Transport.send(message);

            System.out.println("Done");


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void spam(String to, String subject, String message, int count) {
        //Sends a specified number of emails to a specified target
        String storage = subject;
        for (int i = 1; i <= count; i++) {
            subject = storage + i;
            this.sendEmails(to, subject, message);
        }
        subject = storage;
    }

    public void spam(String to, String subject, String message, String fileloc, int count) {
        //Sends a specified number of emails with an attatched file to a specified target
        String storage = subject;
        for (int i = 1; i <= count; i++) {
            subject = storage + i;
            this.sendEmails(to, subject, message, fileloc);
        }
        subject = storage;
    }
    Folder inbox;

    public void readEmail(String Target) {
        //Fetches emails with specified subject from server - Detailed info
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", address, password);
            /* Mention the folder name which you want to read. */
            inbox = store.getFolder("Inbox");
            System.out.println("No of Unread Messages : " + inbox.getMessageCount() + " " + inbox.getUnreadMessageCount());

            /* Open the inbox using store. */
            inbox.open(Folder.READ_WRITE);

            /*
             * Get the messages which is unread in the Inbox Message messages[]
             * = inbox.search(new FlagTerm( new Flags(Flag.SEEN), false));
             */
            Message messages[] = inbox.getMessages();
            /* Use a suitable FetchProfile */
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.CONTENT_INFO);
            inbox.fetch(messages, fp);

            try {
                printAllMessages(messages, Target);

                inbox.close(true);
                store.close();
            } catch (Exception ex) {
                System.out.println("Exception arise at the time of read mail");
            }
        } catch (NoSuchProviderException e) {
            System.exit(3);
        } catch (MessagingException e) {
            System.exit(4);
        }
    }

    public void printAllMessages(Message[] msgs, String Target) throws Exception {
        //Prints all emails with a specified subject - Detailed info
        for (int i = 0; i < msgs.length; i++) {
            try {
                if (msgs[i].getSubject().equals(Target)) {
                    System.out.println("MESSAGE #" + (i + 1) + ":");
                    printEnvelope(msgs[i]);
                    Multipart multipart = (Multipart) msgs[i].getContent();

                    for (int x = 0; x < multipart.getCount(); x++) {
                        BodyPart bodyPart = multipart.getBodyPart(x);

                        String disposition = bodyPart.getDisposition();

                        if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
                            System.out.println("Mail have some attachment : ");

                            DataHandler handler = bodyPart.getDataHandler();
                            System.out.println("file name : " + handler.getName());
                        } else {
                            System.out.println(bodyPart.getContent());
                        }
                    }
                    msgs[i].setFlag(Flags.Flag.DELETED, true);
                }

            } catch (NullPointerException e) {
            }
        }
    }

    /* Print the envelope(FromAddress,ReceivedDate,Subject) */
    public void printEnvelope(Message message) throws Exception {
        //Gets Message Data from an email - Detailed
        Address[] a;
        // FROM
        if ((a = message.getFrom()) != null) {
            for (int j = 0; j < a.length; j++) {
                System.out.println("FROM: " + a[j].toString());
            }
        }
        // TO
        if ((a = message.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++) {
                System.out.println("TO: " + a[j].toString());
            }
        }
        String subject = message.getSubject();
        Date receivedDate = message.getReceivedDate();
        String content = message.getContent().toString();
        System.out.println("Subject : " + subject);
        System.out.println("Received Date : " + receivedDate.toString());

    }

    public ArrayList<String> readEmail_simple(String Target) {
        //Fetches emails with specified subject from server - Simplified
        ArrayList<String> List = new ArrayList<String>();
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", address, password);
            /* Mention the folder name which you want to read. */
            inbox = store.getFolder("Inbox");

            /* Open the inbox using store. */
            inbox.open(Folder.READ_WRITE);

            /*
             * Get the messages which is unread in the Inbox Message messages[]
             * = inbox.search(new FlagTerm( new Flags(Flag.SEEN), false));
             */
            Message messages[] = inbox.getMessages();
            /* Use a suitable FetchProfile */
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.CONTENT_INFO);
            inbox.fetch(messages, fp);

            try {
                List = (printAllMessages_simple(messages, Target));
                inbox.close(true);
                store.close();
                return List;
            } catch (Exception ex) {
            }

        } catch (NoSuchProviderException e) {
            System.exit(1);
        } catch (MessagingException e) {
            System.err.println(e);
            System.exit(2);
        }

        return List;
    }

    public ArrayList<String> printAllMessages_simple(Message[] msgs, String Target) throws Exception {
        //Prints all emails with a specified subject - only needed information
        ArrayList<String> List = new ArrayList<String>();
        for (int i = 0; i < msgs.length; i++) {
            try {
                if (msgs[i].getSubject().equals(Target)) {
                    if (msgs[i].getContent() instanceof String) {
                        combineArrays(List, printEnvelope_simple(msgs[i]));
                    } else {
                        Multipart multipart = (Multipart) msgs[i].getContent();
//
                        for (int x = 0; x < multipart.getCount(); x++) {
                            BodyPart bodyPart = multipart.getBodyPart(x);
//
//                            String disposition = bodyPart.getDisposition();
//
//                            if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
//                            } else {
//                                String temp = bodyPart.getContent().toString();
                            List.add(bodyPart.getContent().toString());
                            // List.add(temp.substring(0, temp.indexOf("\n")));
                            combineArrays(List, printEnvelope_simple(msgs[i]));
                            return clean(List);
                        }
                    }
                    msgs[i].setFlag(FLAGS.Flag.DELETED, true);
                }

            } catch (NullPointerException e) {
            }
        }
        return clean(List);
    }

    private ArrayList<String> clean(ArrayList<String> arr1) {
        //removes unnecessary data from Email responses
        for (int i = 0; i < arr1.size(); i++) {
            if ("\n".equals(arr1.get(i))) {
                arr1.remove(i);
                i--;
            }

            if (arr1.get(i).length() > 10) {
                if (arr1.get(i).substring(0, 10).equals("javax.mail") || arr1.get(i).substring(0, 10).equals("com.sun.ma")) {
                    arr1.remove(i);
                    i--;
                }
            }
            if ("".equals(arr1.get(i))) {
                arr1.remove(i);
                i--;
            }
        }
        return arr1;
    }

//
    public ArrayList<String> printEnvelope_simple(Message message) throws Exception {
        //Gets message data from an email - simplified
        ArrayList<String> List = new ArrayList<String>();
        String contentType = message.getContentType();
        if (contentType.contains("multipart")) {
            Multipart multiPart = (Multipart) message.getContent();
            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    try {
                        //part.saveFile(
                        part.saveFile(System.getProperty("user.dir") + System.getProperty("file.separator") + "Imports" + System.getProperty("file.separator") + message.getSubject() + part.getFileName().substring(part.getFileName().lastIndexOf('.'), part.getFileName().length()));
                        List.add("File saved as: " + System.getProperty("user.dir") + System.getProperty("file.separator") + "Imports" + System.getProperty("file.separator") + message.getSubject() + part.getFileName().substring(part.getFileName().lastIndexOf('.'), part.getFileName().length()));
                    } catch (Exception e) {
                    }

                }
            }
        }
        combineArrays(List, Convert.stringToArray(message.getContent().toString(), "#"));
        return List;
    }
}
