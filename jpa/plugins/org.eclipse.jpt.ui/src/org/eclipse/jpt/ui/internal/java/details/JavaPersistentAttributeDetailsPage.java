/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import java.util.ListIterator;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.ui.internal.details.PersistentAttributeDetailsPage;
import org.eclipse.jpt.ui.internal.java.mappings.properties.NullAttributeMappingUiProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class JavaPersistentAttributeDetailsPage
	extends PersistentAttributeDetailsPage 
{
	public JavaPersistentAttributeDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}
	
	
	@Override
	protected ListIterator<IAttributeMappingUiProvider> attributeMappingUiProviders() {
		return jpaPlatformUi().javaAttributeMappingUiProviders();
	}
	
	protected IAttributeMappingUiProvider nullAttributeMappingUiProvider() {
		return NullAttributeMappingUiProvider.instance();
	}

	@Override
	protected ListIterator<IAttributeMappingUiProvider> defaultAttributeMappingUiProviders() {
		return jpaPlatformUi().defaultJavaAttributeMappingUiProviders();
	}


	/**
	 * These IAtttributeMappingUiProviders will be used as elements in the attributeMapping combo
	 * The first element in the combo will be one of the defaultAttributeMappingUiProviders or
	 * if none of those apply the nullAttributeMappingUiProvider will be used. The rest of the elements
	 * will be the attributeMappingUiProviders.  The defaultAttributeMappingUiProvider is
	 * determined by matching its key with the key of the current attributeMapping.  
	 */
	@Override
	protected IAttributeMappingUiProvider[] attributeMappingUiProvidersFor(IPersistentAttribute persistentAttribute) {
		IAttributeMappingUiProvider[] providers = new IAttributeMappingUiProvider[CollectionTools.size(attributeMappingUiProviders()) + 1];
		providers[0] =  defaultAttributeMappingUiProvider(persistentAttribute.defaultMappingKey());
		int i = 1;
		for (ListIterator<IAttributeMappingUiProvider> iterator = attributeMappingUiProviders(); iterator.hasNext(); ) {
			providers[i++] = iterator.next();
		}
		return providers;
	}
	
	@Override
	protected IAttributeMappingUiProvider defaultAttributeMappingUiProvider(String key) {
		for (ListIterator<IAttributeMappingUiProvider> i = defaultAttributeMappingUiProviders(); i.hasNext(); ) {
			IAttributeMappingUiProvider provider = i.next();
			if (provider.attributeMappingKey() == key) {
				return provider;
			}
		}
		return this.nullAttributeMappingUiProvider();
	}
	
	@Override
	protected void initializeLayout(Composite composite) {
		composite.setLayout(new GridLayout(2, false));
		
		GridData gridData;
		
		buildMappingLabel(composite);
		
		ComboViewer mappingCombo = buildMappingCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		mappingCombo.getCombo().setLayoutData(gridData);
		
		PageBook book = buildMappingPageBook(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		book.setLayoutData(gridData);
	}
}
