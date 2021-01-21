package br.com.obpc.services;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import br.com.obpc.entities.User;

@Service
public class MailService {
	
	public void sendTo(User user) {
		Properties prop = new Properties();

		/** Parâmetros de conexão com servidor Gmail */
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");

//		String sender = MenageProperties.getString("email.sender");
//		String emailPassword = MenageProperties.getString("email.password");
		
		//TODO pegar valores do arquivo properties externo
		String sender = "cristianolimaudi@gmail.com";
		String emailPassword = "Cris@2006";

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, emailPassword);
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			// Remetente

			Address[] toUser = InternetAddress.parse(user.getUsername());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("<no-replay> Ativação de conta Livraria OBPC");// Assunto
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("Para ativar seu acesso clique no link abaixo.\n");
			
			//TODO pegar valores do arquivo properties externo
			String linkActivation = "http://localhost:8587/obpc/user/activation/"+user.getId()+"/"+user.getToken();
			
			strBuilder.append(linkActivation);
			
			message.setText(strBuilder.toString());

			/** Método para enviar a mensagem criada */
			Transport.send(message);

			System.out.println("Feito!!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
