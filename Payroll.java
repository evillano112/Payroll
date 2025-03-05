import java.util.*;

import java.text.*;
import java.io.*;
public class Payroll{

  private ArrayList<Employee> employeesArray = new ArrayList<Employee>();
  private ArrayList<Employee> employeesLeftArray = new ArrayList<Employee>();
  private Employee currentUser;
  private int currentUserId = -1;
  private String currentName;
  private Scanner keyboard = new Scanner(System.in);
  private Scanner fSc;
  private PrintWriter pw;
  private PrintWriter pwPayroll;
  private SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss", Locale.ENGLISH);
  private static String menu = ("\nPayroll Menu\n\t1. Log In" + "\n\t2. Enter employees\n\t3. List Employees"
      + "\n\t4. Change employee data" + "\n\t5. Terminate employees"
      + "\n\t6. Pay employees \n\t0. Exit system\n\tEnter Choice: ");
  private static String dataMenu = ("\n\t1. Change salary\n\t2. Change name\n\t0. Exit");
  private static String subclassMenu = ("\nIs the employee salaried or hourly?\n\t1. Salaried\n\t2. Hourly");
  private static String quitMenu = ("\t1. Yes, I want to quit\n\t2. No, I don't want to quit");
  public Payroll() {

    
    try{
      int choice;
      fSc = new Scanner(new File ("Employees.obj"));
      Date timeFile = new Date();
      for(; fSc.hasNextLine();) {
        int idFile = fSc.nextInt();
        String loginFile = fSc.next();
        float salaryFile = fSc.nextFloat();
        String time = fSc.next();
        try{
        timeFile = df.parse(time);
        } catch (ParseException e){
          System.out.println("error");
        }
        String nameFile = fSc.nextLine();
        choice = 0;
        System.out.println("Current employee:\t" + nameFile + subclassMenu);
        choice = keyboard.nextInt();
        switch(choice){
          case 1:
            employeesArray.add(new Salaried(loginFile, salaryFile, nameFile.stripLeading(), timeFile, idFile));
            break;
          case 2:
            employeesArray.add(new Hourly(loginFile, salaryFile, nameFile.stripLeading(), timeFile, idFile));
            break;
          default:
            
            fSc.close();
        
      }

      }
    }catch(Exception e) {
      
      System.out.println("Employee file not found.\n");
      System.out.println("The Boss can now sign in:");
      System.out.print("Enter new login name:\t");
      String loginBoss = keyboard.next();
      System.out.print("Enter your salary:\t");
      float salaryBoss = keyboard.nextFloat();
      keyboard.nextLine();
      System.out.print("Enter your name:\t");
      String nameBoss = keyboard.nextLine();

      currentUser = new Salaried(loginBoss, salaryBoss, nameBoss);
      employeesArray.add(currentUser);
      
    }
  }
  
public void doMenu() throws FileNotFoundException{
    int choice;
    try{
       for (;;) {
        System.out.println(menu);
        choice = keyboard.nextInt();
        switch (choice) {
          case 0:
           System.out.println("Exiting System");
           break;
          case 1:
            doLogin();
            break;
          case 2:
            newEmployee();
            break;
          case 3:
            listEmployees();
            break;
          case 4:
           changeEmployeeData();
            break;
          case 5:
           terminateEmployees();
            break;
          case 6:
            payEmployees();
            break;
          default:
            System.out.println("Invalid input, try again");
            break;
        }
        
        if (choice == 0){
          break;
        }
      }
      
    } finally {
      pw = new PrintWriter(new File("Employees.obj"));
      for(Employee e: employeesArray){
        pw.println(e);
      }
      pw.close();
      System.out.println("Employees written to file successfully");
      keyboard.close();
      
    }
  }

  private void doLogin(){
    System.out.print("Enter your login name:\t");
    String loginIn = keyboard.next();
    for(Employee e: employeesArray){
      if (e.getLogin().equals(loginIn)){
        currentUser = e;
        currentUserId = e.getId();
        currentName = e.getName();
        return;   
      } 
    }
    System.out.println("Login name not found");
  }

