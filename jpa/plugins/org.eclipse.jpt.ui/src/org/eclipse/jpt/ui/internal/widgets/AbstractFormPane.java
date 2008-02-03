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

import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The abstract pane to use when the pane is shown using the form look and feel,
 * which is handled by <code>TabbedPropertySheetWidgetFactory</code>.
 *
 * @see TabbedPropertySheetWidgetFactory
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractFormPane<T extends Model> extends AbstractPane<T>
{
	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractFormPane(AbstractFormPane<? extends T> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>AbstractFormPane</code>.
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
	protected AbstractFormPane(AbstractFormPane<? extends T> parentPane,
	                           Composite parent,
	                           boolean automaticallyAlignWidgets) {

		super(parentPane, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>AbstractFormPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractFormPane(AbstractFormPane<?> parentPane,
	                           PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>AbstractFormPane</code>.
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
	protected AbstractFormPane(AbstractFormPane<?> parentPane,
	                           PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>AbstractFormPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 *
	 * @category Constructor
	 */
	protected AbstractFormPane(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/**
	 * Creates a new <code>AbstractFormPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 *
	 * @category Constructor
	 */
	protected AbstractFormPane(PropertyValueModel<? extends T> subjectHolder,
	                           Composite parent,
	                           TabbedPropertySheetWidgetFactory widgetFactory) {

		this(subjectHolder, parent, new WidgetFactory(widgetFactory));
	}

	/**
	 * Returns
	 *
	 * @return
	 */
	protected final TabbedPropertySheetWidgetFactory getFormWidgetFactory() {
		WidgetFactory widgetFactory = (WidgetFactory) getWidgetFactory();
		return widgetFactory.widgetFactory;
	}

	private static class WidgetFactory implements IWidgetFactory {

		private final TabbedPropertySheetWidgetFactory widgetFactory;

		public WidgetFactory(TabbedPropertySheetWidgetFactory widgetFactory) {
			super();
			this.widgetFactory = widgetFactory;
		}

		public Button createButton(Composite parent, String text, int style) {
			return widgetFactory.createButton(parent, text, style);
		}

		public CCombo createCombo(Composite parent) {
			return widgetFactory.createCCombo(parent, SWT.FLAT);
		}

		public Composite createComposite(Composite parent) {
			return widgetFactory.createComposite(parent);
		}

		public Group createGroup(Composite parent, String title) {
			return widgetFactory.createGroup(parent, title);
		}

		public Hyperlink createHyperlink(Composite parent, String text) {
			return widgetFactory.createHyperlink(parent, text, SWT.FLAT);
		}

		public Label createLabel(Composite container, String labelText) {
			return widgetFactory.createLabel(container, labelText);
		}

		public List createList(Composite container, int style) {
			return widgetFactory.createList(container, style | SWT.FLAT);
		}

		public Section createSection(Composite parent, int style) {
			return widgetFactory.createSection(parent, style | SWT.FLAT);
		}

		public Text createText(Composite parent) {
			return widgetFactory.createText(parent, null);
		}
	}
}
