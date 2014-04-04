package is.tru.truin;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class FyrirbaenaefniFragment extends Fragment implements OnClickListener {
	
	Button sendafyrirbaenButton;
	EditText Fyrirbaen;
	private static final String username ="truinoglifid@gmail.com";
	private static final String password ="12345truin";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_fyrirbaenaefni, container, false);
        
        Fyrirbaen = (EditText) rootView.findViewById(R.id.fyrirbaenin);
        
        Button sendafyrirbaenButton = (Button) rootView.findViewById(R.id.sendafyrirbaenButton);
        sendafyrirbaenButton.setOnClickListener(this);     
        
        return rootView;
    }

	@Override
	public void onClick(View v) {
		String baenin = Fyrirbaen.getText().toString();
		sendMail("mas91@hi.is", "Fyrirbæn", baenin);
	}
	
	private void sendMail(String email, String subject, String baenin) {
		Session session = createSessionObject();
		
		try {
			Message message = createMessage(email, subject, baenin, session);
			new SendMailTask().execute(message);
		} catch (AddressException e){
			e.printStackTrace();
		} catch (MessagingException e){
			e.printStackTrace();
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	
	private Message createMessage(String email, String subject, String baenin, Session session) throws MessagingException, UnsupportedEncodingException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username, "Appid"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
		message.setSubject(subject);
		message.setText(baenin);
		return message;
	}
	
	private Session createSessionObject() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		return Session.getInstance(properties, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
	
	private class SendMailTask extends AsyncTask<Message, Void, Void> {
		private ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			CharSequence bidid = "Vinsamlega bíðið";
			CharSequence sendi = "Sendi fyrirbænarefni"; 
			pDialog = ProgressDialog.show(getActivity(), bidid, sendi, true, false);
		}
		
		@Override
		protected void onPostExecute(Void aVoid){
			super.onPostExecute(aVoid);
			pDialog.dismiss();
		}
		
		@Override
		protected Void doInBackground(Message... messages) {
			try {
				Transport.send(messages[0]);
			} catch (MessagingException e){
				e.printStackTrace();
			}
			return null;
		}	
	}
}
