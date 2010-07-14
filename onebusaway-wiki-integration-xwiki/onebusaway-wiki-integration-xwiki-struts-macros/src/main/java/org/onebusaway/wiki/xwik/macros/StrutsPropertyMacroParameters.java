package org.onebusaway.wiki.xwik.macros;

/**
 * Parameters for the
 * {@link org.onebusaway.tcc_study.webapp.impl.wiki.macros.StrutsPropertyMacro}
 * Macro.
 */
public class StrutsPropertyMacroParameters extends CommonMacroParameters {

  private String value;

  private String defaultValue;

  private boolean escape;

  private boolean escapeJavaScript;

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDefault() {
    return this.defaultValue;
  }

  public void setDefault(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public boolean isEscape() {
    return escape;
  }

  public void setEscape(boolean escape) {
    this.escape = escape;
  }

  public boolean isEscapeJavaScript() {
    return escapeJavaScript;
  }

  public void setEscapeJavaScript(boolean escapeJavaScript) {
    this.escapeJavaScript = escapeJavaScript;
  }
}
