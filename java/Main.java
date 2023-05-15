import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<HashMap<String, Object>> accounts = new ArrayList();

    public static void main(String[] args){
        // 매뉴
        menu();
    }
    public static void menu() {
        boolean menuFlag = true;
        String input;
        while (menuFlag) {
            System.out.println("======Bank Menu======");
            System.out.println("1. 계좌개설");
            System.out.println("2. 입금하기");
            System.out.println("3. 출금하기");
            System.out.println("4. 전체조회");
            System.out.println("5. 계좌이체");
            System.out.println("6. 프로그램 종료");
            System.out.println("=====================");
            System.out.print("입력: ");

            input = sc.nextLine();

            if (input.equals("1")) {
                makeAccount();
            }
            else if (input.equals("2")) {
                deposit();
            }
            else if (input.equals("3")) {
                withdraw();
            }
            else if (input.equals("4")) {
                showAllAccount();
            }
            else if (input.equals("5")) {
                transfer();
            }
            else if (input.equals("6")) {
                break;
            }
            else {
                System.out.println("옳바른 입력이 아닙니다. 다시 입력해주세요.");
            }
        }
    }
    public static void makeAccount(){
        String input = "";
        String accountName = "", name;
        Boolean isValidAccount = false;
        Boolean isValidMoney = false;
        int money = 0;
        HashMap account = new HashMap();

        while (!isValidAccount) {
            System.out.print("계좌번호를 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input)) { //TODO: 중복 계정 검증
                if (getIndexAccount(input) != -1) {
                    System.out.println("중복된 계좌번호 입니다.");
                }
                else {
                    isValidAccount = true;
                    accountName = input;
                }
            } else {
                System.out.println("옳바른 입력이 아닙니다. 다시 입력해주세요.");
            }
        }

        System.out.print("이름을 입력해주세요: ");
        name = sc.nextLine();

        while (!isValidMoney) {
            System.out.print("예금 금액을 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input) && Integer.parseInt(input) > 0) {
                isValidMoney = true;
                money = Integer.parseInt(input);
            }
            else {
                System.out.println("유효하지 않은 값입니다. 다시 입력해주세요.");
            }
        }

        account.put("accountName", accountName);
        account.put("userName", name);
        account.put("money", money);
        accounts.add(account);


        System.out.println("\n성공적으로 계좌가 개설되었습니다.");
        System.out.println("=====================");

        showAccount(getIndexAccount(accountName));
    }

    public static void deposit() {
        String input = "";
        int depositMoney = 0;
        int accountIndex = 0;

        while (true) {
            System.out.print("입금하실 계좌번호를 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input) && getIndexAccount(input) > -1) {
                accountIndex = getIndexAccount(input);
                showAccount(accountIndex);
                break;
            }
            else {
                System.out.println("유효하지 않은 계좌번호 입니다.");
            }
        }

        while (true) {
            System.out.print("입금 금액을 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input) && Integer.parseInt(input) > 0) {
                depositMoney = Integer.parseInt(input);
                accounts.get(accountIndex).put("money",(int) accounts.get(accountIndex).get("money") + depositMoney);
                showAccount(accountIndex);
                break;
            }
            else {
                System.out.println("올바르지 않은 입금 금액입니다.");
            }
        }
        System.out.println("정상적으로 입금되었습니다.");
        System.out.println("=====================");
    }

    public static void withdraw(){
        String input = "";
        int withdrawMoney = 0;
        int accountIndex = 0;

        while (true) {
            System.out.print("출금하실 계좌번호를 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input) && getIndexAccount(input) > -1) {
                accountIndex = getIndexAccount(input);
                if ((int) accounts.get(accountIndex).get("money") <= 0) {
                    showAccount(accountIndex);
                    System.out.println("잔액이 부족해 출금하실 수 없습니다");
                    return;
                }
                showAccount(accountIndex);
                break;
            }
            else {
                System.out.println("유효하지 않은 계좌번호 입니다.");
            }
        }

        while (true) {
            System.out.print("출금 금액을 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input) && Integer.parseInt(input) > 0) {
                withdrawMoney = Integer.parseInt(input);
                if ((int) accounts.get(accountIndex).get("money") < withdrawMoney) {
                    System.out.println("금액이 부족합니다.");
                }
                else {
                    accounts.get(accountIndex).put("money",(int) accounts.get(accountIndex).get("money") - withdrawMoney);
                    showAccount(accountIndex);
                }

                break;
            }
            else {
                System.out.println("올바르지 않은 출금 금액입니다.");
            }
        }
        System.out.println("정상적으로 출금되었습니다.");
        System.out.println("=====================");
    }

    public static void transfer(){
        String input = "";
        int transferMoney = 0;
        int accountSendIndex = 0;
        int accountReceiveIndex = 0;

        while (true) {
            System.out.print("본인의 계좌번호를 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input) && getIndexAccount(input) > -1) {
                accountSendIndex = getIndexAccount(input);
                if ((int) accounts.get(accountSendIndex).get("money") <= 0) {
                    showAccount(accountSendIndex);
                    System.out.println("잔액이 부족해 이체하실 수 없습니다");
                    return;
                }
                break;
            }
            else {
                System.out.println("유효하지 않은 계좌번호 입니다.");
            }
        }

        while (true) {
            System.out.print("이체할 계좌번호를 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input) && getIndexAccount(input) > -1) {
                accountReceiveIndex = getIndexAccount(input);
                if (accountSendIndex == accountReceiveIndex) {
                    System.out.println("본인에게 이체할 수 없습니다.");
                } else {
                    break;
                }
            }
            else {
                System.out.println("유효하지 않은 계좌번호 입니다.");
            }
        }

        while (true) {
            System.out.print("이체할 금액을 입력해주세요: ");
            input = sc.nextLine();
            if (isDigit(input) && Integer.parseInt(input) > 0) {
                transferMoney = Integer.parseInt(input);
                if ((int) accounts.get(accountSendIndex).get("money") < transferMoney) {
                    System.out.println("금액이 부족합니다.");
                }
                else {
                    accounts.get(accountSendIndex).put("money",(int) accounts.get(accountSendIndex).get("money") - transferMoney);
                    accounts.get(accountReceiveIndex).put("money",(int) accounts.get(accountReceiveIndex).get("money") + transferMoney);

                    showAccount(accountSendIndex);
                    break;
                }

            }
            else {
                System.out.println("올바르지 않은 이체 금액입니다.");
            }
        }
        System.out.println("정상적으로 송금되었습니다.");
        System.out.println("=====================");
    }

    public static void showAllAccount(){
        System.out.println("\n=====================");
        for (int i = 0; i < accounts.size(); i++) {
            HashMap cur = accounts.get(i);
            System.out.println((i + 1) + ". " + "계좌번호: " + cur.get("accountName") + " / 이름: " + cur.get("userName") + " / 잔액: " + cur.get("money"));
        }
        System.out.println("=====================\n");

    }

    public static int getIndexAccount(String accountName) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).get("accountName").equals(accountName)) {
                return i;
            }
        }
        return -1;
    }

    public static void showAccount(int accountIndex){
        System.out.println("계좌이름: " + accounts.get(accountIndex).get("accountName"));
        System.out.println("계좌잔고: " + accounts.get(accountIndex).get("money"));
        System.out.println("=====================");
        System.out.println();
    }


    public static Boolean isDigit(String temp){
        for (int i = 0; i < temp.length(); i++) {
            if (!Character.isDigit(temp.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
