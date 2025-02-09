package com.cestec.parametros;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;


public class prmv003 {

    public static String validaData(String data) {
		if(data == null){
            JOptionPane.showMessageDialog(null, "Campo Data não inserido", "Erro!!", JOptionPane.ERROR_MESSAGE);
            return "erro";
        }
        else if(isValidDateFormat(data)){
            JOptionPane.showMessageDialog(null, 
                                         "Campo Data Não Esta no Formato Adequado \n "
                                       + "Favor Verificar se segue o Padrão 00/00/2000",
                                          "Erro!!", JOptionPane.ERROR_MESSAGE);
            return "erro";
        }
        return "ok";
	}

    public static boolean isValidDateFormat(String dateString) {
        String dataValidacao = "^([0-2][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        
        Pattern pattern = Pattern.compile(dataValidacao);
        Matcher matcher = pattern.matcher(dateString);
        if (!matcher.matches()) {            
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateString);  
            return true; 
        } catch (ParseException e) {
            return false;
        }
    }
}
