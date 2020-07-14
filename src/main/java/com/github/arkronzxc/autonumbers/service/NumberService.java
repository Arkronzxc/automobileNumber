package com.github.arkronzxc.autonumbers.service;

import com.github.arkronzxc.autonumbers.repository.AutomobileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Map.entry;

@Slf4j
@Service
public class NumberService {

    @Value("${number.suffix}")
    private String numberSuffix;

    private final AutomobileRepository repository;

    private static final String WHITESPACE = " ";

    private final Map<Integer, Character> letterMap = Map.ofEntries(
            entry(0, 'А'),
            entry(1, 'В'),
            entry(2, 'Е'),
            entry(3, 'К'),
            entry(4, 'М'),
            entry(5, 'Н'),
            entry(6, 'О'),
            entry(7, 'Р'),
            entry(8, 'С'),
            entry(9, 'Т'),
            entry(10, 'У'),
            entry(11, 'Х')
    );

    private final int mapSize = letterMap.size();

    private final Random random = new Random();

    private final AtomicInteger currentNumber = new AtomicInteger(0);

    public NumberService(AutomobileRepository repository) {
        this.repository = repository;
    }

    public String generateRandomNumber() {

        Integer code;
        int counter = 0;
        do {
            counter++;
            code = random.nextInt(1000000);

        } while (repository.hasKey(code) && counter < 10);
        if (counter == 10) {
            throw new IllegalArgumentException("too many invocation of random number");
        }

        String automobileNumber = generateNumber(code);
        if (!repository.setIfAbsent(code, automobileNumber)) {
            throw new IllegalArgumentException("can not create new automobile number");
        }
        return automobileNumber;
    }

    public String generateNextNumber() {
        Integer code = currentNumber.getAndAdd(1);
        String automobileNumber = generateNumber(code);
        repository.set(code, automobileNumber);
        return automobileNumber;
    }

    private String generateNumber(Integer code) {
        char[] charArrayNumber = new char[6];
        for (int i = 0; i < 3; i++) {
            charArrayNumber[3 - i] = Character.forDigit(code % 10, 10);
            code /= 10;
        }
        charArrayNumber[5] = letterMap.get(code % mapSize);
        code /= mapSize;
        charArrayNumber[4] = letterMap.get(code % mapSize);
        code /= mapSize;
        charArrayNumber[0] = letterMap.get(code % mapSize);

        return String.valueOf(charArrayNumber) + WHITESPACE + numberSuffix;
    }
}
