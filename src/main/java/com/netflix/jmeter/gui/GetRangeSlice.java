package com.netflix.jmeter.gui;

import java.awt.GridBagConstraints;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.testelement.TestElement;

import com.netflix.jmeter.sampler.AbstractSampler;
import com.netflix.jmeter.sampler.GetRangeSliceSampler;

public class GetRangeSlice extends AbstractGUI
{
    private static final long serialVersionUID = 3197090412869386190L;
    private static final String LABEL = "Cassandra Get Range Slice";
    private JTextField startColumnName;
    private JTextField endColumnName;
    private JTextField count;
    private JCheckBox isReverse;
    private JComboBox cserializer;
    private JComboBox vserializer;

    @Override
    public void configure(TestElement element)
    {
        super.configure(element);
        startColumnName.setText(element.getPropertyAsString(GetRangeSliceSampler.START_COLUMN_NAME));
        endColumnName.setText(element.getPropertyAsString(GetRangeSliceSampler.END_COLUMN_NAME));
        count.setText(element.getPropertyAsString(GetRangeSliceSampler.COUNT));
        isReverse.setSelected(element.getPropertyAsBoolean(GetRangeSliceSampler.IS_REVERSE));
        cserializer.setSelectedItem(element.getPropertyAsString(GetRangeSliceSampler.COLUMN_SERIALIZER_TYPE));
        vserializer.setSelectedItem(element.getPropertyAsString(GetRangeSliceSampler.VALUE_SERIALIZER_TYPE));
    }

    public TestElement createTestElement()
    {
        GetRangeSliceSampler sampler = new GetRangeSliceSampler();
        modifyTestElement(sampler);
        sampler.setComment("test comment");
        return sampler;
    }

    public void modifyTestElement(TestElement sampler)
    {
        super.configureTestElement(sampler);

        if (sampler instanceof GetRangeSliceSampler)
        {
            GetRangeSliceSampler gSampler = (GetRangeSliceSampler) sampler;
            gSampler.setCSerializerType((String) cserializer.getSelectedItem());
            gSampler.setVSerializerType((String) vserializer.getSelectedItem());
            gSampler.setStartName(startColumnName.getText());
            gSampler.setEndName(endColumnName.getText());
            gSampler.setCount(count.getText());
            gSampler.setReverse(isReverse.isSelected());
        }
    }

    public void initFields()
    {
        startColumnName.setText("${__Random(1,1000)}");
        endColumnName.setText("${__Random(1,1000)}");
        count.setText("100");
        isReverse.setSelected(true);
        cserializer.setSelectedItem("StringSerializer");
        vserializer.setSelectedItem("StringSerializer");
    }

    @Override
    public void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints)
    {
        addToPanel(mainPanel, labelConstraints, 0, 3, new JLabel("Start Column Name: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 3, startColumnName = new JTextField());
        addToPanel(mainPanel, labelConstraints, 0, 4, new JLabel("End Column Name: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 4, endColumnName = new JTextField());
        addToPanel(mainPanel, labelConstraints, 0, 5, new JLabel("Count: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 5, count = new JTextField());
        addToPanel(mainPanel, labelConstraints, 0, 6, new JLabel("Reverse: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 6, isReverse = new JCheckBox());
        addToPanel(mainPanel, labelConstraints, 0, 7, new JLabel("Column Serializer: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 7, cserializer = new JComboBox(AbstractSampler.getSerializerNames().toArray()));
        addToPanel(mainPanel, labelConstraints, 0, 8, new JLabel("Value Serializer: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 8, vserializer = new JComboBox(AbstractSampler.getSerializerNames().toArray()));
    }

    @Override
    public String getLable()
    {
        return LABEL;
    }
}
