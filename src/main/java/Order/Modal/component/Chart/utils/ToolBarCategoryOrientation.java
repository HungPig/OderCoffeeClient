package Order.Modal.component.Chart.utils;

import Order.Modal.component.ToolBarSelection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class ToolBarCategoryOrientation extends ToolBarSelection<String> {

    public ToolBarCategoryOrientation(JFreeChart chart) {
        super(new String[]{"Vertical", "Horizontal"}, orientation -> {
            chart.getCategoryPlot().setOrientation(orientation == "Horizontal" ? PlotOrientation.HORIZONTAL : PlotOrientation.VERTICAL);
        });
    }
}
