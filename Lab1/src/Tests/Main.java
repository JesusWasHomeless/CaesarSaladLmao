package Tests;

import CaesarEncryptionAndDecryption.EncryptionAndDecryption;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws Exception {
        //���� �����
        Scanner sc = new Scanner(System.in);

        System.out.println("������� ����:");
        int number = sc.nextInt();
        System.out.println("������� ������� �� " + number + " �����! ������ �������� � ���� OutEncrypted.txt!");
        EncryptionAndDecryption book = new EncryptionAndDecryption();
        book.encrypt(number);
        book.decrypt();
    }
}

