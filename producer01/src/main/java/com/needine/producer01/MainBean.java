package com.needine.producer01;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

@Named
@ViewScoped
public class MainBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String msgId;
		
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void save() {
		String string = "{\"user\": \"" + getUserId() + "\", \"msg\": \"" + getMsgId() + "\"}";
		JSONObject jsonObject = new JSONObject(string);

		// Step2: Now pass JSON File Data to REST Service
		try {
			URL url = new URL("http://localhost:9080/consumer01/rest/likesService");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(jsonObject.toString());
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			while (in.readLine() != null) {
			}
			System.out.println("\nCrunchify REST Service Invoked Successfully..");
			in.close();
		} catch (Exception e) {
			System.out.println("\nError while calling Crunchify REST Service");
			System.out.println(e);
		}
		
		FacesMessage message = new FacesMessage("DONE!! " + getUserId());
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}