package org.onebusaway.wiki.api;

/**
 * Generic interface defining methods for rendering a {@link WikiPage} into a
 * form appropriate for display.
 * 
 * @author bdferris
 * @see WikiPage
 * @see WikiDocumentService
 */
public interface WikiRenderingService {

  /**
   * Render a {@link WikiPage} to a String representation, parsing any wiki
   * syntax as necessary to produce the final page.
   * 
   * @param page
   * @return
   * @throws WikiException
   */
  public String renderPage(WikiPage page) throws WikiException;

  /**
   * Where possible, produce an "edit link" that represents a URL/URI for
   * initiating an edit action on the specified WikiPage. If edit operations are
   * not available, this may return null.
   * 
   * @param page the wiki page to edit
   * @return an edit URL/URI for the page resource, or null if none is available
   */
  public String getEditLink(WikiPage page);
}
