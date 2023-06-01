import java.util.Random;
import org.apache.commons.math4.legacy.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RegresionLineal {

    public static void main(String[] args) {
        // Generar datos aleatorios
        Random random = new Random();
        int dataSize = 100; // Tamaño de los datos
        double[] x = new double[dataSize];
        double[] y = new double[dataSize];
        for (int i = 0; i < dataSize; i++) {
            x[i] = random.nextDouble() * 10; // Generar número aleatorio entre 0 y 10 para x
            y[i] = 2 * x[i] + random.nextGaussian(); // Generar número aleatorio para y con una distribución gaussiana
        }

        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < dataSize; i++) {
            regression.addData(x[i], y[i]);
        }

        double slope = regression.getSlope();
        double intercept = regression.getIntercept();
        System.out.println("Slope: " + slope);
        System.out.println("Intercept: " + intercept);

        // Crear la serie de datos para los puntos
        XYSeries series = new XYSeries("Data");
        for (int i = 0; i < dataSize; i++) {
            series.add(x[i], y[i]);
        }

        // Crear la serie de datos para la línea de regresión
        XYSeries regressionLine = new XYSeries("Regression Line");
        for (int i = 0; i < dataSize; i++) {
            regressionLine.add(x[i], regression.predict(x[i]));
        }

        // Crear la colección de series de datos
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(regressionLine);

        // Crear el gráfico de dispersión con línea de regresión
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Linear Regression", // Título del gráfico
                "X", // Etiqueta del eje X
                "Y", // Etiqueta del eje Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Obtener el objeto Plot y personalizarlo
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false); // Ocultar líneas entre los puntos de datos
        renderer.setSeriesLinesVisible(1, true); // Mostrar línea de regresión
        renderer.setSeriesShapesVisible(1, false); // Ocultar puntos en la línea de regresión
        plot.setRenderer(renderer);

        // Mostrar el gráfico en una ventana
        ChartFrame frame = new ChartFrame("Linear Regression", chart);
        frame.pack();
        frame.setVisible(true);
    }
}