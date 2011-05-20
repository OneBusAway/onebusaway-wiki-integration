package org.onebusaway.wiki.api;

import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

import org.onebusaway.wiki.api.impl.WikiPageImpl;

/**
 * Generic representation of a wiki attachment, including namespace, page name,
 * name, locale, and content. The combination of namespace + page name + name
 * should uniquely identify an attachment.
 * 
 * @author bdferris
 * @see WikiPageImpl
 */
public interface WikiAttachmentContent {

  /**
   * 
   * @return the namespace of the attachment
   */
  public String getNamespace();

  /**
   * 
   * @return the name of the page
   */
  public String getPageName();

  /**
   * 
   * @return the name of the attachment
   */
  public String getName();

  /**
   * 
   * @return the locale of the attachment
   */
  public Locale getLocale();

  /**
   * 
   * @return the content of the attachment
   */
  public InputStream getContent();

  /**
   * 
   * @return the content type of the attachment
   */
  public String getContentType();

  /**
   * 
   * @return the last modified time
   */
  public Date getLastModified();
}
