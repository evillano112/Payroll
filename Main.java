/**
Payroll system for company

  @author Edward Villano
  @version 1.0
  @date 2/27/23

*/
public class Main {
  public static void main(String[] args){
    
    System.out.println("\nEdward Villano - Payroll Phase 2\n");
    try {
      Payroll pr = new Payroll();
      pr.doMenu();
    }catch (Exception e){
      System.out.println(e);
    }
  }
}