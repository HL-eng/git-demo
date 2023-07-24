import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestParseExcel {

    @Test
    public void parseExcelTest01(){
        try {
            FileInputStream file = new FileInputStream("G:\\中科本原\\周报\\2023年28周工作周报-侯磊.xlsx");
            Workbook workbook = WorkbookFactory.create(file);//创建excel对象

            // 获取Sheet2
            Sheet sheet = workbook.getSheetAt(1); // Sheet索引从0开始，这里是获取第二个Sheet

            // 迭代遍历行
            for (Row row : sheet) {
                // 迭代遍历单元格
                for (Cell cell : row) {
                    // 根据单元格类型读取数据
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        case BLANK:
                            System.out.print("\t");
                            break;
                        default:
                            System.out.print("\t");
                    }
                }
                System.out.println(); // 换行
            }
            // 关闭文件流
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseExcelTest02(){
        String filePath = "G:\\中科本原\\周报\\2023年28周工作周报-侯磊.xlsx";

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            // 遍历行
            for (Row row : sheet) {
                // 遍历单元格
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dateFormat.format(date);
                        System.out.println(formattedDate);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test03(){
        String value = getValue();
        if(value.contains("-") && value.contains("%")){
            System.out.println(value.substring(value.lastIndexOf("-")+1, value.lastIndexOf("%")));
        }
        System.out.println(value);
    }


    /**
     * 用于解析含有单元格合并的excel表
     */
    public static String getValue(){
        String filePath = "G:\\中科本原\\周报\\2023年28周工作周报-侯磊.xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            for (Row row : sheet) {
                for (Cell cell : row) {
                    if(cell.getRowIndex() == 4 && cell.getColumnIndex() == 2){
                        // 处理合并单元格
                        CellRangeAddress mergedRegion = getMergedRegion(sheet, cell.getRowIndex(), cell.getColumnIndex());
                        if (mergedRegion != null) {

                            int firstRow = mergedRegion.getFirstRow();
                            int firstCol = mergedRegion.getFirstColumn();
                            String mergedValue = sheet.getRow(firstRow).getCell(firstCol).getStringCellValue();
                            System.out.println("Merged Cell Value: " + mergedValue);
                            return mergedValue;
                        } else {
                            //在这个地方判断一下单元格的类型
                            // 根据单元格类型读取数据
                            if(cell.getCellType() == CellType.STRING){
                                System.out.println("Cell Value: " + cell.getStringCellValue());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 辅助方法，用于判断单元格是否是合并单元格，并返回合并区域的范围
    private static CellRangeAddress getMergedRegion(Sheet sheet, int rowIndex, int colIndex) {
        //sheet.getMergedRegions();用于获取合并单元格的区域
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress region : sheet.getMergedRegions()) {
            //用于判断rowIndex,colIndex是否在合并的区域范围内
            if (region.isInRange(rowIndex, colIndex)) {
                return region;
            }
        }
        return null;
    }

    @Test
    public void test04(){
        String filePath = "G:\\中科本原\\周报\\2023年28周工作周报-侯磊.xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            for (Row row : sheet) {
                for (Cell cell : row) {
                    // 处理合并单元格
                    CellRangeAddress mergedRegion = getMergedRegion(sheet, cell.getRowIndex(), cell.getColumnIndex());
                    if (mergedRegion != null) {
                        int firstRow = mergedRegion.getFirstRow();
                        int firstCol = mergedRegion.getFirstColumn();
                        String mergedValue = sheet.getRow(firstRow).getCell(firstCol).getStringCellValue();
                        System.out.println("Merged Cell Value: " + mergedValue);
                    } else {
                        System.out.println("Cell Value: " + cell.getStringCellValue());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
