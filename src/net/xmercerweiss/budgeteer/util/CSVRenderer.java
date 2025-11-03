package net.xmercerweiss.budgeteer.util;

import java.util.Arrays;
import java.util.HashSet;


public class CSVRenderer
{
  // Class Constants
  private static final String BLANK = " ";
  private static final String FIELD_SEP = "    ";

  private static final String NULL_FIELD_ERR_MSG =
    "CSVRenderer constructed with null in place of field name";

  private static final String NULL_CSV_ERR_MSG =
    "CSVRenderer given null in place of CSV content";

  // Instance Fields
  private final HashSet<Integer> rightMarginColumns = new HashSet<>();
  private final String[] fields;
  private String[][] data;

  // Static Methods
  private static String widenStringTo(String str, int width)
  {
    int absWidth = Math.abs(width);
    if (str == null)
      return BLANK.repeat(absWidth);
    int length = str.length();
    if (length >= absWidth)
      return str;
    String whitespace = BLANK.repeat(absWidth - length);
    return width < 0 ? whitespace + str : str + whitespace;
  }

  // Constructors
  public CSVRenderer(String... fields)
  {
    validateGivenFields(fields);
    this.fields = fields;
  }

  // Public Methods
  public void setData(String csv)
  {
    validateCSV(csv);
    this.data = buildDataMatrix(csv);
  }

  public void setRightMarginColumns(int... columns)
  {
    rightMarginColumns.clear();
    Arrays.stream(columns)
      .forEach(rightMarginColumns::add);
  }

  public String render()
  {
    StringBuilder mut = new StringBuilder();
    int[] widths = getFieldWidths(data);
    for (String[] row : data)
    {
      for (int i = 0; i < fields.length; i++)
        row[i] = widenStringTo(row[i], widths[i]);
      mut.append(String.join(FIELD_SEP, row));
      mut.append('\n');
    }
    return mut.toString();
  }

  // Private Methods
  private void validateGivenFields(String[] givenFields)
  {
    for (String given : givenFields)
      if (given == null)
        throw new NullPointerException(NULL_FIELD_ERR_MSG);
  }

  private void validateCSV(String csv)
  {
    if (csv == null)
      throw new NullPointerException(NULL_CSV_ERR_MSG);
  }

  private String[][] buildDataMatrix(String csv)
  {
    String[] lines = csv.split("\n");
    String[][] out = new String[lines.length + 1][];
    out[0] = fields;
    for (int i = 0; i < lines.length; i++)
    {
      String[] row = Arrays.copyOfRange(
        lines[i].split(","),
        0,
        fields.length
      );
      out[i + 1] = row;
    }
    return out;
  }

  private int[] getFieldWidths(String[][] matrix)
  {
    int[] out = new int[matrix[0].length];
    for (String[] row : matrix)
      for (int i = 0; i < out.length; i++)
      {
        String cell = row[i];
        if (cell != null && cell.length() > out[i])
          out[i] = cell.length();
      }
    for (int i = 0; i < out.length; i++)
      if (rightMarginColumns.contains(i))
        out[i] *= -1;
    return out;
  }
}
