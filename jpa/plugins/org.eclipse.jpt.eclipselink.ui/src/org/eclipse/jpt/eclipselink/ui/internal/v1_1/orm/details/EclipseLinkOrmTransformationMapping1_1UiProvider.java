/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.TransformationMappingComposite;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmTransformationMapping1_1UiProvider extends EclipseLinkTransformationMappingUiProvider<EclipseLinkTransformationMapping>
{
	// singleton
	private static final EclipseLinkOrmTransformationMapping1_1UiProvider INSTANCE = 
		new EclipseLinkOrmTransformationMapping1_1UiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<EclipseLinkTransformationMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmTransformationMapping1_1UiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE;
	}
	
	public JpaComposite buildAttributeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<EclipseLinkTransformationMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {
		
		return new TransformationMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
