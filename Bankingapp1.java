import java.util.*;

class InvalidEmailException extends Exception{
  public InvalidEmailException(String message){
    super(message);
  }
}

class InvalidAccountException extends Exception{
  public InvalidAccountException(String message){
    super(message);
  }
}

class InvalidAccountNumberException extends Exception{
  public InvalidAccountNumberException(String message){
    super(message);
  }
}

class InvalidNameException extends Exception{
  public InvalidNameException(String message){
    super(message);
  }
}

class InvalidBalanceException extends Exception{
  public InvalidBalanceException(String message){
    super(message);
  }
}

interface LoanService{
    void applyLoan(double amount);
}
abstract class BankAccount{
    private int accountNumber;
    private String holdername;
    protected double balance;
    private String email;

    public BankAccount(int accountNumber,String holdername,double balance,String email)throws InvalidAccountException,InvalidNameException,InvalidBalanceException,InvalidEmailException
    if(accountNumber<=0){
        throw new InvalidAccountNumberException("Account Number must be positive");
    } 
          if(holdername.isEmpty()){
        throw new InvalidNameException("Account Holder Name cannot be empty");
    }
    if(balance<0){
        throw new InvalidBalanceException("Balance cannot be negative");
    }
    if(!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-90.-]+$")){
    throw new InvalidEmailException("Invalid Email Format");
    }
    {
        this.accountNumber=accountNumber;
        this.holdername=holdername;
        this.balance=balance;
        this.email=email;
    }
    public int getAccountNumber(){
        return accountNumber;
    }
    public void deposit(double amount)throws InvalidAmountException
    {
        if(amount<=0){
            throw new InvalidAmountException("Amount must be positive");
        }
        if(amount>0){
            balance += amount;
            System.out.println("Amount Deposited."+amount);
        }
    }
    public void Withdraw(double amount)throws InvalidAmountException
    {
        if(amount<=0){
            throw new InvalidAmountException("Amount must be positive");
        }
        if(amount<=balance){
            balance-=amount;
            System.out.println("Amount Withdraw:"+amount);
        }
        else{
            System.out.println("Insufficient balance");
        }
    }
    public void showdetails(){
        System.out.println("Account Holder Name:"+holdername);
        System.out.println("Account Number:"+accountNumber);
        System.out.println("Email:"+email);
        System.out.println("Balance:"+balance);
    }
    abstract void CalculateInterest();
}

class SavingsAccount extends BankAccount{
    private double interestRate = 5.0;

    public SavingsAccount(int accNo,String name,double balance,String email)throws InvalidAccountException,InvalidNameException,InvalidBalanceException,InvalidEmailException
    {
        super(accNo,name,balance,email);
    }
    @Override
    void CalculateInterest(){
        double intrest = balance * interestRate / 100;
        System.out.println("Intrest :"+intrest);
    }
}

class CurrentAccount extends BankAccount{
    private double overdraftLimit=10000;

    public CurrentAccount(int accNo,String name,double balance,String email){
        super(accNo,name,balance,email);
    }
    @Override
    public void Withdraw(double amount){
        if(amount<=balance+overdraftLimit){
            balance-=amount;
            System.out.println("Amount Withdraw:"+amount);
        }
        else{
            System.out.println("Overdraft limit exceeded.");
        }
    }
    @Override
    void CalculateInterest(){
        System.out.println("No interest rate for Current Account");
    }
}

class BankingApp1{
    static ArrayList<BankAccount> accounts = new ArrayList();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        while(true){
            try{
            System.out.println("=== BANK MENU ===");
            System.out.println("1. Current Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Show Details");
            System.out.println("5. Apply Interest");
            System.out.println("6. Exit");

            int choice = sc.nextInt();

            switch(choice){
                case 1: 
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    Withdraw();
                    break;
                case 4:
                    showAccount();
                    break;
                case 5:
                    applyInterest();
                    break;
                case 6:
                    System.out.println("Exiting..");
                    return;
                default :
                    System.out.println("Invalid choice");
            }
        ``}
        }
    } 
    static void createAccount(){
        System.out.println("1.Savings Account");
        System.out.println("2.Current Account");
        int type = sc.nextInt();

        System.out.println("Enter Account Number:");
        int accNo=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Account Holder Name:");
        String name=sc.nextLine();
        System.out.println("Enter Email:");
        String email=sc.nextLine();
        if(!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            System.out.println("Invalid email format");
            return;
        }
        
        System.out.println("Enter Balance:");
        double balance=sc.nextDouble();
        
        if(type==1){
            accounts.add(new SavingsAccount(accNo,name,balance,email));
        }
        else if(type==2){
            accounts.add(new CurrentAccount(accNo,name,balance,email));
        }
        else{
            System.out.println("Invalid Account Type");
        }
        System.out.println("Account Created Successfully");
    }  
    static BankAccount findAccount(int accNo){
    for(BankAccount account:accounts){
      if(account.getAccountNumber()==accNo){
        return account;
      }
    }
    return null;
  }
  static void deposit(){
    System.out.println("Enter Account Number:");
    int accNo=sc.nextInt();
    BankAccount account=findAccount(accNo);
    if(account==null){
      System.out.println("Account Not Found");
      return;
    }
    System.out.println("Enter Amount:");
    double amount=sc.nextDouble();
    account.deposit(amount);
    System.out.println("Amount Deposited Successfully");
  }
  static void Withdraw(){
    System.out.println("Enter Account Number:");
    int accNo=sc.nextInt();
    BankAccount account=findAccount(accNo);
    if(account!=null){
      System.out.println("Enter Amount:");
      double amount=sc.nextDouble();
      account.Withdraw(amount);
      System.out.println("Amount Withdrawn Successfully");
    }
    else{
        System.out.println("Account Not Found");
    }
  }
  static void showAccount(){
    System.out.println("Enter Account Number:");
    int accNo = sc.nextInt();
    BankAccount account = findAccount(accNo);
    if(account!=null){
        account.showdetails();
    }
    else{
        System.out.println("Account Not Found");
    }
  }

  static void applyInterest(){
    System.out.println("Enter Account Number:");
    int accNo = sc.nextInt();
    BankAccount account = findAccount(accNo);
    if(account!=null){
        account.CalculateInterest();
    }
    else{
        System.out.println("Account Not Found");
    }
  }
}
