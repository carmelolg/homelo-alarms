package it.carmelolagamba.homelo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email", ignoreUnknownFields = false)
public class EmailProperties {

	private String user;
	private String password;
	private String smtpHost, smtpSSLEnabled, smtpAuth;
	int smtpPort;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpSSLEnabled() {
		return smtpSSLEnabled;
	}

	public void setSmtpSSLEnabled(String smtpSSLEnabled) {
		this.smtpSSLEnabled = smtpSSLEnabled;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

}
