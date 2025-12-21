package net.xmercerweiss.budgeteer.data;

import net.xmercerweiss.budgeteer.utils.*;


public class LedgerRenderer
{
  // Instance Fields
  private final Ledger ledger;
  private final CurrencyFormat currencyFmt;

  // Constructors
  public LedgerRenderer(Ledger ledger, String currencySign, boolean isDecimalCurrency)
  {
    this.ledger = ledger;
    currencyFmt = new CurrencyFormat(currencySign, isDecimalCurrency);
  }

  public String render()
  {
    return ledger.toString();
  }

  // Private Methods
  /*
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

  private String buildDataString(String[][] renderedData)
  {
    StringBuilder mut = new StringBuilder();
    int[] widths = getFieldWidths(renderedData);
    for (String[] row : renderedData)
    {
      for (int i = 0; i < fields.length; i++)
        row[i] = widenStringTo(row[i], widths[i]);
      mut.append(String.join(FIELD_SEP, row));
      mut.append('\n');
    }
    return mut.toString();
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
   */
}
