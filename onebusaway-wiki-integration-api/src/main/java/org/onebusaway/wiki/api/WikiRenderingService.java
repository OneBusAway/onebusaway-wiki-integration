package org.onebusaway.wiki.api;

public interface WikiRenderingService {

  public String renderPage(WikiPage page) throws WikiException;

  public String getEditLink(WikiPage page);
}
