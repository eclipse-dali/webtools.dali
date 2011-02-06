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
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaTypeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class JavaEntityUiDefinition
	extends AbstractEntityUiDefinition<PersistentType, JavaEntity>
	implements JavaTypeMappingUiDefinition<JavaEntity>
{
	// singleton
	private static final JavaEntityUiDefinition INSTANCE = 
			new JavaEntityUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingUiDefinition<JavaEntity> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaEntityUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildTypeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createJavaEntityComposite(subjectHolder, parent, widgetFactory);
	}
}
