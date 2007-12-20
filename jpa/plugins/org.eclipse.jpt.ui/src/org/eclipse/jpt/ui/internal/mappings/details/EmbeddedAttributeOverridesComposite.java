/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EmbeddedAttributeOverridesComposite extends BaseJpaComposite
{
	private ListViewer listViewer;

	private IEmbeddedMapping embedded;
	private Adapter embeddedListener;

	private IAttributeOverride attributeOverride;
	private Adapter attributeOverrideListener;

	protected ColumnComposite columnComposite;

	private Button overrideDefaultButton;

	public EmbeddedAttributeOverridesComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, widgetFactory);
		this.embeddedListener = buildEmbeddedListener();
		this.attributeOverrideListener = buildAttributeOverrideListener();
	}

	private Adapter buildEmbeddedListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				embeddedChanged(notification);
			}
		};
	}

	private Adapter buildAttributeOverrideListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				attributeOverrideChanged(notification);
			}
		};
	}


	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);

		Group attributeOverridesGroup = getWidgetFactory().createGroup(
			composite, JptUiMappingsMessages.AttributeOverridesComposite_attributeOverrides);
		attributeOverridesGroup.setLayout(new GridLayout(2, true));
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace= true;
		attributeOverridesGroup.setLayoutData(gridData);

		this.listViewer = buildAttributeOverridesListViewer(attributeOverridesGroup);
		gridData = new GridData();
		gridData.verticalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace= true;
		this.listViewer.getList().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.listViewer.getList(), IJpaHelpContextIds.MAPPING_EMBEDDED_ATTRIBUTE_OVERRIDES);

		this.overrideDefaultButton = getWidgetFactory().createButton(attributeOverridesGroup, "Override Default", SWT.CHECK);
		this.overrideDefaultButton.addSelectionListener(buildOverrideDefaultSelectionListener());
		gridData = new GridData();
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.overrideDefaultButton.setLayoutData(gridData);


		this.columnComposite = new ColumnComposite(attributeOverridesGroup, getWidgetFactory());
		gridData = new GridData();
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.columnComposite.getControl().setLayoutData(gridData);

	}

	private SelectionListener buildOverrideDefaultSelectionListener() {
		return new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				overrideDefaultButtonSelected(e);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				overrideDefaultButtonSelected(e);
			}
		};
	}

	private void overrideDefaultButtonSelected(SelectionEvent e) {
		boolean selection = this.overrideDefaultButton.getSelection();
		if (selection) {
			int index = this.embedded.getSpecifiedAttributeOverrides().size();
			IAttributeOverride attributeOverride = this.embedded.createAttributeOverride(index);
			this.embedded.getSpecifiedAttributeOverrides().add(attributeOverride);
			attributeOverride.setName(this.attributeOverride.getName());
			attributeOverride.getColumn().setSpecifiedName(this.attributeOverride.getColumn().getName());
		}
		else {
			this.embedded.getSpecifiedAttributeOverrides().remove(this.attributeOverride);
		}
	}


	private ListViewer buildAttributeOverridesListViewer(Composite parent) {
		ListViewer listViewer = new ListViewer(parent, SWT.SINGLE | SWT.BORDER);
		listViewer.setLabelProvider(buildAttributeOverridesLabelProvider());
		listViewer.setContentProvider(buildAttributeOverridesContentProvider());

		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				attributeOverridesListSelectionChanged(event);
			}
		});

		return listViewer;
	}

	protected void attributeOverridesListSelectionChanged(SelectionChangedEvent event) {
		if (((StructuredSelection) event.getSelection()).isEmpty()) {
			this.columnComposite.populate(null);
			this.columnComposite.enableWidgets(false);
			this.overrideDefaultButton.setSelection(false);
			this.overrideDefaultButton.setEnabled(false);
		}
		else {
			this.attributeOverride = getSelectedAttributeOverride();
			boolean specifiedOverride = this.embedded.getSpecifiedAttributeOverrides().contains(this.attributeOverride);
			this.overrideDefaultButton.setSelection(specifiedOverride);
			this.columnComposite.populate(this.attributeOverride.getColumn());
			this.columnComposite.enableWidgets(specifiedOverride);
			this.overrideDefaultButton.setEnabled(true);
		}
	}

	private ILabelProvider buildAttributeOverridesLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				//TODO also display column name somehow
				return ((IAttributeOverride) element).getName();
			}
		};
	}


	private IContentProvider buildAttributeOverridesContentProvider() {
		return new IStructuredContentProvider() {
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			public void dispose() {
			}

			public Object[] getElements(Object inputElement) {
				return ((IEmbeddedMapping) inputElement).getAttributeOverrides().toArray();
			}
		};
	}

	private IAttributeOverride getSelectedAttributeOverride() {
		return (IAttributeOverride) ((StructuredSelection) this.listViewer.getSelection()).getFirstElement();
	}


	public void doPopulate(EObject obj) {
		this.embedded = (IEmbeddedMapping) obj;
		if (this.embedded == null) {
			this.attributeOverride = null;
			this.columnComposite.populate(null);
			this.listViewer.setInput(null);
			return;
		}

		if (this.listViewer.getInput() != this.embedded) {
			this.listViewer.setInput(this.embedded);
		}
		if (!this.embedded.getAttributeOverrides().isEmpty()) {
			if (this.listViewer.getSelection().isEmpty()) {
				IAttributeOverride attributeOverride = this.embedded.getAttributeOverrides().get(0);
				this.listViewer.setSelection(new StructuredSelection(attributeOverride));
			}
			else {
				this.columnComposite.enableWidgets(true);
				this.columnComposite.populate(((IAttributeOverride)((StructuredSelection) this.listViewer.getSelection()).getFirstElement()).getColumn());
			}
		}
		else {
			this.columnComposite.populate(null);
			this.columnComposite.enableWidgets(false);
		}
	}

	@Override
	protected void doPopulate() {
		this.columnComposite.doPopulate();
	}

	@Override
	protected void engageListeners() {
		if (this.embedded != null) {
			this.embedded.eAdapters().add(this.embeddedListener);
			for (IAttributeOverride attributeOverride : this.embedded.getAttributeOverrides()) {
				attributeOverride.eAdapters().add(this.attributeOverrideListener);
			}
		}
	}

	@Override
	protected void disengageListeners() {
		if (this.embedded != null) {
			this.embedded.eAdapters().remove(this.embeddedListener);
			for (IAttributeOverride attributeOverride : this.embedded.getAttributeOverrides()) {
				attributeOverride.eAdapters().remove(this.attributeOverrideListener);
			}
		}
	}


	protected void embeddedChanged(Notification notification) {
		switch (notification.getFeatureID(IEmbeddedMapping.class)) {
			case JpaCoreMappingsPackage.IEMBEDDED__SPECIFIED_ATTRIBUTE_OVERRIDES :
			case JpaCoreMappingsPackage.IEMBEDDED__DEFAULT_ATTRIBUTE_OVERRIDES :
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (listViewer.getList().isDisposed()) {
							return;
						}
						listViewer.refresh();
						columnComposite.enableWidgets(!listViewer.getSelection().isEmpty());
						if (listViewer.getSelection().isEmpty()) {
							columnComposite.populate(null);
						}
					}
				});
				if (notification.getEventType() == Notification.ADD) {
					((IAttributeOverride) notification.getNewValue()).eAdapters().add(this.attributeOverrideListener);
					final Object newValue = notification.getNewValue();
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							if (listViewer.getList().isDisposed()) {
								return;
							}
							listViewer.setSelection(new StructuredSelection(newValue));
						}
					});
				}
				else if (notification.getEventType() == Notification.ADD_MANY) {
					List addedList = (List) notification.getNewValue();
					for (Iterator<IAttributeOverride> i = addedList.iterator(); i.hasNext(); ) {
						IAttributeOverride override = i.next();
						override.eAdapters().add(this.attributeOverrideListener);
					}
				}
				else if (notification.getEventType() == Notification.REMOVE) {
					((IAttributeOverride) notification.getOldValue()).eAdapters().remove(this.attributeOverrideListener);
				}
				else if (notification.getEventType() == Notification.REMOVE_MANY) {
					List removedList = (List) notification.getOldValue();
					for (Iterator<IAttributeOverride> i = removedList.iterator(); i.hasNext(); ) {
						IAttributeOverride override = i.next();
						override.eAdapters().remove(this.attributeOverrideListener);
					}
				}
				break;
			default :
				break;
		}
	}

	protected void attributeOverrideChanged(Notification notification) {
		switch (notification.getFeatureID(IAttributeOverride.class)) {
			case JpaCoreMappingsPackage.IATTRIBUTE_OVERRIDE__NAME :
				final IAttributeOverride attributeOverride = (IAttributeOverride) notification.getNotifier();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						listViewer.refresh(attributeOverride);
					}
				});
				break;
			default :
				break;
		}
	}

	@Override
	public void dispose() {
		this.columnComposite.dispose();
		super.dispose();
	}
}
