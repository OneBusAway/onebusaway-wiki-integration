package org.onebusaway.wiki.xwiki.impl;

import org.onebusaway.wiki.api.WikiPage;

class XWikiContext {

  private static final ThreadLocal<XWikiContext> _context = new ThreadLocal<XWikiContext>();

  public static XWikiContext getContext() {
    return _context.get();
  }

  public static void setContext(XWikiContext context) {
    _context.set(context);
  }
  
  public static void resetContext() {
    _context.remove();
  }

  private final WikiPage _page;

  public XWikiContext(WikiPage page) {
    _page = page;
  }

  public WikiPage getPage() {
    return _page;
  }
}
