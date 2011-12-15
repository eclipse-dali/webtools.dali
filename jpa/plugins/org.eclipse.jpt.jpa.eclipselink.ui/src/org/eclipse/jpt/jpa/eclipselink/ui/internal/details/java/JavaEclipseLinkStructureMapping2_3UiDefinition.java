/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructureMapping2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.AbstractEclipseLinkStructureMapping2_3UiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkStructureMapping2_3Composite;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.swt.widgets.Composite;

public class JavaEclipseLinkStructureMapping2_3UiDefinition
	extends AbstractEclipseLinkStructureMapping2_3UiDefinition<ReadOnlyPersistentAttribute, EclipseLinkStructureMapping2_3>
	implements JavaAttributeMappingUiDefinition<EclipseLinkStructureMapping2_3>
{
	// singleton
	private static final JavaEclipseLinkStructureMapping2_3UiDefinition INSTANCE = 
			new JavaEclipseLinkStructureMapping2_3UiDefinition();


	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<EclipseLinkStructureMapping2_3> instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private JavaEclipseLinkStructureMapping2_3UiDefinition() {
		super();
	}


	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<EclipseLinkStructureMapping2_3> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkStructureMapping2_3Composite(subjectHolder, parent, widgetFactory);
	}
}
