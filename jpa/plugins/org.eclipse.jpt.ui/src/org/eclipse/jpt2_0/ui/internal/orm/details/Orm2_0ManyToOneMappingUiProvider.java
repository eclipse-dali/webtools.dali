/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.ui.internal.orm.details;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractManyToOneMappingUiProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class Orm2_0ManyToOneMappingUiProvider
	extends AbstractManyToOneMappingUiProvider<OrmManyToOneMapping>
{
	// singleton
	private static final Orm2_0ManyToOneMappingUiProvider INSTANCE = 
		new Orm2_0ManyToOneMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<OrmManyToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private Orm2_0ManyToOneMappingUiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM2_0_XML_CONTENT_TYPE;
	}	
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<OrmManyToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new Orm2_0ManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
