package org.onebusaway.wiki.api;

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
