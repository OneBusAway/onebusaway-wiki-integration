package org.onebusaway.wiki.xwiki.impl;

import org.onebusaway.wiki.api.WikiPage;
import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.wiki.WikiModel;

@Component
public class XWikiModelImpl implements WikiModel {

  private String _documentViewUrl;

  private String _documentEditUrl;

  private String _attachmentUrl;

  public void setDocumentViewUrl(String documentViewUrl) {
    _documentViewUrl = documentViewUrl;
  }

  public void setDocumentEditUrl(String documentEditUrl) {
    _documentEditUrl = documentEditUrl;
  }

  public void setAttachmentUrl(String attachmentUrl) {
    _attachmentUrl = attachmentUrl;
  }

  @Override
  public boolean isDocumentAvailable(String documentName) {
    return true;
  }

  @Override
  public String getDocumentViewURL(String documentName, String anchor,
      String queryString) {

    String url = _documentViewUrl;

    if (documentName != null)
      url = url.replace("%{documentName}", documentName);
    if (anchor != null)
      url = url.replace("%{anchor}", anchor);
    if (queryString != null)
      url = url.replace("%{queryString}", queryString);

    return url;
  }

  @Override
  public String getAttachmentURL(String documentName, String attachmentName) {

    String url = _attachmentUrl;

    if (documentName == null) {
      XWikiContext context = XWikiContext.getContext();
      if (context != null) {
        WikiPage page = context.getPage();
        if (page != null)
          documentName = page.getName();
      }
    }

    if (documentName != null)
      url = url.replace("%{documentName}", documentName);
    if (attachmentName != null)
      url = url.replace("%{attachmentName}", attachmentName);

    return url;
  }

  @Override
  public String getDocumentEditURL(String documentName, String anchor,
      String queryString) {

    String url = _documentEditUrl;

    if (documentName != null)
      url = url.replace("%{documentName}", documentName);
    if (anchor != null)
      url = url.replace("%{anchor}", anchor);
    if (queryString != null)
      url = url.replace("%{queryString}", queryString);

    return url;
  }

}
