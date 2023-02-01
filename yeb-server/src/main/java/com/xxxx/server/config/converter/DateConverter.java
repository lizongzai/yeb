package com.xxxx.server.config.converter;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

/**
 * 全局日期转换格式
 * @author lizongzai
 * @since 1.0.0
 */
public class DateConverter implements Converter<String, LocalDate> {

  @Override
  public LocalDate convert(String source) {
    try {
      return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
