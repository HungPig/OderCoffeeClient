package Order.Modal.component.Chart.renderer;

import Order.Modal.component.Chart.Themes.DefaultChartTheme;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;

public class ChartXYBarRenderer extends ClusteredXYBarRenderer {

    public ChartXYBarRenderer() {
        setBarPainter(DefaultChartTheme.getInstance().getXYBarPainter());
        setShadowVisible(DefaultChartTheme.getInstance().isShadowVisible());
        setMargin(0.3);
    }

    @Override
    public String toString() {
        return "Bar";
    }
}
