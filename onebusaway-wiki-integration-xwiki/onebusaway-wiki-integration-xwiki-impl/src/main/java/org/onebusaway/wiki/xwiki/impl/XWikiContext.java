package org.onebusaway.wiki.xwiki.impl;

import org.onebusaway.wiki.api.WikiPage;

/**
 * A simple {@link ThreadLocal} context class that allow us to push XWiki
 * context information, including the currently rendering page, onto a thread
 * local stack. We use the current page when rendering urls and other
 * information.
 * 
 * @author bdferris
 * @see XWikiModelImpl
 * @see XWikiRenderingServiceImpl
 */
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
