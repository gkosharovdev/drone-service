package com.demo.drone.infrastructure.persistence;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.ZonedDateTime;
import java.util.Date;

@WritingConverter
public class ZonedDateTimeWriterConverter implements Converter<ZonedDateTime, Date> {

  @Override
  public Date convert(ZonedDateTime date) {
    return Date.from(date.toInstant());
  }
}
