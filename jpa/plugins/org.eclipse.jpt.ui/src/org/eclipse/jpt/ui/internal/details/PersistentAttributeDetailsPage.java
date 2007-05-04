/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
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
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.BasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.DefaultBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.DefaultEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.IdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.ManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.ManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.NullAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.OneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.OneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.TransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.VersionMappingUiProvider;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class PersistentAttributeDetailsPage extends BaseJpaDetailsPage 
{
	private IPersistentAttribute attribute;
	private IAttributeMapping attributeMapping;
	private Adapter persistentAttributeListener;
	private Adapter attributeMappingListener;
	
	private String currentMappingKey;
	
	private CComboViewer mappingCombo;
	
	private Map<String, IJpaComposite<IAttributeMapping>> mappingComposites;
	protected PageBook mappingPageBook;	
	private IJpaComposite<IAttributeMapping> currentMappingComposite;
	
	/**
	 * These IAtttributeMappingUiProviders will be used as elements in the attributeMapping combo
	 * The first element in the combo will be one of the defaultAttributemappingUiProviders or
	 * if none of those apply the nullAttriubteMappingUiProvider will be used. The rest of the elements
	 * will be the attributeMappingUiProviders.  The defaultAttributeMappingUiProvider is
	 * determined by matching its key with the key of the current attriubteMapping.  
	 */
	private Collection<IAttributeMappingUiProvider> attributeMappingUiProviders;
	private Collection<IAttributeMappingUiProvider> defaultAttributeMappingUiProviders;
	private IAttributeMappingUiProvider nullAttributeMappingUiProvider;
	
	public PersistentAttributeDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NONE, new BasicCommandStack(), widgetFactory);
		persistentAttributeListener = buildAttributeListener();
		attributeMappingListener = buildAttributeMappingListener();
		mappingComposites = new HashMap<String, IJpaComposite<IAttributeMapping>>();
		this.attributeMappingUiProviders = buildAttributeMappingUiProviders();
		this.defaultAttributeMappingUiProviders = buildDefaultAttributeMappingUiProviders();
		this.nullAttributeMappingUiProvider = buildNullAttributeMappingUiProvider();
	}
	
	protected IAttributeMappingUiProvider buildNullAttributeMappingUiProvider() {
		return NullAttributeMappingUiProvider.instance();
	}
	
	protected Collection<IAttributeMappingUiProvider> buildAttributeMappingUiProviders() {
		Collection<IAttributeMappingUiProvider> providers = new ArrayList<IAttributeMappingUiProvider>();
		providers.add(BasicMappingUiProvider.instance());
		providers.add(EmbeddedMappingUiProvider.instance());
		providers.add(EmbeddedIdMappingUiProvider.instance());
		providers.add(IdMappingUiProvider.instance());			
		providers.add(ManyToManyMappingUiProvider.instance());			
		providers.add(ManyToOneMappingUiProvider.instance());			
		providers.add(OneToManyMappingUiProvider.instance());			
		providers.add(OneToOneMappingUiProvider.instance());
		providers.add(TransientMappingUiProvider.instance());
		providers.add(VersionMappingUiProvider.instance());
		return providers;
	}
	
	protected  Collection<IAttributeMappingUiProvider> attributeMappingUiProviders() {
		return this.attributeMappingUiProviders;
	}
	
	protected Collection<IAttributeMappingUiProvider> buildDefaultAttributeMappingUiProviders() {
		Collection<IAttributeMappingUiProvider> providers = new ArrayList<IAttributeMappingUiProvider>();
		providers.add(DefaultBasicMappingUiProvider.instance());		
		providers.add(DefaultEmbeddedMappingUiProvider.instance());		
		return providers;
	}
	
	protected  Collection<IAttributeMappingUiProvider> defaultAttributeMappingUiProviders() {
		return this.defaultAttributeMappingUiProviders;
	}
	
	private IAttributeMappingUiProvider attributeMappingUiProvider(String key) {
		for (IAttributeMappingUiProvider provider : attributeMappingUiProviders()) {
			if (provider.key() == key) {
				return provider;
			}
		}
		return this.nullAttributeMappingUiProvider;
	}
	private IAttributeMappingUiProvider defaultAttributeMappingUiProvider(String key) {
		for (IAttributeMappingUiProvider provider : defaultAttributeMappingUiProviders()) {
			if (provider.key() == key) {
				return provider;
			}
		}
		return this.nullAttributeMappingUiProvider;
	}
	
	private Adapter buildAttributeListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				persistentAttributeChanged(notification);
			}
		};
	}
	
	void persistentAttributeChanged(Notification notification) {
		switch (notification.getFeatureID(IPersistentAttribute.class)) {
			case JpaCorePackage.IPERSISTENT_ATTRIBUTE__MAPPING:
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							populate();
						}
					});
				break;
		}
	}
	
	private Adapter buildAttributeMappingListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				attributeMappingChanged(notification);
			}
		};
	}
	
	void attributeMappingChanged(Notification notification) {
//		switch (notification.getFeatureID(IAttributeMapping.class)) {
//			case JpaCorePackage.IATTRIBUTE_MAPPING__DEFAULT:
//				Display.getDefault().asyncExec(
//					new Runnable() {
//						public void run() {
//							populate();
//						}
//					});
//				break;
//		}
	}
		
	protected Label buildMappingLabel(Composite parent) {
		return getWidgetFactory().createLabel(parent, JptUiMessages.PersistentAttributePage_mapAs);
	}
	
	protected CComboViewer buildMappingCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		mappingCombo = new CComboViewer(combo);
		mappingCombo.setContentProvider(buildContentProvider());
		mappingCombo.setLabelProvider(buildLabelProvider());
		mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				mappingChanged(event);
			}
		});
		return mappingCombo;
	}

	private IContentProvider buildContentProvider() {
		return new IStructuredContentProvider() {
			public void dispose() {
				// do nothing
			}
			
			public Object[] getElements(Object inputElement) {
				if (inputElement == null) {
					return new Object[]{};
				}
				return uiProvidersFor((IPersistentAttribute) inputElement);
			}
			
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		};
	}
	
	IAttributeMappingUiProvider[] uiProvidersFor(IPersistentAttribute persistentAttribute) {
		IAttributeMappingUiProvider[] providers = new IAttributeMappingUiProvider[attributeMappingUiProviders().size() +1];
		providers[0] =  this.nullAttributeMappingUiProvider;
		for (IAttributeMappingUiProvider uiProvider : defaultAttributeMappingUiProviders()) {
			if (uiProvider.key() == persistentAttribute.defaultMappingKey()) {
				providers[0] = uiProvider;
				break;
			}
		}
		int i = 1;
		for (IAttributeMappingUiProvider uiProvider : attributeMappingUiProviders()) {
			providers[i++] = uiProvider;
		}
		return providers;
	}

	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IAttributeMappingUiProvider) element).label();
			}
		};
	}
	
	protected PageBook buildMappingPageBook(Composite parent) {
		mappingPageBook = new PageBook(parent, SWT.NONE);
		return mappingPageBook;
	}
	
	protected IJpaComposite<IAttributeMapping> buildMappingComposite(PageBook pageBook, String key) {
		if (this.attributeMapping == null || this.attributeMapping.isDefault()) {
			return defaultAttributeMappingUiProvider(key).buildAttributeMappingComposite(pageBook, this.commandStack, getWidgetFactory());
		}

		return attributeMappingUiProvider(key).buildAttributeMappingComposite(pageBook, this.commandStack, getWidgetFactory());
	}
		
	void mappingChanged(SelectionChangedEvent event) {
		if (isPopulating()) {
			return;
		}
		if (event.getSelection() instanceof StructuredSelection) {
			IAttributeMappingUiProvider provider = (IAttributeMappingUiProvider) ((StructuredSelection) event.getSelection()).getFirstElement();
			String key = (this.defaultAttributeMappingUiProviders().contains(provider) ? null : provider.key());
			attribute.setSpecifiedMappingKey(key);
		}
	}
	
	@Override
	protected void doPopulate(IJpaContentNode persistentAttributeNode) {
		attribute = (IPersistentAttribute) persistentAttributeNode;
		populateMappingComboAndPage();
	}
	
	@Override
	protected void doPopulate() {
		populateMappingComboAndPage();
	}
	
	@Override
	protected void engageListeners() {
		if (attribute != null) {
			attribute.eAdapters().add(persistentAttributeListener);
			if (attributeMapping != null) {
				attributeMapping.eAdapters().add(attributeMappingListener);
			}
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (attribute != null) {
			attribute.eAdapters().remove(persistentAttributeListener);
			if (attributeMapping != null) {
				attributeMapping.eAdapters().remove(attributeMappingListener);
			}
		}
	}
	
	private void populateMappingComboAndPage() {
		if (attribute == null) {
			attributeMapping = null;
			currentMappingKey = null;
			mappingCombo.setInput(null);
			mappingCombo.setSelection(StructuredSelection.EMPTY);
			
			if (currentMappingComposite != null) {
				currentMappingComposite.populate(null);
				currentMappingComposite = null;
			}
			
			return;
		}
		attributeMapping = attribute.getMapping();
		setComboData(attribute.candidateMappingKeys());
		
		populateMappingPage(attributeMapping == null ? null : attributeMapping.getKey());
	}
	
	private void populateMappingPage(String mappingKey) {
		if (currentMappingComposite != null) {
			if (mappingKey == currentMappingKey) {
				if (currentMappingComposite != null) {
					currentMappingComposite.populate(attributeMapping);
					return;
				}
			}
			else {
				currentMappingComposite.populate(null);
				// don't return
			}
		}
		
		currentMappingKey = mappingKey;
		
		IJpaComposite<IAttributeMapping> composite = mappingCompositeFor(mappingKey);
		mappingPageBook.showPage(composite.getControl());
		
		currentMappingComposite = composite;
		currentMappingComposite.populate(attributeMapping);
	}
	
	private void setComboData(Iterator<String> availableMappingKeys) {
		if (attribute != mappingCombo.getInput()) {
			mappingCombo.setInput(attribute);
		}
		if (attributeMapping == null || attributeMapping.isDefault()) {
			mappingCombo.setSelection(new StructuredSelection(mappingCombo.getElementAt(0)));
		}
		else {
			IAttributeMappingUiProvider provider = attributeMappingUiProvider(attribute.mappingKey());
			if (provider != null && ! provider.equals(((StructuredSelection) mappingCombo.getSelection()).getFirstElement())) {
				mappingCombo.setSelection(new StructuredSelection(provider));
			}
		}
	}
	
	private IJpaComposite<IAttributeMapping> mappingCompositeFor(String key) {
		IJpaComposite<IAttributeMapping> composite = mappingComposites.get(key);
		if (composite != null) {
			return composite;
		}
		
		composite = buildMappingComposite(mappingPageBook, key);
		
		if (composite != null) {
			mappingComposites.put(key, composite);
		}
		
		return composite;
	}
		
//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return mappingCombo.getCombo().setFocus();
//	}
	
	@Override
	public void dispose() {
		disengageListeners();
		for (Iterator<IJpaComposite<IAttributeMapping>> stream = mappingComposites.values().iterator(); stream.hasNext(); ) {
			stream.next().dispose();
		}
		super.dispose();
	}
	
	public IPersistentAttribute getAttribute() {
		return attribute;
	}
	

}
