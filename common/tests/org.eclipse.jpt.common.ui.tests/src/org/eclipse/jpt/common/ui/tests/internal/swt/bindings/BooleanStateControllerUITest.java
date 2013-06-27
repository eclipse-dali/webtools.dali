/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Play around with <code>boolean</code> state controllers
 * (<em>enabled</em> and <em>visible</em>).
 * <p>
 * Note the behavior of composites:<ul>
 * <li>When a composite is disabled, its children are disabled but <em>not</em>
 *     grayed out.
 * <li>When a composite is made invisible, its children are also made
 *     invisible.
 * </ul>
 */
@SuppressWarnings("nls")
public class BooleanStateControllerUITest
	extends ApplicationWindow
{
	private final ModifiablePropertyValueModel<Boolean> enabledModel;
	private final ModifiablePropertyValueModel<Boolean> visibleModel;
	private final SimpleListValueModel<String> listModel;
	private final ModifiablePropertyValueModel<String> listSelectionModel;

	public static void main(String[] args) throws Exception {
		Window window = new BooleanStateControllerUITest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private BooleanStateControllerUITest(@SuppressWarnings("unused") String[] args) {
		super(null);
		this.enabledModel = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.visibleModel = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.listModel = this.buildListModel();
		this.listSelectionModel = new SimplePropertyValueModel<String>(null);
	}

	private SimpleListValueModel<String> buildListModel() {
		SimpleListValueModel<String> result = new SimpleListValueModel<String>();
		result.add("zero");
		result.add("one");
		result.add("two");
		result.add("three");
		result.add("four");
		result.add("five");
		result.add("six");
		result.add("seven");
		return result;
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(this.getClass().getSimpleName());
		parent.setSize(500, 150);
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayout(new FormLayout());
		Control widgetPanel = this.buildWidgetPanels(mainPanel);
		this.buildControlPanel(mainPanel, widgetPanel);
		return mainPanel;
	}

	private Control buildWidgetPanels(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100, -35);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout(SWT.VERTICAL));

		this.buildWidgetPanel1(panel);
		this.buildWidgetPanel2(panel);

		return panel;
	}

	private void buildWidgetPanel1(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayout(new FillLayout(SWT.HORIZONTAL));

		Button enabledComboBoxCheckBox = this.buildEnabledComboBoxCheckBox(panel);
		SWTBindingTools.bind(this.enabledModel, enabledComboBoxCheckBox);

		Button visibleComboBoxCheckBox = this.buildVisibleComboBoxCheckBox(panel);
		SWTBindingTools.bind(this.visibleModel, visibleComboBoxCheckBox);

		Label comboBoxLabel = this.buildComboBoxLabel(panel);
		Combo comboBox = this.buildComboBox(panel);
		SWTBindingTools.bindDropDownListBox(this.listModel, this.listSelectionModel, comboBox);
		SWTBindingTools.bindEnabledState(this.enabledModel, comboBoxLabel, comboBox);
		SWTBindingTools.bindVisibleState(this.visibleModel, comboBoxLabel, comboBox);
	}

	private Button buildEnabledComboBoxCheckBox(Composite parent) {
		return this.buildCheckBox(parent, "enabled");
	}

	private Button buildVisibleComboBoxCheckBox(Composite parent) {
		return this.buildCheckBox(parent, "visible");
	}

	private Button buildCheckBox(Composite parent, String text) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(text);
		return checkBox;
	}

	private Label buildComboBoxLabel(Composite parent) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText("list:");
		return label;
	}

	private Combo buildComboBox(Composite parent) {
		return new Combo(parent, SWT.READ_ONLY);
	}

	private void buildWidgetPanel2(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayout(new FillLayout(SWT.HORIZONTAL));

		Button checkBox1 = this.buildCheckBox(panel, "one");
		this.buildCheckBox(panel, "two");
		this.buildCheckBox(panel, "three");
		this.buildCheckBox(panel, "four");

		SWTBindingTools.bindEnabledState(this.enabledModel, panel, checkBox1);
		SWTBindingTools.bindVisibleState(this.visibleModel, panel);
	}

	private void buildControlPanel(Composite parent, Control widgetPanel) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(widgetPanel);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildClearEnabledModelButton(panel);
		this.buildClearVisibleModelButton(panel);
		this.buildNullSelectionModelButton(panel);
		this.buildNextButton(panel);
	}

	private void buildClearEnabledModelButton(Composite parent) {
		this.buildClearEnabledModelACI().fill(parent);
	}

	private ActionContributionItem buildClearEnabledModelACI() {
		Action action = new Action("clear enabled model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				BooleanStateControllerUITest.this.clearEnabledModel();
			}
		};
		action.setToolTipText("clear enabled model");
		return new ActionContributionItem(action);
	}

	void clearEnabledModel() {
		this.enabledModel.setValue(null);
	}

	private void buildClearVisibleModelButton(Composite parent) {
		this.buildClearVisibleModelACI().fill(parent);
	}

	private ActionContributionItem buildClearVisibleModelACI() {
		Action action = new Action("clear visible model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				BooleanStateControllerUITest.this.clearVisibleModel();
			}
		};
		action.setToolTipText("clear visible model");
		return new ActionContributionItem(action);
	}

	void clearVisibleModel() {
		this.visibleModel.setValue(null);
	}

	private void buildNullSelectionModelButton(Composite parent) {
		this.buildNullSelectionModelACI().fill(parent);
	}

	private ActionContributionItem buildNullSelectionModelACI() {
		Action action = new Action("null selection model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				BooleanStateControllerUITest.this.setSelectionModelNull();
			}
		};
		action.setToolTipText("null selection model");
		return new ActionContributionItem(action);
	}

	void setSelectionModelNull() {
		this.listSelectionModel.setValue(null);
	}

	private void buildNextButton(Composite parent) {
		this.buildNextACI().fill(parent);
	}

	private ActionContributionItem buildNextACI() {
		Action action = new Action("next", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				BooleanStateControllerUITest.this.next();
			}
		};
		action.setToolTipText("next");
		return new ActionContributionItem(action);
	}

	void next() {
		this.listSelectionModel.setValue(this.getNextListSelection());
	}

	private String getNextListSelection() {
		return this.listModel.get(this.getNextListSelectionIndex());
	}

	private int getNextListSelectionIndex() {
		int index = this.getListSelectionIndex();
		if (index == -1) {
			return 0;
		}
		index++;
		return (index == this.listModel.size()) ? 0 : index;
	}

	private int getListSelectionIndex() {
		return this.listModel.indexOf(this.getListSelection());
	}

	private String getListSelection() {
		return this.listSelectionModel.getValue();
	}
}
