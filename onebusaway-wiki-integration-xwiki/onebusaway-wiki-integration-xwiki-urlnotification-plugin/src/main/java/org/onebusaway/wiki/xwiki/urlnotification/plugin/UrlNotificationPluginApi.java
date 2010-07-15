package org.onebusaway.wiki.xwiki.urlnotification.plugin;

import org.onebusaway.wiki.xwiki.urlnotification.api.UrlNotification;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.plugin.PluginApi;

/**
 * Defines the scripting api for the {@link UrlNotificationPlugin}. Except we
 * don't really provide a scripting interface, so this is pretty much empty.
 * 
 * @author bdferris
 * @see UrlNotificationPlugin
 */
public class UrlNotificationPluginApi extends PluginApi<UrlNotificationPlugin> {

  public UrlNotificationPluginApi(UrlNotificationPlugin plugin,
      XWikiContext context) {
    super(plugin, context);
  }

  /**
   * @return The {@link UrlNotification} component to use inside the API
   */
  protected UrlNotification getUrlNotification() {
    return ((UrlNotificationPlugin) getProtectedPlugin()).getUrlNotification();
  }
}