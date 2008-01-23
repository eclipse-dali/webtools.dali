/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class StringWithDefaultChooser extends BaseJpaController
{
	private ComboViewer comboViewer;
	private StringHolder stringHolder;

	private Adapter stringHolderListener;

	private static final String DEFAULT = "default";


	public StringWithDefaultChooser(PropertyValueModel<?> subjectHolder,
	                                Composite parent,
	                                TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
		buildStringHolderListener();
	}


	private IContentProvider buildContentProvider() {
		return new IStructuredContentProvider(){

			public void dispose() {
			}

			public Object[] getElements(Object inputElement) {
				return new String[] {DEFAULT};
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		};
	}

	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element == DEFAULT && stringHolder.wrappedObject() != null) {
					return stringHolder.defaultItem();
				}
				return super.getText(element);
			}
		};
	}

	private void buildStringHolderListener() {
		this.stringHolderListener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				stringHolderChanged(notification);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		CCombo combo = getWidgetFactory().createCCombo(container, SWT.FLAT);
		this.comboViewer = new ComboViewer(combo);
		this.comboViewer.setContentProvider(buildContentProvider());
		this.comboViewer.setLabelProvider(buildLabelProvider());

		this.comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				StringWithDefaultChooser.this.selectionChanged(event.getSelection());
			}
		});

		combo.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					comboModified(e);
				}
			});
	}
	private void comboModified(ModifyEvent e) {
		CCombo combo = (CCombo) e.getSource();
		combo.getSelectionIndex();
		String text = combo.getText();

		if (text != null && combo.getItemCount() > 0 && text.equals(combo.getItem(0))) {
			text = null;
		}
		if (this.stringHolder.getString() != text) {
			this.stringHolder.setString(text);
		}
	}

	@Override
	protected void disengageListeners() {
		super.disengageListeners();
//		if (this.stringHolder != null && this.stringHolder.wrappedObject() != null) {
//			this.stringHolder.wrappedObject().eAdapters().remove(this.stringHolderListener);
//		}
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.comboViewer.setInput(this.stringHolder);
		populateCombo();
	}

	@Override
	protected void engageListeners() {
		super.engageListeners();
//		if (this.stringHolder != null && this.stringHolder.wrappedObject() != null) {
//			this.stringHolder.wrappedObject().eAdapters().add(this.stringHolderListener);
//		}
	}

	public CCombo getCombo() {
		return this.comboViewer.getCCombo();
	}

	private void populateCombo() {
		if (this.stringHolder.wrappedObject() == null) {
			this.comboViewer.setSelection(StructuredSelection.EMPTY);
			return;
		}
		this.comboViewer.update(this.comboViewer.getElementAt(0), null);

		String string = this.stringHolder.getString();
		if (string == null) {
			if (((StructuredSelection) this.comboViewer.getSelection()).getFirstElement() == DEFAULT) {
				this.comboViewer.refresh();
			}
			else {
				this.comboViewer.setSelection(new StructuredSelection(DEFAULT));
			}
		}
		else {
			if (!this.comboViewer.getCombo().getText().equals(string)) {
				this.comboViewer.getCombo().setText(string);
			}
		}
	}

	void selectionChanged(ISelection sel) {
		if (sel instanceof IStructuredSelection) {
			String selection = (String) ((IStructuredSelection) sel).getFirstElement();
			if (this.comboViewer.getCombo().getSelectionIndex() == 0) {
				selection = null;
			}
			if (this.stringHolder.getString() == null) {
				if (selection != null) {
					this.stringHolder.setString(selection);
				}
			}
			else if (!this.stringHolder.getString().equals(selection)) {
				this.stringHolder.setString(selection);
			}
		}
	}

	private void stringHolderChanged(Notification notification) {
		if (notification.getFeatureID(this.stringHolder.featureClass()) ==
				this.stringHolder.featureId()) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						if (getCombo().isDisposed()) {
							return;
						}
						populate();
					}
				});
		}
		else if (this.stringHolder.supportsDefault()) {
			if (notification.getFeatureID(this.stringHolder.featureClass()) ==
				this.stringHolder.defaultFeatureId()) {
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							if (getCombo().isDisposed()) {
								return;
							}
							populate();
						}
					});

			}
		}
	}
	/**
	 * An interface to wrap an object that supports a string with a default setting
	 * An object of this type must be passed in to populate(EObject)
	 */
	public static interface StringHolder<T> {
		int defaultFeatureId();

		String defaultItem();

		/**
		 * Return the Class of the wrapped object
		 * @return
		 */
		Class<T> featureClass();

		/**
		 * Return the feature id of string setting on the wrapped object
		 * @return
		 */
		int featureId();

		/**
		 * Return the string setting from the wrapped object
		 * @return
		 */
		String getString();

		/**
		 * Set the string setting on the wrapped object
		 * @param string
		 */
		void setString(String string);

		boolean supportsDefault();

		/**
		 * The wrapped EObject that the enum setting is stored on
		 * @return
		 */
		T wrappedObject();
	}
}
