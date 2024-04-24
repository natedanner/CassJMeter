package com.netflix.jmeter.gui;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.testelement.TestElement;

import com.netflix.jmeter.sampler.AbstractSampler;
import com.netflix.jmeter.sampler.GetSampler;

public class Get extends AbstractGUI
{
    private static final long serialVersionUID = 3197090412869386190L;
    public static String LABEL = "Cassandra Get";
    private JTextField cname;
    private JComboBox cserializer;
    private JComboBox vserializer;

    @Override
    public void configure(TestElement element)
    {
        super.configure(element);
        cname.setText(element.getPropertyAsString(GetSampler.COLUMN_NAME));
        cserializer.setSelectedItem(element.getPropertyAsString(GetSampler.COLUMN_SERIALIZER_TYPE));
        vserializer.setSelectedItem(element.getPropertyAsString(GetSampler.VALUE_SERIALIZER_TYPE));
    }

    public TestElement createTestElement()
    {
        GetSampler sampler = new GetSampler();
        modifyTestElement(sampler);
        sampler.setComment("test comment");
        return sampler;
    }

    public void modifyTestElement(TestElement sampler)
    {
        super.configureTestElement(sampler);

        if (sampler instanceof GetSampler)
        {
            GetSampler gSampler = (GetSampler) sampler;
            gSampler.setCSerializerType((String) cserializer.getSelectedItem());
            gSampler.setVSerializerType((String) vserializer.getSelectedItem());
            gSampler.setColumnName(cname.getText());
        }
    }

    public void initFields()
    {
        cname.setText("${__Random(1,1000)}");
        cserializer.setSelectedItem("StringSerializer");
        vserializer.setSelectedItem("StringSerializer");
    }

    @Override
    public void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints)
    {
        addToPanel(mainPanel, labelConstraints, 0, 3, new JLabel("Column Name: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 3, cname = new JTextField());
        addToPanel(mainPanel, labelConstraints, 0, 4, new JLabel("Column Serializer: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 4, cserializer = new JComboBox(AbstractSampler.getSerializerNames().toArray()));
        addToPanel(mainPanel, labelConstraints, 0, 5, new JLabel("Value Serializer: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 5, vserializer = new JComboBox(AbstractSampler.getSerializerNames().toArray()));
    }

    @Override
    public String getLable()
    {
        return LABEL;
    }
}
