package org.onebusaway.wiki.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Test;
import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;

public class FileWikiDocumentServiceImplTest {

  @Test
  public void test() throws WikiException {

    FileWikiDocumentServiceImpl service = new FileWikiDocumentServiceImpl();
    service.setDocumentDirectory(new File(
        "src/test/resources/org/onebusaway/wiki/file"));

    WikiPage page = service.getWikiPage(null, "Test", false);
    assertEquals(null, page.getNamespace());
    assertEquals("Test", page.getName());
    assertEquals("Test", page.getTitle());
    assertEquals("This is a test.\n\nIt has multiple lines.\n",
        page.getContent());

    page = service.getWikiPage("Main", "SomeName", false);
    assertEquals("Main", page.getNamespace());
    assertEquals("SomeName", page.getName());
    assertEquals("SomeName", page.getTitle());
    assertEquals("This is a wiki entry.\n", page.getContent());

    page = service.getWikiPage(null, "SomeName", false);
    assertNull(page);

    page = service.getWikiPage(null, "Test2", false);
    assertNull(page);

    page = service.getWikiPage("DoesNotExist", "Test2", false);
    assertNull(page);
  }

  @Test
  public void testExtension() throws WikiException {

    FileWikiDocumentServiceImpl service = new FileWikiDocumentServiceImpl();
    service.setDocumentDirectory(new File(
        "src/test/resources/org/onebusaway/wiki/file"));
    service.setExtension("txt");

    WikiPage page = service.getWikiPage(null, "Test", false);
    assertNull(page);

    page = service.getWikiPage("Main", "SomeName", false);
    assertEquals("Main", page.getNamespace());
    assertEquals("SomeName", page.getName());
    assertEquals("SomeName", page.getTitle());
    assertEquals("This is a wiki entry.\n\nIt has a different extension.\n",
        page.getContent());
  }
}
