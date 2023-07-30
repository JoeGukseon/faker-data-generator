package com.joe;

import com.github.javafaker.Faker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataGenerator {
    public static void main(String[] args) {
        Faker faker = new Faker();
        int numberOfRecords = 5000; // 데이터 갯수
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv"))) {
//            writer.write("companion_id,created_at,last_modified_at,address,companion_status,content,date,lat,lng,title,member_id,nation_id\n");



            for (int i = 0; i < numberOfRecords; i++) {
                String companionId = String.valueOf(i + 100);
                String createdAt = getRandomDateTimeString(formatter, faker);
                String lastModifiedAt = getRandomDateTimeString(formatter, faker);
                String address = faker.address().fullAddress().replace(",", "");
                String companionStatus = String.valueOf(faker.bool().bool());
                String content = faker.lorem().sentence();
                String date = getRandomDateTimeString(DateTimeFormatter.ofPattern("yyyy-MM-dd"), faker);
                String lat = String.format("%.5f", faker.number().randomDouble(5, -90, 90));
                String lng = String.format("%.5f", faker.number().randomDouble(5, -180, 180));
                String title = faker.lorem().words(3).toString().replace(",", "");;
//                String memberId = String.valueOf(faker.number().randomNumber());
//                String nationId = String.valueOf(faker.number().randomNumber());
                String memberId = String.valueOf(1);
                String nationId = String.valueOf(1);

                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        companionId, createdAt, lastModifiedAt, address, companionStatus,
                        content, date, lat, lng, title, memberId, nationId);

                writer.write(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getRandomDateTimeString(DateTimeFormatter formatter, Faker faker) {
        LocalDateTime randomDateTime = LocalDateTime.now().minusDays(faker.number().numberBetween(0, 365))
                .minusHours(faker.number().numberBetween(0, 24))
                .minusMinutes(faker.number().numberBetween(0, 60))
                .minusSeconds(faker.number().numberBetween(0, 60))
                .minusNanos(faker.number().numberBetween(0, 999999999));
        return randomDateTime.format(formatter);
    }
}