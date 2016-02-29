package com.licon.rssfeeds.data.model;

import android.content.ContentValues;

import java.util.Date;

public class FeedItem {

	private String uniqueId;
	private String title;
	private String paymentURL;
	private String link;
	private String description;
	private Date publicationDate;
	private String mediaURL;
	private Long mediaSize;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		uniqueId = uniqueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPaymentURL() {
		return paymentURL;
	}

	public void setPaymentURL(String paymentURL) {
		this.paymentURL = paymentURL;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getMediaURL() {
		return mediaURL;
	}

	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}

	public Long getMediaSize() {
		return mediaSize;
	}

	public void setMediaSize(Long mediaSize) {
		this.mediaSize = mediaSize;
	}
}
