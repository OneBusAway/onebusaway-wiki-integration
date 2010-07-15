package org.onebusaway.wiki.xwik.macros;

import org.apache.struts2.components.If;
import org.xwiki.component.annotation.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * XWiki Macro implementation of a Struts &lt;s:if test=""&gt;...&lt;/s:if&gt;
 * tag.
 * 
 * @author bdferris
 * @see StrutsIfMacroParameters
 */
@Component("struts_if")
public class StrutsIfMacro extends
    AbstractStrutsTagMacro<StrutsIfMacroParameters, If> {

  private static final String DESCRIPTION = "Struts If Tag Macro";

  public StrutsIfMacro() {
    super("Struts If", DESCRIPTION, StrutsIfMacroParameters.class);
  }

  public boolean supportsInlineMode() {
    return true;
  }

  @Override
  protected If getBean(ValueStack stack) {
    return new If(stack);
  }

  @Override
  protected void populateParams(If component, StrutsIfMacroParameters params) {
    super.populateParams(component, params);
    component.setTest(params.getTest());
  }
}
