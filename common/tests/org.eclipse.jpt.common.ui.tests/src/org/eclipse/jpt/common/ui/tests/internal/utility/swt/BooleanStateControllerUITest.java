/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * Play around with boolean state controllers ('enabled' and 'visible').
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
	private final WritablePropertyValueModel<Boolean> enabledHolder;
	private final WritablePropertyValueModel<Boolean> visibleHolder;
	private final SimpleListValueModel<String> listHolder;
	private final WritablePropertyValueModel<String> listSelectionHolder;

	public static void main(String[] args) throws Exception {
		Window window = new BooleanStateControllerUITest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private BooleanStateControllerUITest(@SuppressWarnings("unused") String[] args) {
		super(null);
		this.enabledHolder = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.visibleHolder = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.listHolder = this.buildListHolder();
		this.listSelectionHolder = new SimplePropertyValueModel<String>(null);
	}

	private SimpleListValueModel<String> buildListHolder() {
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
		SWTTools.bind(this.enabledHolder, enabledComboBoxCheckBox);

		Button visibleComboBoxCheckBox = this.buildVisibleComboBoxCheckBox(panel);
		SWTTools.bind(this.visibleHolder, visibleComboBoxCheckBox);

		Label comboBoxLabel = this.buildComboBoxLabel(panel);
		Combo comboBox = this.buildComboBox(panel);
		SWTTools.bind(this.listHolder, this.listSelectionHolder, comboBox);
		SWTTools.controlEnabledState(this.enabledHolder, comboBoxLabel, comboBox);
		SWTTools.controlVisibleState(this.visibleHolder, comboBoxLabel, comboBox);
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

		SWTTools.controlEnabledState(this.enabledHolder, panel, checkBox1);
		SWTTools.controlVisibleState(this.visibleHolder, panel);
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
		this.enabledHolder.setValue(null);
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
		this.visibleHolder.setValue(null);
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
		this.listSelectionHolder.setValue(null);
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
		this.listSelectionHolder.setValue(this.getNextListSelection());
	}

	private String getNextListSelection() {
		return this.listHolder.get(this.getNextListSelectionIndex());
	}

	private int getNextListSelectionIndex() {
		int index = this.getListSelectionIndex();
		if (index == -1) {
			return 0;
		}
		index++;
		return (index == this.listHolder.size()) ? 0 : index;
	}

	private int getListSelectionIndex() {
		return this.listHolder.indexOf(this.getListSelection());
	}

	private String getListSelection() {
		return this.listSelectionHolder.getValue();
	}

}
