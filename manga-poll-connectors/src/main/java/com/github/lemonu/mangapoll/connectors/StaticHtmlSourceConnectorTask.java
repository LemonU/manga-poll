package com.github.lemonu.mangapoll.connectors;

import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.CHAPTER_CSS_QUERY;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.MAIN_PAGE_URL;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.POLL_INTERVAL;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.TITLE_CSS_QUERY;
import static com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig.UPDATE_CSS_QUERY;

import com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig;
import com.github.lemonu.mangapoll.connectors.model.UpdateSchema;
import com.github.lemonu.mangapoll.connectors.service.ElementParser;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticHtmlSourceConnectorTask extends SourceTask {

  private static final Logger logger = LoggerFactory.getLogger(StaticHtmlSourceConnectorTask.class);

  private StaticHtmlConnectorConfig config;
  private Map<String, String> originalProps;
  private String prevUpdateUrl;

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
    if (StaticHtmlSourceConnector.newStart) {
      StaticHtmlSourceConnector.newStart = false;
      logger.info("Fresh start, not sleeping");
    } else {
      try {
        logger.info("Sleeping....");
        Thread.sleep(config.getLong(POLL_INTERVAL));
      } catch (InterruptedException e) {
        return null;
      }
    }

    Document document;
    try {
      document = Jsoup.connect(config.getString(MAIN_PAGE_URL)).get();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    ElementParser parser = new ElementParser(document);
    String updateLink = parser.getUpdateLink(config.getString(UPDATE_CSS_QUERY));
    if (this.prevUpdateUrl != null && this.prevUpdateUrl.equals(updateLink)) {
      logger.info("No updates detected, skipping this poll");
      return null;
    }

    String domainName = "";
    try {
      domainName = new URI(config.getString(MAIN_PAGE_URL)).getHost();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    String title = parser.getTitle(config.getString(TITLE_CSS_QUERY));
    String chapterName = parser.getChapterName(config.getString(CHAPTER_CSS_QUERY));
    this.prevUpdateUrl = updateLink;
    updateLink = domainName + updateLink;
    logger.info("Sending update:{}, {}, {}", title, chapterName, updateLink);
    Struct struct = new Struct(UpdateSchema.getSchema())
        .put(UpdateSchema.TITLE, title)
        .put(UpdateSchema.CHAPTER, chapterName)
        .put(UpdateSchema.URL, updateLink);
    return Collections.singletonList(
        new SourceRecord(null,
            null,
            originalProps.get("topics"),
            UpdateSchema.getSchema(),
            struct
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
