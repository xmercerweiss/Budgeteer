package net.xmercerweiss.budgeteer;

import net.xmercerweiss.budgeteer.core.*;


public class Main
{
  public static void main(String[] args)
  {
    Profile profile = new Profile(
      "mercerweiss",
      "$",
      true
    );
    Application app = new Application(profile);
    app.enter();
  }
}
