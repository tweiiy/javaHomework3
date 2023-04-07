package org.example;

import java.util.Random;
import java.util.Scanner;
public class Main {
    public static int SIZE = 5;
    public static int DOTS_TO_WIN = 5;
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static char[][] map;
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();
    public static void main(String[] args) {
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победил Искуственный Интеллект");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра закончена");
    }
    public static boolean checkWin(char dot) {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (checkLine(x, y, 1, 0, dot)) return true;   // проверка горизонтали
                if (checkLine(x, y, 1, 1, dot)) return true;   // проверка диагонали (главная)
                if (checkLine(x, y, 0, 1, dot)) return true;   // проверка вертикали
                if (checkLine(x, y, 1, -1, dot)) return true;  // проверка диагонали (побочная)
            }
        }
        return false;
    }
    public static boolean checkLine(int x, int y, int vx, int vy, char dot) {
        int farX = x + (DOTS_TO_WIN - 1) * vx;   // вычисляем границы линии
        int farY = y + (DOTS_TO_WIN - 1) * vy;

        // проверяем, не выходит ли линия за границы поля
        if (farX < 0 || farX >= SIZE || farY < 0 || farY >= SIZE) {
            return false;
        }

        for (int i = 0; i < DOTS_TO_WIN; i++) {   // проверяем линию на наличие выигрышной комбинации
            if (map[y + i * vy][x + i * vx] != dot) {
                return false;
            }
        }
        return true;
    }
    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }
    public static void aiTurn() {
        int x, y;
        // проверяем, есть ли у игрока выигрышный ход
        for (y = 0; y < SIZE; y++) {
            for (x = 0; x < SIZE; x++) {
                if (isCellValid(x, y)) {
                    map[y][x] = DOT_X;
                    if (checkWin(DOT_X)) {
                        map[y][x] = DOT_O;
                        System.out.println("Компьютер блокирует ход игрока");
                        return;
                    }
                    map[y][x] = DOT_EMPTY;
                }
            }
        }
        // иначе, ходим случайным образом
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isCellValid(x, y));
        System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y +
                1));
        map[y][x] = DOT_O;
    }
    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y)); // while(isCellValid(x, y) == false)
        map[y][x] = DOT_X;
    }
    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        return map[y][x] == DOT_EMPTY;
    }
    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }
    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
