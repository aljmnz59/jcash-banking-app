import service.BankService;

import java.util.*;

public class Main {
    static void main(String[] args) {
        BankService bankService = new BankService();
        Scanner scanner = new Scanner(System.in);

        while(!bankService.isLoggedIn()){
            System.out.print("Mobile number: ");
            String inputMobile = scanner.next();
            System.out.print("PIN: ");
            int inputPin = scanner.nextInt();

//            boolean loginSuccess = bankService.userLogin(inputMobile, inputPin);
//            if(!loginSuccess && inputMobile.isEmpty()){
//                System.out.println("Exiting application");
//                return;
//            }
        }

        boolean running = true;
        while(running){
            System.out.println("\nWelcome to Jcash App");
            System.out.println("********************");
            System.out.println("1. Check Balance");
            System.out.println("2. Cash-In Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Check Transaction History");
            System.out.println("5. Logout");
            System.out.print("Select type of transaction: ");

            int choice = scanner.nextInt();
            switch(choice){
                case 1 :
                    bankService.displayBalance();
                    break;
                case 2 :
//                    System.out.print("Enter amount to Cash-In: ");
//                    try{
//                        double cashInAmount = scanner.nextDouble();
//                        bankService.cashIn(cashInAmount);
//                    }catch(InputMismatchException e){
//                        System.out.println("Error: Please input valid amount");
//                    }
//                    break;
                case 3 :
                    System.out.print("Enter Receiver's Mobile Number: ");
                    String targetMobile = scanner.next();
                    System.out.print("Enter amount to Transfer: ");
                    try {
                        double transferAmount = scanner.nextDouble();
                        bankService.transfer(targetMobile, transferAmount);
                    } catch(NumberFormatException e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    break;
                case 4 :
                    bankService.showTransaction();
                    break;
                case 5 :
                    bankService.logout();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select between 1 and 5");
            }
        }
    }
}
