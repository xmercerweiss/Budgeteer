package net.xmercerweiss.budgeteer.tables;

import java.util.LinkedList;
import java.io.UncheckedIOException;

import net.xmercerweiss.budgeteer.util.*;


public class Table
{
  // Class Constants
  private static final long INIT_ID = 0;

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

  // Public Methods
  public Row push(String... values)
  {
    Row r = Row.of(currentId, values);
    push(r);
    return r;
  }

  public void push(Row r)
  {
    currentId += r.quant();
    ROWS.add(r);
  }

  public Row pull()
  {
    Row r = ROWS.removeLast();
    currentId -= r.quant();
    return r;
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
        push(StringUtils.cleanSplit(line, ","));
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
    FileIO.write(path, this.asDataCSV());
  }

  public String asDisplayedCSV()
  {
    return getRows(false);
  }

  public String asDataCSV()
  {
    return getRows(true);
  }

  public String getRows(boolean asData) {
    StringBuilder mut = new StringBuilder();
    for (Row r : ROWS)
      mut.append(
        asData ? r.asData() : r.asDisplay()
      ).append('\n');
    return mut.toString().trim();
  }
}
