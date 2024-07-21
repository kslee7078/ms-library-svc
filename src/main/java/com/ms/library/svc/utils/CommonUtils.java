package com.ms.library.svc.utils;

import java.time.OffsetDateTime;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {

  private static final String EMAIL_PATTERN =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

  private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

  public boolean isValidEmail(String email) {
    if (email == null) {
      return false;
    }
    return pattern.matcher(email).matches();
  }

  public static OffsetDateTime getEndOfDayInFuture(int daysToAdd) {
    return OffsetDateTime.now().plusDays(daysToAdd);
  }

}
