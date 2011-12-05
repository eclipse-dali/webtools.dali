/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicMappingComposite;
import org.eclipse.swt.widgets.Composite;

public class JavaEclipseLinkBasicMappingComposite extends EclipseLinkBasicMappingComposite<BasicMapping>
{
	/**
	 * Creates a new <code>EclipseLink1_1OrmBasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>BasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JavaEclipseLinkBasicMappingComposite(PropertyValueModel<? extends BasicMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeBasicCollapsibleSection(container);
		initializeTypeCollapsibleSection(container);
		initializeConvertersCollapsibleSection(container);
	}

}