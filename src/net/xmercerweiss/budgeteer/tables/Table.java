package net.xmercerweiss.budgeteer.tables;

import java.util.LinkedList;
import java.io.UncheckedIOException;

import net.xmercerweiss.budgeteer.util.*;


public class Table
{
  // Class Constants
  private static final long INIT_ID = 1;

  private static final String NULL_ROW_ERR_MSG =
    "Table expected Row object, given null";

  private static final String INV_IMP_DATA_ERR_MSG =
    "Table attempted to import invalid data file \"%s\"";

  private static final String INV_IMP_PATH_ERR_MSG =
    "Table could not import file \"%s\"";

  // Instance Fields
  private final LinkedList<Row> ROWS;
  private long currentId;

  // Constructors
  public Table()
  {
    ROWS = new LinkedList<>();
    currentId = INIT_ID;
  }

  public Table(String path)
  {
    this();
    importData(path);
  }

  // Override Methods
  @Override
  public String toString()
  {
    return this.toCSV(true);
  }

  // Public Methods
  public void append(String... values)
  {
    Row r = Row.of(currentId, values);
    currentId += r.quant();
    ROWS.add(r);
  }

  public void clear()
  {
    ROWS.clear();
    currentId = INIT_ID;
  }

  public void importData(String path)
  {
    try
    {
      for (String line : FileIO.read(path))
        append(StringUtils.cleanSplit(line, ","));
    }
    catch (IllegalArgumentException iae)
    {
      throw new IllegalArgumentException(
        INV_IMP_DATA_ERR_MSG.formatted(path)
      );
    }
    catch (UncheckedIOException ioe)
    {
      throw new IllegalArgumentException(
        INV_IMP_PATH_ERR_MSG.formatted(path)
      );
    }
  }

  public void exportData(String path)
  {
    ROWS.sort(null);
    FileIO.write(path, this.toCSV(false));
  }

  public String toCSV(boolean withId)
  {
    StringBuilder mut = new StringBuilder();
    for (Row r : ROWS)
      mut.append(r.toCSV(withId)).append('\n');
    return mut.toString().trim();
  }
}
