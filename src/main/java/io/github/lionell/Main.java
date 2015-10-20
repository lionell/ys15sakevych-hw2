package io.github.lionell;

import ua.yandex.shad.autocomplete.PrefixMatches;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.print("> ");
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.load("one oneapple onedrive");
        while (!userInput.equals("exit")) {
            for (String str : prefixMatches.wordsWithPrefix(userInput)) {
                System.out.println(str);
            }
            System.out.print("> ");
            userInput = in.nextLine();
        }
    }
}
