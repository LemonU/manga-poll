package com.github.lemonu.mangapoll.connectors;

import com.github.lemonu.mangapoll.connectors.config.ManhuaguiSourceConnectorConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

@Slf4j
public class ManhuaguiSourceConnector extends SourceConnector {

  ManhuaguiSourceConnectorConfig config;
  Map<String, String> originals;

  @Override
  public void start(Map<String, String> props) {
    originals = Collections.unmodifiableMap(props);
    config = new ManhuaguiSourceConnectorConfig(props);
  }

  @Override
  public Class<? extends Task> taskClass() {
    return ManhuaguiSourceConnectorTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int i) {
    return null;
  }

  @Override
  public void stop() {

  }

  @Override
  public ConfigDef config() {
    return ManhuaguiSourceConnectorConfig.configDef();
  }

  @Override
  public String version() {
    return null;
  }
}
