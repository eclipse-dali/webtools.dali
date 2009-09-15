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
package org.eclipse.jpt.ui.internal.jpa2.details.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddedMappingUiProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmEmbeddedMapping2_0UiProvider
	extends AbstractEmbeddedMappingUiProvider<OrmEmbeddedMapping2_0>
{
	// singleton
	private static final OrmEmbeddedMapping2_0UiProvider INSTANCE = 
		new OrmEmbeddedMapping2_0UiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<OrmEmbeddedMapping2_0> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmEmbeddedMapping2_0UiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM2_0_XML_CONTENT_TYPE;
	}	
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<OrmEmbeddedMapping2_0> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEmbeddedMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
}
