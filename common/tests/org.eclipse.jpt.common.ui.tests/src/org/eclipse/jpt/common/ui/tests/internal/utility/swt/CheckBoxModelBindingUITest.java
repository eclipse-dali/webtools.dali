/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.utility.swt;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Play around with a set of check boxes.
 */
@SuppressWarnings("nls")
public class CheckBoxModelBindingUITest
	extends ApplicationWindow
{
	private final TestModel testModel;
	private final WritablePropertyValueModel<TestModel> testModelHolder;
	private final WritablePropertyValueModel<Boolean> flag1Holder;
	private final WritablePropertyValueModel<Boolean> flag2Holder;
	private final WritablePropertyValueModel<Boolean> notFlag2Holder;

	public static void main(String[] args) throws Exception {
		Window window = new CheckBoxModelBindingUITest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private CheckBoxModelBindingUITest(@SuppressWarnings("unused") String[] args) {
		super(null);
		this.testModel = new TestModel(true, true);
		this.testModelHolder = new SimplePropertyValueModel<TestModel>(this.testModel);
		this.flag1Holder = this.buildFlag1Holder(this.testModelHolder);
		this.flag2Holder = this.buildFlag2Holder(this.testModelHolder);
		this.notFlag2Holder = this.buildNotFlag2Holder(this.testModelHolder);
	}

	private WritablePropertyValueModel<Boolean> buildFlag1Holder(PropertyValueModel<TestModel> subjectHolder) {
		return new PropertyAspectAdapter<TestModel, Boolean>(subjectHolder, TestModel.FLAG1_PROPERTY) {
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

	private WritablePropertyValueModel<Boolean> buildFlag2Holder(PropertyValueModel<TestModel> subjectHolder) {
		return new PropertyAspectAdapter<TestModel, Boolean>(subjectHolder, TestModel.FLAG2_PROPERTY) {
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

	private WritablePropertyValueModel<Boolean> buildNotFlag2Holder(PropertyValueModel<TestModel> subjectHolder) {
		return new PropertyAspectAdapter<TestModel, Boolean>(subjectHolder, TestModel.NOT_FLAG2_PROPERTY) {
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

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(this.getClass().getSimpleName());
		parent.setSize(400, 100);
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayout(new FormLayout());
		Control checkBoxPanel = this.buildCheckBoxPanel(mainPanel);
		this.buildControlPanel(mainPanel, checkBoxPanel);
		return mainPanel;
	}

	private Control buildCheckBoxPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100, -35);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildFlag1CheckBox(panel);
		this.buildFlag2CheckBox(panel);
		this.buildNotFlag2CheckBox(panel);
		this.buildUnattachedCheckBox(panel);

		return panel;
	}

	private void buildFlag1CheckBox(Composite parent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText("flag 1");
		SWTTools.bind(this.flag1Holder, checkBox);
	}

	private void buildFlag2CheckBox(Composite parent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText("flag 2");
		SWTTools.bind(this.flag2Holder, checkBox);
	}

	private void buildNotFlag2CheckBox(Composite parent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText("not flag 2");
		SWTTools.bind(this.notFlag2Holder, checkBox);
	}

	private void buildUnattachedCheckBox(Composite parent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText("unattached");
		checkBox.addSelectionListener(this.buildUnattachedSelectionListener());
	}

	private SelectionListener buildUnattachedSelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("unattached default selected: " + e);
			}
			public void widgetSelected(SelectionEvent e) {
				System.out.println("unattached selected: " + e);
			}
		};
	}

	private void buildControlPanel(Composite parent, Control checkBoxPanel) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(checkBoxPanel);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildFlipFlag1Button(panel);
		this.buildNotFlag2ToggleButton(panel);
		this.buildClearModelButton(panel);
		this.buildRestoreModelButton(panel);
		this.buildPrintModelButton(panel);
	}

	private void buildFlipFlag1Button(Composite parent) {
		this.buildFlipFlag1ACI().fill(parent);
	}

	private ActionContributionItem buildFlipFlag1ACI() {
		Action action = new Action("flip flag 1", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				CheckBoxModelBindingUITest.this.flipFlag1();
			}
		};
		action.setToolTipText("flip flag 1");
		return new ActionContributionItem(action);
	}

	void flipFlag1() {
		this.testModel.setFlag1( ! this.testModel.isFlag1());
	}

	private void buildNotFlag2ToggleButton(Composite parent) {
		Button checkBox = new Button(parent, SWT.TOGGLE);
		checkBox.setText("not flag 2");
		SWTTools.bind(this.notFlag2Holder, checkBox);
	}

	private void buildClearModelButton(Composite parent) {
		this.buildClearModelACI().fill(parent);
	}

	private ActionContributionItem buildClearModelACI() {
		Action action = new Action("clear model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				CheckBoxModelBindingUITest.this.clearModel();
			}
		};
		action.setToolTipText("clear model");
		return new ActionContributionItem(action);
	}

	void clearModel() {
		this.testModelHolder.setValue(null);
	}

	private void buildRestoreModelButton(Composite parent) {
		this.buildRestoreModelACI().fill(parent);
	}

	private ActionContributionItem buildRestoreModelACI() {
		Action action = new Action("restore model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				CheckBoxModelBindingUITest.this.restoreModel();
			}
		};
		action.setToolTipText("restore model");
		return new ActionContributionItem(action);
	}

	void restoreModel() {
		this.testModelHolder.setValue(this.testModel);
	}

	private void buildPrintModelButton(Composite parent) {
		this.buildPrintModelACI().fill(parent);
	}

	private ActionContributionItem buildPrintModelACI() {
		Action action = new Action("print model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				CheckBoxModelBindingUITest.this.printModel();
			}
		};
		action.setToolTipText("print model");
		return new ActionContributionItem(action);
	}

	void printModel() {
		System.out.println("flag 1: " + this.testModel.isFlag1());
		System.out.println("flag 2: " + this.testModel.isFlag2());
		System.out.println("not flag 2: " + this.testModel.isNotFlag2());
		System.out.println("***");
	}


	public static class TestModel extends AbstractModel {
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
