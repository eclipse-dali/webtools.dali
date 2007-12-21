/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public final class AccessTypeComboViewer<T> extends BaseJpaController<AccessTypeComboViewer.AccessHolder<? extends T>> {

	private Adapter accessHolderListener;
	private ComboViewer comboViewer;

	public AccessTypeComboViewer(PropertyValueModel<? extends AccessTypeComboViewer.AccessHolder<? extends T>> subjectHolder,
	                             Composite parent,
	                             TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
		buildAccessHolderListener();
	}

	private void buildAccessHolderListener() {
		this.accessHolderListener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				accessHolderChanged(notification);
			}
		};
	}

	@Override
	protected void buildWidget(Composite parent, int style) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		this.comboViewer = new ComboViewer(combo);
		this.comboViewer.setLabelProvider(buildAccessTypeLabelProvider());
		this.comboViewer.add(AccessType.values());

		this.comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				AccessTypeComboViewer.this.accessTypeSelectionChanged(event.getSelection());
			}
		});
	}

	private IBaseLabelProvider buildAccessTypeLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element == AccessType.DEFAULT) {
					return JptUiMappingsMessages.AccessTypeCombo_default;
				}
				return super.getText(element);
			}
		};
	}

	void accessTypeSelectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			AccessType access = (AccessType) ((IStructuredSelection) selection).getFirstElement();
			if ( ! this.subject().getAccess().equals(access)) {
				this.subject().setAccess(access);
			}
		}
	}

	private void accessHolderChanged(Notification notification) {
		if (notification.getFeatureID(this.subject().featureClass()) ==
				this.subject().featureId()) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						populate();
					}
				});
		}
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null && this.subject().wrappedObject() != null) {
			this.subject().wrappedObject().eAdapters().add(this.accessHolderListener);
		}
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null && this.subject().wrappedObject() != null) {
			this.subject().wrappedObject().eAdapters().remove(this.accessHolderListener);
		}
	}

	@Override
	public void doPopulate() {
		populateCombo();
	}

	private void populateCombo() {
		if (this.subject().wrappedObject() == null) {
			return;
		}

		AccessType access = this.subject().getAccess();

		if (((IStructuredSelection) this.comboViewer.getSelection()).getFirstElement() != access) {
			this.comboViewer.setSelection(new StructuredSelection(access));
		}
	}

	@Override
	public Control getControl() {
		return this.comboViewer.getCombo();
	}

	/**
	 * An interface to wrap an object that supports accessType
	 * An object of this type must be passed in to populate(EObject)
	 */
	public static interface AccessHolder<T> {
		/**
		 * Return the AccessType from the wrapped object
		 * @return
		 */
		AccessType getAccess();

		/**
		 * Set the AccessType on the wrapped object
		 * @param accessType
		 */
		void setAccess(AccessType accessType);

		/**
		 * Return the Class of the wrapped object
		 * @return
		 */
		Class<?> featureClass();

		/**
		 * Return the feature id of accessType on the wrapped object
		 * @return
		 */
		int featureId();

		/**
		 * The wrapped EObject that the accessType is stored on
		 * @return
		 */
		T wrappedObject();
	}
}