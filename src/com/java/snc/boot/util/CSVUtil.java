package snc.boot.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.junit.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CSVUtil {
    private static Logger log = Logger.getLogger(CSVUtil.class);
    public static void writeCSV(String type, List<String[]> data){
        File file = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String dt = simpleDateFormat.format(date);
        switch (type) {
            case "class":
                String classfile = dt+"_class.csv";
                file = new File(classfile);
                if (!file.exists())
                    file.mkdir();
                write(classfile,data);
                break;
            case "shop":
                String shopfile = dt+"_shop.csv";
                file = new File(shopfile);
                if (!file.exists())
                    file.mkdir();
                write(shopfile,data);
                break;
            case "gift":
                String giftfile = dt+"_gift.csv";
                file = new File(giftfile);
                if (!file.exists())
                    file.mkdir();
                write(giftfile,data);
                break;

        }
    }

    private static void write(String path, List<String[]>data){
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8.name()), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(data);
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            log.error("csv write error"+path);
        } finally {
            if (null != writer) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    log.error("关闭文件输出流出错", e);
                }
            }
        }
    }

    public static List<String[]> readCSV(String fileName) {
        List<String[]> list = null;
        CSVReader reader = null;
        try {
            reader = new CSVReaderBuilder(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8.name())).build();
            list = reader.readAll();
        } catch (Exception e) {
            log.error("读取CSV数据出错", e);
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("关闭文件输入流出错", e);
                }
            }
        }
        return list;
    }

    private int getFileLineNumber(File file) throws IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        int lineNo = lnr.getLineNumber();
        lnr.close();
        return lineNo;
    }
}
