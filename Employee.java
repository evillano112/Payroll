import java.util.*;
import java.text.*;
import java.io.*;

//super-class
public abstract class Employee implements Serializable {
  protected String login;
  protected float salary;
  protected String name;
  protected Date time; 
  protected int id;
  protected static int nextId = 0;
  protected SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss", Locale.ENGLISH);


public Employee(String loginP, float salaryP, String nameP) {
    login = loginP;
    salary = salaryP;
    name = nameP;
    time = new Date();
    id = nextId;
    nextId++;
    
  }
  
  public Employee(String loginP, float salaryP, String nameP, Date timeP, int idP) {
    login = loginP;
    salary = salaryP;
    name = nameP;
    time = timeP;
    id = idP;
    nextId++;
  }

  public void setSalary(float salary) {
    this.salary = salary;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(String.format("%05d\t%s\t%f\t", id, login, salary));
    sb.append(df.format(time));
    sb.append("\t");
    sb.append(name);
    return sb.toString();
  }

  public String getLogin(){
    return login;
  }
  
  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public abstract float getPay();
}