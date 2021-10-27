package com.demo.drone.infrastructure.persistence;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

@ReadingConverter
public class ZonedDateTimeReaderConverter implements Converter<Date, ZonedDateTime> {

  @Override
  public ZonedDateTime convert(Date date) {
    return date.toInstant().atZone(ZoneOffset.UTC);
  }
}
