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

import java.util.Collection;
import java.util.List;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.PlatformRegistry;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
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
	
	protected IJpaPlatformUi getJpaPlatformUi() {
		String platformId = getPersistentType().getJpaProject().getPlatform().getId();
		return PlatformRegistry.instance().getJpaPlatform(platformId);
	}
	
	@Override
	protected List<ITypeMappingUiProvider> typeMappingUiProviders() {
		return getJpaPlatformUi().javaTypeMappingUiProviders();
	}

	@Override
	protected void initializeLayout(Composite composite) {	    
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 2;
	    composite.setLayout(gridLayout);
		
	    buildTypeMappingLabel(composite);
	    
		CComboViewer typeMappingCombo = buildTypeMappingCombo(composite);
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
