/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmManyToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkManyToOneMappingComposite;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractManyToOneMappingUiProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmManyToOneMappingUiProvider
	extends AbstractManyToOneMappingUiProvider<EclipseLinkOrmManyToOneMapping>
{
	// singleton
	private static final EclipseLinkOrmManyToOneMappingUiProvider INSTANCE = 
		new EclipseLinkOrmManyToOneMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<EclipseLinkOrmManyToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmManyToOneMappingUiProvider() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<EclipseLinkOrmManyToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
