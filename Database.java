public abstract class Database {
    abstract void add(String name, String number);

    abstract void delete(String name);

    abstract void update(String name, String number);

    abstract String search(String name);
}