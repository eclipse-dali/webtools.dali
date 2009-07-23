/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.java.details;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.BasicCollectionMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkBasicCollectionMappingUiProvider;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkJavaBasicCollectionMappingUiProvider extends EclipseLinkBasicCollectionMappingUiProvider<EclipseLinkBasicCollectionMapping>
{
	// singleton
	private static final EclipseLinkJavaBasicCollectionMappingUiProvider INSTANCE = 
		new EclipseLinkJavaBasicCollectionMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<EclipseLinkBasicCollectionMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJavaBasicCollectionMappingUiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE;
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<EclipseLinkBasicCollectionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new BasicCollectionMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
