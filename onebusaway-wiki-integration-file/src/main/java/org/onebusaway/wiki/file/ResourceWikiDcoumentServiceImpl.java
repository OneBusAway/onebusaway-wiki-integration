package org.onebusaway.wiki.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.onebusaway.wiki.api.WikiAttachmentContent;
import org.onebusaway.wiki.api.WikiDocumentService;
import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.impl.WikiAttachmentContentImpl;
import org.onebusaway.wiki.api.impl.WikiPageImpl;

public abstract class ResourceWikiDcoumentServiceImpl implements
    WikiDocumentService {

  public static final String DEFAULT_CHARSET = "UTF-8";

  public static final String DEFAULT_EXTENSION = "wiki";

  protected String _extension = DEFAULT_EXTENSION;

  protected boolean _includeEmptyLocalePage = true;

  protected String _charsetName = DEFAULT_CHARSET;

  /**
   * Set the extension to use when looking for wiki files. Default is "
   * {@value #DEFAULT_EXTENSION}".
   * 
   * @param extension the new extension for wiki files
   */
  public void setExtension(String extension) {
    _extension = extension;
  }

  /**
   * By default, we attempt to look for a wiki page of the form
   * "Name_locale.extension", where "locale" matches the locale language code
   * specified in the call to
   * {@link ResourceWikiDcoumentServiceImpl#getWikiPage(String, String, Locale, boolean)}
   * . In the case where a file cannot be found matching the specified for the
   * specified locale, we also have the option of simply looking for a wiki page
   * of the form "Name.extension", but only if the "includeEmptyLocalePage"
   * parameter is set to true, which is the default behavior. To disable this
   * behavior, set "includeEmptyLocalePage" to false.
   * 
   * @param includeEmptyLocalePage
   */
  public void setIncludeEmptyLocalePage(boolean includeEmptyLocalePage) {
    _includeEmptyLocalePage = includeEmptyLocalePage;
  }

  /**
   * The {@link Charset} name we'll use for reading wiki files. The default is "
   * {@value ResourceWikiDcoumentServiceImpl#DEFAULT_CHARSET}".
   * 
   * @param charsetName
   */
  public void setCharsetName(String charsetName) {
    _charsetName = charsetName;
  }

  @Override
  public WikiPage getWikiPage(String namespace, String pageName, Locale locale,
      boolean forceRefresh) throws WikiException {

    List<Localized<String>> fullNames = getFullNames(pageName, null, locale);

    List<Localized<URL>> paths = null;

    try {
      paths = getResources(namespace, fullNames, locale);
    } catch (MalformedURLException ex) {
      throw new WikiException("error reading wiki page " + namespace + "/"
          + pageName, ex);
    }

    for (Localized<URL> localizedPath : paths) {
      Locale pathLocale = localizedPath.getLocale();
      URL path = localizedPath.getObject();
      try {
        return getUrlAsWikiPage(namespace, pageName, pathLocale, path);
      } catch (Exception ex) {
        throw new WikiException("error reading wiki page " + namespace + "/"
            + pageName + " from file " + path, ex);
      }
    }
    return null;
  }

  @Override
  public WikiAttachmentContent getWikiAttachmentContent(String namespace,
      String pageName, String name, Locale locale, boolean forceRefresh)
      throws WikiException {

    List<Localized<String>> fullNames = getFullNames(pageName, name, locale);

    List<Localized<URL>> paths = null;

    try {
      paths = getResources(namespace, fullNames, locale);
    } catch (MalformedURLException ex) {
      throw new WikiException("error reading wiki page " + namespace + "/"
          + name, ex);
    }

    for (Localized<URL> localizedPath : paths) {
      URL path = localizedPath.getObject();
      try {
        WikiAttachmentContentImpl content = new WikiAttachmentContentImpl();
        content.setNamespace(namespace);
        content.setPageName(pageName);
        content.setName(name);
        content.setLocale(localizedPath.getLocale());
        content.setContent(path.openStream());
        content.setContentType(getContentTypeForUrl(path));
        return content;
      } catch (Exception ex) {
        throw new WikiException("error reading wiki page " + namespace + "/"
            + name + " from file " + path, ex);
      }
    }
    return null;
  }

  protected List<Localized<String>> getFullNames(String pageName,
      String attachmentName, Locale locale) {

    String name = pageName;
    String extension = "";

    if (attachmentName != null)
      name += "-" + attachmentName;
    else if (_extension != null)
      extension += "." + _extension;

    List<Localized<String>> fullNames = new ArrayList<Localized<String>>();

    if (locale != null) {
      String fullName = name + "_" + locale.getLanguage() + extension;
      fullNames.add(Localized.create(fullName, locale));
    }

    if (_includeEmptyLocalePage) {
      String fullName = name + extension;
      fullNames.add(Localized.create(fullName, Locale.getDefault()));
    }
    return fullNames;
  }

  protected abstract List<Localized<URL>> getResources(String namespace,
      List<Localized<String>> fullNames, Locale locale)
      throws MalformedURLException;

  protected WikiPage getUrlAsWikiPage(String namespace, String name,
      Locale locale, URL resource) throws IOException, URISyntaxException {

    InputStream in = resource.openStream();
    InputStreamReader isReader = new InputStreamReader(in, _charsetName);
    BufferedReader reader = new BufferedReader(isReader);

    String line = null;
    StringBuilder b = new StringBuilder();

    while ((line = reader.readLine()) != null)
      b.append(line).append("\n");

    reader.close();

    WikiPageImpl page = new WikiPageImpl();
    page.setNamespace(namespace);
    page.setName(name);
    page.setLocale(locale);
    page.setTitle(name);
    page.setContent(b.toString());
    if (resource.getProtocol().equals("file")) {
      File file = new File(resource.toURI().toString());
      page.setLastModified(new Date(file.lastModified()));
    }

    return page;
  }

  protected String getContentTypeForUrl(URL url) {
    String path = url.getPath();
    int index = path.lastIndexOf('.');
    if (index == -1)
      return null;
    String extension = path.substring(index + 1).toLowerCase();
    if (extension.equals("png"))
      return "image/png";
    if (extension.equals("jpeg") || extension.equals("jpg"))
      return "image/jpeg";
    return null;
  }
}
