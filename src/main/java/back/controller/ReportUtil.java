package back.controller;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Component
public class ReportUtil implements Serializable {

    public byte[] gerarRelatorio(List listDados, String relatorio, ServletContext servletContext)
            throws Exception {

        /* 1. Cria a fonte de dados */
        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDados);

        /* 2. Ajuste do nome e carregamento do arquivo */
        // O caminho deve começar com / e estar dentro de src/main/resources
        String nomeArquivo = "/relatorios/" + relatorio + ".jasper";
        InputStream inputStream = this.getClass().getResourceAsStream(nomeArquivo);

        if (inputStream == null) {
            throw new FileNotFoundException("Erro: O arquivo [" + nomeArquivo + "] não foi encontrado. " +
                    "Verifique se o arquivo RelatorioPessoa.jasper está na pasta src/main/resources/relatorios/");
        }

        /* 3. Preenche o relatório */
        // JasperFillManager aceita o InputStream diretamente
        JasperPrint impressoraJasper = JasperFillManager.fillReport(inputStream, new HashMap<>(), jrbcds);

        /* 4. Exporta para PDF */
        return JasperExportManager.exportReportToPdf(impressoraJasper);
    }
}