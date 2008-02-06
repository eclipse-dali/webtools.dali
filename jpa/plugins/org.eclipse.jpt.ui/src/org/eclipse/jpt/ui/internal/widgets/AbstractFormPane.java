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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
	 * Returns the actual widget factory wrapped by the <code>IWidgetFactory</code>.
	 *
	 * @return The factory used to create the widgets with the form style
	 * (flat-style) look and feel
	 */
	protected final TabbedPropertySheetWidgetFactory getFormWidgetFactory() {
		WidgetFactory widgetFactory = (WidgetFactory) getWidgetFactory();
		return widgetFactory.widgetFactory;
	}

	/**
	 * This <code>IWidgetFactory</code> is responsible to create the widgets
	 * using the <code>TabbedPropertySheetWidgetFactory</code> in order use the
	 * form style (flat-style) look and feel.
	 */
	private static class WidgetFactory implements IWidgetFactory {

		private final TabbedPropertySheetWidgetFactory widgetFactory;

		WidgetFactory(TabbedPropertySheetWidgetFactory widgetFactory) {
			super();
			this.widgetFactory = widgetFactory;
		}

		public Button createButton(Composite parent, String text, int style) {
			return widgetFactory.createButton(parent, text, style);
		}

		public CCombo createCCombo(Composite parent) {
			return createCombo(parent, SWT.READ_ONLY);
		}

		public Combo createCombo(Composite parent) {
			return new Combo(parent, SWT.READ_ONLY);
		}

		private CCombo createCombo(Composite parent, int style) {
			parent = fixBorderNotPainted(parent);
			return widgetFactory.createCCombo(parent, SWT.FLAT | style);
		}

		public Composite createComposite(Composite parent) {
			return widgetFactory.createComposite(parent);
		}

		public CCombo createEditableCCombo(Composite parent) {
			return createCombo(parent, SWT.NULL);
		}

		public Combo createEditableCombo(Composite parent) {
			return new Combo(parent, SWT.FLAT);
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
			return widgetFactory.createList(container, SWT.FLAT | style);
		}

		public Section createSection(Composite parent, int style) {
			return widgetFactory.createSection(parent, SWT.FLAT | style);
		}

		public Text createText(Composite parent) {
			return widgetFactory.createText(parent, null);
		}

		/**
		 * Wraps the given <code>Composite</code> into a new <code>Composite</code>
		 * in order to have the widgets' border painted. This must be a bug in the
		 * <code>GridLayout</code> used in a form.
		 *
		 * @param container The parent of the sub-pane with 1 pixel border
		 * @return A new <code>Composite</code> that has the necessary space to paint
		 * the border
		 */
		private Composite fixBorderNotPainted(Composite container) {

			GridLayout layout = new GridLayout(1, false);
			layout.marginHeight = 0;
			layout.marginWidth  = 0;
			layout.marginTop    = 1;
			layout.marginLeft   = 1;
			layout.marginBottom = 1;
			layout.marginRight  = 1;

			GridData gridData = new GridData();
			gridData.horizontalAlignment       = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			container = widgetFactory.createComposite(container);
			container.setLayoutData(gridData);
			container.setLayout(layout);

			return container;
		}
	}
}