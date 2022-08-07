package com.example.auth.core.config.pages;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

public class PageRequest implements Pageable {
  private final long offSet;
  private final int limit;

  public PageRequest() {
    this(0, 5);
  }

  public PageRequest(long offSet, int limit) {
    checkArgument(offSet >= 0, "OffSet must be greater or equals to zero");
    checkArgument(limit > 0, "Limit must be greater than zero");

    this.offSet = offSet;
    this.limit = limit;
  }

  @Override
  public long offset() {
    return offSet;
  }

  @Override
  public int limit() {
    return limit;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("offset", offSet)
            .append("limit", limit)
            .toString();
  }
}
