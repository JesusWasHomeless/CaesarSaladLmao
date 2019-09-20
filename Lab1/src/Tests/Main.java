package Tests;

import CaesarEncryptionAndDecryption.EncryptionAndDecryption;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws Exception {
        //Ввод числа
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите ключ:");
        int number = sc.nextInt();
        System.out.println("Алфавит сдвинут на " + number + " ячеек! Теперь смотрите в файл OutEncrypted.txt!");
        EncryptionAndDecryption book = new EncryptionAndDecryption();
        book.encrypt(number);
        book.decrypt();
    }
}

