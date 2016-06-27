/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.RadioButtonModelAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;


/**
 * Play around with a set of radio buttons.
 */
@SuppressWarnings("nls")
public class RadioButtonModelAdapterUITest {

	private ColoredThing coloredThing;
	private ModifiablePropertyValueModel<ColoredThing> coloredThingModel;
	private ModifiablePropertyValueModel<String> colorModel;
	private ButtonModel redButtonModel;
	private ButtonModel greenButtonModel;
	private ButtonModel blueButtonModel;

	public static void main(String[] args) throws Exception {
		new RadioButtonModelAdapterUITest().exec();
	}

	private RadioButtonModelAdapterUITest() {
		super();
	}

	private void exec() throws Exception {
		this.coloredThing = new ColoredThing();
		this.coloredThingModel = new SimplePropertyValueModel<>(this.coloredThing);
		this.colorModel = this.buildColorModel(this.coloredThingModel);
		this.redButtonModel = this.buildRadioButtonModelAdapter(this.colorModel, ColoredThing.RED);
		this.greenButtonModel = this.buildRadioButtonModelAdapter(this.colorModel, ColoredThing.GREEN);
		this.blueButtonModel = this.buildRadioButtonModelAdapter(this.colorModel, ColoredThing.BLUE);
		this.openWindow();
	}

	private ModifiablePropertyValueModel<String> buildColorModel(PropertyValueModel<ColoredThing> ctm) {
		return new PropertyAspectAdapter<ColoredThing, String>(ctm, ColoredThing.COLOR_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getColor();
			}
			@Override
			protected void setValue_(String value) {
				this.subject.setColor(value);
			}
		};
	}

	private ButtonModel buildRadioButtonModelAdapter(ModifiablePropertyValueModel<String> colorPVM, String color) {
		return new RadioButtonModelAdapter<>(colorPVM, color);
	}

	private void openWindow() {
		JFrame window = new JFrame(this.getClass().getName());
		window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(this.buildWindowListener());
		window.getContentPane().add(this.buildMainPanel(), "Center");
		window.setSize(400, 100);
		window.setLocation(200, 200);
		window.setVisible(true);
	}

	private WindowListener buildWindowListener() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				System.exit(0);
			}
		};
	}

	private Component buildMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(this.buildRadioButtonPanel(), BorderLayout.NORTH);
		mainPanel.add(this.buildControlPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}

	private Component buildRadioButtonPanel() {
		JPanel taskListPanel = new JPanel(new GridLayout(1, 0));
		taskListPanel.add(this.buildRedRadioButton());
		taskListPanel.add(this.buildGreenRadioButton());
		taskListPanel.add(this.buildBlueRadioButton());
		return taskListPanel;
	}

	private JRadioButton buildRedRadioButton() {
		JRadioButton radioButton = new JRadioButton();
		radioButton.setText("red");
		radioButton.setModel(this.redButtonModel);
		return radioButton;
	}

	private JRadioButton buildGreenRadioButton() {
		JRadioButton radioButton = new JRadioButton();
		radioButton.setText("green");
		radioButton.setModel(this.greenButtonModel);
		return radioButton;
	}

	private JRadioButton buildBlueRadioButton() {
		JRadioButton radioButton = new JRadioButton();
		radioButton.setText("blue");
		radioButton.setModel(this.blueButtonModel);
		return radioButton;
	}

	private Component buildControlPanel() {
		JPanel controlPanel = new JPanel(new GridLayout(1, 0));
		controlPanel.add(this.buildResetColorButton());
		controlPanel.add(this.buildClearModelButton());
		controlPanel.add(this.buildRestoreModelButton());
		controlPanel.add(this.buildPrintModelButton());
		return controlPanel;
	}

	private JButton buildResetColorButton() {
		return new JButton(this.buildResetColorAction());
	}

	private Action buildResetColorAction() {
		Action action = new AbstractAction("reset color") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				RadioButtonModelAdapterUITest.this.resetColor();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void resetColor() {
		this.coloredThing.setColor(ColoredThing.DEFAULT_COLOR);
	}

	private JButton buildClearModelButton() {
		return new JButton(this.buildClearModelAction());
	}

	private Action buildClearModelAction() {
		Action action = new AbstractAction("clear model") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				RadioButtonModelAdapterUITest.this.clearModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void clearModel() {
		this.coloredThingModel.setValue(null);
	}

	private JButton buildRestoreModelButton() {
		return new JButton(this.buildRestoreModelAction());
	}

	private Action buildRestoreModelAction() {
		Action action = new AbstractAction("restore model") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				RadioButtonModelAdapterUITest.this.restoreModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void restoreModel() {
		this.coloredThingModel.setValue(this.coloredThing);
	}

	private JButton buildPrintModelButton() {
		return new JButton(this.buildPrintModelAction());
	}

	private Action buildPrintModelAction() {
		Action action = new AbstractAction("print model") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) {
				RadioButtonModelAdapterUITest.this.printModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void printModel() {
		System.out.println(this.coloredThing);
	}


	private static class ColoredThing
		extends AbstractModel
	{
		private String color;
			public static final String COLOR_PROPERTY = "color";
			public static final String RED = "red";
			public static final String GREEN = "green";
			public static final String BLUE = "blue";
			public static final String DEFAULT_COLOR = RED;
			public static final String[] VALID_COLORS = {
				RED,
				GREEN,
				BLUE
			};
	
		public ColoredThing() {
			this(DEFAULT_COLOR);
		}
		public ColoredThing(String color) {
			this.color = color;
		}
		public String getColor() {
			return this.color;
		}
		public void setColor(String color) {
			if ( ! ArrayTools.contains(VALID_COLORS, color)) {
				throw new IllegalArgumentException(color);
			}
			Object old = this.color;
			this.color = color;
			this.firePropertyChanged(COLOR_PROPERTY, old, color);
		}
		@Override
		public String toString() {
			return "TestModel(" + this.color + ")";
		}
	}

}
