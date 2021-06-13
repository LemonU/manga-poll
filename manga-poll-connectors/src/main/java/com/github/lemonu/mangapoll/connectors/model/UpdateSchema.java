package com.github.lemonu.mangapoll.connectors.model;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class UpdateSchema {

  public static final String TITLE = "title";
  public static final String CHAPTER = "chapter";
  public static final String URL = "url";

  private static final Schema SCHEMA = SchemaBuilder.struct()
      .name("chapter-update-record-schema")
      .field(TITLE, Schema.STRING_SCHEMA)
      .field(CHAPTER, Schema.STRING_SCHEMA)
      .field(URL, Schema.STRING_SCHEMA)
      .build();

  public static Schema getSchema() {return SCHEMA;}

}
