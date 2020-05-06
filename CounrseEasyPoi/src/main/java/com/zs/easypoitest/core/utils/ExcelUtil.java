package com.zs.easypoitest.core.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.sun.deploy.net.URLEncoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by cloud on 2019/02/19.
 */
public class ExcelUtil {

    /**
     * 导出Excel
     *
     * @param list           行数据列表
     * @param title          标题
     * @param sheetName      sheet名
     * @param pojoClass      实体类
     * @param fileName       文件名
     * @param isCreateHeader 是否创建标题
     * @param response       写入response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 导出Excel
     *
     * @param list      行数据列表
     * @param title     标题
     * @param sheetName sheet名
     * @param pojoClass 实体类
     * @param fileName  文件名
     * @param response  写入response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 导出Excel
     *
     * @param list     行数据列表(键值对形式)
     * @param fileName 文件名
     * @param response 写入response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * 导出Excel基础方法
     *
     * @param list         行数据列表
     * @param pojoClass    实体类
     * @param fileName     文件名
     * @param response     写入response
     * @param exportParams 导出参数
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 导出Excel基础方法
     *
     * @param fileName 文件名
     * @param response 写入response
     * @param workbook workbook
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            /*TODO 火狐文件名乱码问题  需要根据 HttpServletRequeset 判断浏览器代理  不想添加方法参数的话 在调用时判断response Content-Disposition已存在即不修改*/

            //response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));


            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 导出Excel基础方法
     *
     * @param list     参数以及数据项列表(键值对形式)
     * @param fileName 文件名
     * @param response 写入response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 导入Excel
     *
     * @param filePath   文件路径
     * @param titleRows  列名行数
     * @param headerRows 标题行数
     * @param pojoClass  实体类
     * @param <T>        返回实体类
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    /**
     * 导入Excel
     *
     * @param file       文件
     * @param titleRows  列名行数
     * @param headerRows 标题行数
     * @param pojoClass  实体类
     * @param <T>        返回实体类
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }


    /**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     *
     * @param templatePath word模板地址
     * @param temDir       生成临时文件存放地址
     * @param fileName     文件名
     * @param params       替换的参数
     * @param response     HttpServletResponse
     */
    public static void exportWord(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletResponse response) {
        Assert.notNull(templatePath, "模板路径不能为空");
        Assert.notNull(temDir, "临时文件路径不能为空");
        Assert.notNull(fileName, "导出文件名不能为空");
        Assert.isTrue(fileName.endsWith(".docx"), "word导出请使用docx格式");
        if (!temDir.endsWith("/")) {
            temDir = temDir + File.separator;
        }
        File dir = new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {

            fileName = URLEncoder.encode(fileName, "UTF-8");

            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            doc.write(fos);
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
        }

    }

}