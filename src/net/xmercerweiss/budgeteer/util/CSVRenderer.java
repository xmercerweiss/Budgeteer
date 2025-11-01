package net.xmercerweiss.budgeteer.util;

import java.util.Arrays;


public class CSVRenderer
{
  private static final String BLANK = " ";
  private static final String FIELD_SEP = "    ";

  public static String render(String csv, String... fields)
  {
    return render(
      csv,
      fields,
      new int[0]
    );
  }

  public static String render(String csv, String[] fields, int[] leftJustified)
  {
    // TODO Implement left justified fields
    StringBuilder mut = new StringBuilder();
    String[][] matrix = buildMatrix(csv, fields);
    int[] widths = getFieldWidths(matrix, leftJustified);
    for (String[] row : matrix)
    {
      for (int i = 0; i < fields.length; i++)
        row[i] = widenStringTo(row[i], widths[i]);
      mut.append(String.join(FIELD_SEP, row));
      mut.append('\n');
    }
    return mut.toString().trim();
  }

  private static String[][] buildMatrix(String csv, String[] fields)
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

  private static int[] getFieldWidths(String[][] matrix, int[] leftJustified)
  {
    int[] out = new int[matrix[0].length];
    int leftIndex = 0;
    for (String[] row: matrix)
      for (int i = 0; i < out.length; i++)
      {
        String cell = row[i];
        if (cell != null && cell.length() > out[i])
          out[i] = cell.length();
        if (i == leftJustified[leftIndex])
        {
          leftIndex++;
          out[i] *= -1;
        }
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
