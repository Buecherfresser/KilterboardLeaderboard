package com.jonasdrechsel.kilterboardleaderboard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManualTesting {
    public static void main(String[] args) {
        DateTimeFormatter kilterFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime climbDate = LocalDateTime.parse("2023-03-10 16:56:43", kilterFormatter);
        System.out.println(climbDate);
    }
}
