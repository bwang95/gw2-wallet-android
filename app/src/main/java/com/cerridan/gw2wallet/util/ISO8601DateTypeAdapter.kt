package com.cerridan.gw2wallet.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter.ISO_INSTANT
import java.util.*

class ISO8601DateTypeAdapter: TypeAdapter<Date>() {
  override fun write(out: JsonWriter, value: Date?) {
    value?.let { out.value(ISO_INSTANT.format(it.instant)) } ?: out.nullValue()
  }

  override fun read(reader: JsonReader) = reader.peek()?.let {
    Instant.parse(reader.nextString()).date
  } ?: run {
    reader.nextNull()
    null
  }

  private val Instant.date get() = Date(toEpochMilli())

  private val Date.instant get() = Instant.ofEpochMilli(time)
}