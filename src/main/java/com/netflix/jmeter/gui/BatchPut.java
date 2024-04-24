package com.netflix.jmeter.gui;

import java.awt.GridBagConstraints;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import org.apache.jmeter.testelement.TestElement;

import com.netflix.jmeter.sampler.AbstractSampler;
import com.netflix.jmeter.sampler.BatchPutSampler;

public class BatchPut extends AbstractGUI
{
    private static final long serialVersionUID = 3197090412869386190L;
    public static String LABEL = "Cassandra Batch Put";
    private JTextArea nameAndValue;
    private JComboBox cserializer;
    private JComboBox vserializer;
    private JCheckBox isCounter;

    @Override
    public void configure(TestElement element)
    {
        super.configure(element);
        nameAndValue.setText(element.getPropertyAsString(BatchPutSampler.NAME_AND_VALUE));
        cserializer.setSelectedItem(element.getPropertyAsString(BatchPutSampler.COLUMN_SERIALIZER_TYPE));
        vserializer.setSelectedItem(element.getPropertyAsString(BatchPutSampler.VALUE_SERIALIZER_TYPE));
        isCounter.setSelected(element.getPropertyAsBoolean(BatchPutSampler.IS_COUNTER));
    }

    public TestElement createTestElement()
    {
        BatchPutSampler sampler = new BatchPutSampler();
        modifyTestElement(sampler);
        sampler.setComment("test comment");
        return sampler;
    }

    public void modifyTestElement(TestElement sampler)
    {
        super.configureTestElement(sampler);
        if (sampler instanceof BatchPutSampler)
        {
            BatchPutSampler gSampler = (BatchPutSampler) sampler;
            gSampler.setCSerializerType((String) cserializer.getSelectedItem());
            gSampler.setVSerializerType((String) vserializer.getSelectedItem());
            gSampler.setNameValue(nameAndValue.getText());
            gSampler.setCounter(isCounter.isSelected());
        }
    }

    public void initFields()
    {
        nameAndValue.setText("${__Random(1,1000)}:${__Random(1,1000)}\n${__Random(1,1000)}:${__Random(1,1000)}");
        cserializer.setSelectedItem("Column Serializer");
        vserializer.setSelectedItem("Value Serializer");
        isCounter.setSelected(false);
    }

    public void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints)
    {
        addToPanel(mainPanel, labelConstraints, 0, 3, new JLabel("Column K/V(eg: Name:Value): ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 3, nameAndValue = new JTextArea());
        nameAndValue.setRows(10);
        nameAndValue.setBorder(new BevelBorder(BevelBorder.LOWERED));
        addToPanel(mainPanel, labelConstraints, 0, 4, new JLabel("Column Serializer: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 4, cserializer = new JComboBox(AbstractSampler.getSerializerNames().toArray()));
        addToPanel(mainPanel, labelConstraints, 0, 5, new JLabel("Value Serializer: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 5, vserializer = new JComboBox(AbstractSampler.getSerializerNames().toArray()));
        addToPanel(mainPanel, labelConstraints, 0, 6, new JLabel("Counter: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 6, isCounter = new JCheckBox());
    }

    @Override
    public String getLable()
    {
        return LABEL;
    }
}
