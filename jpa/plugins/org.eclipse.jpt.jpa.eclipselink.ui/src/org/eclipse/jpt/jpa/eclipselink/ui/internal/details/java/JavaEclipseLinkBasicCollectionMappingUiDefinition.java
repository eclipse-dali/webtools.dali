/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.AbstractEclipseLinkBasicCollectionMappingUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicCollectionMappingComposite;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.swt.widgets.Composite;

public class JavaEclipseLinkBasicCollectionMappingUiDefinition
	extends AbstractEclipseLinkBasicCollectionMappingUiDefinition<ReadOnlyPersistentAttribute, EclipseLinkBasicCollectionMapping>
	implements JavaAttributeMappingUiDefinition<EclipseLinkBasicCollectionMapping>
{
	// singleton
	private static final JavaEclipseLinkBasicCollectionMappingUiDefinition INSTANCE = 
			new JavaEclipseLinkBasicCollectionMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<EclipseLinkBasicCollectionMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaEclipseLinkBasicCollectionMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<EclipseLinkBasicCollectionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkBasicCollectionMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
