/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.customization;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.customization.PersistenceXmlCustomizationTab;
import org.eclipse.swt.widgets.Composite;

/**
 *  PersistenceXmlCustomization2_0Tab
 */
public class PersistenceXmlCustomization2_0Tab extends PersistenceXmlCustomizationTab<Customization>
{
	public PersistenceXmlCustomization2_0Tab(
		PropertyValueModel<Customization> subjectHolder,
		Composite parent,
        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void buildEclipseLinkCustomizationComposite(Composite container) {
		new EclipseLinkCustomization2_0Composite(this, container);
	}
	
}
