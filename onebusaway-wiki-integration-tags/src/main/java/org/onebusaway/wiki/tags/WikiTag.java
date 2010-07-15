package org.onebusaway.wiki.tags;

import java.io.IOException;
import java.io.Writer;
import java.net.UnknownHostException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.onebusaway.wiki.api.WikiDocumentService;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.WikiRenderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WikiTag extends BodyTagSupport {

  private static final long serialVersionUID = 1L;

  private WikiDocumentService _wikiDocumentService;

  private WikiRenderingService _wikiRenderingService;

  protected String _namespace = "Main";

  protected String _name;

  @Autowired
  public void setWikiDocumentService(WikiDocumentService wikiDocumentService) {
    _wikiDocumentService = wikiDocumentService;
  }

  @Autowired
  public void setWikiRenderingService(WikiRenderingService wikiRenderingService) {
    _wikiRenderingService = wikiRenderingService;
  }

  public void setNamespace(String namespace) {
    _namespace = namespace;
  }

  public void setName(String name) {
    _name = name;
  }

  @Override
  public int doStartTag() throws JspException {
    WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
    context.getAutowireCapableBeanFactory().autowireBean(this);
    return SKIP_BODY;
  }

  @Override
  public int doEndTag() throws JspException {

    Writer writer = pageContext.getOut();

    if (_namespace != null && _name != null) {
      try {
        WikiPage page = _wikiDocumentService.getWikiPage(_namespace, _name,
            false);
        if (page != null) {
          String content = _wikiRenderingService.renderPage(page);
          writer.write(content);
        }
      } catch (UnknownHostException ex) {
        try {
          writer.write("error connecting to " + ex.getMessage());
        } catch (IOException e) {
        }
      } catch (Exception ex) {
        throw new JspException(ex);
      }
    }

    return EVAL_PAGE;
  }
}
