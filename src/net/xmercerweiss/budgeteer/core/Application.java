package net.xmercerweiss.budgeteer.core;

import java.util.*;
import java.util.function.Consumer;
import java.io.PrintStream;
import static java.util.Map.entry;

import net.xmercerweiss.budgeteer.data.Ledger;
import net.xmercerweiss.budgeteer.data.LedgerRenderer;
import net.xmercerweiss.budgeteer.util.*;


public class Application
{
  // Class Constants
  private static final Scanner IN = new Scanner(System.in);
  private static final PrintStream OUT = System.out;

  private static final String PROMPT = "\n?> ";

  private static final String INV_CMD_MSG =
    "Invalid command \"%s\"\n";

  private static final String BAD_USE_MSG =
    "Bad usage of \"%s\"\n";

  // Instance Fields
  private boolean isRunning = false;

  private final Ledger ledger;
  private final LedgerRenderer renderer;

  private final Map<String,Consumer<String[]>> commands = Map.ofEntries(
    entry("exit", this::exit),
    entry("gain", this::addGain),
    entry("loss", this::addLoss),
    entry("view", this::viewLedger),
    entry("save", this::saveLedger),
    entry("wipe", this::wipeLedger)
  );

  // Constructors
  public Application()
  {
    ledger = new Ledger();
    renderer = new LedgerRenderer(ledger);
  }

  // Public Methods
  public void enter(String... args)
  {
    isRunning = true;
    while (isRunning)
    {
      viewLedger();
      String[] input = promptUser();
      if (input.length > 0)
        dispatch(input);
    }
  }

  public void exit(String... args)
  {
    isRunning = false;
  }

  // Private Application Methods
  private String[] promptUser()
  {
    OUT.print(PROMPT);
    return StringUtils.cleanSplit(IN.nextLine());
  }

  private void dispatch(String... input)
  {
    String called = input[0];
    String[] args = Arrays.copyOfRange(input, 1, input.length);
    if (commands.containsKey(called))
    {
      Consumer<String[]> cmd = commands.get(called);
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

  // Private Command Methods
  private void addGain(String... args)
  {

  }

  private void addLoss(String... args)
  {

  }

  private void viewLedger(String... args)
  {
    OUT.println(renderer.render());
  }

  private void saveLedger(String... args)
  {

  }

  private void wipeLedger(String... args)
  {

  }
}
