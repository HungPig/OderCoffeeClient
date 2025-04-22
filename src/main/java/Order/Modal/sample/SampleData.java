package Order.Modal.sample;

import Order.Modal.model.ModelEmployee;
import Order.Modal.model.ModelProfile;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.TableXYDataset;
import raven.extras.AvatarIcon;


import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SampleData {

    public static List<ModelEmployee> getSampleEmployeeData(boolean defaultIcon) {
        List<ModelEmployee> list = new ArrayList<>();
        list.add(new ModelEmployee("15-April-2025", 1200, "Barista", "Specialist in espresso-based drinks with excellent customer service skills.", new ModelProfile(getProfileIcon("profile_1.jpg", defaultIcon), "Liam Nguyen", "Hanoi")));
        list.add(new ModelEmployee("10-April-2025", 1350, "Shift Supervisor", "Leads daily operations and maintains smooth service flow.", new ModelProfile(getProfileIcon("profile_2.jpg", defaultIcon), "Emma Tran", "Da Nang")));
        list.add(new ModelEmployee("05-April-2025", 1100, "Cashier", "Efficient in order handling and managing payments with accuracy.", new ModelProfile(getProfileIcon("profile_3.jpg", defaultIcon), "Noah Le", "Ho Chi Minh City")));
        list.add(new ModelEmployee("28-March-2025", 1500, "Store Manager", "Oversees daily store performance and staff training.", new ModelProfile(getProfileIcon("profile_4.jpg", defaultIcon), "Olivia Pham", "Hue")));
        list.add(new ModelEmployee("25-March-2025", 1250, "Coffee Roaster", "Experienced in roasting beans to desired flavor profiles.", new ModelProfile(getProfileIcon("profile_5.jpg", defaultIcon), "William Vo", "Nha Trang")));
        list.add(new ModelEmployee("20-March-2025", 1400, "Marketing Lead", "Drives promotional campaigns and customer engagement.", new ModelProfile(getProfileIcon("profile_6.jpg", defaultIcon), "Sophia Do", "Can Tho")));
        list.add(new ModelEmployee("18-March-2025", 1150, "Inventory Staff", "Handles stock control and supply ordering efficiently.", new ModelProfile(getProfileIcon("profile_7.jpg", defaultIcon), "James Bui", "Vung Tau")));
        list.add(new ModelEmployee("15-March-2025", 1300, "Baker", "Prepares fresh pastries and baked goods daily.", new ModelProfile(getProfileIcon("profile_8.jpg", defaultIcon), "Mia Nguyen", "Hai Phong")));
        list.add(new ModelEmployee("12-April-2025", 1550, "Trainer", "Trains new baristas and ensures beverage quality.", new ModelProfile(getProfileIcon("profile_9.jpg", defaultIcon), "Lucas Dang", "Bien Hoa")));
        return list;
    }


    public static List<ModelEmployee> getSampleBasicEmployeeData() {
        List<ModelEmployee> list = new ArrayList<>();
        list.add(new ModelEmployee("15-April-2025", 1200, "Barista", "Specialist in espresso-based drinks with excellent customer service skills.", new ModelProfile(null, "Liam Nguyen", "Hanoi")));
        list.add(new ModelEmployee("10-April-2025", 1350, "Shift Supervisor", "Leads daily operations and maintains smooth service flow.", new ModelProfile(null, "Emma Tran", "Da Nang")));
        list.add(new ModelEmployee("05-April-2025", 1100, "Cashier", "Efficient in order handling and managing payments with accuracy.", new ModelProfile(null, "Noah Le", "Ho Chi Minh City")));
        list.add(new ModelEmployee("28-March-2025", 1500, "Store Manager", "Oversees daily store performance and staff training.", new ModelProfile(null, "Olivia Pham", "Hue")));
        list.add(new ModelEmployee("25-March-2025", 1250, "Coffee Roaster", "Experienced in roasting beans to desired flavor profiles.", new ModelProfile(null, "William Vo", "Nha Trang")));
        list.add(new ModelEmployee("20-March-2025", 1400, "Marketing Lead", "Drives promotional campaigns and customer engagement.", new ModelProfile(null, "Sophia Do", "Can Tho")));
        list.add(new ModelEmployee("18-March-2025", 1150, "Inventory Staff", "Handles stock control and supply ordering efficiently.", new ModelProfile(null, "James Bui", "Vung Tau")));
        list.add(new ModelEmployee("15-March-2025", 1300, "Baker", "Prepares fresh pastries and baked goods daily.", new ModelProfile(null, "Mia Nguyen", "Hai Phong")));
        list.add(new ModelEmployee("12-April-2025", 1550, "Trainer", "Trains new baristas and ensures beverage quality.", new ModelProfile(null, "Lucas Dang", "Bien Hoa")));
        return list;
    }

    public static TableXYDataset getTimeSeriesDataset() {
        TimeTableXYDataset dataset = new TimeTableXYDataset();
        String seriesIncome = "Income";

        dataset.add(new Month(3, 2024), 181.8, seriesIncome);
        dataset.add(new Month(4, 2024), 167.3, seriesIncome);
        dataset.add(new Month(5, 2024), 153.8, seriesIncome);
        dataset.add(new Month(6, 2024), 167.6, seriesIncome);
        dataset.add(new Month(7, 2024), 158.8, seriesIncome);
        dataset.add(new Month(8, 2024), 148.3, seriesIncome);
        dataset.add(new Month(9, 2024), 153.9, seriesIncome);
        dataset.add(new Month(10, 2024), 142.7, seriesIncome);
        dataset.add(new Month(11, 2024), 123.2, seriesIncome);
        dataset.add(new Month(12, 2024), 131.8, seriesIncome);
        dataset.add(new Month(1, 2025), 139.6, seriesIncome);
        dataset.add(new Month(2, 2025), 142.9, seriesIncome);
        dataset.add(new Month(3, 2025), 138.7, seriesIncome);
        dataset.add(new Month(4, 2025), 137.3, seriesIncome);
        dataset.add(new Month(5, 2025), 143.9, seriesIncome);

        String seriesExpense = "Expense";
        dataset.add(new Month(3, 2024), 129.6, seriesExpense);
        dataset.add(new Month(4, 2024), 123.2, seriesExpense);
        dataset.add(new Month(5, 2024), 117.2, seriesExpense);
        dataset.add(new Month(6, 2024), 124.1, seriesExpense);
        dataset.add(new Month(7, 2024), 122.6, seriesExpense);
        dataset.add(new Month(8, 2024), 119.2, seriesExpense);
        dataset.add(new Month(9, 2024), 116.5, seriesExpense);
        dataset.add(new Month(10, 2024), 112.7, seriesExpense);
        dataset.add(new Month(11, 2024), 101.5, seriesExpense);
        dataset.add(new Month(12, 2024), 106.1, seriesExpense);
        dataset.add(new Month(1, 2025), 125.2, seriesExpense);
        dataset.add(new Month(2, 2025), 111.7, seriesExpense);
        dataset.add(new Month(3, 2025), 111.0, seriesExpense);
        dataset.add(new Month(4, 2025), 109.6, seriesExpense);
        dataset.add(new Month(5, 2025), 113.2, seriesExpense);


        return dataset;
    }

    public static CategoryDataset getCategoryDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // series key
        String series1 = "Order";
        String series2 = "Discount";
        String series3 = "Refund";
        String series4 = "Custom";

// product names
        String product1 = "Espresso";
        String product2 = "Latte";
        String product3 = "Pastry";


        // product 1
        dataset.addValue(200, product1, series1);
        dataset.addValue(50, product1, series2);
        dataset.addValue(80, product1, series3);
        dataset.addValue(150, product1, series4);

        // product 2
        dataset.addValue(50, product2, series1);
        dataset.addValue(180, product2, series2);
        dataset.addValue(250, product2, series3);
        dataset.addValue(230, product2, series4);

        // product 3
        dataset.addValue(180, product3, series1);
        dataset.addValue(100, product3, series2);
        dataset.addValue(250, product3, series3);
        dataset.addValue(80, product3, series4);

        return dataset;
    }

    public static PieDataset getPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Espresso", 30);
        dataset.setValue("Cappuccino", 25);
        dataset.setValue("Latte", 18);
        dataset.setValue("Americano", 12);
        return dataset;
    }

    public static OHLCDataset getOhlcDataset() {
        Date[] date = new Date[47];
        double[] high = new double[47];
        double[] low = new double[47];
        double[] open = new double[47];
        double[] close = new double[47];
        double[] volume = new double[47];
        int jan = 1;
        int feb = 2;
        // Dữ liệu cho các ngày trong tháng 1 năm 2025
        date[0] = createOhlcData(2025, jan, 1, 12, 0);
        high[0] = 47.0;
        low[0] = 33.0;
        open[0] = 35.0;
        close[0] = 33.0;
        volume[0] = 100.0;
        date[1] = createOhlcData(2025, jan, 2, 12, 0);
        high[1] = 47.0;
        low[1] = 32.0;
        open[1] = 41.0;
        close[1] = 37.0;
        volume[1] = 150.0;

        date[2] = createOhlcData(2025, jan, 3, 12, 0);
        high[2] = 49.0;
        low[2] = 43.0;
        open[2] = 46.0;
        close[2] = 48.0;
        volume[2] = 70.0;

        date[3] = createOhlcData(2025, jan, 4, 12, 0);
        high[3] = 51.0;
        low[3] = 39.0;
        open[3] = 40.0;
        close[3] = 47.0;
        volume[3] = 200.0;

        date[4] = createOhlcData(2025, jan, 5, 12, 0);
        high[4] = 60.0;
        low[4] = 40.0;
        open[4] = 46.0;
        close[4] = 53.0;
        volume[4] = 120.0;

        date[5] = createOhlcData(2025, jan, 6, 12, 0);
        high[5] = 62.0;
        low[5] = 55.0;
        open[5] = 57.0;
        close[5] = 61.0;
        volume[5] = 110.0;

        date[6] = createOhlcData(2025, jan, 7, 12, 0);
        high[6] = 65.0;
        low[6] = 56.0;
        open[6] = 62.0;
        close[6] = 59.0;
        volume[6] = 70.0;

        date[7] = createOhlcData(2025, jan, 8, 12, 0);
        high[7] = 55.0;
        low[7] = 43.0;
        open[7] = 45.0;
        close[7] = 47.0;
        volume[7] = 20.0;

        date[8] = createOhlcData(2025, jan, 9, 12, 0);
        high[8] = 54.0;
        low[8] = 33.0;
        open[8] = 40.0;
        close[8] = 51.0;
        volume[8] = 30.0;

        date[9] = createOhlcData(2025, jan, 10, 12, 0);
        high[9] = 47.0;
        low[9] = 33.0;
        open[9] = 35.0;
        close[9] = 33.0;
        volume[9] = 100.0;

        date[10] = createOhlcData(2025, jan, 11, 12, 0);
        high[10] = 48.0;
        low[10] = 34.0;
        open[10] = 42.0;
        close[10] = 46.0;
        volume[10] = 140.0;

        date[11] = createOhlcData(2025, jan, 12, 12, 0);
        high[11] = 45.0;
        low[11] = 38.0;
        open[11] = 40.0;
        close[11] = 42.0;
        volume[11] = 95.0;

        date[12] = createOhlcData(2025, jan, 13, 12, 0);
        high[12] = 43.0;
        low[12] = 30.0;
        open[12] = 35.0;
        close[12] = 40.0;
        volume[12] = 130.0;

        date[13] = createOhlcData(2025, jan, 14, 12, 0);
        high[13] = 51.0;
        low[13] = 45.0;
        open[13] = 47.0;
        close[13] = 50.0;
        volume[13] = 110.0;

        date[14] = createOhlcData(2025, jan, 15, 12, 0);
        high[14] = 60.0;
        low[14] = 50.0;
        open[14] = 55.0;
        close[14] = 58.0;
        volume[14] = 90.0;

        date[15] = createOhlcData(2025, jan, 16, 12, 0);
        high[15] = 63.0;
        low[15] = 53.0;
        open[15] = 58.0;
        close[15] = 61.0;
        volume[15] = 80.0;

        date[16] = createOhlcData(2025, jan, 17, 12, 0);
        high[16] = 59.0;
        low[16] = 48.0;
        open[16] = 51.0;
        close[16] = 57.0;
        volume[16] = 100.0;

        date[17] = createOhlcData(2025, jan, 18, 12, 0);
        high[17] = 62.0;
        low[17] = 55.0;
        open[17] = 59.0;
        close[17] = 60.0;
        volume[17] = 110.0;

        date[18] = createOhlcData(2025, jan, 19, 12, 0);
        high[18] = 64.0;
        low[18] = 57.0;
        open[18] = 61.0;
        close[18] = 63.0;
        volume[18] = 90.0;

        date[19] = createOhlcData(2025, jan, 20, 12, 0);
        high[19] = 68.0;
        low[19] = 60.0;
        open[19] = 65.0;
        close[19] = 67.0;
        volume[19] = 85.0;

        date[20] = createOhlcData(2025, jan, 21, 12, 0);
        high[20] = 72.0;
        low[20] = 65.0;
        open[20] = 68.0;
        close[20] = 70.0;
        volume[20] = 95.0;

        date[21] = createOhlcData(2025, jan, 22, 12, 0);
        high[21] = 74.0;
        low[21] = 67.0;
        open[21] = 70.0;
        close[21] = 72.0;
        volume[21] = 80.0;

        date[22] = createOhlcData(2025, jan, 23, 12, 0);
        high[22] = 76.0;
        low[22] = 69.0;
        open[22] = 72.0;
        close[22] = 74.0;
        volume[22] = 75.0;

        date[23] = createOhlcData(2025, jan, 24, 12, 0);
        high[23] = 80.0;
        low[23] = 72.0;
        open[23] = 74.0;
        close[23] = 78.0;
        volume[23] = 65.0;

        date[24] = createOhlcData(2025, jan, 25, 12, 0);
        high[24] = 82.0;
        low[24] = 75.0;
        open[24] = 76.0;
        close[24] = 80.0;
        volume[24] = 60.0;

        date[25] = createOhlcData(2025, jan, 26, 12, 0);
        high[25] = 85.0;
        low[25] = 77.0;
        open[25] = 78.0;
        close[25] = 83.0;
        volume[25] = 55.0;

        date[26] = createOhlcData(2025, jan, 27, 12, 0);
        high[26] = 87.0;
        low[26] = 80.0;
        open[26] = 82.0;
        close[26] = 86.0;
        volume[26] = 50.0;

        date[27] = createOhlcData(2025, jan, 28, 12, 0);
        high[27] = 90.0;
        low[27] = 83.0;
        open[27] = 85.0;
        close[27] = 89.0;
        volume[27] = 45.0;

        date[28] = createOhlcData(2025, jan, 29, 12, 0);
        high[28] = 92.0;
        low[28] = 84.0;
        open[28] = 88.0;
        close[28] = 91.0;
        volume[28] = 40.0;

        date[29] = createOhlcData(2025, jan, 30, 12, 0);
        high[29] = 95.0;
        low[29] = 86.0;
        open[29] = 89.0;
        close[29] = 93.0;
        volume[29] = 35.0;

        date[30] = createOhlcData(2025, jan, 31, 12, 0);
        high[30] = 97.0;
        low[30] = 88.0;
        open[30] = 90.0;
        close[30] = 96.0;
        volume[30] = 30.0;
// Dữ liệu cho các ngày trong tháng 2 năm 2025
        date[31] = createOhlcData(2025, feb, 1, 12, 0);
        high[31] = 99.0;
        low[31] = 90.0;
        open[31] = 92.0;
        close[31] = 98.0;
        volume[31] = 25.0;

        date[32] = createOhlcData(2025, feb, 2, 12, 0);
        high[32] = 100.0;
        low[32] = 92.0;
        open[32] = 95.0;
        close[32] = 99.0;
        volume[32] = 20.0;

        date[33] = createOhlcData(2025, feb, 3, 12, 0);
        high[33] = 101.0;
        low[33] = 94.0;
        open[33] = 96.0;
        close[33] = 100.0;
        volume[33] = 15.0;

        date[34] = createOhlcData(2025, feb, 4, 12, 0);
        high[34] = 103.0;
        low[34] = 96.0;
        open[34] = 98.0;
        close[34] = 102.0;
        volume[34] = 10.0;

        date[35] = createOhlcData(2025, feb, 5, 12, 0);
        high[35] = 104.0;
        low[35] = 97.0;
        open[35] = 99.0;
        close[35] = 103.0;
        volume[35] = 12.0;

        date[36] = createOhlcData(2025, feb, 6, 12, 0);
        high[36] = 106.0;
        low[36] = 99.0;
        open[36] = 101.0;
        close[36] = 105.0;
        volume[36] = 14.0;

        date[37] = createOhlcData(2025, feb, 7, 12, 0);
        high[37] = 108.0;
        low[37] = 100.0;
        open[37] = 102.0;
        close[37] = 107.0;
        volume[37] = 13.0;

        date[38] = createOhlcData(2025, feb, 8, 12, 0);
        high[38] = 110.0;
        low[38] = 101.0;
        open[38] = 104.0;
        close[38] = 109.0;
        volume[38] = 18.0;

        date[39] = createOhlcData(2025, feb, 9, 12, 0);
        high[39] = 112.0;
        low[39] = 103.0;
        open[39] = 105.0;
        close[39] = 111.0;
        volume[39] = 17.0;

        date[40] = createOhlcData(2025, feb, 10, 12, 0);
        high[40] = 113.0;
        low[40] = 104.0;
        open[40] = 107.0;
        close[40] = 112.0;
        volume[40] = 16.0;

        date[41] = createOhlcData(2025, feb, 11, 12, 0);
        high[41] = 115.0;
        low[41] = 106.0;
        open[41] = 108.0;
        close[41] = 114.0;
        volume[41] = 19.0;

        date[42] = createOhlcData(2025, feb, 12, 12, 0);
        high[42] = 117.0;
        low[42] = 108.0;
        open[42] = 110.0;
        close[42] = 116.0;
        volume[42] = 20.0;

        date[43] = createOhlcData(2025, feb, 13, 12, 0);
        high[43] = 118.0;
        low[43] = 109.0;
        open[43] = 112.0;
        close[43] = 117.0;
        volume[43] = 22.0;

        date[44] = createOhlcData(2025, feb, 14, 12, 0);
        high[44] = 120.0;
        low[44] = 110.0;
        open[44] = 113.0;
        close[44] = 119.0;
        volume[44] = 25.0;

        date[45] = createOhlcData(2025, feb, 15, 12, 0);
        high[45] = 122.0;
        low[45] = 112.0;
        open[45] = 115.0;
        close[45] = 121.0;
        volume[45] = 28.0;

        date[46] = createOhlcData(2025, feb, 16, 12, 0);
        high[46] = 123.0;
        low[46] = 113.0;
        open[46] = 116.0;
        close[46] = 122.0;
        volume[46] = 27.0;

        return new DefaultHighLowDataset("Series 1", date, high, low, open, close, volume);
    }

    private static Date createOhlcData(int y, int m, int d, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, d, hour, min);
        return calendar.getTime();
    }

    private static Icon getProfileIcon(String name, boolean defaultIcon) {
        if (defaultIcon) {
            return new ImageIcon(SampleData.class.getResource("/raven/modal/demo/images/" + name));
        } else {
            AvatarIcon avatarIcon = new AvatarIcon(SampleData.class.getResource("/raven/modal/demo/images/" + name), 55, 55, 3f);
            avatarIcon.setType(AvatarIcon.Type.MASK_SQUIRCLE);
            return avatarIcon;
        }
    }
}
