package luceneSearcher;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

@SuppressWarnings("serial")
public class createGraph extends ApplicationFrame {

	public createGraph(String applicationTitle, String chartTitle) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createLineChart(chartTitle, "Time (In hours)", "Number of Logs",
				createDataset(), PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

	private DefaultCategoryDataset createDataset() {
		constants x = new constants();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < x.val.length; i++) {
			dataset.addValue(x.val[i], "logs", Integer.toString(i + 1));
		}

		return dataset;
	}

}
