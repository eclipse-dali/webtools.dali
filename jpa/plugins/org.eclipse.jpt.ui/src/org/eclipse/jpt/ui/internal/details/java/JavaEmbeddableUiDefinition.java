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

import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.java.JavaTypeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddableUiDefinition;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class JavaEmbeddableUiDefinition 
	extends AbstractEmbeddableUiDefinition<JavaEmbeddable>
	implements JavaTypeMappingUiDefinition<JavaEmbeddable>
{
	// singleton
	private static final JavaEmbeddableUiDefinition INSTANCE = new JavaEmbeddableUiDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingUiDefinition<JavaEmbeddable> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaEmbeddableUiDefinition() {
		super();
	}
	
	public JpaComposite buildTypeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaEmbeddable> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return factory.createJavaEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
}
