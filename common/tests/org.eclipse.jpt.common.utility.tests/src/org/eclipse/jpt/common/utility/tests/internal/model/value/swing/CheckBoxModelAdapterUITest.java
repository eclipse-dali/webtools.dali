/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.CheckBoxModelAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * Play around with a set of check boxes.
 */
@SuppressWarnings("nls")
public class CheckBoxModelAdapterUITest {

	private TestModel testModel;
	private ModifiablePropertyValueModel<TestModel> testModelHolder;
	private ModifiablePropertyValueModel<Boolean> flag1Holder;
	private ModifiablePropertyValueModel<Boolean> flag2Holder;
	private ModifiablePropertyValueModel<Boolean> notFlag2Holder;
	private ButtonModel flag1ButtonModel;
	private ButtonModel flag2ButtonModel;
	private ButtonModel notFlag2ButtonModel;

	public static void main(String[] args) throws Exception {
		new CheckBoxModelAdapterUITest().exec();
	}

	private CheckBoxModelAdapterUITest() {
		super();
	}

	private void exec() throws Exception {
		this.testModel = new TestModel(true, true);
		this.testModelHolder = new SimplePropertyValueModel<TestModel>(this.testModel);
		this.flag1Holder = this.buildFlag1Holder(this.testModelHolder);
		this.flag1ButtonModel = this.buildCheckBoxModelAdapter(this.flag1Holder);
		this.flag2Holder = this.buildFlag2Holder(this.testModelHolder);
		this.flag2ButtonModel = this.buildCheckBoxModelAdapter(this.flag2Holder);
		this.notFlag2Holder = this.buildNotFlag2Holder(this.testModelHolder);
		this.notFlag2ButtonModel = this.buildCheckBoxModelAdapter(this.notFlag2Holder);
		this.openWindow();
	}

	private ModifiablePropertyValueModel<Boolean> buildFlag1Holder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, Boolean>(vm, TestModel.FLAG1_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isFlag1());
			}
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setFlag1(value.booleanValue());
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildFlag2Holder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, Boolean>(vm, TestModel.FLAG2_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isFlag2());
			}
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setFlag2(value.booleanValue());
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildNotFlag2Holder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, Boolean>(vm, TestModel.NOT_FLAG2_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isNotFlag2());
			}
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setNotFlag2(value.booleanValue());
			}
		};
	}

	private ButtonModel buildCheckBoxModelAdapter(ModifiablePropertyValueModel<Boolean> booleanHolder) {
		return new CheckBoxModelAdapter(booleanHolder);
	}

	private void openWindow() {
		JFrame window = new JFrame(this.getClass().getName());
		window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(this.buildWindowListener());
		window.getContentPane().add(this.buildMainPanel(), "Center");
		window.setSize(400, 100);
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
		mainPanel.add(this.buildCheckBoxPanel(), BorderLayout.NORTH);
		mainPanel.add(this.buildControlPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}

	private Component buildCheckBoxPanel() {
		JPanel taskListPanel = new JPanel(new GridLayout(1, 0));
		taskListPanel.add(this.buildFlag1CheckBox());
		taskListPanel.add(this.buildFlag2CheckBox());
		taskListPanel.add(this.buildNotFlag2CheckBox());
		taskListPanel.add(this.buildUnattachedCheckBox());
		return taskListPanel;
	}

	private JCheckBox buildFlag1CheckBox() {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setText("flag 1");
		checkBox.setModel(this.flag1ButtonModel);
		return checkBox;
	}

	private JCheckBox buildFlag2CheckBox() {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setText("flag 2");
		checkBox.setModel(this.flag2ButtonModel);
		return checkBox;
	}

	private JCheckBox buildNotFlag2CheckBox() {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setText("not flag 2");
		checkBox.setModel(this.notFlag2ButtonModel);
		return checkBox;
	}

	private JCheckBox buildUnattachedCheckBox() {
		JCheckBox checkBox = new JCheckBox("unattached");
		checkBox.getModel().addItemListener(this.buildUnattachedItemListener());
		return checkBox;
	}

	private ItemListener buildUnattachedItemListener() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println("unattached state changed: " + e);
			}
		};
	}

	private Component buildControlPanel() {
		JPanel controlPanel = new JPanel(new GridLayout(1, 0));
		controlPanel.add(this.buildFlipFlag1Button());
		controlPanel.add(this.buildClearModelButton());
		controlPanel.add(this.buildRestoreModelButton());
		controlPanel.add(this.buildPrintModelButton());
		return controlPanel;
	}

	private JButton buildFlipFlag1Button() {
		return new JButton(this.buildFlipFlag1Action());
	}

	private Action buildFlipFlag1Action() {
		Action action = new AbstractAction("flip flag 1") {
			public void actionPerformed(ActionEvent event) {
				CheckBoxModelAdapterUITest.this.flipFlag1();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void flipFlag1() {
		this.testModel.setFlag1( ! this.testModel.isFlag1());
	}

	private JButton buildClearModelButton() {
		return new JButton(this.buildClearModelAction());
	}

	private Action buildClearModelAction() {
		Action action = new AbstractAction("clear model") {
			public void actionPerformed(ActionEvent event) {
				CheckBoxModelAdapterUITest.this.clearModel();
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
				CheckBoxModelAdapterUITest.this.restoreModel();
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
				CheckBoxModelAdapterUITest.this.printModel();
			}
		};
		action.setEnabled(true);
		return action;
	}

	void printModel() {
		System.out.println("flag 1: " + this.testModel.isFlag1());
		System.out.println("flag 2: " + this.testModel.isFlag2());
		System.out.println("not flag 2: " + this.testModel.isNotFlag2());
		System.out.println("***");
	}


	private class TestModel extends AbstractModel {
		private boolean flag1;
			public static final String FLAG1_PROPERTY = "flag1";
		private boolean flag2;
			public static final String FLAG2_PROPERTY = "flag2";
		private boolean notFlag2;
			public static final String NOT_FLAG2_PROPERTY = "notFlag2";
	
		public TestModel(boolean flag1, boolean flag2) {
			this.flag1 = flag1;
			this.flag2 = flag2;
			this.notFlag2 = ! flag2;
		}
		public boolean isFlag1() {
			return this.flag1;
		}
		public void setFlag1(boolean flag1) {
			boolean old = this.flag1;
			this.flag1 = flag1;
			this.firePropertyChanged(FLAG1_PROPERTY, old, flag1);
		}
		public boolean isFlag2() {
			return this.flag2;
		}
		public void setFlag2(boolean flag2) {
			boolean old = this.flag2;
			this.flag2 = flag2;
			this.firePropertyChanged(FLAG2_PROPERTY, old, flag2);
	
			old = this.notFlag2;
			this.notFlag2 = ! flag2;
			this.firePropertyChanged(NOT_FLAG2_PROPERTY, old, this.notFlag2);
		}
		public boolean isNotFlag2() {
			return this.notFlag2;
		}
		public void setNotFlag2(boolean notFlag2) {
			this.setFlag2( ! notFlag2);
		}
		@Override
		public String toString() {
			return "TestModel(" + this.isFlag1() + " - " + this.isFlag2() + ")";
		}
	}

}
