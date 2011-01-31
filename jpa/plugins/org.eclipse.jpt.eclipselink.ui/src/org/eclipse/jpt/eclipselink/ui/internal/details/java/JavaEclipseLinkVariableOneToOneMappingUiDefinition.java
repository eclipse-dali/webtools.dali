/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.AbstractEclipseLinkVariableOneToOneMappingUiDefinition;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkVariableOneToOneMappingComposite;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class JavaEclipseLinkVariableOneToOneMappingUiDefinition
	extends AbstractEclipseLinkVariableOneToOneMappingUiDefinition<ReadOnlyPersistentAttribute, EclipseLinkVariableOneToOneMapping>
	implements JavaAttributeMappingUiDefinition<EclipseLinkVariableOneToOneMapping>
{
	// singleton
	private static final JavaEclipseLinkVariableOneToOneMappingUiDefinition INSTANCE = 
			new JavaEclipseLinkVariableOneToOneMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<EclipseLinkVariableOneToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaEclipseLinkVariableOneToOneMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<EclipseLinkVariableOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkVariableOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
