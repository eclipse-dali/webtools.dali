/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractBasicMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class JavaBasicMappingUiDefinition
	extends AbstractBasicMappingUiDefinition<ReadOnlyPersistentAttribute, JavaBasicMapping>
	implements JavaAttributeMappingUiDefinition<JavaBasicMapping>
{
	// singleton
	private static final JavaBasicMappingUiDefinition INSTANCE = 
		new JavaBasicMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<JavaBasicMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaBasicMappingUiDefinition() {
		super();
	}
	
	public JpaComposite buildAttributeMappingComposite(JavaUiFactory factory, PropertyValueModel<JavaBasicMapping> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return factory.createJavaBasicMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
