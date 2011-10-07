/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.details.JpaPageComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 *  PersistenceXmlCustomizationTabItem
 */
public class PersistenceXmlCustomizationTab<T extends Customization>
								extends Pane<T>
								implements JpaPageComposite
{
	public PersistenceXmlCustomizationTab(
			PropertyValueModel<T> subjectHolder,
			Composite parent,
            WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		
		this.buildEclipseLinkCustomizationComposite(container);
	}
	
	// ********** JpaPageComposite implementation **********

	public String getHelpID() {
		return EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION;
	}

	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}
	
	public String getPageText() {
		return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_title;
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

	protected void buildEclipseLinkCustomizationComposite(Composite container) {
		new EclipseLinkCustomizationComposite<T>(this, container);
	}
}
