/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Section;

class EclipseLinkPersistenceUnitOptions2_4EditorPage 
	extends EclipseLinkPersistenceUnitOptions2_0EditorPage {

	public EclipseLinkPersistenceUnitOptions2_4EditorPage(
			PropertyValueModel<PersistenceUnit> subjectModel,
            Composite parent,
            WidgetFactory widgetFactory) {

		super(subjectModel, parent, widgetFactory);
	}

	@Override
	protected Control initializeLoggingSection(Section section) {			
		return new EclipseLinkLogging2_4Composite(this.buildLoggingModel(), section, getWidgetFactory()).getControl();
	}
}