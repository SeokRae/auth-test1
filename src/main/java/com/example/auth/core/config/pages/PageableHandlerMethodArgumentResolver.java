package com.example.auth.core.config.pages;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static org.apache.commons.lang3.math.NumberUtils.toLong;

public class PageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  private static final int DEFAULT_OFFSET = 1;
  private static final int DEFAULT_LIMIT = 5;
  private final String offSetKey;
  private final String limitKey;

  public PageableHandlerMethodArgumentResolver() {
    this("offset", "limit");
  }

  public PageableHandlerMethodArgumentResolver(String offSetKey, String limitKey) {
    this.offSetKey = offSetKey;
    this.limitKey = limitKey;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Pageable.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    String offSetParam = webRequest.getParameter(offSetKey);
    String limitParam = webRequest.getParameter(limitKey);

    long offSet = toLong(offSetParam, DEFAULT_OFFSET);
    int limit = toInt(limitParam, DEFAULT_LIMIT);

    if(offSet < 0) {
      offSet = DEFAULT_OFFSET;
    }
    if(limit < 1 || limit > 5) {
      limit = DEFAULT_LIMIT;
    }
    return new PageRequest(offSet, limit);
  }
}
