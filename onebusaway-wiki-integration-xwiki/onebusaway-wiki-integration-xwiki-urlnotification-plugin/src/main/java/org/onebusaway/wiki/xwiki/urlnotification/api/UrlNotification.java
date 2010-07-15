package org.onebusaway.wiki.xwiki.urlnotification.api;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;

/**
 * Defines the url notification interface. Very simple. Just supports
 * initialization at the moment.
 * 
 * @author bdferris
 */
public interface UrlNotification {
  /**
   * Init method, must be called on plugin init.
   * 
   * @param context the XWiki context
   * @throws XWikiException if the init of the activity stream failed
   */
  void init(XWikiContext context) throws XWikiException;
}
