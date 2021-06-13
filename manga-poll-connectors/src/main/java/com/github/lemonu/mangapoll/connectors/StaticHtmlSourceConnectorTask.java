package com.github.lemonu.mangapoll.connectors;

import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.MAIN_PAGE_URL;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.POLL_INTERVAL;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.UPDATE_CSS_QUERY;

import com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticHtmlSourceConnectorTask extends SourceTask {

  private static final Logger logger = LoggerFactory.getLogger(StaticHtmlSourceConnectorTask.class);

  private StaticHtmlConnectorConfig config;
  private Map<String, String> originalProps;

  @Override
  public String version() {
    return null;
  }

  @Override
  public void start(Map<String, String> props) {
    config = new StaticHtmlConnectorConfig(props);
    originalProps = Collections.unmodifiableMap(props);
    logger.info("TASK started");
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    try {
      Thread.sleep(config.getLong(POLL_INTERVAL));
    } catch (InterruptedException e) {
      return null;
    }

    Document document = null;
    try {
      document = Jsoup.connect(config.getString(MAIN_PAGE_URL)).get();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
    String[] queries = config.getString(UPDATE_CSS_QUERY).split(",");
    Elements tmp = document.getAllElements();
    for (String query : queries) {
      tmp = tmp.select(query);
    }
    String lastUpdatedLink = tmp.first().attr("href");
    String topics = originalProps.get("topics");
    return Collections.singletonList(
        new SourceRecord(
            null,
            null,
            topics,
            null,
            lastUpdatedLink
        )
    );
  }

  @Override
  public void commit() throws InterruptedException {
    logger.info("TASK COMMIT HAS BEEN CALLED");
    super.commit();
  }

  @Override
  public void stop() {
    logger.info("TASK HAS BEEN STOPPED!");
  }
}
