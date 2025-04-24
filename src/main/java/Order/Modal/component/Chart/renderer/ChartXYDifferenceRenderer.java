package Order.Modal.component.Chart.renderer;

import Order.Modal.component.Chart.Themes.DefaultChartTheme;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;

import java.awt.*;

public class ChartXYDifferenceRenderer extends XYDifferenceRenderer {

    public ChartXYDifferenceRenderer() {
        setPositivePaint(DefaultChartTheme.getColor(0));
        setNegativePaint(DefaultChartTheme.getColor(1));
        setAutoPopulateSeriesStroke(false);
        setDefaultStroke(new BasicStroke(0f));
    }

    @Override
    public String toString() {
        return "Different";
    }
}
