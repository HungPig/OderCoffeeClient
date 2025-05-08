package Order.Modal.utils;

public class ComboOrder {
    private String key;
    private String value;

    public ComboOrder(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public String getValue() { return value; }

    @Override
    public String toString() {
        return value; // hiển thị text trên comboBox
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComboOrder) {
            ComboOrder other = (ComboOrder) obj;
            return key.equals(other.key); // so sánh bằng key
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
