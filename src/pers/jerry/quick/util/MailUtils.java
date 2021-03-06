// Copyright (c) 2017 Quick
// ============================================================================
// CHANGE LOG
// V.1.0 : 2017-XX-XX, jerry.zhao, creation
// ============================================================================

/**
 * @author jerry.zhao
 *
 */
package pers.jerry.quick.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public final class MailUtils {
    private static final Logger logger = Logger.getLogger(MailUtils.class);
    private static final String CONSTANT_MAIL_ACCOUNT = "mail.account";
    private static final String CONSTANT_MAIL_PASSWORD = "mail.password";
    private static final String CONSTANT_MAIL_SMTP_HOST = "mail.SMTP.host";
    private static final String CONSTANT_MAIL_FROM_NAME = "mail.from.name";
    private static final String CONSTANT_MAIL_CONTENT = "mail.content";
    private static final String UTF8 = "UTF-8";

    private static String mailAccount;
    private static String mailPassword;
    private static String mailSMTPHost;
    private static String mailFromName;
    private static String htmlContent;
    private static String captcha;
    private static String emailUserName = "";

    static {
        init();
    }

    private MailUtils() {
    }

    private static void init() {
        final ResourceBundle resource = ResourceBundle.getBundle("MailSetting");
        mailAccount =  resource.getString(CONSTANT_MAIL_ACCOUNT);
        mailPassword =  resource.getString(CONSTANT_MAIL_PASSWORD);
        mailSMTPHost =  resource.getString(CONSTANT_MAIL_SMTP_HOST);
        mailFromName =  resource.getString(CONSTANT_MAIL_FROM_NAME);
        //linux
        try {
			htmlContent =  new String(resource.getString(CONSTANT_MAIL_CONTENT).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		}
        //windows
        //htmlContent = getHTMLContent();
    }

    public static String sendMail(String receiveMailAccount, String userName)
            throws MessagingException, UnsupportedEncodingException {
        if (StringUtils.isNotBlank(userName)) {
            emailUserName = userName;
        }
        final Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", mailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "465"); 
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port","465");

        final Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        final MimeMessage message = createMimeMessage(session, mailAccount, receiveMailAccount);

        final Transport transport = session.getTransport();
        transport.connect(mailAccount, mailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        return captcha;
    }

    private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail)
            throws MessagingException, UnsupportedEncodingException {
        final MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, mailFromName, UTF8));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, UTF8));
        message.setSubject("ע��JERRY HOME", UTF8);
        message.setContent(emailContent(), "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private static String getHTMLContent() {
        final StringBuffer content = new StringBuffer();
        final String filePath = MailUtils.class.getResource("/emailTemplate.html").getFile().substring(1);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (final FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (final IOException e) {
            logger.error("IOException", e);
        }
        return content.toString();
    }

    private static String emailContent() {
        return htmlContent.replace("{username}", emailUserName).replace("{captcha}", generateCaptcha());
    }


    private static String generateCaptcha() {
        final StringBuffer sb = new StringBuffer();
        final Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        captcha = sb.toString();
        return captcha;
    }

}
