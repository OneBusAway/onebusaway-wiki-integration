package org.onebusaway.wiki.xwiki.impl;

/**
 * An XWiki page translation.
 * 
 * @author Vincent Privat
 * @see XWikiPageImpl
 */
public class XWikiPageTranslation {
  
  private String language;
  
  private String url;
  
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
  
  public String getURL() {
    return url;
  }

  public void setURL(String url) {
    this.url = url;
  }
  
  public void addLink(XWikiPageLink link) {
    if (link.getRel().equals("http://www.xwiki.org/rel/page")) {
      setURL(link.getHref());
    }
  }
}
