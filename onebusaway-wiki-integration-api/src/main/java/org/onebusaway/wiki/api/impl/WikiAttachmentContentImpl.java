package org.onebusaway.wiki.api.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

import org.onebusaway.wiki.api.WikiAttachmentContent;

public class WikiAttachmentContentImpl implements WikiAttachmentContent {

  private String namespace;

  private String pageName;

  private String name;

  private Locale locale;

  private InputStream content;

  private String contentType;

  private Date lastModified;

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setContent(InputStream content) {
    this.content = content;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  /****
   * {@link WikiAttachmentContent} Interface
   ****/

  @Override
  public String getNamespace() {
    return namespace;
  }

  @Override
  public String getPageName() {
    return pageName;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Locale getLocale() {
    return locale;
  }

  @Override
  public InputStream getContent() {
    return content;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public Date getLastModified() {
    return lastModified;
  }
}
