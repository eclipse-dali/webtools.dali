/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.WindowConstants;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.DateSpinnerModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ListSpinnerModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.swing.NumberSpinnerModelAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

/**
 * Play around with a set of spinners.
 */
@SuppressWarnings("nls")
public class SpinnerModelAdapterUITest {

	private TestModel testModel;
	private WritablePropertyValueModel<TestModel> testModelHolder;

	private WritablePropertyValueModel<Object> birthDateHolder;
	private SpinnerModel birthDateSpinnerModel;

	private WritablePropertyValueModel<Number> ageHolder;
	private SpinnerModel ageSpinnerModel;

	private WritablePropertyValueModel<Object> eyeColorHolder;
	private SpinnerModel eyeColorSpinnerModel;


	public static void main(String[] args) throws Exception {
		new SpinnerModelAdapterUITest().exec();
	}

	private SpinnerModelAdapterUITest() {
		super();
	}

	private void exec() throws Exception {
		this.testModel = new TestModel();
		this.testModelHolder = new SimplePropertyValueModel<TestModel>(this.testModel);

		this.birthDateHolder = this.buildBirthDateHolder(this.testModelHolder);
		this.birthDateSpinnerModel = this.buildBirthDateSpinnerModel(this.birthDateHolder);

		this.ageHolder = this.buildAgeHolder(this.testModelHolder);
		this.ageSpinnerModel = this.buildAgeSpinnerModel(this.ageHolder);

		this.eyeColorHolder = this.buildEyeColorHolder(this.testModelHolder);
		this.eyeColorSpinnerModel = this.buildEyeColorSpinnerModel(this.eyeColorHolder);

		this.openWindow();
	}

