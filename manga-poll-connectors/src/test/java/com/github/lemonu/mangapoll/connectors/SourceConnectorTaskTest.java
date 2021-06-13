package com.github.lemonu.mangapoll.connectors;

import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class SourceConnectorTaskTest {

  @Test
  void test() throws InterruptedException {
    StaticHtmlSourceConnectorTask testSubject = new StaticHtmlSourceConnectorTask();
    Map<String, String> props = new HashMap<>();
    props.put("topics", "manhuagui-test");
    props.put(MAIN_PAGE_URL, "https://tw.manhuagui.com/comic/37546/");
    props.put(UPDATE_CSS_QUERY, "li[class=status],a[href]");
    props.put(TITLE_CSS_QUERY, "div[class=book-title],h1");
    props.put(CHAPTER_CSS_QUERY, "li[class=status],a[href]");
    props.put(POLL_INTERVAL, String.valueOf(1));
    testSubject.start(props);
    for (int i = 0; i < 5; i++) {
      testSubject.poll();
    }
  }

}
