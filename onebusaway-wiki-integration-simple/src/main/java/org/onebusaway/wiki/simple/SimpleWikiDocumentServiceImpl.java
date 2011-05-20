package org.onebusaway.wiki.simple;

import java.util.Date;
import java.util.Locale;

import org.onebusaway.wiki.api.WikiAttachmentContent;
import org.onebusaway.wiki.api.WikiDocumentService;
import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.impl.WikiPageImpl;

public class SimpleWikiDocumentServiceImpl implements WikiDocumentService {

  @Override
  public WikiPage getWikiPage(String namespace, String name, Locale locale,
      boolean forceRefresh) throws WikiException {
    WikiPageImpl page = new WikiPageImpl();
    page.setContent("content:" + namespace + "/" + name);
    page.setName(name);
    page.setLocale(locale);
    page.setNamespace(namespace);
    page.setTitle("title:" + namespace + "/" + name);
    page.setLastModified(new Date());
    return page;
  }

  @Override
  public WikiAttachmentContent getWikiAttachmentContent(String namespace,
      String pageName, String name, Locale locale, boolean forceRefresh)
      throws WikiException {
    throw new UnsupportedOperationException();
  }
}
