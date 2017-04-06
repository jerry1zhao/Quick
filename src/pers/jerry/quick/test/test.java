// Copyright (c) 2017 Quick
// ============================================================================
// CHANGE LOG
// V.1.0 : 2017-XX-XX, jerry.zhao, creation
// ============================================================================
/**
 * @author jerry.zhao
 *
 */
package pers.jerry.quick.test;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class test{
    private Configuration cfg;            //ģ�����ö���

    public void init() throws Exception {
            //��ʼ��FreeMarker����
            //����һ��Configurationʵ��
            cfg = new Configuration();
            //����FreeMarker��ģ���ļ���λ��
            cfg.setDirectoryForTemplateLoading(new File("d:\\freemarkertest\\src"));
    }

    public void process() throws Exception {
            //����������ݵ�Map
            final Map map = new HashMap();
            map.put("user", "lavasoft");
            map.put("url", "http://www.baidu.com/");
            map.put("name", "�ٶ�");
            //����ģ�����
            final Template t = cfg.getTemplate("test.ftl");
            //��ģ����ִ�в�ֵ��������������ƶ����������
            t.process(map, new OutputStreamWriter(System.out));
    }

    public static void main(String[] args) throws Exception {
            final test hf = new test();
            hf.init();
            hf.process();
    }
}
