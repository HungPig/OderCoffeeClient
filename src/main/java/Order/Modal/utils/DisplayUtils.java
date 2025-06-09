package Order.Modal.utils;

public class DisplayUtils {
    public static String getStatusText(int status) {
        return switch (status) {
            case 1 -> "Còn Hàng";
            case 0 -> "Hết Hàng";
            default -> "Không rõ";
        };
    }

    public static String getCategoryName(int categoryId) {
        return switch (categoryId) {
            case 1 -> "Cà phê";
            case 2 -> "Trà Sữa";
            case 3 -> "Tráng miệng";
            default -> "Không rõ";
        };
    }
}
