/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team2.courseManagement;

public class App {
  public String getMessage() {
    return "Hello from the courseManagement.";
  }

  public static void main(String[] args) {
    App a = new App();
    System.out.println(a.getMessage());
    for (int i = 0; i < args.length; i++) {
      System.out.println("args[" + i + "]=" + args[i]);
    }
  }
}
