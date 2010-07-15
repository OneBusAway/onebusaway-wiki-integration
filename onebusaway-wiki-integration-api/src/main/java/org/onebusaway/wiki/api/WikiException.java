package org.onebusaway.wiki.api;

/**
 * Base extension thrown by wiki operations.
 * 
 * @author bdferris
 * @see WikiDocumentService
 * @see WikiRenderingService
 */
public class WikiException extends Exception {

  private static final long serialVersionUID = 1L;

  public WikiException(String message) {
    super(message);
  }

  public WikiException(String message, Throwable cause) {
    super(message, cause);
  }

  public WikiException(Throwable cause) {
    super(cause);
  }
}
