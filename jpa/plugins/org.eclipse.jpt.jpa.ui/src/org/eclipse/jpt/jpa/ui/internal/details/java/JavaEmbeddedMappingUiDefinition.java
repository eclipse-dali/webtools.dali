/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class JavaEmbeddedMappingUiDefinition
	extends AbstractEmbeddedMappingUiDefinition<ReadOnlyPersistentAttribute, JavaEmbeddedMapping>
	implements JavaAttributeMappingUiDefinition<JavaEmbeddedMapping>
{
	// singleton
	private static final JavaEmbeddedMappingUiDefinition INSTANCE = 
			new JavaEmbeddedMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<JavaEmbeddedMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaEmbeddedMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaEmbeddedMapping> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createJavaEmbeddedMappingComposite(subjectHolder, enabledModel, parent, widgetFactory);
	}
}
