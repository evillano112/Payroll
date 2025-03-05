import java.util.*;

//sub class of employee

public class Hourly extends Employee {
  private float hoursWorked;
  private Scanner keyboard = new Scanner(System.in);
  

  
  public Hourly(String loginP, float salaryP, String nameP){
    super(loginP, salaryP, nameP);
  
    
  }
  public Hourly(String loginP, float salaryP, String nameP, Date timeP, int idP){
  super(loginP, salaryP, nameP, timeP, idP);
  }

  public float getPay(){
    System.out.println("Hour many hours did " + name + " work? ");
    hoursWorked = keyboard.nextFloat();
    return (hoursWorked * salary);
  }

}