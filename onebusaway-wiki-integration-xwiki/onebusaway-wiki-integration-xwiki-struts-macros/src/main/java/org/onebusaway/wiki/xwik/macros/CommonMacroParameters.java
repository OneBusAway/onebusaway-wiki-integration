package org.onebusaway.wiki.xwik.macros;

/**
 * Common macro parameters. Mostly used to determine if the body of an Apache
 * Struts 2 tag/component should be rendered using wiki syntax.
 * 
 * @author bdferris
 */
public class CommonMacroParameters {

  private boolean wiki = true;

  public boolean isWiki() {
    return wiki;
  }

  public void setWiki(boolean wiki) {
    this.wiki = wiki;
  }
}
