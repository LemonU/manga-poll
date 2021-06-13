package com.github.lemonu.mangapoll.connectors;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickExample {

  static final Logger logger = LoggerFactory.getLogger(QuickExample.class);

  public static void main(String[] args) {
    Document document = null;
    try {
      document = Jsoup.connect("https://tw.manhuagui.com/comic/23394/").get();
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    String queries = "li[class=status],a[href]";
    Elements tmp = document.getAllElements();
    for (String query : queries.split(",")) {
      tmp = tmp.select(query);
      logger.info(tmp.toString());
    }
    logger.info(tmp.first().attr("href"));
  }

}
