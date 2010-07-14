package org.onebusaway.wiki.api.impl;

import java.io.Serializable;

import org.onebusaway.wiki.api.WikiPage;

public class WikiPageImpl implements WikiPage, Serializable {

  private static final long serialVersionUID = 1L;

  private String namespace;

  private String name;

  private String title;

  private String content;

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
