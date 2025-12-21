package net.xmercerweiss.budgeteer.utils;

import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.Path;


public class FileUtils
{
  public static final Charset CHARSET = StandardCharsets.UTF_8;

  private static final String INV_INP_PATH_ERR_MSG =
    "FileUtils cannot read from path \"%s\"";

  private static final String INV_OUT_PATH_ERR_MSG =
    "FileUtils cannot write to path \"%s\"";

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
