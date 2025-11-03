package net.xmercerweiss.budgeteer;

import java.util.Map;
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
  private static final String TAB_PATH = "data/ledger.csv";
  private static final String[] TAB_FIELDS = {"ID", "VALUE", "USAGE"};

  private static final String CREDIT_PATH = "data/credit";

  private static final Scanner INP = new Scanner(System.in);
  private static final PrintStream OUT = System.out;
  private static final Table TAB = new Table(TAB_PATH);
  private static final CSVRenderer RENDERER = new CSVRenderer(TAB_FIELDS);

  private static final String PROMPT = "\n?> ";
  private static final String CLEAR_MSG = "Ledger cleared, credit reset";

  private static final String VIEW_CREDIT_MSG_FMT = "You have a credit of %s\n";
  private static final String ADD_CREDIT_MSG_FMT = "Credited %s\n";
  private static final String DEBIT_PASS_MSG_FMT = "Debited %s for %s\n";
  private static final String DEBIT_FAIL_MSG_FMT = "Could not debit %s; not enough credit\n";
  private static final String SAVE_MSG_FMT = "Ledger saved in %s\nCredit saved in %s\n";

  private static final String INV_CMD_MSG =
    "Invalid command \"%s\"\n";

  private static final String BAD_USE_MSG =
    "Bad usage of \"%s\"\n";

  private static final Map<String,Consumer<String[]>> COMMANDS = Map.ofEntries(
    entry("x", Main::exit),
    entry("+", Main::addUnallocated),
    entry("-", Main::addRow),
    entry("*", Main::viewTable),
    entry("d", Main::clearState),
    entry("s", Main::saveState)
  );

  // Static Fields
  private static boolean isRunning = true;
  private static long credit;

  // Methods implementing the application as a whole
  public static void main(String[] args)
  {
    RENDERER.setRightMarginColumns(1);
    importCredit();
    viewTable();
    while (isRunning)
    {
      OUT.printf(
        VIEW_CREDIT_MSG_FMT,
        StringUtils.asDollars(credit)
      );
      String[] input = promptUser();
      if (input.length > 0)
        dispatch(input);
    }
  }

  private static void importCredit()
  {
    try
    {
      credit = Long.parseLong(
        FileIO.read(CREDIT_PATH)[0]
      );
    }
    catch (Exception e)
    {
      credit = 0;
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
    credit += n;
    OUT.printf(
      ADD_CREDIT_MSG_FMT,
      StringUtils.asDollars(n)
    );
  }

  private static void addRow(String... args)
  {
    Row r = TAB.push(args);
    if (r.quant() > credit)
    {
      TAB.pull();
      OUT.printf(
        DEBIT_FAIL_MSG_FMT,
        r.getDisplayedQuant()
      );
    }
    else
    {
      credit -= r.quant();
      OUT.printf(
        DEBIT_PASS_MSG_FMT,
        r.getDisplayedQuant(),
        r.getDisplayedTitle()
      );
    }
  }

  private static void viewTable(String... args)
  {
    RENDERER.setData(TAB.asDisplayedCSV());
    OUT.println(RENDERER.render());
  }

  private static void clearState(String... args)
  {
    TAB.clear();
    credit = 0;
    OUT.println(CLEAR_MSG);
  }

  private static void saveState(String... args)
  {
    TAB.exportData(TAB_PATH);
    FileIO.write(CREDIT_PATH, Long.toString(credit));
    OUT.printf(
      SAVE_MSG_FMT,
      TAB_PATH,
      CREDIT_PATH
    );
  }
}
