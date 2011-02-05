/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.options;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.swt.widgets.Composite;

/**
 *  PersistenceXmlOptions2_0Tab
 */
public class PersistenceXmlOptions2_0Tab extends PersistenceXmlOptionsTab<Options2_0>
{
	// ********** constructors/initialization **********
	public PersistenceXmlOptions2_0Tab(
				PropertyValueModel<Options2_0> subjectHolder, 
				Composite parent, 
				WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		new EclipseLinkOptions2_0Composite(this, container);
	}

}
