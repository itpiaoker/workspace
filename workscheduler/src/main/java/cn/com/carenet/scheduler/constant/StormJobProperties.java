package cn.com.carenet.scheduler.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "job.storm")
public class StormJobProperties {
	private String frameworkurl;
	private String xmlencoding;
	private String mvnpackage;
	private String mvninstall;
	private String nimbusseeds;
	private String nimbusthriftport;
	public String getFrameworkurl() {
		return frameworkurl;
	}
	public void setFrameworkurl(String frameworkurl) {
		this.frameworkurl = frameworkurl;
	}
	public String getXmlencoding() {
		return xmlencoding;
	}
	public void setXmlencoding(String xmlencoding) {
		this.xmlencoding = xmlencoding;
	}
	public String getMvnpackage() {
		return mvnpackage;
	}
	public void setMvnpackage(String mvnpackage) {
		this.mvnpackage = mvnpackage;
	}
	public String getMvninstall() {
		return mvninstall;
	}
	public void setMvninstall(String mvninstall) {
		this.mvninstall = mvninstall;
	}
	public String getNimbusseeds() {
		return nimbusseeds;
	}
	public void setNimbusseeds(String nimbusseeds) {
		this.nimbusseeds = nimbusseeds;
	}
	public String getNimbusthriftport() {
		return nimbusthriftport;
	}
	public void setNimbusthriftport(String nimbusthriftport) {
		this.nimbusthriftport = nimbusthriftport;
	}
}
