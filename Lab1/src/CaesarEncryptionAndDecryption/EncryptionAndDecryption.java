package CaesarEncryptionAndDecryption;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class EncryptionAndDecryption {
    //Инициализация массивов под алфавит:
    private float[] alphabet1 = new float[64]; //Исходный алфавит, [А-Я, а-я]
    private float[] alphabet2 = new float[64]; //Алфавит на выходе

//=======================================================Метод шифрования алгоритмом Цезаря с задаваемым пользователем сдвигом - shift:=============================================

    public void encrypt (int shift, String nameIn, String nameOut) throws Exception{

        int letter; //индекс буквы

        //Открываем поток чтения файла и задаём для данного потока кодировку UTF-8:
        FileInputStream file = new FileInputStream(nameIn);
        InputStreamReader text = new InputStreamReader(file,"UTF-8");


        //Открываем поток записи с кодировкой UTF-8
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(nameOut), StandardCharsets.UTF_8);

        //Алгоритм шифрования:
        //1. Читаем файл посимвольно до конца. Конец и есть (-1)
        //2. Если у нас русскоязычная буква (Диапазон от 1040 до 1103, Ё не включена в диапазон), то инкрементируем количество этой буквы в массиве алфавит (считаем количество)
        //3. После делаем саму зашифровку с помощью сдвига. Если сдвигом мы вышли за грани алфавита русского, то циклим сдвиг на начало алфавита.
        //4. Знаки препинания не затрагиваются, иные буквы - тоже
        //5. После того как получили зашифрованую букву мы инкрементируем количество этой буквы в тексте во втором массиве алфавита
        //6. Запись символа в файл

        while ((letter=text.read())!=-1){
            if (letter>=1040&letter<=1103) {
                alphabet1[letter - 1040]++;
                letter += shift;
                if (letter > 1103) letter -= 64;
                 alphabet2[letter - 1040]++;
            }
            writer.write(letter); //запись зашифрованного текста в файл
        }
        //Закрываем потоки
        text.close();
        file.close();
        writer.close();
    }

    //==================================================================================================================================================================================
//=======================================================Метод дешифрирования алгоритмом Цезаря частотным анализом: ================================================================
    //Метод дешифрирования шифра Цезаря частотным анализом:
    public void  decrypt () throws Exception {

        //Инициализация массива для индексов, чьи частоты совпали
        int[] find = new int[64];

        //Открываем поток чтения и отправляем в него ЗАШИФРОВАННЫЙ!!! файл с кодировкой UTF-8:
        FileInputStream fileFull = new FileInputStream("C:/Cryptography/InFull.txt");
        InputStreamReader textFull = new InputStreamReader(fileFull, "UTF-8");


        //Открываем поток записи с кодировкой UTF-8 итогового дешифрованного текста:
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("C:/Cryptography/OutDecrypted.txt"), StandardCharsets.UTF_8);


        Map<Character, Double> mapEnc = analize("C:/Cryptography/OutEncrypted.txt");
        Map<Character, Double> mapFull = analize("C:/Cryptography/InFull.txt");

        Character maxFull = 'a';
        Double maxFullEl = -1.0;
        Character maxEnc  = 'a';
        Double maxEncEl = -1.0;

        for(Character c : mapEnc.keySet()) {
           if(mapEnc.get(c) > maxEncEl){
               maxEnc = c;
               maxEncEl = mapEnc.get(c);
           }
        }

        for(Character c : mapFull.keySet()) {
            if(mapFull.get(c) > maxFullEl){
                maxFull = c;
                maxFullEl = mapFull.get(c);
            }
        }

        int shift = 32 + (int)maxFull - (int)maxEnc;

        encrypt(shift, "C:/Cryptography/OutEncrypted.txt", "C:/Cryptography/OutDecrypted.txt");

        System.out.println(maxEnc);
        System.out.println(maxFull);
        System.out.println("shift = " + shift);
               //Закрываем потоки
        //text.close();
        //file.close();
        writer.close();

    }

    Map<Character, Double> analize(String name) throws IOException {

        FileInputStream file = new FileInputStream(name);//"C:/Cryptography/OutEncrypted.txt"
        InputStreamReader text = new InputStreamReader(file, "UTF-8");

        int letter, count = 0;
        Map<Character, Double> m = new HashMap<>();

        while ((letter = text.read()) != -1) {
            if (letter >= 1040 & letter <= 1103) {
                if(m.containsKey((char)letter)){
                    m.put((char)letter, m.get((char)letter) + 1);
                } else {
                    m.put((char)letter, 1.0);
                }
                count++;
            }
            //writer.write(letter);
        }

        for(Character c : m.keySet()) {
            m.put(c, m.get(c) / count);
        }
        System.out.println(m);
        System.out.println(count);
        text.close();
        file.close();
        return m;
    }
    //==================================================================================================================================================================================



}

