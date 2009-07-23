/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddable;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEmbeddableComposite;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddableUiProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmEmbeddable1_1UiProvider 
	extends AbstractEmbeddableUiProvider<EclipseLinkOrmEmbeddable>
{
	// singleton
	private static final EclipseLinkOrmEmbeddable1_1UiProvider INSTANCE = 
		new EclipseLinkOrmEmbeddable1_1UiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static TypeMappingUiProvider<EclipseLinkOrmEmbeddable> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmEmbeddable1_1UiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE;
	}
	
	public JpaComposite buildPersistentTypeMappingComposite(
			JpaUiFactory factory, 
			PropertyValueModel<EclipseLinkOrmEmbeddable> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipseLinkOrmEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
}
