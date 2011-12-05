/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkIdMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkMutableComposite;
import org.eclipse.jpt.jpa.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkIdMappingComposite
	extends EclipseLinkIdMappingComposite<IdMapping>
{
	public OrmEclipseLinkIdMappingComposite(
			PropertyValueModel<? extends IdMapping> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeIdSection(Composite container) {		
		new ColumnComposite(this, buildColumnHolder(), container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new EclipseLinkMutableComposite(this, buildMutableHolder(), container);
	}
}
