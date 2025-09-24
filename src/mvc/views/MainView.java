package mvc.views;

import java.util.Scanner;

public class MainView {
    public String userInputString() {
        var scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int userInputInt() {
        var scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void output(String output) {
        System.out.print(output);
    }

    public void outputln(String output) {
        System.out.println(output);
    }

    public void outputln() {
        System.out.println();
    }

    public void output(int output) {
        System.out.print(output);
    }

    public void outputln(int output) {
        System.out.println(output);
    }
}
