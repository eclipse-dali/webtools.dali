/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class PersistentTypeDetailsPage<T extends IPersistentType> extends BaseJpaDetailsPage<T>
{
	private Map<String, IJpaComposite<ITypeMapping>> composites;
	private String currentMappingKey;

	private IPersistentType persistentType;

	private Adapter persistentTypeListener;

	private ComboViewer typeMappingCombo;

	protected PageBook typeMappingPageBook;

	private IJpaComposite<ITypeMapping> visibleMappingComposite;

	public PersistentTypeDetailsPage(PropertyValueModel<? extends T> subjectHolder,
                                    Composite parent,
                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NONE, widgetFactory);
		this.persistentTypeListener = buildPersistentTypeListener();
		this.composites = new HashMap<String, IJpaComposite<ITypeMapping>>();
	}

	private IContentProvider buildContentProvider() {
		return new IStructuredContentProvider() {
			public void dispose() {
				// do nothing
			}

			public Object[] getElements(Object inputElement) {
				return (persistentType == null) ?
						new String[] {}:
						CollectionTools.array(PersistentTypeDetailsPage.this.typeMappingUiProviders());
			}

			public void inputChanged(
					Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		};
	}

	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ITypeMappingUiProvider) element).label();
			}
		};
	}

	protected IJpaComposite<ITypeMapping> buildMappingComposite(PageBook pageBook, String key)  {
		return typeMappingUiProvider(key).buildPersistentTypeMappingComposite(pageBook, getWidgetFactory());
	}

	private Adapter buildPersistentTypeListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				persistentTypeChanged(notification);
			}
		};
	}

	protected ComboViewer buildTypeMappingCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		this.typeMappingCombo = new ComboViewer(combo);
		this.typeMappingCombo.setContentProvider(buildContentProvider());
		this.typeMappingCombo.setLabelProvider(buildLabelProvider());
		this.typeMappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				typeMappingChanged(event);
			}
		});
		return this.typeMappingCombo;
	}

	protected Label buildTypeMappingLabel(Composite parent) {
		return getWidgetFactory().createLabel(parent, JptUiMessages.PersistentTypePage_mapAs);
	}

	protected PageBook buildTypeMappingPageBook(Composite parent) {
		this.typeMappingPageBook = new PageBook(parent, SWT.NONE);
		return this.typeMappingPageBook;
	}
	@Override
	protected void disengageListeners() {
		if (this.persistentType != null) {
			this.persistentType.eAdapters().remove(this.persistentTypeListener);
		}
	}

	@Override
	public void dispose() {
		disengageListeners();
		for (Iterator<IJpaComposite<ITypeMapping>> i = this.composites.values().iterator(); i.hasNext(); ) {
			i.next().dispose();
		}
		super.dispose();
	}


	@Override
	protected void doPopulate() {
		populateMappingComboAndPage();
	}

	@Override
	protected void engageListeners() {
		if (this.persistentType != null) {
			this.persistentType.eAdapters().add(this.persistentTypeListener);
		}
	}

	public IPersistentType getPersistentType() {
		return this.persistentType;
	}

	private IJpaComposite<ITypeMapping> mappingCompositeFor(String key) {
		IJpaComposite<ITypeMapping> mappingComposite = this.composites.get(key);
		if (mappingComposite != null) {
			return mappingComposite;
		}

		mappingComposite = buildMappingComposite(this.typeMappingPageBook, key);

		if (mappingComposite != null) {
			this.composites.put(key, mappingComposite);
		}

		return mappingComposite;
	}

	private void persistentTypeChanged(Notification notification) {
		switch (notification.getFeatureID(IPersistentType.class)) {
			case JpaCorePackage.IPERSISTENT_TYPE__MAPPING_KEY:
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							populate();
						}
					});
				break;
		}
	}

	private void populateMappingComboAndPage() {
		if (this.persistentType == null) {
			this.currentMappingKey = null;
			this.typeMappingCombo.setInput(null);
			this.typeMappingCombo.setSelection(StructuredSelection.EMPTY);

			if (this.visibleMappingComposite != null) {
				this.visibleMappingComposite.populate(null);
				this.visibleMappingComposite = null;
			}

			return;
		}

		String mappingKey = this.persistentType.getMapping().getKey();
		setComboData(mappingKey);

		populateMappingPage(mappingKey);
	}

	private void populateMappingPage(String mappingKey) {
		if (this.visibleMappingComposite != null) {
			if (mappingKey  == this.currentMappingKey) {
				if (this.visibleMappingComposite != null) {
					this.visibleMappingComposite.populate();
					return;
				}
			}
			else {
				this.visibleMappingComposite.populate();
				// don't return
			}
		}

		this.currentMappingKey = mappingKey;

		IJpaComposite mappingComposite = mappingCompositeFor(mappingKey);
		this.typeMappingPageBook.showPage(mappingComposite.getControl());

		this.visibleMappingComposite = mappingComposite;
		this.visibleMappingComposite.populate();
	}

	private void setComboData(String mappingKey) {
		if (this.persistentType != this.typeMappingCombo.getInput()) {
			this.typeMappingCombo.setInput(this.persistentType);
		}

		ITypeMappingUiProvider provider = typeMappingUiProvider(mappingKey);
		if (! provider.equals(((StructuredSelection) this.typeMappingCombo.getSelection()).getFirstElement())) {
			this.typeMappingCombo.setSelection(new StructuredSelection(provider));
		}
	}

	private void typeMappingChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof StructuredSelection) {
			ITypeMappingUiProvider provider = (ITypeMappingUiProvider) ((StructuredSelection) event.getSelection()).getFirstElement();
			this.persistentType.setMappingKey(provider.mappingKey());
		}
	}

//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return typeMappingCombo.getCombo().setFocus();
//	}

	private ITypeMappingUiProvider typeMappingUiProvider(String key) {
		for (ListIterator<ITypeMappingUiProvider> i = this.typeMappingUiProviders(); i.hasNext();) {
			ITypeMappingUiProvider provider = i.next();
			if (provider.mappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported type mapping UI provider key: " + key);
	}

	protected abstract ListIterator<ITypeMappingUiProvider> typeMappingUiProviders();

}
