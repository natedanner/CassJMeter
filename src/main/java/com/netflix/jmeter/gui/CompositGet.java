package com.netflix.jmeter.gui;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.testelement.TestElement;

import com.netflix.jmeter.sampler.AbstractSampler;
import com.netflix.jmeter.sampler.CompositGetSampler;

public class CompositGet extends AbstractGUI
{
    private static final long serialVersionUID = 3197090412869386190L;
    public static String LABEL = "Cassandra Composite Get";
    private JTextField cname;
    private JComboBox vserializer;

    @Override
    public void configure(TestElement element)
    {
        super.configure(element);
        cname.setText(element.getPropertyAsString(CompositGetSampler.COLUMN_NAME));
        vserializer.setSelectedItem(element.getPropertyAsString(CompositGetSampler.VALUE_SERIALIZER_TYPE));
    }

    public TestElement createTestElement()
    {
        CompositGetSampler sampler = new CompositGetSampler();
        modifyTestElement(sampler);
        sampler.setComment("test comment");
        return sampler;
    }

    public void modifyTestElement(TestElement sampler)
    {
        super.configureTestElement(sampler);

        if (sampler instanceof CompositGetSampler)
        {
            CompositGetSampler gSampler = (CompositGetSampler) sampler;
            gSampler.setVSerializerType((String) vserializer.getSelectedItem());
            gSampler.setColumnName(cname.getText());
        }
    }

    public void initFields()
    {
        cname.setText("${__Random(1,1000)}:${__Random(1,1000)}");
        vserializer.setSelectedItem("StringSerializer");
    }

    @Override
    public void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints)
    {
        addToPanel(mainPanel, labelConstraints, 0, 3, new JLabel("Column Name: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 3, cname = new JTextField());
        addToPanel(mainPanel, labelConstraints, 0, 5, new JLabel("Value Serializer: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 5, vserializer = new JComboBox(AbstractSampler.getSerializerNames().toArray()));
    }

    @Override
    public String getLable()
    {
        return LABEL;
    }
}
