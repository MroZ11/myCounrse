package com.example.testac.core.activiti.controller;

import com.example.testac.core.service.ActModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;

/**
 * Created by cloud on 2019/03/27.
 * 用于保存activiti流程模板
 */
@RestController
@RequestMapping("act/model")
public class ModelSaveRestResource implements ModelDataJsonConstants {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ActModelService actModelService;
    @Resource
    private RuntimeService runtimeService;

    private static final String modelerBaseUrl = "/activiti/modeler.html";


    public ModelSaveRestResource() {
    }

    @RequestMapping(
            value = {"{modelId}/save"},
            method = {RequestMethod.PUT}
    )
    @ResponseStatus(HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, @RequestParam("name") String name,
                          @RequestParam("json_xml") String json_xml, @RequestParam("svg_xml") String svg_xml,
                          @RequestParam("description") String description) {//对接收参数进行了修改(源包中的saveModel会报错)
        try {

            Model model = repositoryService.getModel(modelId);

            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

            modelJson.put(MODEL_NAME, name);
            modelJson.put(MODEL_DESCRIPTION, description);
            model.setMetaInfo(modelJson.toString());
            model.setName(name);

            repositoryService.saveModel(model);

            repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

            InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);

            PNGTranscoder transcoder = new PNGTranscoder();
            // Setup output
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            // Do the transformation
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();

        } catch (Exception e) {
            LOGGER.error("Error saving model", e);
            throw new ActivitiException("Error saving model", e);
        }
    }

    @RequestMapping("create")
    public void newModel(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            //初始化一个空模型
            Model model = repositoryService.newModel();

            //设置一些默认信息
            String name = "new-process";
            String description = "";
            int revision = 1;
            String key = "process";

            ObjectNode modelNode = objectMapper.createObjectNode();
            modelNode.put(MODEL_NAME, name);
            modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

            model.setName(name);
            model.setKey(key);
            model.setMetaInfo(modelNode.toString());

            repositoryService.saveModel(model);
            String id = model.getId();

            //完善ModelEditorSource
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace",
                    "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));

            response.sendRedirect(request.getContextPath() + modelerBaseUrl + "?modelId=" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("{modelId}/update")
    public void newModel(@PathVariable String modelId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            Model model = repositoryService.getModel(modelId);
            if (model != null) {
                response.sendRedirect(request.getContextPath() + modelerBaseUrl + "?modelId=" + modelId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("{modelId}/export")
    public void export(@PathVariable String modelId,  HttpServletResponse response) throws UnsupportedEncodingException {

        actModelService.export(modelId,response);

    }

    @RequestMapping("import")
    public void impot( HttpServletRequest request, HttpServletResponse response) throws Exception {

        File file = new File("E:/A1.xml");
        InputStream inputStream = new FileInputStream(file);//实例化FileInputStream
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader isr = null;
        XMLStreamReader xtr = null;
        String modelId = "";
        try {
            isr = new InputStreamReader(inputStream, "utf-8");
            xtr = xif.createXMLStreamReader(isr);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
            // 处理异常
            if (bpmnModel.getMainProcess() == null
                    || bpmnModel.getMainProcess().getId() == null) {
                throw new Exception("模板文件可能存在问题，请检查后重试！");
            }

            ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);
            Model modelData = repositoryService.newModel();
            modelData.setKey("pre1121");
            modelData.setName("入库流程");

            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "入库流程");
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "");
            modelData.setMetaInfo(modelObjectNode.toString());

            //校验流程类型key，并自动校正
            //updateProcessKey(modelNode);

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

            modelId = modelData.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("模板文件存在问题，操作失败！");
        } finally {
            try {
                xtr.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect(request.getContextPath() + modelerBaseUrl + "?modelId=" + modelId);
    }


    @RequestMapping("{modelId}/deploy")
    public void deploy(@PathVariable String modelId,  HttpServletResponse response) throws UnsupportedEncodingException {
        actModelService.deploy(modelId);
    }


    @RequestMapping("{key}/start")
    public void start(@PathVariable String key,  HttpServletResponse response) throws UnsupportedEncodingException {

        runtimeService.startProcessInstanceByKey(key);
    }


}
