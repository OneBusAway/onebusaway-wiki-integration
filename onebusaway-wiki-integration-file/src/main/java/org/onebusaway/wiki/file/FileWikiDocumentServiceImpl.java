package org.onebusaway.wiki.file;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FileWikiDocumentServiceImpl extends
    ResourceWikiDcoumentServiceImpl {

  private File _documentDirectory;

  /**
   * Set the root document directory where we'll look for wiki files
   * 
   * @param documentDirectory
   */
  public void setDocumentDirectory(File documentDirectory) {
    _documentDirectory = documentDirectory;
  }

  @Override
  protected List<Localized<URL>> getResources(String namespace,
      List<Localized<String>> fullNames, Locale locale)
      throws MalformedURLException {

    if (_documentDirectory == null)
      return Collections.emptyList();

    List<Localized<URL>> paths = new ArrayList<Localized<URL>>();

    for (Localized<String> fullName : fullNames) {
      if (namespace != null) {
        File path = new File(_documentDirectory, namespace + File.separator
            + fullName.getObject());
        if (path.exists())
          paths.add(Localized.create(path.toURI().toURL(), fullName.getLocale()));
      }

      File path = new File(_documentDirectory, fullName.getObject());
      if (path.exists())
        paths.add(Localized.create(path.toURI().toURL(), fullName.getLocale()));
    }

    return paths;
  }
}