  private void newEmployee(){
    if(currentUserId == 0){
      System.out.print("Enter new login name:\t");
      String newLogin = keyboard.next();
      for(Employee e: employeesArray){
        if (e.getLogin().equals(newLogin)){
          System.out.println(e.getLogin() + newLogin);
          System.out.println("Error! Login " + newLogin + " already exists!");
          return;
        }
      }
      System.out.print("Enter new pay:\t");
      float newSalary = keyboard.nextFloat();
      keyboard.nextLine();
      System.out.print("Enter new name:\t");
      String newName = keyboard.nextLine();
      System.out.print(subclassMenu + "\n");
      int choice = keyboard.nextInt();
      
      switch (choice) {
        case 1:
          employeesArray.add(new Salaried(newLogin, newSalary, newName));
          break;
        case 2:
          employeesArray.add(new Hourly(newLogin, newSalary, newName));
      }
      
    } else {
      System.out.println("Cannot enter new employees: not logged in as boss");
    }
  }
  
  private void listEmployees(){
    System.out.println();
    if(currentUserId == 0){
      for(Employee e: employeesArray){
        System.out.println(e);
      }
    } else {
      System.out.println(currentUser);
    }
  }

  private void changeEmployeeData(){
    System.out.println();
    if(currentUserId == 0){
      System.out.println("What employee will you like to change?\nEnter employee id:\t");
      int idChosen = keyboard.nextInt();
      System.out.println(dataMenu);
      int choice = keyboard.nextInt();
      float newSalary = 0;
      String newName = "";
      switch(choice){
        case 1:
          System.out.println("\nWhat is the new salary?");
          newSalary = keyboard.nextFloat();
          break;
        case 2:
          System.out.println("\nWhat is the new name?");
          newName = keyboard.nextLine();
          break;
      }
      int i = -1;
      for(Employee e: employeesArray){
        ++i;
        if (idChosen == e.getId()){
          switch(choice){
            case 1:
              e.setSalary(newSalary);
              break;
            case 2:
              e.setName(newName);
              break;
          }
          employeesArray.set(i, e);
          break;
        } 
      }
    }else {
      System.out.println("Cannot change employee data: not logged in as boss");
    }
    
  }
  
  private void terminateEmployees(){
    if(currentUserId == 0){
      System.out.println("What employee will you like to terminate?\nEnter employee id:\t");
      int idChosen = keyboard.nextInt();
      int i = -1;
      for(Employee e: employeesArray){
        ++i;
        if (idChosen == e.getId()){
          employeesArray.remove(i);
          employeesLeftArray.add(e);
          break;
        }
       }
      System.out.println("Employee terminated!");
      } else {
      System.out.println("Logged in as " + currentName + " do you want to quit your job?");
      System.out.println(quitMenu);
      int choice = keyboard.nextInt();
      if (choice == 1){
          employeesArray.remove(currentUserId);
          employeesLeftArray.add(currentUser);

      }
    }
    }

  private void payEmployees() throws FileNotFoundException{
    if (currentUserId == 0){
      pwPayroll = new PrintWriter(new File("payroll.txt"));
      String out;
      float pays[] = new float[employeesArray.size()];
      int i = 0;
        //prompts "pays" before printing, its a bit redundant, but it looks ugly to ask for hours worked in the middle of the table
      for(Employee e: employeesArray){
        pays[i] = e.getPay();
        i++;
      }
      System.out.println("\nPay             ID      Name");
      pwPayroll.println("\nPay             ID      Name");
      i = 0;
      for(Employee e: employeesArray){
        out = String.format("%-15.2f %05d   %s", pays[i], e.getId(), e.getName());
        System.out.println(out);
        pwPayroll.println(out);
        i++;
      }
      pwPayroll.close();
      System.out.println();
    } else {
      System.out.println("Unable to perform action: not logged in as boss");
    }
    
    
  }
    }
  
