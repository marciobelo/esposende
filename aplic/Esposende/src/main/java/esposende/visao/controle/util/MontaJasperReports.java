package esposende.visao.controle.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class MontaJasperReports {

    private Map<String, Object> parametros;
    private OutputStream outputStream;
    private InputStream inputStream;
    private JRBeanCollectionDataSource ds;

    public MontaJasperReports setDataSource(Collection dataSource) {
        this.ds = new JRBeanCollectionDataSource(dataSource);
        return this;
    }

    public MontaJasperReports addParametro(String key, Object value){
        if(parametros == null) parametros = new HashMap<String, Object>();
        parametros.put(key, value);
        return this;
    }

    public MontaJasperReports setOutputStream(OutputStream outputStream){
        this.outputStream = outputStream;
        return this;
    }

    public MontaJasperReports setInputStream(String relatorioJasper) throws IOException {
        this.inputStream = new ClassPathResource((relatorioJasper.contains(".jasper"))?relatorioJasper:relatorioJasper+".jasper").getInputStream();
        return this;
    }

    public void montaRelatorioJasper() throws JRException {
        JasperRunManager.runReportToPdfStream(inputStream, outputStream, parametros, ds);
    }

}
