/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.swt.widgets.Composite;

public class EclipseLink2_4PersistenceXmlUiFactory extends EclipseLink2_0PersistenceXmlUiFactory {
	
	// ********** persistence unit tabs **********
	
	@Override
	protected PersistenceXmlOptionsTab<PersistenceUnit> buildOptionsTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		return new PersistenceXmlOptions2_4Tab(subjectHolder, parent, widgetFactory);
	}
}
