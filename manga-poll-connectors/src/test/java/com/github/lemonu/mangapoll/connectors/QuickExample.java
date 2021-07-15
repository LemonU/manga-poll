package com.github.lemonu.mangapoll.connectors;

import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.CHAPTER_CSS_QUERY;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.MAIN_PAGE_URL;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.TITLE_CSS_QUERY;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.UPDATE_CSS_QUERY;

import com.github.lemonu.mangapoll.connectors.service.ElementParser;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class QuickExample {

  public static void main(String[] args) {
    Document document;
    try {
      document = Jsoup.connect("https://tw.manhuagui.com/comic/37546/").get();
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    ElementParser parser = new ElementParser(document);
    String updateLink = parser.getUpdateLink("li[class=status],a[href]");
//    if (this.prevUpdateUrl == null || this.prevUpdateUrl.equals(updateLink)) {
//      logger.info("No updates detected, skipping this poll");
//      this.prevUpdateUrl = updateLink;
//      return null;
//    }

    String domainName = "";
    try {
      domainName = new URI("https://tw.manhuagui.com/comic/37546/").getHost();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    String title = parser.getTitle("div[class=book-title],h1");
    String chapterName = parser.getChapterName("li[class=status],a[href]");
    updateLink = domainName + updateLink;
    log.info("Sending update:{}, {}, {}", title, chapterName, updateLink);
  }

}
