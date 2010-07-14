package org.onebusaway.wiki.xwiki.urlnotification.api;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;

/**
 * Manages the activity stream.
 * 
 * @version $Id: ActivityStream.java 24078 2009-09-27 02:37:41Z sdumitriu $
 */
public interface UrlNotification
{
    /**
     * Init method, must be called on plugin init.
     * 
     * @param context the XWiki context
     * @throws XWikiException if the init of the activity stream failed
     */
    void init(XWikiContext context) throws XWikiException;
}
