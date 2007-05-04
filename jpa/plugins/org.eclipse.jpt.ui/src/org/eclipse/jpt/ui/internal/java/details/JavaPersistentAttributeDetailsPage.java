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

import org.eclipse.jpt.ui.internal.details.PersistentAttributeDetailsPage;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
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
	protected void initializeLayout(Composite composite) {
		composite.setLayout(new GridLayout(2, false));
		
		GridData gridData;
		
		buildMappingLabel(composite);
		
		CComboViewer mappingCombo = buildMappingCombo(composite);
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
