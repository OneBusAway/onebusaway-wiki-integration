package org.onebusaway.wiki.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Locale;

import org.junit.Test;
import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;

public class FileWikiDocumentServiceImplTest {

  @Test
  public void test() throws WikiException {

    FileWikiDocumentServiceImpl service = new FileWikiDocumentServiceImpl();
    service.setDocumentDirectory(new File(
        "src/test/resources/org/onebusaway/wiki/file"));

    WikiPage page = service.getWikiPage(null, "Test", Locale.ENGLISH, false);
    assertEquals(null, page.getNamespace());
    assertEquals("Test", page.getName());
    assertEquals(Locale.ENGLISH, page.getLocale());
    assertEquals("Test", page.getTitle());
    assertEquals("This is a test.\n\nIt has multiple lines.\n",
        page.getContent());

    page = service.getWikiPage(null, "Test", Locale.FRENCH, false);
    assertEquals(null, page.getNamespace());
    assertEquals("Test", page.getName());
    assertEquals(Locale.FRENCH, page.getLocale());
    assertEquals("Test", page.getTitle());
    assertEquals("C'est une page de test.\n\nAvec plusieurs lignes.\n",
        page.getContent());

    /**
     * There is no JAPANESE locale defined. However, we have
     * "includeEmptyLocalePage" set to true by default, so the contents of
     * Test.wiki will be returned.
     */
    page = service.getWikiPage(null, "Test", Locale.JAPANESE, false);
    assertEquals(null, page.getNamespace());
    assertEquals("Test", page.getName());
    assertEquals(Locale.getDefault(), page.getLocale());
    assertEquals("Test", page.getTitle());
    assertEquals(
        "This is a test for the default locale.\n\nIt has multiple lines.\n",
        page.getContent());

    /**
     * Now we're going to turn off the "includeEmptyLocalePage" behavior, so
     * nothing should match.
     */
    service.setIncludeEmptyLocalePage(false);
    page = service.getWikiPage(null, "Test", Locale.JAPANESE, false);
    assertNull(page);
    service.setIncludeEmptyLocalePage(true);

    page = service.getWikiPage("Main", "SomeName", Locale.ENGLISH, false);
    assertEquals("Main", page.getNamespace());
    assertEquals("SomeName", page.getName());
    assertEquals("SomeName", page.getTitle());
    assertEquals(Locale.ENGLISH, page.getLocale());
    assertEquals("This is a wiki entry.\n", page.getContent());

    page = service.getWikiPage("Main", "SomeName", Locale.FRENCH, false);
    assertEquals("Main", page.getNamespace());
    assertEquals("SomeName", page.getName());
    assertEquals("SomeName", page.getTitle());
    assertEquals(Locale.FRENCH, page.getLocale());
    assertEquals("C'est une entrée de wiki.\n", page.getContent());

    /**
     * There is no JAPANESE locale defined. However, we have
     * "includeEmptyLocalePage" set to true by default, so the contents of
     * Test.wiki will be returned.
     */
    page = service.getWikiPage("Main", "SomeName", Locale.JAPANESE, false);
    assertEquals("Main", page.getNamespace());
    assertEquals("SomeName", page.getName());
    assertEquals("SomeName", page.getTitle());
    assertEquals(Locale.getDefault(), page.getLocale());
    assertEquals("This is a wiki entry for the default locale.\n",
        page.getContent());

    /**
     * Now we're going to turn off the "includeEmptyLocalePage" behavior, so
     * nothing should match.
     */
    service.setIncludeEmptyLocalePage(false);
    page = service.getWikiPage("Main", "SomeName", Locale.JAPANESE, false);
    assertNull(page);
    service.setIncludeEmptyLocalePage(true);

    page = service.getWikiPage(null, "SomeName", Locale.ENGLISH, false);
    assertNull(page);

    page = service.getWikiPage(null, "SomeName", Locale.FRENCH, false);
    assertNull(page);

    page = service.getWikiPage(null, "Test2", Locale.ENGLISH, false);
    assertNull(page);

    page = service.getWikiPage(null, "Test2", Locale.FRENCH, false);
    assertNull(page);

    page = service.getWikiPage("DoesNotExist", "Test2", Locale.ENGLISH, false);
    assertNull(page);

    page = service.getWikiPage("DoesNotExist", "Test2", Locale.FRENCH, false);
    assertNull(page);
  }

  @Test
  public void testExtension() throws WikiException {

    FileWikiDocumentServiceImpl service = new FileWikiDocumentServiceImpl();
    service.setDocumentDirectory(new File(
        "src/test/resources/org/onebusaway/wiki/file"));
    service.setExtension("txt");

    WikiPage page = service.getWikiPage(null, "Test", Locale.ENGLISH, false);
    assertNull(page);

    page = service.getWikiPage(null, "Test", Locale.FRENCH, false);
    assertNull(page);

    page = service.getWikiPage("Main", "SomeName", Locale.ENGLISH, false);
    assertEquals("Main", page.getNamespace());
    assertEquals("SomeName", page.getName());
    assertEquals("SomeName", page.getTitle());
    assertEquals("This is a wiki entry.\n\nIt has a different extension.\n",
        page.getContent());
  }
}
