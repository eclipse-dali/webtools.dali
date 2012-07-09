/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.AbstractElementCollectionMapping2_0UiDefinition;
import org.eclipse.jpt.jpa.ui.jpa2.details.java.JavaUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

public class JavaElementCollectionMapping2_0UiDefinition
	extends AbstractElementCollectionMapping2_0UiDefinition<ReadOnlyPersistentAttribute, JavaElementCollectionMapping2_0>
	implements JavaAttributeMappingUiDefinition<JavaElementCollectionMapping2_0>
{
	// singleton
	private static final JavaElementCollectionMapping2_0UiDefinition INSTANCE = 
			new JavaElementCollectionMapping2_0UiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<JavaElementCollectionMapping2_0> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaElementCollectionMapping2_0UiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaElementCollectionMapping2_0> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return ((JavaUiFactory2_0) factory).createJavaElementCollectionMapping2_0Composite(subjectHolder, enabledModel, parent, widgetFactory);
	}
}
