/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractVersionMappingComposite;
import org.eclipse.jpt.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmVersionMappingComposite extends AbstractVersionMappingComposite<OrmVersionMapping>
{
	/**
	 * Creates a new <code>EclipseLinkOrmVersionMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>VersionMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmVersionMappingComposite(PropertyValueModel<? extends OrmVersionMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeVersionSection(Composite container) {
		new ColumnComposite(this, buildColumnHolder(), container);
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
	}
}