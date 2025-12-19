package com.auth.ums.Utility;

import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utility {
        // Returns a default username
        public static String getDefaultUsername() {
            return "SYSTEM";
        }

        // Returns today's date (YYYY-MM-DD)
        public static LocalDate getCurrentDate() {
            return LocalDate.now();
        }

        // Returns current date & time (YYYY-MM-DDTHH:MM:SS)
        public static LocalDateTime getCurrentDateTime() {
            return LocalDateTime.now();
        }

//    public static String hashToken(String token) {
//        return DigestUtils
//                .sha256Hex(token);
//        }
    }


