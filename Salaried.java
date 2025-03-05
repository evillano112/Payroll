import java.util.*;

//sub class of employee

public class Salaried extends Employee {

  
  public Salaried(String loginP, float salaryP, String nameP){
    super(loginP, salaryP, nameP);
  }

  public Salaried(String loginP, float salaryP, String nameP, Date timeP, int idP){
    super(loginP, salaryP, nameP, timeP, idP);
  }
  public float getPay(){
    return salary/24;
  }
  public String toString() {
    StringBuilder sb = new StringBuilder(String.format("%05d\t%s\t%f\t", id, login, salary));
    sb.append(df.format(time));
    sb.append("\t");
    sb.append(name);
    return sb.toString();
  }
}