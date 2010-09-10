package org.onebusaway.wiki.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.onebusaway.wiki.api.WikiDocumentService;
import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.impl.WikiPageImpl;

public class FileWikiDocumentServiceImpl implements WikiDocumentService {

  private File _documuentDirectory;

  private String _extension = "wiki";

  public void setDocumentDirectory(File documentDirectory) {
    _documuentDirectory = documentDirectory;
  }

  public void setExtension(String extension) {
    _extension = extension;
  }

  @Override
  public WikiPage getWikiPage(String namespace, String name,
      boolean forceRefresh) throws WikiException {

    List<File> paths = getFiles(namespace, name);

    for (File path : paths) {
      if (!path.exists())
        continue;
      try {
        return getFileAsWikiPage(namespace, name, path);
      } catch (IOException ex) {
        throw new WikiException("error reading wiki page " + namespace + "/"
            + name + " from file " + path, ex);
      }
    }
    return null;
  }

  private List<File> getFiles(String namespace, String name) {
    List<File> paths = new ArrayList<File>();
    String extension = "";
    if (_extension != null)
      extension = "." + _extension;
    if (_documuentDirectory != null) {
      if (namespace != null)
        paths.add(new File(_documuentDirectory, namespace + File.separator
            + name + extension));
      paths.add(new File(_documuentDirectory, name + extension));
    }
    return paths;
  }

  private WikiPage getFileAsWikiPage(String namespace, String name, File path)
      throws IOException {

    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line = null;
    StringBuilder b = new StringBuilder();

    while ((line = reader.readLine()) != null)
      b.append(line).append("\n");

    reader.close();

    WikiPageImpl page = new WikiPageImpl();
    page.setNamespace(namespace);
    page.setName(name);
    page.setTitle(name);
    page.setContent(b.toString());
    return page;
  }
}
