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
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkMutableComposite;
import org.eclipse.jpt.jpa.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.jpa.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkBasicMappingComposite extends EclipseLinkBasicMappingComposite<BasicMapping>
{
	/**
	 * Creates a new <code>EclipseLinkOrmBasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>BasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEclipseLinkBasicMappingComposite(PropertyValueModel<? extends BasicMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeBasicSection(Composite container) {
		new ColumnComposite(this, buildColumnHolder(), container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new FetchTypeComposite(this, container);
		new OptionalComposite(this, addSubPane(container, 4));
		new EclipseLinkMutableComposite(this, buildMutableHolder(), container);
	}
}