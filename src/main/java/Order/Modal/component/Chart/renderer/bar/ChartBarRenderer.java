package Order.Modal.component.Chart.renderer.bar;

import Order.Modal.component.Chart.Themes.ChartDrawingSupplier;
import org.jfree.chart.renderer.category.BarRenderer;

public class ChartBarRenderer extends BarRenderer {

    public ChartBarRenderer() {
        initStyle();
    }

    private void initStyle() {
        setDefaultLegendShape(ChartDrawingSupplier.getDefaultShape());
    }

    @Override
    public String toString() {
        return "Bar";
    }
}
