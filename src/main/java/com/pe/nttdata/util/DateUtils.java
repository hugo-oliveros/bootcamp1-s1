package com.pe.nttdata.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;

/**
 *Implement DateUtils. <br/>
 *<b>Class</b>: {@link DateUtils}<br/>
 *<b>Copyright</b>: &Copy; 2024 NTTDATA Per&uacute;. <br/>
 *<b>Company</b>: NTTDATA del Per&uacute;. <br/>
 *
 *@author NTTDATA Per&uacute;. (EVE) <br/>
 *<u>Developed by</u>: <br/>
 *<ul>
 *<li>Hugo Oliveros Monti</li>
 *</ul>
 *<u>Changes</u>:<br/>
 *<ul>
 *<li>Mar. 7, 2024 (acronym) Creation class.</li>
 *</ul>
 *@version 1.0
 */
@AllArgsConstructor
public class DateUtils {

  public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  public static final String DEFAULT_ZONE_ID = "America/Lima";
  public static final String DATE_TIME_FORMAT_RESOURCES = "yyyy-MM-dd HH:mm";


  /**
   * <p/>
   * .
   * Method ZonedDateTime
   *
   **/
  public static ZonedDateTime getDefaultCurrentZonedDateTime() {
    return getCurrentZonedDateTime(ZoneId.of(DEFAULT_ZONE_ID));
  }

  /**
   * <p/>
   * .
   * Method ZonedDateTime
   *
   **/
  public static ZonedDateTime getCurrentZonedDateTime(ZoneId zoneId) {
    Instant now = Instant.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, zoneId);
    return zonedDateTime;
  }

  /**
   * <p/>
   * .
   * Method ZonedDateTime
   *
   **/
  public static Date obtenerFechaHoraActual() {
    ZoneId zona = ZoneId.systemDefault();
    Date fechaActual = Date.from(LocalDateTime.now().atZone(zona).toInstant());
    return fechaActual;
  }

  /**
   * <p/>
   * .
   * Method ZonedDateTime
   *
   **/
  public static Date obtenerFechaHoraActualMinusMin(long minutes) {
    ZoneId zona = ZoneId.systemDefault();
    LocalDateTime localDateTime = LocalDateTime.now();
    localDateTime = localDateTime.minusMinutes(minutes);
    Date fechaActual = Date.from(localDateTime.atZone(zona).toInstant());
    return fechaActual;
  }

  /**
   * <p/>
   * .
   * Method ZonedDateTime
   *
   **/
  public static Date obtenerFechaHoraActualPlusMin(long minutes) {
    ZoneId zona = ZoneId.systemDefault();
    LocalDateTime localDateTime = LocalDateTime.now();
    localDateTime = localDateTime.plusMinutes(minutes);
    Date fechaActual = Date.from(localDateTime.atZone(zona).toInstant());
    return fechaActual;
  }

  /**
   * <p/>
   * .
   * Method ZonedDateTime
   *
   **/
  public static Date obtenerFechaHoraActualPlusHora(long horas) {
    ZoneId zona = ZoneId.systemDefault();
    LocalDateTime localDateTime = LocalDateTime.now();
    localDateTime = localDateTime.plusHours(horas);
    Date fechaActual = Date.from(localDateTime.atZone(zona).toInstant());
    return fechaActual;
  }

}