package com.github.lemonu;

import java.util.List;
import java.util.Map;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

public class ManhuaguiSourceConnectorTask extends SourceTask {

  @Override
  public String version() {
    return null;
  }

  @Override
  public void start(Map<String, String> map) {

  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    return null;
  }

  @Override
  public void stop() {

  }
}
