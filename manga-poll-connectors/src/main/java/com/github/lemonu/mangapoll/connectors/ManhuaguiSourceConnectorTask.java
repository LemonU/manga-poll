package com.github.lemonu.mangapoll.connectors;

import static com.github.lemonu.mangapoll.connectors.config.ManhuaguiSourceConnectorConfig.MAIN_PAGE_URL;
import static com.github.lemonu.mangapoll.connectors.config.ManhuaguiSourceConnectorConfig.POLL_INTERVAL;

import com.github.lemonu.mangapoll.connectors.config.ManhuaguiSourceConnectorConfig;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManhuaguiSourceConnectorTask extends SourceTask {

  private static final Logger logger = LoggerFactory.getLogger(ManhuaguiSourceConnectorTask.class);

  private ManhuaguiSourceConnectorConfig config;
  private Map<String, String> originalProps;

  @Override
  public String version() {
    return null;
  }

  @Override
  public void start(Map<String, String> props) {
    config = new ManhuaguiSourceConnectorConfig(props);
    originalProps = Collections.unmodifiableMap(props);
    logger.info("{} started", ManhuaguiSourceConnectorTask.class.getName());
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    Thread.sleep(config.getLong(POLL_INTERVAL));
    Document document = null;
    try {
      document = Jsoup.connect(config.getString(MAIN_PAGE_URL)).get();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
    Element lastUpdate = document.select("li[class=status]").select("a[href]").first();
    String topics = originalProps.get("topics");
    return Collections.singletonList(
        new SourceRecord(
            null,
            null,
            topics,
            null,
            lastUpdate.attr("href"))
    );
  }

  @Override
  public void commit() throws InterruptedException {
    super.commit();
  }

  @Override
  public void stop() {}
}
