/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.java.JavaTypeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.ui.internal.details.AbstractMappedSuperclassUiDefinition;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class JavaMappedSuperclassUiDefinition
	extends AbstractMappedSuperclassUiDefinition<PersistentType, JavaMappedSuperclass>
	implements JavaTypeMappingUiDefinition<JavaMappedSuperclass>
{
	// singleton
	private static final JavaMappedSuperclassUiDefinition INSTANCE = 
			new JavaMappedSuperclassUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingUiDefinition<JavaMappedSuperclass> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaMappedSuperclassUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildTypeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaMappedSuperclass> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createJavaMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
}
