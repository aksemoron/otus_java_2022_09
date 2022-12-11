package storage;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Banknotes {
    fiftyBill(50),
    hundredBill(100),
    thousandBill(1000),
    fiveThousandBill(5000);

    Banknotes(Integer value) {
        this.value = value;
    }

    private final Integer value;


    public Integer getValue() {
        return this.value;
    }

    public static Map<Banknotes, Integer> getAllOrderDesc() {
        Map<Banknotes, Integer> map = new LinkedHashMap<>();
        map.put(fiveThousandBill, 0);
        map.put(thousandBill, 0);
        map.put(hundredBill, 0);
        map.put(fiftyBill, 0);
        return map;
    }
}
