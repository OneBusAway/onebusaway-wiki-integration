package org.onebusaway.wiki.xwiki.urlnotification.plugin;

import org.onebusaway.wiki.xwiki.urlnotification.api.UrlNotification;
import org.onebusaway.wiki.xwiki.urlnotification.impl.UrlNotificationImpl;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.api.Api;
import com.xpn.xwiki.plugin.XWikiDefaultPlugin;
import com.xpn.xwiki.plugin.XWikiPluginInterface;

public class UrlNotificationPlugin extends XWikiDefaultPlugin {

  /**
   * Name of the plugin.
   */
  public static final String PLUGIN_NAME = "urlnotification";

  /**
   * We should user inversion of control instead.
   */
  private UrlNotification urlNotification;

  /**
   * Constructor.
   * 
   * @see XWikiDefaultPlugin#XWikiDefaultPlugin(String,String,com.xpn.xwiki.XWikiContext)
   * @param name name of the plugin
   * @param className class name of the plugin
   * @param context the XWiki context
   */
  public UrlNotificationPlugin(String name, String className,
      XWikiContext context) {
    super(name, className, context);

    UrlNotificationImpl impl = new UrlNotificationImpl();

    String urls = getUrlNotificationPreference("urls", "", context);
    urls = urls.trim();
    if (urls.length() > 0) {
      for (String url : urls.split(",")) {
        url = url.trim();
        if (url.length() == 0)
          continue;
        impl.addUrl(url);
      }
    }

    setUrlNotification(impl);
  }

  /**
   * {@inheritDoc}
   * 
   * @see XWikiDefaultPlugin#getName()
   */
  public String getName() {
    return PLUGIN_NAME;
  }

  /**
   * {@inheritDoc}
   * 
   * @see XWikiDefaultPlugin#getPluginApi
   */
  public Api getPluginApi(XWikiPluginInterface plugin, XWikiContext context) {
    return new UrlNotificationPluginApi((UrlNotificationPlugin) plugin, context);
  }

  /**
   * @return The {@link UrlNotification} component used in behind by this
   *         plug-in instance
   */
  public UrlNotification getUrlNotification() {
    return urlNotification;
  }

  public void setUrlNotification(UrlNotification urlNotification) {
    this.urlNotification = urlNotification;
  }

  /**
   * Get a preference for the urlnotification from the XWiki configuration.
   * 
   * @param preference Name of the preference to get the value from
   * @param defaultValue Default value if the preference is not found in the
   *          configuration
   * @param context the XWiki context
   * @return value for the given preference
   */
  public String getUrlNotificationPreference(String preference,
      String defaultValue, XWikiContext context) {
    String preferencePrefix = "xwiki.plugin.urlnotification.";
    String prefName = preferencePrefix + preference;
    return context.getWiki().getXWikiPreference(prefName, prefName,
        defaultValue, context);
  }

  /**
   * {@inheritDoc}
   * 
   * @see XWikiDefaultPlugin#init(XWikiContext)
   */
  public void init(XWikiContext context) {
    super.init(context);
    try {
      urlNotification.init(context);
    } catch (Exception e) {
      // Do nothing.
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see XWikiDefaultPlugin#virtualInit(XWikiContext)
   */
  public void virtualInit(XWikiContext context) {
    super.virtualInit(context);
  }
}
