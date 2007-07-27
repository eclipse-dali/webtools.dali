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

import java.util.HashMap;
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
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.IJpaUiFactory;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.PlatformRegistry;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
import org.eclipse.jpt.utility.internal.CollectionTools;
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
	
	private String currentMappingKey;
	
	private CComboViewer mappingCombo;
	
	private Map<String, IJpaComposite<IAttributeMapping>> mappingComposites;
	protected PageBook mappingPageBook;	
	private IJpaComposite<IAttributeMapping> currentMappingComposite;	
	
	public PersistentAttributeDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NONE, new BasicCommandStack(), widgetFactory);
		this.persistentAttributeListener = buildAttributeListener();
		this.mappingComposites = new HashMap<String, IJpaComposite<IAttributeMapping>>();
	}
	
	protected IJpaPlatformUi jpaPlatformUi() {
		String platformId = getAttribute().getJpaProject().getPlatform().getId();
		return PlatformRegistry.instance().getJpaPlatform(platformId);
	}

	protected IJpaUiFactory jpaUiFactory() {
		return jpaPlatformUi().getJpaUiFactory();
	}
	
	protected abstract ListIterator<IAttributeMappingUiProvider> attributeMappingUiProviders();
	
	protected abstract ListIterator<IAttributeMappingUiProvider> defaultAttributeMappingUiProviders();
		
	protected IAttributeMappingUiProvider attributeMappingUiProvider(String key) {
		for (ListIterator<IAttributeMappingUiProvider> i = attributeMappingUiProviders(); i.hasNext(); ) {
			IAttributeMappingUiProvider provider = i.next();
			if (provider.attributeMappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported attribute mapping UI provider key: ");
	}
	
	protected abstract IAttributeMappingUiProvider defaultAttributeMappingUiProvider(String key);
	
	private Adapter buildAttributeListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				persistentAttributeChanged(notification);
			}
		};
	}
	
	protected void persistentAttributeChanged(Notification notification) {
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
		
	protected Label buildMappingLabel(Composite parent) {
		return getWidgetFactory().createLabel(parent, JptUiMessages.PersistentAttributePage_mapAs);
	}
	
	protected CComboViewer buildMappingCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		this.mappingCombo = new CComboViewer(combo);
		this.mappingCombo.setContentProvider(buildContentProvider());
		this.mappingCombo.setLabelProvider(buildLabelProvider());
		this.mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				mappingChanged(event);
			}
		});
		return this.mappingCombo;
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
				return attributeMappingUiProvidersFor((IPersistentAttribute) inputElement);
			}
			
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		};
	}
	
	protected abstract IAttributeMappingUiProvider[] attributeMappingUiProvidersFor(IPersistentAttribute persistentAttribute);
	
	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IAttributeMappingUiProvider) element).label();
			}
		};
	}
	
	protected PageBook buildMappingPageBook(Composite parent) {
		this.mappingPageBook = new PageBook(parent, SWT.NONE);
		return this.mappingPageBook;
	}
	
	protected IJpaComposite<IAttributeMapping> buildMappingComposite(PageBook pageBook, String key) {
		if (this.attributeMapping == null || this.attributeMapping.isDefault()) {
			return defaultAttributeMappingUiProvider(key).buildAttributeMappingComposite(jpaUiFactory(), pageBook, this.commandStack, getWidgetFactory());
		}

		return attributeMappingUiProvider(key).buildAttributeMappingComposite(jpaUiFactory(), pageBook, this.commandStack, getWidgetFactory());
	}
		
	void mappingChanged(SelectionChangedEvent event) {
		if (isPopulating()) {
			return;
		}
		if (event.getSelection() instanceof StructuredSelection) {
			IAttributeMappingUiProvider provider = (IAttributeMappingUiProvider) ((StructuredSelection) event.getSelection()).getFirstElement();
			String key = (CollectionTools.contains(defaultAttributeMappingUiProviders(), provider) ? null : provider.attributeMappingKey());
			this.attribute.setSpecifiedMappingKey(key);
		}
	}
	
	@Override
	protected void doPopulate(IJpaContentNode persistentAttributeNode) {
		this.attribute = (IPersistentAttribute) persistentAttributeNode;
		populateMappingComboAndPage();
	}
	
	@Override
	protected void doPopulate() {
		populateMappingComboAndPage();
	}
	
	@Override
	protected void engageListeners() {
		if (this.attribute != null) {
			this.attribute.eAdapters().add(this.persistentAttributeListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.attribute != null) {
			this.attribute.eAdapters().remove(this.persistentAttributeListener);
		}
	}
	
	private void populateMappingComboAndPage() {
		if (this.attribute == null) {
			this.attributeMapping = null;
			this.currentMappingKey = null;
			this.mappingCombo.setInput(null);
			this.mappingCombo.setSelection(StructuredSelection.EMPTY);
			
			if (this.currentMappingComposite != null) {
				this.currentMappingComposite.populate(null);
				this.currentMappingComposite = null;
			}
			
			return;
		}
		this.attributeMapping = this.attribute.getMapping();
		setComboData();
		
		populateMappingPage(this.attributeMapping == null ? null : this.attributeMapping.getKey());
	}
	
	private void populateMappingPage(String mappingKey) {
		if (this.currentMappingComposite != null) {
			if (mappingKey == this.currentMappingKey) {
				if (this.currentMappingComposite != null) {
					this.currentMappingComposite.populate(this.attributeMapping);
					return;
				}
			}
			else {
				this.currentMappingComposite.populate(null);
				// don't return
			}
		}
		
		this.currentMappingKey = mappingKey;
		
		IJpaComposite<IAttributeMapping> composite = mappingCompositeFor(mappingKey);
		this.mappingPageBook.showPage(composite.getControl());
		
		this.currentMappingComposite = composite;
		this.currentMappingComposite.populate(this.attributeMapping);
	}
	
	private void setComboData() {
		if (this.attribute != this.mappingCombo.getInput()) {
			this.mappingCombo.setInput(this.attribute);
		}
		if (this.attributeMapping == null || this.attributeMapping.isDefault()) {
			this.mappingCombo.setSelection(new StructuredSelection(this.mappingCombo.getElementAt(0)));
		}
		else {
			IAttributeMappingUiProvider provider = attributeMappingUiProvider(this.attribute.mappingKey());
			if (provider != null && ! provider.equals(((StructuredSelection) this.mappingCombo.getSelection()).getFirstElement())) {
				this.mappingCombo.setSelection(new StructuredSelection(provider));
			}
		}
	}
	
	private IJpaComposite<IAttributeMapping> mappingCompositeFor(String key) {
		IJpaComposite<IAttributeMapping> composite = this.mappingComposites.get(key);
		if (composite != null) {
			return composite;
		}
		
		composite = buildMappingComposite(this.mappingPageBook, key);
		
		if (composite != null) {
			this.mappingComposites.put(key, composite);
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
		for (IJpaComposite<IAttributeMapping> composite : this.mappingComposites.values()) {
			composite.dispose();
		}
		super.dispose();
	}
	
	public IPersistentAttribute getAttribute() {
		return this.attribute;
	}
}
