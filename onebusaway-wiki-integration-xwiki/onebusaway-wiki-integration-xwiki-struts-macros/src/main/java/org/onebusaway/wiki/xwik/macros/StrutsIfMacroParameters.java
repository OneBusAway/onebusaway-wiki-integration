package org.onebusaway.wiki.xwik.macros;

import org.xwiki.properties.annotation.PropertyMandatory;

/**
 * Parameters for the
 * {@link org.onebusaway.tcc_study.webapp.impl.wiki.macros.StrutsPropertyMacro}
 * Macro.
 */
public class StrutsIfMacroParameters extends CommonMacroParameters {

  private String test;

  public String getTest() {
    return test;
  }

  @PropertyMandatory
  public void setTest(String test) {
    this.test = test;
  }
}
