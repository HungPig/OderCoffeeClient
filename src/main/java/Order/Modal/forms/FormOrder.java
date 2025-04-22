package Order.Modal.forms;

import Order.Modal.forms.other.Card;
import Order.Modal.layout.ResponsiveLayout;
import Order.Modal.model.ModelEmployee;
import Order.Modal.sample.SampleData;
import Order.Modal.System.Form;
import Order.Modal.utils.SystemForm;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.function.Consumer;

@SystemForm(name = "Order", description = "Order form display some details")
public class FormOrder extends Form {
    private JPanel panelCard;

    public FormOrder() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("wrap,fill", "[fill]", "[grow 0][fill]"));
        add(createInfo());
        add(createOptions());
    }

    private Component createOptions() {
        JPanel panel = new JPanel(new MigLayout("wrap,fill", "[fill]", "[fill]"));
        panel.add(createExample());
        return panel;
    }

    @Override
    public void formInit() {
        // add sample data
        panelCard.removeAll();
        for (ModelEmployee employee : SampleData.getSampleEmployeeData(true)) {
            panelCard.add(new Card(employee, createEventCard()));
        }
        panelCard.repaint();
        panelCard.revalidate();
    }

    private Consumer<ModelEmployee> createEventCard() {
        return e -> {
            JOptionPane.showMessageDialog(this, e.getProfile().getName());
        };
    }

    private JPanel createInfo() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap", "[fill]"));
        JLabel title = new JLabel("Order Form");
        JTextPane text = new JTextPane();
        text.setText("This is a simple Order form that displays Table cards in a scrollable panel.");
        text.setEditable(false);
        text.setBorder(BorderFactory.createEmptyBorder());
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +3");

        panel.add(title);
        panel.add(text, "width 500");
        return panel;
    }



    private Component createExample() {
        ResponsiveLayout responsiveLayout = new ResponsiveLayout(ResponsiveLayout.JustifyContent.FIT_CONTENT, new Dimension(-1, -1), 10, 10);
        panelCard = new JPanel(responsiveLayout);
        panelCard.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:10,10,10,10;");
        JScrollPane scrollPane = new JScrollPane(panelCard);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" +
                "thumbInsets:0,0,0,0;" +
                "width:5;");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" +
                "thumbInsets:0,0,0,0;" +
                "width:5;");
        scrollPane.setBorder(new TitledBorder("Table"));
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(scrollPane);
        splitPane.setRightComponent(Box.createGlue());
        splitPane.setResizeWeight(1);
        return splitPane;
    }
}
