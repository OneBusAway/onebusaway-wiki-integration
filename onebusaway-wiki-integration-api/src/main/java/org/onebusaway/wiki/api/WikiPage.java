package org.onebusaway.wiki.api;

import org.onebusaway.wiki.api.impl.WikiPageImpl;

/**
 * Generic representation of a wiki page, including namespace, name, title, and
 * content. The combination of namespace + name should uniquely identify a page.
 * 
 * @author bdferris
 * @see WikiPageImpl
 */
public interface WikiPage {

  /**
   * 
   * @return the namespace of the page
   */
  public String getNamespace();

  /**
   * 
   * @return the name of the page
   */
  public String getName();

  /**
   * @return the title of the wiki page, when available. May or may not be
   *         different than the page name.
   */
  public String getTitle();

  /**
   * 
   * @return the content of a page
   */
  public String getContent();
}
