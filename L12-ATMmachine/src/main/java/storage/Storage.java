package storage;

import java.util.LinkedHashMap;
import java.util.Map;

public class Storage {

    //будем считать что тут какая -то сложная логика с хранением купюр.

    private static Storage storage;
    private Map<Banknotes, Integer> storagePack = new LinkedHashMap<>();

    //Костыль чтобы никто не дотянулся до хранилища
    private Map<Banknotes, Integer> storagePackCopy = new LinkedHashMap<>();

    private Storage() {

    }

    //Костыль чтобы никто не дотянулся до хранилища
    public Map<Banknotes, Integer> getStoragePack() {
        return storagePackCopy;
    }

    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
            storage.storagePack.putAll(Banknotes.getAllOrderDesc());
            storage.storagePackCopy.putAll(storage.storagePack);
        }
        return storage;
    }

    public void addInStorage(Banknotes banknotes, Integer count) {
        //Тут всякая сложная система проверки кто вызвал метод и тд и тп, какието может токены и в таком духе
        storagePack.replace(banknotes, storagePack.get(banknotes) + count);
        storagePackCopy.putAll(storagePack);
    }

    public void removeFromStorage(Banknotes banknotes, Integer count) {
        //Тут всякая сложная система проверки кто вызвал метод и тд и тп, какието может токены и в таком духе
        storagePack.replace(banknotes, storagePack.get(banknotes) - count);
        storagePackCopy.putAll(storagePack);
    }

}
