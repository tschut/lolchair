package com.lolchair.lolchair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.widget.Toast;

@EBean
public class Mailer {

    @RootContext
    Context         context;

    private Session session = createSessionObject();

    public void submit(String comment, InputStream inputStream) {
        Toast.makeText(context, "Submitting picture", Toast.LENGTH_SHORT).show();
        Message message;
        try {
            message = createMessage(comment, inputStream);
            sendMessage(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background
    void sendMessage(Message message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        submissionDone(context);
    }

    @UiThread
    void submissionDone(Context context) {
        Toast.makeText(context, "Submission successful", Toast.LENGTH_SHORT).show();
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lolchairSender", "blRymiU9GtZ4h0T1PFZN");
            }
        });
    }

    private Message createMessage(String body, InputStream inputStream) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("lolchairSender@gmail.com", "lolchair app"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("tschut+lolchair@gmail.com", "tschut+lolchair@gmail.com"));
        message.setSubject("lolchair submission");
        message.setText(body);

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setFileName("lolchair");
        bodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(IOUtils.toByteArray(inputStream), "image/*")));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        message.setContent(multipart);

        return message;
    }
}
