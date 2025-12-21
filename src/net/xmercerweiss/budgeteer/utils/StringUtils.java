package net.xmercerweiss.budgeteer.utils;

import java.util.*;
import java.util.stream.Stream;


public class StringUtils
{
  // Class Constants
  private static final HashSet<String> UNCAPITALIZED = new HashSet<>(
    List.of(
      "a",
      "an",
      "the",
      "and",
      "or",
      "but",
      "at",
      "by",
      "to",
      "from",
      "with",
      "without",
      "in",
      "on",
      "of",
      "is",
      "for",
      "yet",
      "as",
      "was",
      "beneath",
      "above"
    )
  );

  private static final String BLANK = " ";

  private static final String INV_INDEX_ERR_MSG =
    "Invalid index passed to String utility; check arguments?";

  // Static Methods
  public static String toTitleCase(String str)
  {
    String[] split = cleanSplit(str);
    for (int i = 0; i < split.length; i++)
    {
      String word = split[i];
      if (i == 0 || i == split.length - 1 || !UNCAPITALIZED.contains(word))
        split[i] = capitalize(word);
    }
    return join(" ", split);
  }

  public static String[] cleanSplit(String str)
  {
    return cleanSplit(str, "\\s");
  }

  public static String[] cleanSplit(String str, String sep)
  {
    return collapse(Stream.of(
      str.trim()
        .toLowerCase()
        .split(sep)
    ));
  }

  public static String join(int from, String... arr)
  {
    return join(" ", from, arr.length, arr);
  }

  public static String join(int from, int to, String... arr)
  {
    return join(" ", from, to, arr);
  }

  public static String join(String sep, String... arr)
  {
    return join(sep, 0, arr.length, arr);
  }

  public static String join(String sep, int from, String... arr)
  {
    return join(sep, from, arr.length, arr);
  }

  public static String join(String sep, int from, int to, String... arr)
  {
    if (to > arr.length)
      throw new IndexOutOfBoundsException(INV_INDEX_ERR_MSG);
    return String.join(sep, Arrays.copyOfRange(arr, from, to));
  }

  public static String[] collapse(Collection<String> strings)
  {
    return strings.stream()
      .filter(s -> !s.isEmpty())
      .toList()
      .toArray(new String[0]);
  }

  public static String[] collapse(Stream<String> strings)
  {
    return strings
      .filter(s -> !s.isEmpty())
      .toList()
      .toArray(new String[0]);
  }

  public static String capitalize(String str)
  {
    if (str.isEmpty())
      return str;
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }

  public static boolean matches(String str, String pattern)
  {
    return str.toLowerCase().matches(
      ".*" + pattern.toLowerCase() + ".*"
    );
  }

  private static String widenTo(String str, int width)
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
