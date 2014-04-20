/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lemano.pingchartgenerator;

import info.monitorenter.gui.chart.ITrace2D;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.lemano.pingchartgenerator.model.Dominio;

/**
 *
 * @author Kevim
 */
public class Tasker extends TimerTask {

    private Map<String, Integer> pico = new HashMap<String, Integer>();
    private Map<String, Integer> status = new HashMap<String, Integer>();
    private Map<String, Integer> perdas = new HashMap<String, Integer>();
    private Map<String, List<Integer>> dados = new HashMap<String,List<Integer>>();
    List<Dominio> dominios = new ArrayList<Dominio>();
    JPanel panelStatus;

    public Tasker() {
    }

    public Tasker(List<Dominio> dominios, JPanel panelStatus) {
        this.dominios = dominios;
        this.panelStatus = panelStatus;
        run();
    }

    @Override
    public void run() {
        Integer rand = 0;
        try {
            long millis = new Date().getTime();
            for (Dominio d : dominios) {
                ITrace2D trace = d.getTrace();
                rand = getMS(pingGetResponse(d.getEndereco()));
                if (pico.get(d.getNome()) == null) {
                    pico.put(d.getNome(), rand);
                } else {
                    Integer ultimo = pico.get(d.getNome());
                    if (rand>ultimo) {
                        pico.put(d.getNome(), rand);
                    }
                }
                if(rand==0){
                    Integer perda = perdas.get(d.getNome());
                    if(perda==null){
                        perdas.put(d.getNome(),1);
                    }else{
                        perdas.put(d.getNome(),perda+1);
                    }
                }
                if(dados.get(d.getNome())==null){
                    dados.put(d.getNome(),new ArrayList<Integer>());
                }else{
                    if(dados.get(d.getNome()).size()>5){
                        Integer media = getMedia(dados.get(d.getNome())).intValue();
                        dados.put(d.getNome(),new ArrayList<Integer>());
                        dados.get(d.getNome()).add(media);
                    }
                    dados.get(d.getNome()).add(rand);
                }
                status.put(d.getNome(), rand);
                trace.addPoint(millis, rand);
                atualizaStatus(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public Float getMedia(List<Integer> dados){
        float soma=0;
        for(Integer i:dados){
            soma+=i;
        }
        return (soma/dados.size());
    }
    
    public void atualizaStatus(Dominio d) {
        String picoComp = "pico_" + d.getNome();
        String msComp = "txt_" + d.getNome();
        String perdaComp = "perda_" + d.getNome();
        String mediaComp = "media_" + d.getNome();
        String nomeDominio = d.getNome();
        for (Component c : panelStatus.getComponents()) {
            if (msComp.equals(c.getName())) {
                JLabel l = (JLabel) c;
                Integer latency = status.get(nomeDominio);
                String ms = latency+"ms";
                l.setForeground(getDinamicColor(latency));
                l.setText(ms);
            }else if (picoComp.equals(c.getName())) {
                JLabel l = (JLabel) c;
                Integer latency = pico.get(nomeDominio);
                String ms = latency+"ms";
                l.setForeground(getDinamicColor(latency));
                l.setText(ms);
            }else if (perdaComp.equals(c.getName())) {
                JLabel l = (JLabel) c;
                Integer perda = perdas.get(nomeDominio);
                String ms = "";
                if (perda==null) {
                    ms = "0";
                }else{
                    ms=""+perda;
                }
                l.setText(ms);
            }else if (mediaComp.equals(c.getName())) {
                JLabel l = (JLabel) c;
                Float latency = getMedia(dados.get(nomeDominio));
                DecimalFormat formattter = new DecimalFormat("0.00");
                
                String ms = formattter.format(latency)+"ms";
//                l.setForeground(getDinamicColor(latency));
                l.setText(ms);
            }
        }
    }

    public Color getDinamicColor(int value){
        if(value<=40){
            return new Color(32, 74,25);
        }else{
            return Color.RED;
        }
    }
    
    public Integer getMS(String pingResponse) {
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

    private String pingGetResponse(String host) throws IOException,
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

    public Map<String, Integer> getPico() {
        return pico;
    }

    public void setPico(Map<String, Integer> pico) {
        this.pico = pico;
    }

    public Map<String, Integer> getStatus() {
        return status;
    }

    public void setStatus(Map<String, Integer> status) {
        this.status = status;
    }

}
