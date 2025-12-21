package net.xmercerweiss.budgeteer.utils;

import java.text.*;


public class CurrencyFormat
{
  // Class Constants
  private static final String SIGN_FMT = "'%s'";
  private static final String BASE_FMT = "#,##0";
  private static final String DECIMAL_FMT = ".00";
  private static final String PATTERN_FMT = "%s;(#)";

  // Instance Fields
  private final String sign;
  private final boolean isDecimal;
  private final DecimalFormat fmt;

  // Constructors
  public CurrencyFormat(String currencySign, boolean isDecimalCurrency)
  {
    sign = currencySign;
    isDecimal = isDecimalCurrency;
    fmt = buildDecimalFormat();
  }

  // Public Methods
  public String format(long amount)
  {
    if (isDecimal)
      return fmt.format(amount / (double) 100);
    else
      return fmt.format(amount);
  }

  // Private Methods
  private DecimalFormat buildDecimalFormat()
  {
    String signString = SIGN_FMT.formatted(
      sign.replace("'", "''")
    );
    String pattern = PATTERN_FMT.formatted(
      signString + BASE_FMT + (isDecimal ? DECIMAL_FMT : "")
    );
    return new DecimalFormat(pattern);
  }
}
