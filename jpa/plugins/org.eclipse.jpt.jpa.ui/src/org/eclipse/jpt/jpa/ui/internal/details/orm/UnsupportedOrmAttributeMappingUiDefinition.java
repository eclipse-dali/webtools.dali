/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class UnsupportedOrmAttributeMappingUiDefinition
	extends AbstractMappingUiDefinition
{
	// singleton
	private static final MappingUiDefinition INSTANCE = new UnsupportedOrmAttributeMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private UnsupportedOrmAttributeMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return null;
	}	
	
	public String getLabel() {
		return JptJpaUiDetailsOrmMessages.UNSUPPORTED_ORM_MAPPING_UI_PROVIDER_LABEL;
	}
	
	public String getLinkLabel() {
		return JptJpaUiDetailsOrmMessages.UNSUPPORTED_ORM_MAPPING_UI_PROVIDER_LINK_LABEL;
	}

	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextModel> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new NullComposite(mappingModel, parentComposite, widgetFactory, resourceManager);
	}
}
