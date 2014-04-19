package org.lemano.pingchartgenerator;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxisLabelFormatter;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.labelformatters.LabelFormatterDate;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Demonstrates minimal effort to create a dynamic chart.
 *
 * @author Achim Westermann
 *
 */
public class PingGraphics {

    static ITrace2D trace = new Trace2DLtd(200);
    static ITrace2D trace2 = new Trace2DLtd(200);
    static ITrace2D trace3 = new Trace2DLtd(200);

    public static JPanel getChart() {
        Chart2D chart = new Chart2D();
        // Create an ITrace:
        // Note that dynamic charts need limited amount of values!!!
        trace.setColor(Color.RED);
        trace2.setColor(Color.BLUE);
        trace3.setColor(Color.orange);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        IAxisLabelFormatter formatter = new LabelFormatterDate(df);
        chart.getAxisX().setFormatter(formatter);

        trace.setName("Google");
        trace2.setName("Roteador");
        trace3.setName("UOL");

        // Add the trace to the chart. This has to be done before adding points
        // (deadlock prevention):
        chart.addTrace(trace2);
        chart.addTrace(trace);
        chart.addTrace(trace3);
        return chart;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MinimalDynamicChart");
        frame.getContentPane().add(getChart());
        frame.setSize(400, 300);
        // Enable the termination button [cross on the upper right edge]:
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
        loop(1000);
    }

    public static void loop(int millis) {
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            private double m_y = 0;
            private long m_starttime = System.currentTimeMillis();
            @Override
            public void run() {
                Integer rand;
                Integer rand1;
                Integer rand2;
                try {
                    rand = getMS(pingGetResponse("www.google.com.br"));
                    rand1 = getMS(pingGetResponse("177.54.146.89"));
                    rand2 = getMS(pingGetResponse("www.uol.com.br"));
                    long millis = new Date().getTime();
                    trace.addPoint(millis, rand);
                    trace2.addPoint(millis, rand1);
                    trace3.addPoint(millis, rand2);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Calendar time = Calendar.getInstance(new Locale("BR"));
        timer.schedule(task, time.getTime(), millis);
    }

    public static Integer getMS(String pingResponse) {
        try {

            if (pingResponse != null && !pingResponse.isEmpty()) {
                for (String parte : pingResponse.split(" ")) {
                    if (parte.contains("tempo")) {
                        String ms = parte.split("=")[1];
                        ms = ms.substring(0, ms.length() - 2);
                        return Integer.parseInt(ms);
                    }
                }
            } else {
                return 0;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private static String pingGetResponse(String host) throws IOException,
            InterruptedException {
        // boolean isWindows =
        // System.getProperty("os.name").toLowerCase().contains("win");

        ProcessBuilder processBuilder = new ProcessBuilder("ping", "-n", "1",
                "-l", "10", host);
        Process proc = processBuilder.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(
                proc.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.contains("Resposta")) {
                builder.append("(" + new Date() + ") " + line);
                break;
            } else if (line.contains("Esgotado")) {
                builder.append("(" + new Date() + ") "
                        + "*******Sem sinal********");
                break;
            }
        }
        String result = builder.toString();
        return result;
    }

    /**
     * Defcon.
     */
    private PingGraphics() {
        // nop
    }
}
