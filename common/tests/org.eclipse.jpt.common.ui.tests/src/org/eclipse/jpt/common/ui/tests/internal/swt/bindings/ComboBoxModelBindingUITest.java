/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.swt.bindings;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Play around with a set of combo-boxes.
 */
@SuppressWarnings("nls")
public class ComboBoxModelBindingUITest
	extends ApplicationWindow
{
	private final SimpleListValueModel<String> nameListModel;
	private final TestModel testModel;
		private static final String DEFAULT_NAME = "Scooby-Doo";
	private final ModifiablePropertyValueModel<TestModel> testModelModel;
	private final ModifiablePropertyValueModel<String> nameModel;
	private final PropertyValueModel<String> allCapsNameModel;

	private final ModifiablePropertyValueModel<String> nameListSelectionModel;
	private final ModifiablePropertyValueModel<String> nameListIndexTextModel;
	private final ModifiablePropertyValueModel<String> nameListNameModel;


	public static void main(String[] args) throws Exception {
		Window window = new ComboBoxModelBindingUITest();
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private ComboBoxModelBindingUITest() {
		super(null);
		this.nameListModel = this.buildNameListModel();
		this.testModel = new TestModel(DEFAULT_NAME);
		this.testModelModel = new SimplePropertyValueModel<TestModel>(this.testModel);
		this.nameModel = new NameModel(this.testModelModel);
		this.allCapsNameModel = this.buildAllCapsNameModel(this.testModelModel);

		this.nameListSelectionModel = new SimplePropertyValueModel<String>();
		this.nameListIndexTextModel = new SimplePropertyValueModel<String>();
		this.nameListNameModel = new SimplePropertyValueModel<String>();
	}
	private static final Transformer<String, Integer> STRING_INTEGER_TRANSFORMER = new StringIntegerTransformer();
	static class StringIntegerTransformer
		extends TransformerAdapter<String, Integer>
	{
		@Override
		public Integer transform(String input) {
			try {
				return Integer.valueOf(input);
			} catch (NumberFormatException ex){
				return Integer.valueOf(0);
			}
		}
	}

	private SimpleListValueModel<String> buildNameListModel() {
		SimpleListValueModel<String> x = new SimpleListValueModel<String>();
		x.add("Daphne");
		x.add("Fred");
		x.add("Scooby-Doo");
		x.add("Shaggy");
		x.add("Velma");
		return x;
	}

	private static class NameModel
		extends PropertyAspectAdapter<TestModel, String>
	{
		NameModel(PropertyValueModel<TestModel> vm) {
			super(vm, TestModel.NAME_PROPERTY);
		}
		@Override
		protected String buildValue_() {
			return this.subject.name();
		}
		@Override
		protected void setValue_(String value) {
			this.subject.setName(value);
		}
	}

	private PropertyValueModel<String> buildAllCapsNameModel(PropertyValueModel<TestModel> vm) {
		return new TransformationPropertyValueModel<String, String>(new NameModel(vm), UPPER_CASE_TRANSFORMER);
	}

	public static final Transformer<String, String> UPPER_CASE_TRANSFORMER = new UpperCaseTransformer();

	/* CU private */ static class UpperCaseTransformer
		extends AbstractTransformer<String, String>
	{
		@Override
		protected String transform_(String string) {
			return string.toUpperCase();
		}
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(this.getClass().getSimpleName());
		parent.setSize(400, 500);
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayout(new FormLayout());

		Control comboBoxPanel = this.buildComboBoxPanel(mainPanel);
		Control comboBoxControlPanel = this.buildComboBoxControlPanel(mainPanel, comboBoxPanel);

		Control nameListControlPanel = this.buildNameListControlPanel(mainPanel);
		this.buildNameListPanel(mainPanel, comboBoxControlPanel, nameListControlPanel);

		return mainPanel;
	}

	private Control buildComboBoxPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(0, 35);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildNameComboBox(panel);
		this.buildNameLabel(panel);
		this.buildAllCapsNameLabel(panel);

		return panel;
	}

	private void buildNameComboBox(Composite parent) {
		Combo comboBox = new Combo(parent, SWT.DROP_DOWN);
		SWTBindingTools.bindComboBox(this.nameListModel, this.nameModel, comboBox);
	}

	private void buildNameLabel(Composite parent) {
		Label textLabel = new Label(parent, SWT.NONE);
		SWTBindingTools.bindTextLabel(this.nameModel, textLabel);
	}

	private void buildAllCapsNameLabel(Composite parent) {
		Label textLabel = new Label(parent, SWT.NONE);
		SWTBindingTools.bindTextLabel(this.allCapsNameModel, textLabel);
	}

	private Control buildComboBoxControlPanel(Composite parent, Control comboBoxPanel) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(comboBoxPanel);
			fd.bottom = new FormAttachment(comboBoxPanel, 35, SWT.BOTTOM);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildResetNameButton(panel);
		this.buildClearModelButton(panel);
		this.buildRestoreModelButton(panel);
		this.buildPrintModelButton(panel);

		return panel;
	}

	private void buildResetNameButton(Composite parent) {
		this.buildResetNameACI().fill(parent);
	}

	private ActionContributionItem buildResetNameACI() {
		Action action = new Action("reset name", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ComboBoxModelBindingUITest.this.resetName();
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
				ComboBoxModelBindingUITest.this.clearModel();
			}
		};
		action.setToolTipText("clear model");
		return new ActionContributionItem(action);
	}

	void clearModel() {
		this.testModelModel.setValue(null);
	}

	private void buildRestoreModelButton(Composite parent) {
		this.buildRestoreModelACI().fill(parent);
	}

	private ActionContributionItem buildRestoreModelACI() {
		Action action = new Action("restore model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ComboBoxModelBindingUITest.this.restoreModel();
			}
		};
		action.setToolTipText("restore model");
		return new ActionContributionItem(action);
	}

	void restoreModel() {
		this.testModelModel.setValue(this.testModel);
	}

	private void buildPrintModelButton(Composite parent) {
		this.buildPrintModelACI().fill(parent);
	}

	private ActionContributionItem buildPrintModelACI() {
		Action action = new Action("print model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ComboBoxModelBindingUITest.this.printModel();
			}
		};
		action.setToolTipText("print model");
		return new ActionContributionItem(action);
	}

	void printModel() {
		System.out.println("current model: " + this.testModel);
	}

	private Control buildNameListPanel(Composite parent, Control comboBoxControlPanel, Control nameListControlPanel) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayout(new FormLayout());
		FormData fd = new FormData();
			fd.top = new FormAttachment(comboBoxControlPanel);
			fd.bottom = new FormAttachment(nameListControlPanel);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		Label listLabel = new Label(panel, SWT.LEFT | SWT.VERTICAL);
		listLabel.setText("Names");
		fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(0, 35);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		listLabel.setLayoutData(fd);

		org.eclipse.swt.widgets.List listBox = new org.eclipse.swt.widgets.List(panel, SWT.SINGLE | SWT.BORDER);
		fd = new FormData();
			fd.top = new FormAttachment(listLabel);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		listBox.setLayoutData(fd);
		SWTBindingTools.bind(this.nameListModel, this.nameListSelectionModel, listBox);  // use #toString()

		return panel;
	}

	private Control buildNameListControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(100, -35);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildNameListIndexTextField(panel);
		this.buildNameListNameTextField(panel);
		this.buildAddNameButton(panel);
		this.buildSetNameButton(panel);
		this.buildRemoveNameButton(panel);
		this.buildReplaceNameListButton(panel);
		this.buildClearNameListButton(panel);

		return panel;
	}

	private void buildNameListIndexTextField(Composite parent) {
		Text textField = new Text(parent, SWT.SINGLE | SWT.BORDER);
		SWTBindingTools.bind(this.nameListIndexTextModel, textField);
	}

	private void buildNameListNameTextField(Composite parent) {
		Text textField = new Text(parent, SWT.SINGLE | SWT.BORDER);
		SWTBindingTools.bind(this.nameListNameModel, textField);
	}

	private void buildAddNameButton(Composite parent) {
		this.buildAddNameACI().fill(parent);
	}

	private ActionContributionItem buildAddNameACI() {
		Action action = new Action("add", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ComboBoxModelBindingUITest.this.addName();
			}
		};
		action.setToolTipText("add");
		return new ActionContributionItem(action);
	}

	void addName() {
		int index = STRING_INTEGER_TRANSFORMER.transform(this.nameListIndexTextModel.getValue()).intValue();
		String newName = this.nameListNameModel.getValue();
		if (StringTools.isNotBlank(newName)) {
			this.nameListModel.add(index, newName);
		}
	}

	private void buildSetNameButton(Composite parent) {
		this.buildSetNameACI().fill(parent);
	}

	private ActionContributionItem buildSetNameACI() {
		Action action = new Action("set", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ComboBoxModelBindingUITest.this.setName();
			}
		};
		action.setToolTipText("set");
		return new ActionContributionItem(action);
	}

	void setName() {
		int index = STRING_INTEGER_TRANSFORMER.transform(this.nameListIndexTextModel.getValue()).intValue();
		String newName = this.nameListNameModel.getValue();
		if (StringTools.isNotBlank(newName)) {
			this.nameListModel.set(index, newName);
		}
	}

	private void buildRemoveNameButton(Composite parent) {
		this.buildRemoveNameACI().fill(parent);
	}

	private ActionContributionItem buildRemoveNameACI() {
		Action action = new Action("remove", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ComboBoxModelBindingUITest.this.removeName();
			}
		};
		action.setToolTipText("remove");
		return new ActionContributionItem(action);
	}

	void removeName() {
		this.nameListModel.remove(this.nameListNameModel.getValue());
	}

	private void buildClearNameListButton(Composite parent) {
		this.buildClearNameListACI().fill(parent);
	}

	private ActionContributionItem buildClearNameListACI() {
		Action action = new Action("clear", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ComboBoxModelBindingUITest.this.clearNameList();
			}
		};
		action.setToolTipText("clear");
		return new ActionContributionItem(action);
	}

	void clearNameList() {
		this.nameListModel.clear();
	}

	private void buildReplaceNameListButton(Composite parent) {
		this.buildReplaceNameListACI().fill(parent);
	}

	private ActionContributionItem buildReplaceNameListACI() {
		Action action = new Action("replace", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ComboBoxModelBindingUITest.this.replaceNameList();
			}
		};
		action.setToolTipText("replace");
		return new ActionContributionItem(action);
	}

	void replaceNameList() {
		this.nameListModel.setListValues(REPLACEMENT_LIST);
	}
	private static final Iterable<String> REPLACEMENT_LIST =
			ArrayTools.iterable(new String[] {
					"AAAA",
					"BBBB",
					"CCCC",
					"DDDD",
					"EEEE"
			});


	// ********** model class **********

	class TestModel
		extends AbstractModel
	{
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
			if (this.firePropertyChanged(NAME_PROPERTY, old, name)) {
				System.out.println("set model name: " + name);
			}
		}
		@Override
		public String toString() {
			return "TestModel(" + this.name + ")";
		}
	}
}
