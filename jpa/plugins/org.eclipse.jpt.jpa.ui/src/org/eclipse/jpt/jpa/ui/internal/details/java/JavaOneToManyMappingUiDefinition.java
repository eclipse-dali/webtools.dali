/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToManyMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class JavaOneToManyMappingUiDefinition
	extends AbstractOneToManyMappingUiDefinition<ReadOnlyPersistentAttribute, JavaOneToManyMapping>
	implements JavaAttributeMappingUiDefinition<JavaOneToManyMapping>
{
	// singleton
	private static final JavaOneToManyMappingUiDefinition INSTANCE = 
			new JavaOneToManyMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<JavaOneToManyMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaOneToManyMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createJavaOneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
