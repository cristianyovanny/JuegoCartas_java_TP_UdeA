package apuntador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JPanel;


public class Jugador {
    
    private int TOTAL_CARTAS = 10;
    private int MARGEN = 20;
    private int DISTANCIA = 50;
    
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    
    private Random r = new Random();
    
    private ArrayList<Carta> totalCartas = new ArrayList<>();
    
    public void repartir(){
        for(int i = 0; i < TOTAL_CARTAS; i++){
            cartas[i] = new Carta(r);
        }
    }
    
    public void mostrar(JPanel pnl){
        
        pnl.removeAll();
        int x = MARGEN;
        
        //Recorrido objetual por una lista de objetos
        for(Carta c:cartas){
            c.mostrar(pnl, x, MARGEN);
            x += DISTANCIA;
        }
        
        pnl.repaint();
    }
    
    public String getGrupos(){
        String mensaje = "No se encontraron grupos";
        int[] contadores = new int[NombreCarta.values().length];
        for(Carta c : cartas){
            contadores[c.getNombre().ordinal()]++;
        }
        
        int totalgrupos = 0;
        for(int c : contadores){
            if(c >= 2) {
                totalgrupos++;
            }
        }
        
        if(totalgrupos > 0){
            mensaje = "Los grupos fueron encontrados:\n";
            for(int i = 0; i < contadores.length; i++){
                if(contadores[i] >= 2){
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                }
            }
        }
        return mensaje;
    }
    public String getEscalera(){
        String mensaje = "";
        Map<Pinta, ArrayList<Carta>> agruparPintas = new HashMap<>();
        Map<Pinta, List<List<Carta>>> mismaPinta = new HashMap<>();
        
        for(Pinta p: Pinta.values()){
            agruparPintas.put(p, new ArrayList<>());
            mismaPinta.put(p, new ArrayList<>());
        }
        for(Carta c: cartas) {
            agruparPintas.get(c.getPinta()).add(c);
            totalCartas.add(c);
        }
        for(Pinta p: Pinta.values()){
            ArrayList<Carta> pintasActuales = agruparPintas.get(p);
            Collections.sort(pintasActuales, Comparator.comparing(c -> c.getNombre().ordinal()));
            ArrayList<Carta> escaleraTemporal = new ArrayList<>();
            for(int i = 0; i < pintasActuales.size() -1; i++) {
                Carta actual = pintasActuales.get(i);
                Carta siguiente = pintasActuales.get(i + 1);
                if(siguiente.getNombre().ordinal() - actual.getNombre().ordinal() == 1) {
                    escaleraTemporal.add(actual);
                    escaleraTemporal.add(siguiente);
                    totalCartas.remove(actual);
                    totalCartas.remove(siguiente);
                } else {
                    if (escaleraTemporal.size() >= 2) {
                        mismaPinta.get(p).add(new ArrayList<>(escaleraTemporal));
                    }
                }
                escaleraTemporal.clear();
            }
            if(escaleraTemporal.size() >= 2) {
                mismaPinta.get(p).add(new ArrayList<>(escaleraTemporal));
            }
            
        }
        for(Pinta p: Pinta.values()) {
            for(List<Carta> escalera : mismaPinta.get(p)){
                mensaje += "Se encontraró una escalera de " + escalera.get(0).getNombre() + " a " + escalera.get(escalera.size() -1).getNombre() + " de " + escalera.get(0).getPinta() + "\n";
            }
            if(mensaje.isEmpty()) {
                mensaje = "No se encontro escaleras";
            }else {
                return mensaje.substring(0, mensaje.length() -1);
            }
        }

        return mensaje;
    }
    
    public String getMensajeCartas() {
        return getGrupos() + "\n" + getEscalera();
    }
    
    public int getPuntaje() {
        int puntaje = 0;
        for(Carta c: totalCartas) {
            if(c.getNombre() == NombreCarta.KING || c.getNombre() == NombreCarta.QUEEN || c.getNombre() == NombreCarta.JACK || c.getNombre() == NombreCarta.AS) {
                puntaje +=10;
            }
            else {
                puntaje += c.getNombre().ordinal() +1;
            }
        }
        totalCartas.clear();
        return puntaje;
    }
    
    public String mensajePuntaje() {
        int puntaje = getPuntaje();
        String resultadoString = "" + puntaje; 
        
        return resultadoString;
    }   
    
    
}