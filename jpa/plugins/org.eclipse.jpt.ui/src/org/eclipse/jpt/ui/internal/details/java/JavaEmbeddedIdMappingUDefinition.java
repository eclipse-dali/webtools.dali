/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddedIdMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class JavaEmbeddedIdMappingUDefinition
	extends AbstractEmbeddedIdMappingUiDefinition<ReadOnlyPersistentAttribute, JavaEmbeddedIdMapping>
	implements JavaAttributeMappingUiDefinition<JavaEmbeddedIdMapping>
{
	// singleton
	private static final JavaEmbeddedIdMappingUDefinition INSTANCE = 
			new JavaEmbeddedIdMappingUDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<JavaEmbeddedIdMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaEmbeddedIdMappingUDefinition() {
		super();
	}	
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaEmbeddedIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createJavaEmbeddedIdMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
