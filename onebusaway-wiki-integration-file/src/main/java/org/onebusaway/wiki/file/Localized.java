package org.onebusaway.wiki.file;

import java.util.Locale;

class Localized<T> {

  private final T object;

  private final Locale locale;

  public static <T> Localized<T> create(T object, Locale locale) {
    return new Localized<T>(object, locale);
  }

  public Localized(T object, Locale locale) {
    this.object = object;
    this.locale = locale;
  }

  public T getObject() {
    return object;
  }

  public Locale getLocale() {
    return locale;
  }
}