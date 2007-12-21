/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??
 * â?? â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â?¬â??â?? â??
 * â?? â??                                                                     â??â?¼â?? â??
 * â?? â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â?´â??â?? â??
 * â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??â??</pre>
 *
 * @see EnumHolder
 * @see ColumnComposite - A container of this controller
 * @see CommonWidgets - A factory creating it
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class EnumComboViewer<T extends IJpaNode, V> extends BaseJpaController<T>
{
	private ComboViewer comboViewer;
	private PropertyChangeListener propertyChangeListener;

	public EnumComboViewer(PropertyValueModel<? extends T> subjectHolder,
	                       Composite parent,
	                       TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	protected IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element == defaultValue()) {
					return NLS.bind(JptUiMappingsMessages.EnumComboViewer_default, defaultStringValue());
				}
				return super.getText(element);
			}
		};
	}

	private PropertyChangeListener buildPropertyChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				EnumComboViewer.this.populate();
			}
		};
	}

	@SuppressWarnings("unchecked")
	private ISelectionChangedListener buildSelectionChangedListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				StructuredSelection selection = (StructuredSelection) e.getSelection();
				V value = (V) selection.getFirstElement();
				EnumComboViewer.this.setValue(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void buildWidget(Composite parent, int style) {
		CCombo combo = getWidgetFactory().createCCombo(parent);

		this.comboViewer = new ComboViewer(combo);
		this.comboViewer.setLabelProvider(buildLabelProvider());
		this.comboViewer.addSelectionChangedListener(buildSelectionChangedListener());
	}

	/**
	 * Returns the possible choices to show in the viewer.
	 *
	 * @return The items to show in the combos
	 */
	protected abstract V[] choices();

	/**
	 * Returns the displayable string of the default value.
	 *
	 * @return The human readable text of the default value
	 */
	protected abstract String defaultStringValue();

	/**
	 * Returns the default value.
	 *
	 * @return The value that is declared as being the default when it is not
	 * defined or <code>null</code> if there is no default value
	 */
	protected abstract V defaultValue();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners() {
		super.disengageListeners();
		getSubjectHolder().removePropertyChangeListener(propertyName(), propertyChangeListener);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		populateCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners() {
		super.engageListeners();
		getSubjectHolder().addPropertyChangeListener(propertyName(), propertyChangeListener);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public CCombo getControl() {
		return this.comboViewer.getCCombo();
	}

	protected abstract V getValue();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		propertyChangeListener = buildPropertyChangeListener();
	}

	private void populateCombo() {
		this.getControl().removeAll();
		this.comboViewer.add(this.choices());

		V value = getValue();
		if (value != null) {

			this.comboViewer.setSelection(new StructuredSelection(value));
		}
		else {
			this.comboViewer.setSelection(new StructuredSelection());
		}
	}

	/**
	 * Returns the property name used to listen for changes of the value when it
	 * is done outside of this viewer.
	 *
	 * @return The property name associated with the value being shown by this
	 * viewer
	 */
	protected abstract String propertyName();

	/**
	 * Requests the given new value be set on the subject.
	 *
	 * @param value The new value to be set
	 */
	protected abstract void setValue(V value);
}