import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarCorreos {
	public static void main(String args[]) throws IOException {
		Properties propiedades = System.getProperties();

		propiedades.put("mail.smtp.starttls.enable", "true");
		propiedades.put("mail.smtp.ssl.protocols", "TLSv1.2");
		propiedades.put("mail.smtp.host", "smtp.gmail.com");
		propiedades.put("mail.smtp.port", "587");
		propiedades.put("mail.smtp.auth", "true");
		propiedades.put("mail.smtp.user", "dmacel1ca2@gmail.com");
		
		Session sesion = Session.getDefaultInstance(propiedades); 
		System.out.println("Hemos iniciado sesión con GMAIL");
		
		Scanner scan = new Scanner(System.in);
		try { 
			MimeMessage email = new MimeMessage(sesion);
			int seleccion = 0;
			ArrayList<String> correos = new ArrayList<String>();
			do {
				System.out.println("Ingrese un correo");
				correos.add(scan.nextLine());
				System.out.println("¿Quiere añadir otro? [Y,N]");
				if(scan.nextLine().equalsIgnoreCase("N")) {
					seleccion=1;
				}
			}while(seleccion==0);
			if(correos.isEmpty()) {
				System.out.println("No hay correos");
			}else {
				String destinatarios="";
				for(String correoE: correos) {
					destinatarios = destinatarios+","+correoE;
				}
				
				InternetAddress[] direccionesTo = InternetAddress.parse(destinatarios, true);
				
				email.setRecipients(Message.RecipientType.TO, direccionesTo);
				email.setSubject("Buenos dias");
				email.setSentDate(new Date());
				System.out.println("Escribe el mensaje");
				email.setText(scan.nextLine());
				System.out.println("Hemos configurado el email");
				// Creando transportador de emails usando protocolo SMTP. 
				Transport t = sesion.getTransport("smtp");
				// Conectando al servidor de correo con autentificación. 
				t.connect("dmacel1ca2@gmail.com", "hwvwikgxytoljsaq");
				System.out.println("Hemos conectado con el servidor de GMAIL");
				int opcion2 = 0;
				//Permite enviar mas de un mensaje
				do {
					t.sendMessage(email, email.getAllRecipients());
					opcion2++;
				}while(opcion2<1);
				
				t.close();
				System.out.println("El mensaje ha sido enviado con éxito");
			}

		} catch (MessagingException e) {
			System.out.println("No se ha podido enviar el mensaje " + e);
		}
	}
}
