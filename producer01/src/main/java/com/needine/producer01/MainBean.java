package com.needine.producer01;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.io.Serializable;
import java.net.URI;

@Named
@ViewScoped
public class MainBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String msgId;
	
	private String targetUrl;
	private Client client;
    private Response response;
	
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

	private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:9080/consumer01").build();
    }
	  
	public void save() {
		System.out.println("user " + getUserId() + " likes " + getMsgId());

		//targetUrl = "http://localhost:9080/consumer01/rest/hello";
		client = ClientBuilder.newClient();
		
		WebTarget target = client.target(getBaseURI());
    	String response = target.path("rest").
                path("hello").
                request().
                accept(MediaType.TEXT_PLAIN).
                get(Response.class)
                .toString();

		String plainAnswer =
		    target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class);
		String xmlAnswer =
		    target.path("rest").path("hello").request().accept(MediaType.TEXT_XML).get(String.class);
		String htmlAnswer=
		    target.path("rest").path("hello").request().accept(MediaType.TEXT_HTML).get(String.class);
		
		System.out.println(response);
		System.out.println(plainAnswer);
		System.out.println(xmlAnswer);
		System.out.println(htmlAnswer);
		
		
		FacesMessage message = new FacesMessage("DONE!! " + getUserId());
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}