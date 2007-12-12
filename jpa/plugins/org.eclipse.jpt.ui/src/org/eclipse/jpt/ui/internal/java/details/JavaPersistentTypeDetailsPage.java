/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import java.util.ListIterator;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class JavaPersistentTypeDetailsPage extends
		PersistentTypeDetailsPage 
{	
	
	public JavaPersistentTypeDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}
	
	protected IJpaPlatformUi jpaPlatformUi() {
		String platformId = getPersistentType().jpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
	}
	
	@Override
	protected ListIterator<ITypeMappingUiProvider> typeMappingUiProviders() {
		return jpaPlatformUi().javaTypeMappingUiProviders();
	}

	@Override
	protected void initializeLayout(Composite composite) {	    
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 2;
	    composite.setLayout(gridLayout);
		
	    buildTypeMappingLabel(composite);
	    
		ComboViewer typeMappingCombo = buildTypeMappingCombo(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
	    gridData.grabExcessHorizontalSpace = true;
		typeMappingCombo.getCombo().setLayoutData(gridData);

		PageBook typeMappingPageBook = buildTypeMappingPageBook(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		typeMappingPageBook.setLayoutData(gridData);
	}	

}
