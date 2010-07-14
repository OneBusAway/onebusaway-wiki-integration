package org.onebusaway.wiki.xwiki.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.onebusaway.wiki.api.WikiDocumentService;
import org.onebusaway.wiki.api.WikiException;
import org.onebusaway.wiki.api.WikiPage;
import org.onebusaway.wiki.api.impl.WikiPageImpl;

public class XWikiDocumentServiceImpl implements WikiDocumentService {

  private String _xwikiUrl;

  public void setXwikiUrl(String xwikiUrl) {
    _xwikiUrl = xwikiUrl;
  }

  @Override
  public WikiPage getWikiPage(String namespace, String name,
      boolean forceRefresh) throws WikiException {

    HttpClient httpClient = new HttpClient();

    String url = _xwikiUrl + "/rest/wikis/xwiki/spaces/" + namespace
        + "/pages/" + name;
    GetMethod getMethod = new GetMethod(url);
    getMethod.addRequestHeader("Accept", "application/xml");
    int code = evaluateHttpMethod(httpClient, getMethod);

    if (code != HttpStatus.SC_OK) {
      return null;
    }

    Digester digester = new Digester();

    List<WikiPage> pages = new ArrayList<WikiPage>();
    digester.push(pages);

    digester.addObjectCreate("page", WikiPageImpl.class);
    digester.addBeanPropertySetter("page/space", "namespace");
    digester.addBeanPropertySetter("page/name");
    digester.addBeanPropertySetter("page/title");
    digester.addBeanPropertySetter("page/content");
    digester.addSetNext("page", "add");

    try {
      digester.parse(getMethod.getResponseBodyAsStream());
    } catch (Exception ex) {
      throw new WikiException("error parsing xwiki response", ex);
    }

    if (pages.isEmpty())
      return null;

    return pages.get(0);
  }

  private int evaluateHttpMethod(HttpClient httpClient, GetMethod getMethod)
      throws WikiException {
    try {
      return httpClient.executeMethod(getMethod);
    } catch (Exception ex) {
      throw new WikiException("error evaluating xwiki http method", ex);
    }

  }
}
