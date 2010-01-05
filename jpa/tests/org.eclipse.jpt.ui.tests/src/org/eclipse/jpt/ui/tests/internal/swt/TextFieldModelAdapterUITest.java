/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.ui.internal.swt.TextFieldModelAdapter;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Play around with a set of entry fields.
 */
public class TextFieldModelAdapterUITest
	extends ApplicationWindow
{
	private final TestModel testModel;
		private static final String DEFAULT_NAME = "Scooby Doo";
	private final WritablePropertyValueModel<TestModel> testModelHolder;
	private final WritablePropertyValueModel<String> nameHolder;
	private final WritablePropertyValueModel<String> allCapsNameHolder;


	public static void main(String[] args) throws Exception {
		Window window = new TextFieldModelAdapterUITest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private TextFieldModelAdapterUITest(String[] args) {
		super(null);
		this.testModel = new TestModel(DEFAULT_NAME);
		this.testModelHolder = new SimplePropertyValueModel<TestModel>(this.testModel);
		this.nameHolder = this.buildNameHolder(this.testModelHolder);
		this.allCapsNameHolder = this.buildAllCapsNameHolder(this.testModelHolder);
	}

	private WritablePropertyValueModel<String> buildNameHolder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, String>(vm, TestModel.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.name();
			}
			@Override
			protected void setValue_(String value) {
				this.subject.setName(value);
			}
		};
	}

	private WritablePropertyValueModel<String> buildAllCapsNameHolder(PropertyValueModel<TestModel> vm) {
		return new PropertyAspectAdapter<TestModel, String>(vm, TestModel.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.name().toUpperCase();
			}
			@Override
			protected void setValue_(String value) {
				// do nothing
			}
		};
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(this.getClass().getSimpleName());
		parent.setSize(400, 100);
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayout(new FormLayout());
		Control textFieldPanel = this.buildTextFieldPanel(mainPanel);
		this.buildControlPanel(mainPanel, textFieldPanel);
		return mainPanel;
	}

	private Control buildTextFieldPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100, -35);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildNameTextField(panel);
		this.buildReadOnlyNameTextField(panel);
		this.buildAllCapsNameTextField(panel);

		return panel;
	}

	private void buildNameTextField(Composite parent) {
		Text textField = new Text(parent, SWT.SINGLE);
		TextFieldModelAdapter.adapt(this.nameHolder, textField);
	}

	private void buildReadOnlyNameTextField(Composite parent) {
		Text textField = new Text(parent, SWT.SINGLE);
		textField.setEnabled(false);
		TextFieldModelAdapter.adapt(this.nameHolder, textField);
	}

	private void buildAllCapsNameTextField(Composite parent) {
		Text textField = new Text(parent, SWT.SINGLE);
		textField.setEnabled(false);
		TextFieldModelAdapter.adapt(this.allCapsNameHolder, textField);
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
		this.buildResetNameButton(panel);
		this.buildClearModelButton(panel);
		this.buildRestoreModelButton(panel);
		this.buildPrintModelButton(panel);
	}

	private void buildResetNameButton(Composite parent) {
		this.buildResetNameACI().fill(parent);
	}

	private ActionContributionItem buildResetNameACI() {
		Action action = new Action("reset name", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TextFieldModelAdapterUITest.this.resetName();
			}
		};
		action.setToolTipText("reset name");
		return new ActionContributionItem(action);
	}

	void resetName() {
		this.testModel.setName(DEFAULT_NAME);
	}

	private void buildClearModelButton(Composite parent) {
		this.buildClearModelACI().fill(parent);
	}

	private ActionContributionItem buildClearModelACI() {
		Action action = new Action("clear model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				TextFieldModelAdapterUITest.this.clearModel();
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
				TextFieldModelAdapterUITest.this.restoreModel();
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
				TextFieldModelAdapterUITest.this.printModel();
			}
		};
		action.setToolTipText("print model");
		return new ActionContributionItem(action);
	}

	void printModel() {
		System.out.println("name: " + this.testModel.name());
	}


	// ********** model class **********

	class TestModel extends AbstractModel {
		private String name;
			public static final String NAME_PROPERTY = "name";

		public TestModel(String name) {
			this.name = name;
		}
		public String name() {
			return this.name;
		}
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}
		@Override
		public String toString() {
			return "TestModel(" + this.name + ")";
		}
	}

}
