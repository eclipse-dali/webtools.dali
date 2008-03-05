/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.swt;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.ui.internal.swt.TriStateCheckBoxModelAdapter;
import org.eclipse.jpt.ui.internal.widgets.DefaultWidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Play around with a set of tri-state check boxes.
 */
public class TriStateCheckBoxModelAdapterUITest
	extends ApplicationWindow
{
	private final TestModel testModel;
	private final WritablePropertyValueModel<TestModel> testModelHolder;
	private final WritablePropertyValueModel<Boolean> flag1Holder;
	private final WritablePropertyValueModel<Boolean> flag2Holder;
	private final WritablePropertyValueModel<Boolean> notFlag2Holder;

	public static void main(String[] args) throws Exception {
		Window window = new TriStateCheckBoxModelAdapterUITest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private TriStateCheckBoxModelAdapterUITest(String[] args) {
		super(null);
		this.testModel = new TestModel(Boolean.TRUE, Boolean.FALSE);
		this.testModelHolder = new SimplePropertyValueModel<TestModel>(this.testModel);
		this.flag1Holder = this.buildFlag1Holder(this.testModelHolder);
		this.flag2Holder = this.buildFlag2Holder(this.testModelHolder);
		this.notFlag2Holder = this.buildNotFlag2Holder(this.testModelHolder);
	}

	private WritablePropertyValueModel<Boolean> buildFlag1Holder(PropertyValueModel<TestModel> subjectHolder) {
		return new PropertyAspectAdapter<TestModel, Boolean>(subjectHolder, TestModel.FLAG1_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.isFlag1();
			}
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setFlag1(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildFlag2Holder(PropertyValueModel<TestModel> subjectHolder) {
		return new PropertyAspectAdapter<TestModel, Boolean>(subjectHolder, TestModel.FLAG2_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.isFlag2();
			}
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setFlag2(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildNotFlag2Holder(PropertyValueModel<TestModel> subjectHolder) {
		return new PropertyAspectAdapter<TestModel, Boolean>(subjectHolder, TestModel.NOT_FLAG2_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.isNotFlag2();
			}
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setNotFlag2(value);
			}
		};
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(ClassTools.shortClassNameForObject(this));
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
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, "flag 1", DefaultWidgetFactory.instance());
		TriStateCheckBoxModelAdapter.adapt(this.flag1Holder, checkBox);
	}

	private void buildFlag2CheckBox(Composite parent) {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, "flag 2", DefaultWidgetFactory.instance());
		TriStateCheckBoxModelAdapter.adapt(this.flag2Holder, checkBox);
	}

	private void buildNotFlag2CheckBox(Composite parent) {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, "next flag 2", DefaultWidgetFactory.instance());
		TriStateCheckBoxModelAdapter.adapt(this.notFlag2Holder, checkBox);
	}

	private void buildUnattachedCheckBox(Composite parent) {
		TriStateCheckBox checkBox = new TriStateCheckBox(parent, "unattached", DefaultWidgetFactory.instance());
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
		this.buildClearModelButton(panel);
		this.buildRestoreModelButton(panel);
		this.buildPrintModelButton(panel);
	}

	private void buildFlipFlag1Button(Composite parent) {
		this.buildFlipFlag1ACI().fill(parent);
	}

	private ActionContributionItem buildFlipFlag1ACI() {
		Action action = new Action("next flag 1", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TriStateCheckBoxModelAdapterUITest.this.nextFlag1();
			}
		};
		action.setToolTipText("next flag 1");
		return new ActionContributionItem(action);
	}

	void nextFlag1() {
		this.testModel.nextFlag1();
	}

	private void buildClearModelButton(Composite parent) {
		this.buildClearModelACI().fill(parent);
	}

	private ActionContributionItem buildClearModelACI() {
		Action action = new Action("clear model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TriStateCheckBoxModelAdapterUITest.this.clearModel();
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
				TriStateCheckBoxModelAdapterUITest.this.restoreModel();
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
				TriStateCheckBoxModelAdapterUITest.this.printModel();
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


	private class TestModel extends AbstractModel {
		private Boolean flag1;
			public static final String FLAG1_PROPERTY = "flag1";
		private Boolean flag2;
			public static final String FLAG2_PROPERTY = "flag2";
		private Boolean notFlag2;
			public static final String NOT_FLAG2_PROPERTY = "notFlag2";
	
		public TestModel(Boolean flag1, Boolean flag2) {
			this.flag1 = flag1;
			this.flag2 = flag2;
			this.notFlag2 = this.next(flag2);
		}
		private Boolean next(Boolean b) {
			return (b == null) ? Boolean.TRUE : b.booleanValue() ? Boolean.FALSE : null;
		}
		public Boolean isFlag1() {
			return this.flag1;
		}
		public void setFlag1(Boolean flag1) {
			Boolean old = this.flag1;
			this.flag1 = flag1;
			this.firePropertyChanged(FLAG1_PROPERTY, old, flag1);
		}
		public void nextFlag1() {
			this.setFlag1(this.next(this.flag1));
		}
		public Boolean isFlag2() {
			return this.flag2;
		}
		public void setFlag2(Boolean flag2) {
			Boolean old = this.flag2;
			this.flag2 = flag2;
			this.firePropertyChanged(FLAG2_PROPERTY, old, flag2);

			old = this.notFlag2;
			this.notFlag2 = this.next(flag2);
			this.firePropertyChanged(NOT_FLAG2_PROPERTY, old, this.notFlag2);
		}
		public void nextFlag2() {
			this.setFlag2(this.next(this.flag2));
		}
		public Boolean isNotFlag2() {
			return this.notFlag2;
		}
		public void setNotFlag2(Boolean notFlag2) {
			this.setFlag2(this.next(flag2));
		}
		public void nextNotFlag2() {
			this.setNotFlag2(this.next(this.notFlag2));
		}
		@Override
		public String toString() {
			return "TestModel(" + this.isFlag1() + " - " + this.isFlag2() + ")";
		}
	}

}
