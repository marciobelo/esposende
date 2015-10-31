package esposende.visao.controle.converter;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StringDateConverter extends PropertyEditorSupport{

    public void setAsText(String text){
        Date data;
        try {
            data = (text.isEmpty())?null:new SimpleDateFormat("dd/MM/yyyy").parse(text);
        } catch (ParseException e) {
            data = null;
        }

        setValue(data);
    }

    public String getAsText(){
        String data;
        try {
            data = new SimpleDateFormat("dd/MM/yyyy").format(getValue());
        } catch (Exception e) {
            data = "";
        }

        return data;
    }

}
