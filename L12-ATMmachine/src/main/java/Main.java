import service.MoneyOperations;
import storage.Banknotes;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //Создание базовых сущностей
        MoneyOperations moneyOperations = new MoneyOperations();


        Map<Banknotes, Integer> map = new HashMap<>();
        map.put(Banknotes.fiftyBill, 10);
        map.put(Banknotes.hundredBill, 10);
        map.put(Banknotes.thousandBill, 10);
        map.put(Banknotes.fiveThousandBill, 10);

        //Заполняем банкомат купюрами
        //Выше по иерархии должно быть api, которое считает сколько и каких купюр сунули в банкомат, а также проверяет каждую купюру.
        // Как вариант можно dto сделать, но с map проще так как Enum в нейминге. В любом случае отталкиваться надо от api купюроприемника.
        moneyOperations.addInATM(map);
        //дергать так же будет апи, которое с разных ячеек разные купюры формирует и положит в лоток для выдачи
        int sum = 7800;
        Map<Banknotes, Integer> banknotes = moneyOperations.getMoney(sum);
        System.out.println("Выдача суммы " + sum + ":");
        banknotes.forEach((key, value) -> {
            System.out.println(key.getValue() + ": " + value);
        });
        System.out.println("Всего денег в банкомате: " + moneyOperations.getAllMoney());

    }
}
