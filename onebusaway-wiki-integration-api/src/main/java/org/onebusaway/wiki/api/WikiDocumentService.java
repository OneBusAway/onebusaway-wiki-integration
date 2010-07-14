package org.onebusaway.wiki.api;

public interface WikiDocumentService {
  public WikiPage getWikiPage(String namespace, String name,
      boolean forceRefresh) throws WikiException;
}
