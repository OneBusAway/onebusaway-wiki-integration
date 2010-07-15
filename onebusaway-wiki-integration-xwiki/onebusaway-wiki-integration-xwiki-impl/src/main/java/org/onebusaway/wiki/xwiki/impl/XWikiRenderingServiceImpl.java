package org.onebusaway.wiki.xwiki.impl;

import java.io.StringReader;

import javax.annotation.PostConstruct;

import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.WikiRenderingService;
import org.xwiki.component.embed.EmbeddableComponentManager;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.rendering.converter.ConversionException;
import org.xwiki.rendering.converter.Converter;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.rendering.renderer.printer.WikiPrinter;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.wiki.WikiModel;

/**
 * Implementation of {@link WikiRenderingService} that uses XWiki rendering
 * components to render XWiki 2.0 syntax into HTML. We provide flexibilty in how
 * internal links (for other wiki pages, edit links, and attachment links) are
 * rendered into urls. Use {@link #setWikiDocumentViewUrl(String)},
 * {@link #setWikiDocumentEditUrl(String)}, and
 * {@link #setWikiAttachmentUrl(String)} to control that urls that will be used,
 * taking note of url variable substitution that will take place, as defined in
 * {@link XWikiModelImpl}.
 * 
 * @author bdferris
 * @see WikiRenderingService
 */
public class XWikiRenderingServiceImpl implements WikiRenderingService {

  private Converter _converter;

  private String _wikiDocumentViewUrl;

  private String _wikiDocumentEditUrl;

  private String _wikiAttachmentUrl;

  private XWikiModelImpl _modelImpl;

  public void setWikiDocumentViewUrl(String wikiDocumentViewUrl) {
    _wikiDocumentViewUrl = wikiDocumentViewUrl;
  }

  public void setWikiDocumentEditUrl(String wikiDocumentEditUrl) {
    _wikiDocumentEditUrl = wikiDocumentEditUrl;
  }

  public void setWikiAttachmentUrl(String wikiAttachmentUrl) {
    _wikiAttachmentUrl = wikiAttachmentUrl;
  }

  @PostConstruct
  public void setup() throws ComponentLookupException {
    EmbeddableComponentManager ecm = new EmbeddableComponentManager();
    ecm.initialize(getClass().getClassLoader());
    _converter = ecm.lookup(Converter.class);
    WikiModel model = ecm.lookup(WikiModel.class);
    if (!(model instanceof XWikiModelImpl))
      throw new IllegalStateException(
          "expected WikiModel component to be instanceof "
              + XWikiModelImpl.class);
    _modelImpl = (XWikiModelImpl) model;
    _modelImpl.setDocumentViewUrl(_wikiDocumentViewUrl);
    _modelImpl.setDocumentEditUrl(_wikiDocumentEditUrl);
    _modelImpl.setAttachmentUrl(_wikiAttachmentUrl);
  }

  @Override
  public String renderPage(WikiPage page) throws WikiException {
    XWikiContext.setContext(new XWikiContext(page));
    try {
      WikiPrinter printer = new DefaultWikiPrinter();

      _converter.convert(new StringReader(page.getContent()), Syntax.XWIKI_2_0,
          Syntax.XHTML_1_0, printer);
      return printer.toString();
    } catch (ConversionException ex) {
      throw new WikiException("error converting xwiki content to html", ex);
    } finally {
      XWikiContext.resetContext();
    }
  }

  @Override
  public String getEditLink(WikiPage page) {
    return _modelImpl.getDocumentEditURL(page.getName(), null, null);
  }
}
