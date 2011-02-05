/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.logging;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.logging.Logging2_0;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.logging.PersistenceXmlLoggingTab;
import org.eclipse.swt.widgets.Composite;

/**
 *  PersistenceXmlLogging2_0Tab
 */
public class PersistenceXmlLogging2_0Tab extends PersistenceXmlLoggingTab<Logging2_0>
{
	// ********** constructors/initialization **********
	public PersistenceXmlLogging2_0Tab(
				PropertyValueModel<Logging2_0> subjectHolder, 
				Composite parent, 
				WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		new EclipseLinkLogging2_0Composite(this, container);
	}
	
}
