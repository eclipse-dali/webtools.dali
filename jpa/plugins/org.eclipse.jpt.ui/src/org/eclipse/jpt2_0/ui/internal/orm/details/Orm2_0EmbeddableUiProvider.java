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
package org.eclipse.jpt2_0.ui.internal.orm.details;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEmbeddableComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class Orm2_0EmbeddableUiProvider 
	extends AbstractEmbeddableUiProvider<OrmEmbeddable>
{
	// singleton
	private static final Orm2_0EmbeddableUiProvider INSTANCE = 
		new Orm2_0EmbeddableUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static TypeMappingUiProvider<OrmEmbeddable> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private Orm2_0EmbeddableUiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM2_0_XML_CONTENT_TYPE;
	}
	
	public JpaComposite buildPersistentTypeMappingComposite(
			JpaUiFactory factory, 
			PropertyValueModel<OrmEmbeddable> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new OrmEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
}
