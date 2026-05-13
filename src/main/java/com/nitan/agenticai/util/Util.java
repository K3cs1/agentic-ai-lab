package com.nitan.agenticai.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String RESET = "\u001B[0m";
  private static final String BG = "\u001B[48;5;255m"; // almost white

  private Util() {}
  
  public static void prettyPrint(String label, String message) {
    log.info("{}{}{}: {}{}{}{}", BG, RED, label, BG, GREEN, message, RESET);

  }

  public static void prettyPrintLabel(String label) {
    log.info("{}{}{}", BG, RED, label);
  }

  public static void prettyPrintMessage(String message) {
    log.info("{}{}{}{}", BG, GREEN, message, RESET);
  }
}
