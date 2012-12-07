/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class UnsupportedOrmAttributeMappingUiDefinition
	extends AbstractMappingUiDefinition
	implements OrmAttributeMappingUiDefinition
{
	// singleton
	private static final OrmAttributeMappingUiDefinition INSTANCE = new UnsupportedOrmAttributeMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition instance() {
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
		return JptUiDetailsOrmMessages.UnsupportedOrmMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return JptUiDetailsOrmMessages.UnsupportedOrmMappingUiProvider_linkLabel;
	}

	public JpaComposite buildAttributeMappingComposite(OrmXmlUiFactory factory, PropertyValueModel mappingModel, PropertyValueModel enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new NullComposite(mappingModel, parentComposite, widgetFactory, resourceManager);
	}
	
	public static class NullComposite
		extends Pane<JavaAttributeMapping>
		implements JpaComposite
	{
		NullComposite(PropertyValueModel<JavaAttributeMapping> mappingModel, Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
			super(mappingModel, parent, widgetFactory, resourceManager);
		}
		@Override
		protected void initializeLayout(Composite container) {
			// NOP
		}
	}
}
