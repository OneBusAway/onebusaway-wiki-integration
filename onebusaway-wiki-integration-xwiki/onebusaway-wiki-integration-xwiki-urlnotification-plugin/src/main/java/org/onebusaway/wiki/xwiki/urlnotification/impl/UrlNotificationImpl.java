package org.onebusaway.wiki.xwiki.urlnotification.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.onebusaway.wiki.xwiki.urlnotification.api.UrlNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.SpaceReference;
import org.xwiki.observation.EventListener;
import org.xwiki.observation.ObservationManager;
import org.xwiki.observation.event.DocumentDeleteEvent;
import org.xwiki.observation.event.DocumentSaveEvent;
import org.xwiki.observation.event.DocumentUpdateEvent;
import org.xwiki.observation.event.Event;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.web.Utils;

public class UrlNotificationImpl implements UrlNotification, EventListener {

  private static final Logger _log = LoggerFactory.getLogger(UrlNotificationImpl.class);

  /**
   * The name of the listener.
   */
  private static final String LISTENER_NAME = "urlnotification";

  /**
   * The events to match.
   */
  private static final List<Event> LISTENER_EVENTS = new ArrayList<Event>() {
    private static final long serialVersionUID = 1L;
    {
      add(new DocumentSaveEvent());
      add(new DocumentUpdateEvent());
      add(new DocumentDeleteEvent());
    }
  };

  private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]+)\\}");

  private List<String> _urls = new ArrayList<String>();

  private int _numberOfThreads = 1;

  private ExecutorService _executor;

  public void addUrl(String url) {
    _log.debug("adding notification url: " + url);
    _urls.add(url);
  }

  public void setNumberOfThreads(int numberOfThreads) {
    _numberOfThreads = numberOfThreads;
  }

  public void init(XWikiContext context) throws XWikiException {

    ObservationManager observationManager = Utils.getComponent(ObservationManager.class);

    if (observationManager.getListener(getName()) == null)
      observationManager.addListener(this);

    _executor = Executors.newFixedThreadPool(_numberOfThreads);
  }

  /****
   * {@link EventListener} Interface
   ****/

  public List<Event> getEvents() {
    return LISTENER_EVENTS;
  }

  public String getName() {
    return LISTENER_NAME;
  }

  public void onEvent(Event event, Object source, Object data) {

    XWikiDocument document = (XWikiDocument) source;

    for (String url : _urls) {
      try {
        url = updateUrl(url, document);
        _log.debug("sending notification url: {}",url);
        URL parsedUrl = new URL(url);
        _executor.execute(new UrlFetcher(parsedUrl));
      } catch (Exception ex) {
        _log.warn("error parsing and updating url " + url, ex);
      }
    }
  }

  protected String updateUrl(String url, XWikiDocument document)
      throws UnsupportedEncodingException {

    DocumentReference df = document.getDocumentReference();
    Matcher m = _pattern.matcher(url);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      String name = m.group(1);
      String value = "";
      if (name.equals("name")) {
        value = df.getName();
      } else if (name.equals("space")) {
        SpaceReference space = df.getLastSpaceReference();
        value = space.getName();
      }
      m.appendReplacement(sb, URLEncoder.encode(value, "UTF-8"));
    }
    m.appendTail(sb);
    return sb.toString();
  }

  private static class UrlFetcher implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;

    private URL _url;

    public UrlFetcher(URL url) {
      _url = url;
    }

    @Override
    public void run() {
      InputStream in = null;
      try {
        in = _url.openStream();
        byte[] buffer = new byte[1024];
        while (true) {
          int rc = in.read(buffer);
          if (rc == -1)
            break;
        }
        in.close();
      } catch (IOException ex) {
        _log.warn("error fetching url: " + _url, ex);
      } finally {
        if (in != null)
          try {
            in.close();
          } catch (IOException e) {
          }
      }
    }
  }
}
