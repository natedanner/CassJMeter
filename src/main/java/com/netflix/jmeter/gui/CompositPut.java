package com.netflix.jmeter.gui;

import java.awt.GridBagConstraints;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.testelement.TestElement;

import com.netflix.jmeter.sampler.AbstractSampler;
import com.netflix.jmeter.sampler.CompsitePutSampler;

public class CompositPut extends AbstractGUI
{
    private static final long serialVersionUID = 3197090412869386190L;
    private static final String LABEL = "Cassandra Composite Put";
    private JTextField cname;
    private JTextField value;
    private JComboBox vserializer;
    private JCheckBox isCounter;

    @Override
    public void configure(TestElement element)
    {
        super.configure(element);
        cname.setText(element.getPropertyAsString(CompsitePutSampler.COLUMN_NAME));
        value.setText(element.getPropertyAsString(CompsitePutSampler.VALUE));
        vserializer.setSelectedItem(element.getPropertyAsString(CompsitePutSampler.VALUE_SERIALIZER_TYPE));
        isCounter.setSelected(element.getPropertyAsBoolean(CompsitePutSampler.IS_COUNTER));
    }

    public TestElement createTestElement()
    {
        CompsitePutSampler sampler = new CompsitePutSampler();
        modifyTestElement(sampler);
        sampler.setComment("test comment");
        return sampler;
    }

    public void modifyTestElement(TestElement sampler)
    {
        super.configureTestElement(sampler);

        if (sampler instanceof CompsitePutSampler)
        {
            CompsitePutSampler gSampler = (CompsitePutSampler) sampler;
            gSampler.setVSerializerType((String) vserializer.getSelectedItem());
            gSampler.setColumnName(cname.getText());
            gSampler.setValue(value.getText());
            gSampler.setCounter(isCounter.isSelected());
        }
    }

    public void initFields()
    {
        cname.setText("${__Random(1,1000)}");
        value.setText("${__Random(1,1000)}");
        vserializer.setSelectedItem("Value Serializer");
        isCounter.setSelected(false);
    }

    @Override
    public void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints)
    {
        addToPanel(mainPanel, labelConstraints, 0, 3, new JLabel("Column Name: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 3, cname = new JTextField());
        addToPanel(mainPanel, labelConstraints, 0, 4, new JLabel("Column Value: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 4, value = new JTextField());
        addToPanel(mainPanel, labelConstraints, 0, 6, new JLabel("Value Serializer: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 6, vserializer = new JComboBox(AbstractSampler.getSerializerNames().toArray()));
        addToPanel(mainPanel, labelConstraints, 0, 7, new JLabel("Counter: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, 7, isCounter = new JCheckBox());
    }

    @Override
    public String getLable()
    {
        return LABEL;
    }
}
