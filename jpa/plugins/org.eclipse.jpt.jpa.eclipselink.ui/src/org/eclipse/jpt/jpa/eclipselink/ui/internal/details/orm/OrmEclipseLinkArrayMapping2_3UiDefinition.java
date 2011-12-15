/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkArrayMapping2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.AbstractEclipseLinkArrayMapping2_3UiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkArrayMapping2_3Composite;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkArrayMapping2_3UiDefinition
	extends AbstractEclipseLinkArrayMapping2_3UiDefinition<ReadOnlyPersistentAttribute, EclipseLinkArrayMapping2_3>
	implements OrmAttributeMappingUiDefinition<EclipseLinkArrayMapping2_3>
{
	// singleton
	private static final OrmEclipseLinkArrayMapping2_3UiDefinition INSTANCE = 
		new OrmEclipseLinkArrayMapping2_3UiDefinition();


	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<EclipseLinkArrayMapping2_3> instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkArrayMapping2_3UiDefinition() {
		super();
	}

	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory, 
			PropertyValueModel<EclipseLinkArrayMapping2_3> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkArrayMapping2_3Composite(subjectHolder, parent, widgetFactory);
	}
}
