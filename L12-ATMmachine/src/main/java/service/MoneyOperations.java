package service;

import exaptions.InvalidInputException;
import storage.Banknotes;
import storage.Storage;

import java.util.HashMap;
import java.util.Map;

public class MoneyOperations {

    private final Storage storage = Storage.getStorage();

    public void addInATM(Map<Banknotes, Integer> money) {
        money.forEach((key, value) -> {
            if (value < 0) {
                throw new InvalidInputException("Ошибка внесения " + value + " купюр");
            }
        });
        money.forEach(storage::addInStorage);
    }

    public Map<Banknotes, Integer> getMoney(Integer sumMoney) {
        Map<Banknotes, Integer> map = new HashMap<>(Banknotes.getAllOrderDesc());

        for (var entry : storage.getStoragePack().entrySet()) {
            while (entry.getValue() > 0) {
                if (sumMoney - entry.getKey().getValue() < 0) {
                    break;
                }
                sumMoney -= entry.getKey().getValue();
                map.replace(entry.getKey(), map.get(entry.getKey()) + 1);
            }

        }
        if (sumMoney > 0) {
            throw new InvalidInputException("Нет купюр для выдачи " + sumMoney + ". Операция отменена");
        }
        map.forEach(storage::removeFromStorage);
        return map;
    }

    public Integer getAllMoney() {
        int sum = 0;
        for (var entry : storage.getStoragePack().entrySet()) {
            sum += entry.getValue() * entry.getKey().getValue();
        }
        return sum;
    }

}
