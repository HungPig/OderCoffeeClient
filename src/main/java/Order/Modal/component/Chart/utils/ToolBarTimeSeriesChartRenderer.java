package Order.Modal.component.Chart.utils;

import Order.Modal.component.Chart.TimeSeriesChart;
import Order.Modal.component.Chart.renderer.*;
import Order.Modal.component.ToolBarSelection;
import org.jfree.chart.renderer.xy.XYItemRenderer;

public class ToolBarTimeSeriesChartRenderer extends ToolBarSelection<XYItemRenderer> {

    public ToolBarTimeSeriesChartRenderer(TimeSeriesChart chart) {
        super(getRenderers(), renderer -> {
            chart.setRenderer(renderer);
        });
    }

    private static XYItemRenderer[] getRenderers() {
        XYItemRenderer[] renderers = new XYItemRenderer[]{
                new ChartXYCurveRenderer(),
                new ChartXYLineRenderer(),
                new ChartXYBarRenderer(),
                new ChartStackedXYBarRenderer(),
                new ChartDeviationStepRenderer(),
                new ChartXYDifferenceRenderer()
        };
        return renderers;
    }
}
