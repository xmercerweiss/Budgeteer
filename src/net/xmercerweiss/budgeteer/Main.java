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
  private static final String TABLE_PATH = "data/table.csv";

  private static final Scanner INP = new Scanner(System.in);
  private static final PrintStream OUT = System.out;
  private static final Table TAB = new Table(TABLE_PATH);

  private static final String PROMPT = "?> ";

  private static final String INV_CMD_MSG =
    "Invalid command \"%s\"\n";

  private static final String BAD_USE_MSG =
    "Bad usage of \"%s\"\n";

  private static final Map<String,Consumer<String[]>> COMMANDS = Map.ofEntries(
    entry("exit", Main::exit),
    entry("add", Main::addRow),
    entry("clear", Main::clearRows)
  );

  // Static Fields
  private static boolean isRunning = true;

  // Methods implementing the application as a whole
  public static void main(String[] args)
  {
    TAB.importData(TABLE_PATH);
    while (isRunning)
    {
      String[] input = promptUser();
      if (input.length > 0)
        dispatch(input);
    }
    TAB.exportData(TABLE_PATH);
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

  private static void addRow(String... args)
  {
    TAB.append(args);
    OUT.println(TAB);
  }

  private static void clearRows(String... args)
  {
    TAB.clear();
    OUT.println(TAB);
  }
}
