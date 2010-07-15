package org.onebusaway.wiki.xwik.macros;

import org.apache.struts2.components.Property;
import org.xwiki.component.annotation.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * XWiki Macro implementation of a Struts &lt;s:property value="..." /&gt;
 * tag.
 * 
 * @author bdferris
 * @see StrutsPropertyMacroParameters
 */
@Component("struts_property")
public class StrutsPropertyMacro extends AbstractStrutsTagMacro<StrutsPropertyMacroParameters,Property> {

  private static final String DESCRIPTION = "Struts Property Tag Macro";

  public StrutsPropertyMacro() {
    super("Struts Property", DESCRIPTION, StrutsPropertyMacroParameters.class);
  }

  public boolean supportsInlineMode() {
    return true;
  }

  @Override
  protected Property getBean(ValueStack stack) {
    return new Property(stack);
  }

  @Override
  protected void populateParams(Property component,
      StrutsPropertyMacroParameters params) {
    super.populateParams(component, params);
    component.setDefault(params.getDefault());
    component.setValue(params.getValue());
    component.setEscape(params.isEscape());
    component.setEscapeJavaScript(params.isEscapeJavaScript());
  }
}