	private WritablePropertyValueModel<Object> buildBirthDateHolder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, Object>(vm, TestModel.BIRTH_DATE_PROPERTY) {
			@Override
			protected Object buildValue_() {
				return this.subject.getBirthDate();
			}
			@Override
			protected void setValue_(Object value) {
				this.subject.setBirthDate((Date) value);
			}
		};
	}

	private SpinnerModel buildBirthDateSpinnerModel(WritablePropertyValueModel<Object> valueHolder) {
		return new DateSpinnerModelAdapter(valueHolder);
	}

	private WritablePropertyValueModel<Number> buildAgeHolder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, Number>(vm, TestModel.AGE_PROPERTY) {
			@Override
			protected Number buildValue_() {
				return new Integer(this.subject.getAge());
			}
			@Override
			protected void setValue_(Number value) {
				this.subject.setAge(value.intValue());
			}
		};
	}

	private SpinnerModel buildAgeSpinnerModel(WritablePropertyValueModel<Number> valueHolder) {
		return new NumberSpinnerModelAdapter(valueHolder, valueHolder.getValue().intValue(), TestModel.MIN_AGE, TestModel.MAX_AGE, 1);
	}

	private WritablePropertyValueModel<Object> buildEyeColorHolder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, Object>(vm, TestModel.EYE_COLOR_PROPERTY) {
			@Override
			protected Object buildValue_() {
				return this.subject.getEyeColor();
			}
			@Override
			protected void setValue_(Object value) {
				this.subject.setEyeColor((String) value);
			}
		};
	}

	private SpinnerModel buildEyeColorSpinnerModel(WritablePropertyValueModel<Object> valueHolder) {
		return new ListSpinnerModelAdapter(valueHolder, TestModel.VALID_EYE_COLORS);
	}

	private void openWindow() {
		JFrame window = new JFrame(this.getClass().getName());
		window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(this.buildWindowListener());
		window.getContentPane().add(this.buildMainPanel(), "Center");
		window.setSize(600, 100);
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
		mainPanel.add(this.buildSpinnerPanel(), BorderLayout.NORTH);
		mainPanel.add(this.buildControlPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}

	private Component buildSpinnerPanel() {
		JPanel taskListPanel = new JPanel(new GridLayout(1, 0));
		taskListPanel.add(this.buildBirthDateSpinner());
		taskListPanel.add(this.buildAgeSpinner());
		taskListPanel.add(this.buildEyeColorSpinner());
		return taskListPanel;
	}

	private JSpinner buildBirthDateSpinner() {
		return new JSpinner(this.birthDateSpinnerModel);
	}

	private JSpinner buildAgeSpinner() {
		return new JSpinner(this.ageSpinnerModel);
	}

	private JSpinner buildEyeColorSpinner() {
		return new JSpinner(this.eyeColorSpinnerModel);
	}

	private Component buildControlPanel() {
		JPanel controlPanel = new JPanel(new GridLayout(1, 0));
		controlPanel.add(this.buildResetModelButton());
		controlPanel.add(this.buildClearModelButton());
		controlPanel.add(this.buildRestoreModelButton());
		controlPanel.add(this.buildPrintModelButton());
		return controlPanel;
	}

	private JButton buildResetModelButton() {
		return new JButton(this.buildResetModelAction());
	}

	private Action buildResetModelAction() {
		Action action = new AbstractAction("reset model") {
			public void actionPerformed(ActionEvent event) {
				SpinnerModelAdapterUITest.this.resetModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void resetModel() {
		this.testModel.setBirthDate(TestModel.DEFAULT_BIRTH_DATE);
		this.testModel.setEyeColor(TestModel.DEFAULT_EYE_COLOR);
	}

	private JButton buildClearModelButton() {
		return new JButton(this.buildClearModelAction());
	}

	private Action buildClearModelAction() {
		Action action = new AbstractAction("clear model") {
			public void actionPerformed(ActionEvent event) {
				SpinnerModelAdapterUITest.this.clearModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void clearModel() {
		this.testModelHolder.setValue(null);
	}

	private JButton buildRestoreModelButton() {
		return new JButton(this.buildRestoreModelAction());
	}

	private Action buildRestoreModelAction() {
		Action action = new AbstractAction("restore model") {
			public void actionPerformed(ActionEvent event) {
				SpinnerModelAdapterUITest.this.restoreModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void restoreModel() {
		this.testModelHolder.setValue(this.testModel);
	}

	private JButton buildPrintModelButton() {
		return new JButton(this.buildPrintModelAction());
	}

	private Action buildPrintModelAction() {
		Action action = new AbstractAction("print model") {
			public void actionPerformed(ActionEvent event) {
				SpinnerModelAdapterUITest.this.printModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void printModel() {
		System.out.println("birth date: " + this.testModel.getBirthDate());
		System.out.println("age: " + this.testModel.getAge());
		System.out.println("eyes: " + this.testModel.getEyeColor());
	}


	static class TestModel extends AbstractModel {
		private Calendar birthCal = Calendar.getInstance();
			// "virtual" properties
			public static final String BIRTH_DATE_PROPERTY = "birthDate";
			public static final String AGE_PROPERTY = "age";
			public static final Date DEFAULT_BIRTH_DATE = new Date();
			public static final int DEFAULT_AGE = 0;
			public static final int MIN_AGE = 0;
			public static final int MAX_AGE = 150;
		private String eyeColor;
			public static final String EYE_COLOR_PROPERTY = "eyeColor";
			public static final String[] VALID_EYE_COLORS = {"blue", "brown", "green", "hazel", "pink"};
			public static final String DEFAULT_EYE_COLOR = VALID_EYE_COLORS[3];
	
		TestModel() {
			this(DEFAULT_BIRTH_DATE, DEFAULT_EYE_COLOR);
		}
		public TestModel(Date birthDate, String eyeColor) {
			this.setBirthDate(birthDate);
			this.setEyeColor(eyeColor);
		}
		public Date getBirthDate() {
			return (Date) this.birthCal.getTime().clone();
		}
		public void setBirthDate(Date birthDate) {
			Date oldBirthDate = this.getBirthDate();
			int oldAge = this.getAge();
			this.birthCal.setTimeInMillis(birthDate.getTime());
			int newAge = this.getAge();
			if (newAge < MIN_AGE || newAge > MAX_AGE) {
				throw new IllegalArgumentException(birthDate.toString());
			}
			this.firePropertyChanged(BIRTH_DATE_PROPERTY, oldBirthDate, this.getBirthDate());
			this.firePropertyChanged(AGE_PROPERTY, oldAge, newAge);
		}
		public int getAge() {
			Calendar currentCal = Calendar.getInstance();
			int age = currentCal.get(Calendar.YEAR) - this.birthCal.get(Calendar.YEAR);
			if (currentCal.get(Calendar.MONTH) < this.birthCal.get(Calendar.MONTH)) {
				age--;
			} else if (currentCal.get(Calendar.MONTH) == this.birthCal.get(Calendar.MONTH)) {
				if (currentCal.get(Calendar.DAY_OF_MONTH) < this.birthCal.get(Calendar.DAY_OF_MONTH)) {
					age--;
				}
			}
			return age;
		}
		public void setAge(int newAge) {
			if (newAge < MIN_AGE || newAge > MAX_AGE) {
				throw new IllegalArgumentException(String.valueOf(newAge));
			}
	
			int oldAge = this.getAge();
			int delta = newAge - oldAge;
	
			Calendar newBirthCal = Calendar.getInstance();
			newBirthCal.setTimeInMillis(this.birthCal.getTime().getTime());
			// if the age increased, the birth date must be "decreased"; and vice versa
			newBirthCal.set(Calendar.YEAR, newBirthCal.get(Calendar.YEAR) - delta);
			this.setBirthDate(newBirthCal.getTime());
		}
		public String getEyeColor() {
			return this.eyeColor;
		}
		public void setEyeColor(String eyeColor) {
			if ( ! ArrayTools.contains(VALID_EYE_COLORS, eyeColor)) {
				throw new IllegalArgumentException(eyeColor);
			}
			Object old = this.eyeColor;
			this.eyeColor = eyeColor;
			this.firePropertyChanged(EYE_COLOR_PROPERTY, old, eyeColor);
		}
		@Override
		public String toString() {
			return "TestModel(birth: " + this.getBirthDate() + " - eyes: " + this.eyeColor + ")";
		}
	}

}
