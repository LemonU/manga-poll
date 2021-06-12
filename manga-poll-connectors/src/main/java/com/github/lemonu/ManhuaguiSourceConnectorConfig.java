package com.github.lemonu;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

public class ManhuaguiSourceConnectorConfig extends AbstractConfig {

  public ManhuaguiSourceConnectorConfig(ConfigDef definition,
      Map<?, ?> originals) {
    super(definition, originals);
  }
}
