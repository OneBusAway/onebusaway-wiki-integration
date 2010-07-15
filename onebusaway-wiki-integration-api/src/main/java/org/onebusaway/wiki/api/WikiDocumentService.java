package org.onebusaway.wiki.api;

/**
 * Generic interface for retrieving content in the form of a {@link WikiPage}
 * from a back-end wiki or other content management system.
 * 
 * @author bdferris
 * @see WikiPage
 * @see WikiRenderingService
 */
public interface WikiDocumentService {

  /**
   * Retrieve a {@link WikiPage} with the specified namespace and name from a
   * back-end wiki or content management system implementation. Handling of
   * namespace and name combination in identifying a page will likely be
   * implementation specific.
   * 
   * @param namespace the namespace of the wiki page
   * @param name the name of the wiki page
   * @param forceRefresh a caching hint that indicates if the page should be
   *          refreshed completely if true, otherwise cached content may be
   *          returned
   * @return a page for the requested resource
   * @throws WikiException on error
   */
  public WikiPage getWikiPage(String namespace, String name,
      boolean forceRefresh) throws WikiException;
}
