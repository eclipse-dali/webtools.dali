/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.mappings.properties;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.EntityComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EntityUiProvider implements ITypeMappingUiProvider<IEntity>
{
	// singleton
	private static final EntityUiProvider INSTANCE = new EntityUiProvider();

	/**
	 * Return the singleton.
	 */
	public static ITypeMappingUiProvider<IEntity> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private EntityUiProvider() {
		super();
	}

	public String mappingKey() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public String label() {
		return JptUiMappingsMessages.PersistentTypePage_EntityLabel;
	}

	public IJpaComposite<IEntity> buildPersistentTypeMappingComposite(
			PropertyValueModel<IEntity> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EntityComposite(subjectHolder, parent, widgetFactory);
	}
}
