package org.onebusaway.wiki.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.onebusaway.wiki.api.WikiDocumentService;
import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.impl.WikiPageImpl;

public class FileWikiDocumentServiceImpl implements WikiDocumentService {

  public static final String DEFAULT_CHARSET = "UTF-8";

  public static final String DEFAULT_EXTENSION = "wiki";

  private File _documentDirectory;

  private String _extension = DEFAULT_EXTENSION;

  private boolean _includeEmptyLocalePage = true;

  private String _charsetName = DEFAULT_CHARSET;

  /**
   * Set the root document directory where we'll look for wiki files
   * 
   * @param documentDirectory
   */
  public void setDocumentDirectory(File documentDirectory) {
    _documentDirectory = documentDirectory;
  }

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
   * {@link FileWikiDocumentServiceImpl#getWikiPage(String, String, Locale, boolean)}
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
   * {@value FileWikiDocumentServiceImpl#DEFAULT_CHARSET}".
   * 
   * @param charsetName
   */
  public void setCharsetName(String charsetName) {
    _charsetName = charsetName;
  }

  @Override
  public WikiPage getWikiPage(String namespace, String name, Locale locale,
      boolean forceRefresh) throws WikiException {

    List<Localized<File>> paths = getFiles(namespace, name, locale);

    for (Localized<File> localizedPath : paths) {
      Locale pathLocale = localizedPath.getLocale();
      File path = localizedPath.getObject();
      if (!path.exists())
        continue;
      try {
        return getFileAsWikiPage(namespace, name, pathLocale, path);
      } catch (IOException ex) {
        throw new WikiException("error reading wiki page " + namespace + "/"
            + name + " from file " + path, ex);
      }
    }
    return null;
  }

  private List<Localized<File>> getFiles(String namespace, String name,
      Locale locale) {

    if (_documentDirectory == null)
      return Collections.emptyList();

    String extension = "";

    if (_extension != null)
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

    List<Localized<File>> paths = new ArrayList<Localized<File>>();

    for (Localized<String> fullName : fullNames) {
      if (namespace != null) {
        File path = new File(_documentDirectory, namespace + File.separator
            + fullName.getObject());
        paths.add(Localized.create(path, fullName.getLocale()));
      }

      File path = new File(_documentDirectory, fullName.getObject());
      paths.add(Localized.create(path, fullName.getLocale()));
    }

    return paths;
  }

  private WikiPage getFileAsWikiPage(String namespace, String name,
      Locale locale, File path) throws IOException {

    FileInputStream in = new FileInputStream(path);
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
    page.setLastModified(new Date(path.lastModified()));
    return page;
  }

  private static class Localized<T> {

    private final T object;

    private final Locale locale;

    public static <T> Localized<T> create(T object, Locale locale) {
      return new Localized<T>(object, locale);
    }

    public Localized(T object, Locale locale) {
      this.object = object;
      this.locale = locale;
    }

    public T getObject() {
      return object;
    }

    public Locale getLocale() {
      return locale;
    }
  }
}
