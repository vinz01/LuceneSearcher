package vitHack;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

@SuppressWarnings("serial")
public class createChart extends JFrame {
	public createChart(String appTitle,String chartTitle,Double val1) {
		PieDataset dataset = createDataset(val1,100-val1);
		JFreeChart chart = createChart(dataset,chartTitle);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(700,400));
		setContentPane(chartPanel);
	}
	
	private PieDataset createDataset(Double val1,Double val2) {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Errors(abnormal/error/failed/invalid)", val1);
		result.setValue("Other Log activities",  val2);
		
		return result;
		
	}
	
	private JFreeChart createChart(PieDataset dataset,String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset,true,true,false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

}
