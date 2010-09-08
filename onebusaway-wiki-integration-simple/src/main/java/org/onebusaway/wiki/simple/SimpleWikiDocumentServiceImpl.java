package org.onebusaway.wiki.simple;

import org.onebusaway.wiki.api.WikiDocumentService;
import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.impl.WikiPageImpl;

public class SimpleWikiDocumentServiceImpl implements WikiDocumentService {

  @Override
  public WikiPage getWikiPage(String namespace, String name,
      boolean forceRefresh) throws WikiException {
    WikiPageImpl page = new WikiPageImpl();
    page.setContent("content:" + namespace + "/" + name);
    page.setName(name);
    page.setNamespace(namespace);
    page.setTitle("title:" + namespace + "/" + name);
    return page;
  }
}
