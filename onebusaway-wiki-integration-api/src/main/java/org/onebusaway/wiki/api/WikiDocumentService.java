package org.onebusaway.wiki.api;

import java.util.Locale;

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
   * Retrieve a {@link WikiPage} with the specified namespace, name and locale
   * from a back-end wiki or content management system implementation. Handling
   * of namespace and name combination in identifying a page will likely be
   * implementation specific.
   * 
   * @param namespace the namespace of the wiki page
   * @param name the name of the wiki page
   * @param locale the locale of the wiki page
   * @param forceRefresh a caching hint that indicates if the page should be
   *          refreshed completely if true, otherwise cached content may be
   *          returned
   * @return a page for the requested resource, or null if not found
   * @throws WikiException on error
   */
  public WikiPage getWikiPage(String namespace, String name, Locale locale,
      boolean forceRefresh) throws WikiException;

  /**
   * Optionally supported method to retrieve the content of an attachment with
   * the specified namespace, page name, name and locale from a back-end wiki or
   * content management system implementation. Handling of namespace, page name,
   * and name combination in identifying a page will likely be implementation
   * specific.
   * 
   * @param namespace the namespace of the wiki page
   * @param pageName the name of the wiki page
   * @param name the name of the attachment
   * @param locale the locale of the attachment
   * @param forceRefresh a caching hint that indicates if the attachment should
   *          be refreshed completely if true, otherwise cached content may be
   *          returned
   * @return the content for the requested resource, or null if not found
   * @throws WikiException on error
   */
  public WikiAttachmentContent getWikiAttachmentContent(String namespace,
      String pageName, String name, Locale locale, boolean forceRefresh)
      throws WikiException;
}
