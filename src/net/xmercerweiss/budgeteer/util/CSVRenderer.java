package net.xmercerweiss.budgeteer.util;

import java.util.Arrays;


public class CSVRenderer
{
  private static final String BLANK = " ";
  private static final String FIELD_SEP = "   ";

  public static String render(String csv, String... fields)
  {
    StringBuilder mut = new StringBuilder();
    String[][] matrix = buildMatrix(csv, fields);
    int[] widths = getFieldWidths(matrix);
    for (String[] row : matrix)
    {
      for (int i = 0; i < fields.length; i++)
        row[i] = widenStringTo(row[i], widths[i]);
      mut.append(String.join(FIELD_SEP, row));
      mut.append('\n');
    }
    return mut.toString();
  }

  private static String[][] buildMatrix(String csv, String[] fields)
  {
    String[] lines = StringUtils.cleanSplit(csv, "\n");
    String[][] out = new String[lines.length + 1][];
    out[0] = fields;
    for (int i = 0; i < lines.length; i++)
    {
      String[] row = Arrays.copyOfRange(
        StringUtils.cleanSplit(lines[i], ","),
        0,
        fields.length
      );
      out[i + 1] = row;
    }
    return out;
  }

  private static int[] getFieldWidths(String[][] matrix)
  {
    int[] out = new int[matrix[0].length];
    for (String[] row: matrix)
      for (int j = 0; j < out.length; j++)
      {
        String cell = row[j];
        if (cell != null && cell.length() > out[j])
          out[j] = cell.length();
      }
    return out;
  }

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
}
