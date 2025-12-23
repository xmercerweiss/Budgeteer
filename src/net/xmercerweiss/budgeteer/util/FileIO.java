package net.xmercerweiss.budgeteer.util;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;


public class FileIO
{
  public static final Charset CHARSET = StandardCharsets.UTF_8;

  private static final String INV_INP_PATH_ERR_MSG =
    "FileIO cannot read from path \"%s\"";

  private static final String INV_OUT_PATH_ERR_MSG =
    "FileIO cannot write to path \"%s\"";

  public static String[] read(String path)
  {
    ArrayList<String> lines = new ArrayList<>();
    try (Scanner inp = new Scanner(Path.of(path), CHARSET))
    {
      while (inp.hasNextLine())
        lines.add(inp.nextLine());
    }
    catch (IOException ioe)
    {
      throw new UncheckedIOException(
        INV_INP_PATH_ERR_MSG.formatted(path),
        ioe
      );
    }
    return StringUtils.collapse(lines);
  }

  public static void write(String path, String content)
  {
    try (PrintWriter out = new PrintWriter(path, CHARSET))
    {
      out.print(content);
    }
    catch (IOException ioe)
    {
      throw new UncheckedIOException(
        INV_OUT_PATH_ERR_MSG.formatted(path),
        ioe
      );
    }
  }
}
