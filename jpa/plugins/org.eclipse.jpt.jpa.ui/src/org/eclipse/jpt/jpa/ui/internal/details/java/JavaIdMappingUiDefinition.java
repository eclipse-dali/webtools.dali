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

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractIdMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class JavaIdMappingUiDefinition
	extends AbstractIdMappingUiDefinition<ReadOnlyPersistentAttribute, JavaIdMapping>
	implements JavaAttributeMappingUiDefinition<JavaIdMapping>
{
	// singleton
	private static final JavaIdMappingUiDefinition INSTANCE = 
			new JavaIdMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<JavaIdMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	protected JavaIdMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaIdMapping> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createJavaIdMappingComposite(subjectHolder, enabledModel, parent, widgetFactory);
	}
}
