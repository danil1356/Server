package org.example.KPRestAPI.Controllers;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.KPRestAPI.Repositories.AffixMorphemesRepository;
import org.example.KPRestAPI.Repositories.AllomorphesRepository;
import org.example.KPRestAPI.Repositories.LanguageRepository;
import org.example.KPRestAPI.Resources.AffixMorphemesResource;
import org.example.KPRestAPI.Resources.AllomorphesResource;
import org.example.KPRestAPI.Resources.LanguageResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/downloadExcel")

public class ExportInExcel {
    private final LanguageRepository languageRepository;
    private final AllomorphesRepository allomorphesRepository;
    private final AffixMorphemesRepository affixMorphemesRepository;

    public ExportInExcel(LanguageRepository languageRepository, AllomorphesRepository allomorphesRepository, AffixMorphemesRepository affixMorphemesRepository) {
        this.languageRepository = languageRepository;
        this.allomorphesRepository = allomorphesRepository;
        this.affixMorphemesRepository = affixMorphemesRepository;
    }

    @RequestMapping("/allomorphAffix")
    void get1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("allomorph_affix");

            LanguageResource[] lang = Arrays.stream(languageRepository.select())
                    .map(entity -> {
                        LanguageResource resource = new LanguageResource(entity);
                        return resource;
                    })
                    .toArray(LanguageResource[]::new);
            String[] headers = new String[lang.length];

            AllomorphesResource[] allomorph = Arrays.stream(allomorphesRepository.select())
                    .map(entity -> {
                        AllomorphesResource resource = new AllomorphesResource(entity);
                        return resource;
                    })
                    .toArray(AllomorphesResource[]::new);

            AffixMorphemesResource[] affix = Arrays.stream(affixMorphemesRepository.select())
                .map(entity -> {
                    AffixMorphemesResource resource = new AffixMorphemesResource(entity);
                    return resource;
                })
                .toArray(AffixMorphemesResource[]::new);

            for(int i=0; i<lang.length; i++)
            {
                headers[i] = lang[i].getName();
            }



        for (int i = 0; i <= allomorph.length; i++)
        {
            Row row = sheet.createRow(i);
            for (int j=0; j <= headers.length; j++)
            {
                Cell cell = row.createCell(j);
                if(row.getRowNum() == 0 )
                {
                    if(cell.getColumnIndex() == 0)
                    {
                        cell.setCellValue(new HSSFRichTextString(" "));
                    }
                    else
                    {
                        HSSFRichTextString text = new HSSFRichTextString(headers[j-1]);
                        cell.setCellValue(text);
                    }
                }
                else
                {
                    if(row.getRowNum() > 0 && cell.getColumnIndex() == 0)
                    {
                        HSSFRichTextString text = new HSSFRichTextString(allomorph[i-1].getValue());
                        cell.setCellValue(text);
                    }
                    else
                    {
                        for (int r=0; r< affix.length; r++)
                        {
                            if(allomorph[i-1].getId_affix() == affix[r].getId())
                            {
                                if(lang[j-1].getId() == affix[r].getId_language())
                                {
                                    HSSFRichTextString text = new HSSFRichTextString(affix[r].getName());
                                    cell.setCellValue(text);
                                }
                            }
                        }
                    }
                }
            }
        }
        //
        // Меняем размер столбца
        sheet.autoSizeColumn(4);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename="+"alomorph_on_affix.xls");//Имя файла Excel
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Записать содержимое рабочей книги в выходной поток
        book.write(response.getOutputStream());
        response.setContentType("application/octet-stream");
    }


}
