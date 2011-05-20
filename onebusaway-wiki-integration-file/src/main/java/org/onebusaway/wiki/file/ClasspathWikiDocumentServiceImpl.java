package org.onebusaway.wiki.file;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ClasspathWikiDocumentServiceImpl extends
    ResourceWikiDcoumentServiceImpl {

  private String _packageRoot;

  /**
   * Set the root classpath package where we'll look for wiki resources
   * 
   * @param packageRot
   */
  public void setPackageRoot(String packageRot) {
    _packageRoot = packageRot.replace('.', '/');
  }

  @Override
  protected List<Localized<URL>> getResources(String namespace,
      List<Localized<String>> fullNames, Locale locale) {

    if (_packageRoot == null)
      return Collections.emptyList();

    List<Localized<URL>> paths = new ArrayList<Localized<URL>>();

    for (Localized<String> fullName : fullNames) {

      if (namespace != null) {
        String path = _packageRoot + "/" + namespace + "/"
            + fullName.getObject();
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource != null)
          paths.add(Localized.create(resource, fullName.getLocale()));
      }

      String path = _packageRoot + "/" + fullName.getObject();
      URL resource = getClass().getClassLoader().getResource(path);
      if (resource != null)
        paths.add(Localized.create(resource, fullName.getLocale()));
    }

    return paths;
  }
}
