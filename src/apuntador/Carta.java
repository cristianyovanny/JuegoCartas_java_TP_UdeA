package apuntador;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class Carta {
    
    private int indice;
    
    public Carta(Random r){
        indice = r.nextInt(52) + 1;
    }
    public void mostrar(JPanel pnl, int x, int y){
        //Obtener el nombre del archivo de la imagen de cada carta
        String nombreImagen = "/imagenes/CARTA" + String.valueOf(indice) + ".JPG";
        
        //Cargar la imagen en memoria
        ImageIcon imagen = new ImageIcon(getClass().getResource(nombreImagen));
        
        //Crear la instancia del objeto JLABEL
        JLabel lbl = new JLabel(imagen);
       
        //Definir las coordenas de despliegue y el ancho y el alto
        lbl.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
        
        //Agregar la etiqueta al panel
        pnl.add(lbl);
    }
    
    public Pinta getPinta(){
        if(indice <= 13)
            return Pinta.TREBOL;
        else if(indice <= 26)
            return Pinta.PICA;
        else if(indice <= 39)
            return Pinta.CORAZON;
        else
            return Pinta.DIAMANTE;
    }
    
    public NombreCarta getNombre(){
        int residuo = indice % 13;
        if(residuo == 0){
            residuo = 13;
        }
        return NombreCarta.values()[residuo - 1];
    }
}
