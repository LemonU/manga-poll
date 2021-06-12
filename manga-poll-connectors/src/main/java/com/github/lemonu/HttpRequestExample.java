package com.github.lemonu;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Slf4j
public class HttpRequestExample {

  static final Logger logger = LoggerFactory.getLogger(HttpRequestExample.class);

  public static void main(String[] args) throws IOException {
    Document doc = Jsoup.connect("https://tw.manhuagui.com/comic/39757/").get();
    Element status = doc.select("li[class=status]").select("a[href]").first();
    logger.info(status.attr("href"));
  }

}
