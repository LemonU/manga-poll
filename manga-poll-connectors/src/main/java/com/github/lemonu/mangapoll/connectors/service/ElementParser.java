package com.github.lemonu.mangapoll.connectors.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ElementParser {

  private Document html;

  public ElementParser(String html) {
    this.html = Jsoup.parse(html);
  }

  public ElementParser(Document document) {
    this.html = document;
  }

  public String getTitle(String queries) {
    return queryWith(queries).text();
  }

  public String getUpdateLink(String queries) {
    return queryWith(queries).attr("href");
  }

  public String getChapterName(String queries) {
    return queryWith(queries).text();
  }

  private Element queryWith(String queries) {
    Elements tmp = html.getAllElements();
    for (String query : queries.split(","))
      tmp = tmp.select(query);
    return tmp.first();
  }

}
