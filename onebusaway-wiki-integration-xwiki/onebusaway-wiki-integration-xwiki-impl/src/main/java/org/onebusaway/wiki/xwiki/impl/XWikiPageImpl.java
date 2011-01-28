package org.onebusaway.wiki.xwiki.impl;

import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.impl.WikiPageImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * An XWiki specific {@link WikiPage} implementation.
 * 
 * @author Vincent Privat
 * @see WikiPage
 */
public class XWikiPageImpl extends WikiPageImpl {

  private static final long serialVersionUID = 2L;

  private String defaultLanguage;

  private String language;
  
  private Map<String, XWikiPageTranslation> translations;

  public XWikiPageImpl() {
    this.translations = new HashMap<String, XWikiPageTranslation>();
  }

  public String getDefaultLanguage() {
    return defaultLanguage;
  }

  public void setDefaultLanguage(String defaultLanguage) {
    this.defaultLanguage = defaultLanguage;
    if (language == null || language.isEmpty()) {
      setLocale(new Locale(defaultLanguage));
    }
  }
  
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
    if (language != null && !language.isEmpty()) {
      setLocale(new Locale(language));
    }
  }
  
  public void addTranslation(XWikiPageTranslation translation) {
    this.translations.put(translation.getLanguage(), translation);
  }
  
  public XWikiPageTranslation findTranslation(String language) {
    return this.translations.get(language);
  }
  
  public Iterator<XWikiPageTranslation> getTranslations() {
    return this.translations.values().iterator();
  }
}
