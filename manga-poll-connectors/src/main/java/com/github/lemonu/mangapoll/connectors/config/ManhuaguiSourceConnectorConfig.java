package com.github.lemonu.mangapoll.connectors.config;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

public class ManhuaguiSourceConnectorConfig extends AbstractConfig {

  public static final String MAIN_PAGE_URL = "mainPageUrl";
  private static final Type MAIN_PAGE_URL_TYP = Type.STRING;
  private static final Importance MAIN_PAGE_URL_IMPORTANCE = Importance.HIGH;
  private static final String MAIN_PAGE_URL_DOC = "The url to the manga's main page";

  public static final String OUTPUT_DIR = "outputDir";
  private static final Type OUTPUT_DIR_TYPE = Type.STRING;
  private static final Importance OUTPUT_DIR_IMPORTANCE = Importance.HIGH;
  private static final String OUTPUT_DIR_DOC = "Path to the folder you'd like to save the downloads";

  public ManhuaguiSourceConnectorConfig(Map<String, String> originals) {
    super(configDef(), originals);
  }

  public static ConfigDef configDef() {
    return new ConfigDef()
        .define(MAIN_PAGE_URL, MAIN_PAGE_URL_TYP, MAIN_PAGE_URL_IMPORTANCE, MAIN_PAGE_URL_DOC)
        .define(OUTPUT_DIR, OUTPUT_DIR_TYPE, OUTPUT_DIR_IMPORTANCE, OUTPUT_DIR_DOC);
  }
}
