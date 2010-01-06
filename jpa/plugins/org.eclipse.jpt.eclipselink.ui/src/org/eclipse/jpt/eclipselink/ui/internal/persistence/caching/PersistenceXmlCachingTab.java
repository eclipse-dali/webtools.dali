/*******************************************************************************
* Copyright (c) 2007, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 *  PersistenceXmlCachingTab
 */
public class PersistenceXmlCachingTab<T extends Caching>
								extends Pane<T>
								implements JpaPageComposite
{
	public PersistenceXmlCachingTab(
			PropertyValueModel<T> subjectHolder,
			Composite parent,
            WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		
		new EclipseLinkCachingComposite<T>(this, container);
	}

	// ********** JpaPageComposite implementation **********

	public String getHelpID() {
		return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
	}

	public Image getPageImage() {
		return null;
	}
	public String getPageText() {
		return EclipseLinkUiMessages.PersistenceXmlCachingTab_title;
	}

	// ********** Layout **********
	
	@Override
	protected Composite addContainer(Composite parent) {
		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginTop = 0;
		layout.marginLeft = 0;
		layout.marginBottom = 0;
		layout.marginRight = 0;
		layout.verticalSpacing = 15;
		
		Composite container = addPane(parent, layout);
		updateGridData(container);
		
		return container;
	}

	private void updateGridData(Composite container) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		container.setLayoutData(gridData);
	}

}
