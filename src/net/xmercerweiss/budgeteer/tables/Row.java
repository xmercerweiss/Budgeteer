package net.xmercerweiss.budgeteer.tables;

import net.xmercerweiss.budgeteer.util.*;


public record Row(long id, long quant, String title)
  implements Comparable<Row>
{
  // Class Constants
  private static final String NO_VALUES_ERR_MSG =
    "Row must be created with at least 2 values";

  private static final String INV_VALUES_ERR_MSG =
    "Row must be created with values matching format \"%d, %s\"";

  // Static Methods
  public static Row of(long id, String... values)
  {
    if (values.length < 2)
      throw new IllegalArgumentException(NO_VALUES_ERR_MSG);
    try
    {
      long newQuant = Long.parseLong(values[0]);
      String newTitle = StringUtils.join(1, values);
      return new Row(id, newQuant, newTitle);
    }
    catch (NumberFormatException nfe)
    {
      throw new IllegalArgumentException(INV_VALUES_ERR_MSG);
    }
  }

  // Override Methods
  @Override
  public int compareTo(Row that)
  {
    return Long.compare(this.id, that.id);
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (obj instanceof Row that)
      return this.compareTo(that) == 0;
    else
      return false;
  }

  // Public Methods
  public String asData()
  {
    return String.join(",", String.valueOf(quant), title);
  }

  public String asDisplay()
  {
    return String.join(
      ",",
      getDisplayedID(),
      getDisplayedQuant(),
      getDisplayedTitle()
    );
  }

  public String getDisplayedID()
  {
    return StringUtils.asID(id + quant);
  }

  public String getDisplayedQuant()
  {
    return StringUtils.asDollars(quant);
  }

  public String getDisplayedTitle()
  {
    return StringUtils.toTitleCase(title);
  }
}
