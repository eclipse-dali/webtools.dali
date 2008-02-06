/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

/**
 * The abstract pane to use when the pane is shown in an <code>AbstractDialog</code>.
 *
 * @see AbstractDialog
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractDialogPane<T extends Node> extends AbstractPane<T> {

	/**
	 * Creates a new <code>AbstractDialog</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractDialogPane(AbstractDialogPane<? extends T> parentPane,
	                             Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>AbstractDialogPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected AbstractDialogPane(AbstractDialogPane<? extends T> parentPane,
	                             Composite parent,
	                             boolean automaticallyAlignWidgets) {

		super(parentPane, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>AbstractDialogPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractDialogPane(AbstractDialogPane<?> parentPane,
	                             PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>AbstractDialogPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected AbstractDialogPane(AbstractDialogPane<?> parentPane,
	                             PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent,
	                             boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>AbstractDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractDialogPane(PropertyValueModel<? extends T> subjectHolder,
	                             Composite parent) {

		super(subjectHolder, parent, WidgetFactory.instance());
	}

	/**
	 * This <code>IWidgetFactory</code> simply creates plain SWT widgets.
	 */
	private static class WidgetFactory implements IWidgetFactory {

		private static final IWidgetFactory INSTANCE = new WidgetFactory();

		static IWidgetFactory instance() {
			return INSTANCE;
		}

		public Button createButton(Composite parent, String text, int style) {
			Button button = new Button(parent, SWT.BORDER | style);
			button.setText(text);
			return button;
		}

		public CCombo createCCombo(Composite parent) {
			return new CCombo(parent, SWT.BORDER | SWT.READ_ONLY);
		}

		public Combo createCombo(Composite parent) {
			return new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		}

		public Composite createComposite(Composite parent) {
			return new Composite(parent, SWT.NULL);
		}

		public CCombo createEditableCCombo(Composite parent) {
			return new CCombo(parent, SWT.BORDER);
		}

		public Combo createEditableCombo(Composite parent) {
			return new Combo(parent, SWT.BORDER);
		}

		public Group createGroup(Composite parent, String title) {
			Group group = new Group(parent, SWT.NULL);
			group.setText(title);
			return group;
		}

		public Hyperlink createHyperlink(Composite parent, String text) {
			Hyperlink hyperlink = new Hyperlink(parent, SWT.NULL);
			hyperlink.setText(text);
			return hyperlink;
		}

		public Label createLabel(Composite parent, String labelText) {
			Label label = new Label(parent, SWT.LEFT);
			label.setText(labelText);
			return label;
		}

		public List createList(Composite parent, int style) {
			return new List(parent, SWT.BORDER | style);
		}

		public Section createSection(Composite parent, int style) {
			return new Section(parent, style);
		}

		public Text createText(Composite parent) {
			return new Text(parent, SWT.BORDER);
		}
	}
}