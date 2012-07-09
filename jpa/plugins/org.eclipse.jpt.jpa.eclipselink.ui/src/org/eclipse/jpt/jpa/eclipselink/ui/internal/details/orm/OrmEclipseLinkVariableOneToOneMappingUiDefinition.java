/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.AbstractEclipseLinkVariableOneToOneMappingUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkVariableOneToOneMappingComposite;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkVariableOneToOneMappingUiDefinition
	extends AbstractEclipseLinkVariableOneToOneMappingUiDefinition<ReadOnlyPersistentAttribute, EclipseLinkVariableOneToOneMapping>
	implements OrmAttributeMappingUiDefinition<EclipseLinkVariableOneToOneMapping>
{
	// singleton
	private static final OrmEclipseLinkVariableOneToOneMappingUiDefinition INSTANCE = 
			new OrmEclipseLinkVariableOneToOneMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<EclipseLinkVariableOneToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkVariableOneToOneMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<EclipseLinkVariableOneToOneMapping> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkVariableOneToOneMappingComposite(subjectHolder, enabledModel, parent, widgetFactory);
	}
}
