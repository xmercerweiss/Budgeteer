package net.xmercerweiss.budgeteer;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;
import java.io.PrintStream;
import static java.util.Map.entry;

import net.xmercerweiss.budgeteer.util.*;
import net.xmercerweiss.budgeteer.tables.*;


public class Main
{
  // Class Constants
  private static final String TAB_PATH = "data/table.csv";
  private static final List<String> TAB_FIELDS = List.of("ID", "VALUE", "USAGE");

  private static final String UNALLOC_PATH = "data/unallocated";

  private static final Scanner INP = new Scanner(System.in);
  private static final PrintStream OUT = System.out;
  private static final Table TAB = new Table(TAB_PATH);

  private static final String PROMPT = "?> ";
  private static final String CLEAR_MSG = "Table cleared";

  private static final String VIEW_UNALLOC_MSG_FMT = "You have a credit of %s\n";
  private static final String ADD_UNALLOC_MSG_FMT = "Credited %s\n";
  private static final String ADD_ROW_PASS_MSG_FMT = "Debited %s for %s\n";
  private static final String ADD_ROW_FAIL_MSG_FMT = "Could not debit %s; not enough credit\n";
  private static final String SAVE_MSG_FMT = "Table saved in %s\nCredit saved in %s\n";

  private static final String INV_CMD_MSG =
    "Invalid command \"%s\"\n";

  private static final String BAD_USE_MSG =
    "Bad usage of \"%s\"\n";

  private static final Map<String,Consumer<String[]>> COMMANDS = Map.ofEntries(
    entry("exit", Main::exit),
    entry("earn", Main::addUnallocated),
    entry("spend", Main::addRow),
    entry("view", Main::viewTable),
    entry("clear", Main::clearState),
    entry("save", Main::saveState)
  );

  // Static Fields
  private static boolean isRunning = true;
  private static long unallocated ;

  // Methods implementing the application as a whole
  public static void main(String[] args)
  {
    importUnallocated();
    viewTable();
    while (isRunning)
    {
      OUT.printf(
        VIEW_UNALLOC_MSG_FMT,
        StringUtils.asCents(unallocated)
      );
      OUT.println();
      String[] input = promptUser();
      if (input.length > 0)
        dispatch(input);
    }
  }

  private static void importUnallocated()
  {
    try
    {
      unallocated = Long.parseLong(
        FileIO.read(UNALLOC_PATH)[0]
      );
    }
    catch (Exception e)
    {
      unallocated = 0;
    }
  }

  private static String[] promptUser()
  {
    OUT.print(PROMPT);
    return StringUtils.cleanSplit(INP.nextLine());
  }

  private static void dispatch(String... input)
  {
    String called = input[0];
    String[] args = Arrays.copyOfRange(input, 1, input.length);
    if (COMMANDS.containsKey(called))
    {
      Consumer<String[]> cmd = COMMANDS.get(called);
      try
      {
        cmd.accept(args);
      }
      catch (Exception e)
      {
        OUT.printf(BAD_USE_MSG, called);
      }
    }
    else
      OUT.printf(INV_CMD_MSG, called);
  }

  // Methods implementing commands within the application
  private static void exit(String... args)
  {
    isRunning = false;
  }

  private static void addUnallocated(String... args)
  {
    long n = Long.parseLong(args[0]);
    unallocated += n;
    OUT.printf(
      ADD_UNALLOC_MSG_FMT,
      StringUtils.asCents(n)
    );
  }

  private static void addRow(String... args)
  {
    Row r = TAB.push(args);
    if (r.quant() > unallocated)
    {
      TAB.pull();
      OUT.printf(
        ADD_ROW_FAIL_MSG_FMT,
        r.getDisplayedQuant()
      );
    }
    else
    {
      unallocated -= r.quant();
      OUT.printf(
        ADD_ROW_PASS_MSG_FMT,
        r.getDisplayedQuant(),
        r.getDisplayedTitle()
      );
    }
  }

  private static void viewTable(String... args)
  {
    OUT.println(
      CSVRenderer.render(
        TAB.asDisplayedCSV(),
        TAB_FIELDS.toArray(new String[0])
      )
    );
  }

  private static void clearState(String... args)
  {
    TAB.clear();
    OUT.println(CLEAR_MSG);
  }

  private static void saveState(String... args)
  {
    TAB.exportData(TAB_PATH);
    OUT.printf(
      SAVE_MSG_FMT,
      TAB_PATH,
      UNALLOC_PATH
    );
  }
}
