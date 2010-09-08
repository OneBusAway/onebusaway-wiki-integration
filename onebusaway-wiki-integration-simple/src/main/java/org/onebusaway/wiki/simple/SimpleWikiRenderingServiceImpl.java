package org.onebusaway.wiki.simple;

import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.WikiRenderingService;

public class SimpleWikiRenderingServiceImpl implements WikiRenderingService {

  @Override
  public String renderPage(WikiPage page) throws WikiException {
    return page.getContent();
  }

  @Override
  public String getEditLink(WikiPage page) {
    return null;
  }
}
