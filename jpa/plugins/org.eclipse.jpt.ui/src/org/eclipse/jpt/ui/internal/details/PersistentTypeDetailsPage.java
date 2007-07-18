/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.ui.internal.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EntityUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.MappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.NullTypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
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
	
	/**
	 * A Map of mapping Composites of type IPersistenceComposite that is keyed on IConfigurationElement
	 */
	private Map composites;
	
	protected PageBook typeMappingPageBook;
	
	private IJpaComposite visibleMappingComposite;
	
	private Collection<ITypeMappingUiProvider> typeMappingUiProviders;
	
	public PersistentTypeDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NONE, new BasicCommandStack(), widgetFactory);
		this.persistentTypeListener = buildPersistentTypeListener();
		this.composites = new HashMap();
		this.typeMappingUiProviders = buildTypeMappingUiProviders();
	}
	
	protected Collection<ITypeMappingUiProvider> buildTypeMappingUiProviders() {
		Collection<ITypeMappingUiProvider> typeMappingUiProviders = new ArrayList<ITypeMappingUiProvider>();
		typeMappingUiProviders.add(NullTypeMappingUiProvider.instance());
		typeMappingUiProviders.add(EntityUiProvider.instance());
		typeMappingUiProviders.add(MappedSuperclassUiProvider.instance());			
		typeMappingUiProviders.add(EmbeddableUiProvider.instance());			
		return typeMappingUiProviders;
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
		typeMappingCombo = new CComboViewer(combo);
		typeMappingCombo.setContentProvider(buildContentProvider());
		typeMappingCombo.setLabelProvider(buildLabelProvider());
		typeMappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				typeMappingChanged(event);
			}
		});
		return typeMappingCombo;
	}
	
	private IContentProvider buildContentProvider() {
		return new IStructuredContentProvider() {
			public void dispose() {
				// do nothing
			}
			
			public Object[] getElements(Object inputElement) {
				return (persistentType == null) ?
						new String[] {}:
						PersistentTypeDetailsPage.this.typeMappingUiProviders.toArray();
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
		typeMappingPageBook = new PageBook(parent, SWT.NONE);
		return typeMappingPageBook;
	}
		
	private IJpaProject getJpaProject() {
		return getPersistentType().getJpaProject();
	}

	private ITypeMappingUiProvider typeMappingUiProvider(String key) {
		for (ITypeMappingUiProvider provider : this.typeMappingUiProviders) {
			if (provider.key() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("unsupported type mapping UI provider key: " + key);
	}
	
	private void typeMappingChanged(SelectionChangedEvent event) {
		if (isPopulating()) {
			return;
		}
		if (event.getSelection() instanceof StructuredSelection) {
			ITypeMappingUiProvider provider = (ITypeMappingUiProvider) ((StructuredSelection) event.getSelection()).getFirstElement();
			persistentType.setMappingKey(provider.key());
		}
	}
	
	@Override
	protected void doPopulate(IJpaContentNode persistentTypeNode) {
		persistentType = (IPersistentType) persistentTypeNode;
		populateMappingComboAndPage();
	}
	
	@Override
	protected void doPopulate() {
		populateMappingComboAndPage();
	}
	
	protected void engageListeners() {
		if (persistentType != null) {
			persistentType.eAdapters().add(persistentTypeListener);
		}
	}
	
	protected void disengageListeners() {
		if (persistentType != null) {
			persistentType.eAdapters().remove(persistentTypeListener);
		}
	}
	
	private void populateMappingComboAndPage() {
		if (persistentType == null) {
			currentMappingKey = null;
			typeMappingCombo.setInput(null);
			typeMappingCombo.setSelection(StructuredSelection.EMPTY);
			
			if (visibleMappingComposite != null) {
				visibleMappingComposite.populate(null);
				visibleMappingComposite = null;
			}
			
			return;
		}
		
		String mappingKey = persistentType.getMapping().getKey();
		setComboData(mappingKey, persistentType.candidateMappingKeys());
		
		populateMappingPage(mappingKey);
	}
	
	private void populateMappingPage(String mappingKey) {
		if (visibleMappingComposite != null) {
			if (mappingKey  == currentMappingKey) {
				if (visibleMappingComposite != null) {
					visibleMappingComposite.populate(persistentType.getMapping());
					return;
				}
			}
			else {
				visibleMappingComposite.populate(null);
				// don't return
			}
		}
		
		currentMappingKey = mappingKey;
		
		IJpaComposite mappingComposite = mappingCompositeFor(mappingKey);
		typeMappingPageBook.showPage(mappingComposite.getControl());
		
		visibleMappingComposite = mappingComposite;
		visibleMappingComposite.populate(persistentType.getMapping());
	}
	
	private void setComboData(String mappingKey, Iterator availableMappingKeys) {
		if (persistentType != typeMappingCombo.getInput()) {
			typeMappingCombo.setInput(persistentType);
		}
		
		ITypeMappingUiProvider provider = typeMappingUiProvider(mappingKey);
		if (! provider.equals(((StructuredSelection) typeMappingCombo.getSelection()).getFirstElement())) {
			typeMappingCombo.setSelection(new StructuredSelection(provider));
		}
	}
	
	private IJpaComposite mappingCompositeFor(String key) {
		IJpaComposite mappingComposite = (IJpaComposite) composites.get(key);
		if (mappingComposite != null) {
			return mappingComposite;
		}
		
		mappingComposite = buildMappingComposite(typeMappingPageBook, key);
		
		if (mappingComposite != null) {
			composites.put(key, mappingComposite);
		}
		
		return mappingComposite;
	}
	
	protected IJpaComposite buildMappingComposite(PageBook pageBook, String key)  {
		//TODO what about null composite?
		return typeMappingUiProvider(key).buildPersistentTypeMappingComposite(pageBook, this.commandStack, getWidgetFactory());
//		return new NullComposite(pageBook, commandStack);
	}

//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return typeMappingCombo.getCombo().setFocus();
//	}
	
	public void dispose() {
		disengageListeners();
		for (Iterator i = composites.values().iterator(); i.hasNext(); ) {
			((IJpaComposite) i.next()).dispose();
		}
		super.dispose();
	}
	
	public IPersistentType getPersistentType() {
		return persistentType;
	}

}
