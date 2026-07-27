package com.nitan.agenticai.service;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class HumanApprovalService {

  public boolean requestApproval(String reason) {
    System.out.println("--- HUMAN REVIEW REQUIRED---");
    System.out.println(reason);
    System.out.print("Approve this request? (y/n): ");

    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine().trim().toLowerCase();
    return input.equals("y") || input.equals("yes");
  }
}