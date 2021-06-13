package com.github.lemonu.mangapoll.connectors;

import com.github.lemonu.mangapoll.connectors.config.StaticHtmlConnectorConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticHtmlSourceConnector extends SourceConnector {

  private static final Logger log = LoggerFactory.getLogger(StaticHtmlSourceConnector.class);

  Map<String, String> originals;

  @Override
  public void start(Map<String, String> props) {
    log.info("CONNECTOR HAS STARTED!");
    originals = Collections.unmodifiableMap(props);
  }

  @Override
  public Class<? extends Task> taskClass() {
    return StaticHtmlSourceConnectorTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int i) {
    log.info("CONNECTOR DISTRIBUTING TASK CONFIGS");
    return Collections.singletonList(originals);
  }

  @Override
  public void stop() {
    log.info("CONNECTOR HAS STOPPED!");
  }

  @Override
  public ConfigDef config() {
    return StaticHtmlConnectorConfig.configDef();
  }

  @Override
  public String version() {
    return null;
  }
}
