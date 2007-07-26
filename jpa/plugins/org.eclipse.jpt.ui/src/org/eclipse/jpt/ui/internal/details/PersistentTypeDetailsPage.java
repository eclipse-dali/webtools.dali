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
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class PersistentTypeDetailsPage extends BaseJpaDetailsPage 
{	
	private IPersistentType persistentType;
	private Adapter persistentTypeListener;
	
	private String currentMappingKey;
	
	private CComboViewer typeMappingCombo;
	
	private Map<String, IJpaComposite<ITypeMapping>> composites;
	
	protected PageBook typeMappingPageBook;
	
	private IJpaComposite<ITypeMapping> visibleMappingComposite;
	
	public PersistentTypeDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NONE, new BasicCommandStack(), widgetFactory);
		this.persistentTypeListener = buildPersistentTypeListener();
		this.composites = new HashMap<String, IJpaComposite<ITypeMapping>>();
	}
	
	protected abstract ListIterator<ITypeMappingUiProvider> typeMappingUiProviders();
	
	private ITypeMappingUiProvider typeMappingUiProvider(String key) {
		for (ListIterator<ITypeMappingUiProvider> i = this.typeMappingUiProviders(); i.hasNext();) {
			ITypeMappingUiProvider provider = i.next();
			if (provider.mappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported type mapping UI provider key: " + key);
	}

	private Adapter buildPersistentTypeListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				persistentTypeChanged(notification);
			}
		};
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

	protected Label buildTypeMappingLabel(Composite parent) {
		return getWidgetFactory().createLabel(parent, JptUiMessages.PersistentTypePage_mapAs);
	}
	
	protected CComboViewer buildTypeMappingCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		this.typeMappingCombo = new CComboViewer(combo);
		this.typeMappingCombo.setContentProvider(buildContentProvider());
		this.typeMappingCombo.setLabelProvider(buildLabelProvider());
		this.typeMappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				typeMappingChanged(event);
			}
		});
		return this.typeMappingCombo;
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
	
	protected PageBook buildTypeMappingPageBook(Composite parent) {
		this.typeMappingPageBook = new PageBook(parent, SWT.NONE);
		return this.typeMappingPageBook;
	}
	
	
	private void typeMappingChanged(SelectionChangedEvent event) {
		if (isPopulating()) {
			return;
		}
		if (event.getSelection() instanceof StructuredSelection) {
			ITypeMappingUiProvider provider = (ITypeMappingUiProvider) ((StructuredSelection) event.getSelection()).getFirstElement();
			this.persistentType.setMappingKey(provider.mappingKey());
		}
	}
	
	@Override
	protected void doPopulate(IJpaContentNode persistentTypeNode) {
		this.persistentType = (IPersistentType) persistentTypeNode;
		populateMappingComboAndPage();
	}
	
	@Override
	protected void doPopulate() {
		populateMappingComboAndPage();
	}
	
	protected void engageListeners() {
		if (this.persistentType != null) {
			this.persistentType.eAdapters().add(this.persistentTypeListener);
		}
	}
	
	protected void disengageListeners() {
		if (this.persistentType != null) {
			this.persistentType.eAdapters().remove(this.persistentTypeListener);
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
					this.visibleMappingComposite.populate(this.persistentType.getMapping());
					return;
				}
			}
			else {
				this.visibleMappingComposite.populate(null);
				// don't return
			}
		}
		
		this.currentMappingKey = mappingKey;
		
		IJpaComposite mappingComposite = mappingCompositeFor(mappingKey);
		this.typeMappingPageBook.showPage(mappingComposite.getControl());
		
		this.visibleMappingComposite = mappingComposite;
		this.visibleMappingComposite.populate(this.persistentType.getMapping());
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
	
	protected IJpaComposite<ITypeMapping> buildMappingComposite(PageBook pageBook, String key)  {
		return typeMappingUiProvider(key).buildPersistentTypeMappingComposite(pageBook, this.commandStack, getWidgetFactory());
	}

//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return typeMappingCombo.getCombo().setFocus();
//	}
	
	public void dispose() {
		disengageListeners();
		for (Iterator<IJpaComposite<ITypeMapping>> i = this.composites.values().iterator(); i.hasNext(); ) {
			i.next().dispose();
		}
		super.dispose();
	}
	
	public IPersistentType getPersistentType() {
		return this.persistentType;
	}

}
