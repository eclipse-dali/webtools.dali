/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition2_0;
import org.eclipse.jpt.jpa.ui.persistence.JptJpaUiPersistenceMessages;
import org.eclipse.swt.widgets.Composite;

public class PersistenceUnitPropertiesEditorPageDefinition
	extends PersistenceUnitEditorPageDefinition2_0
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
			new PersistenceUnitPropertiesEditorPageDefinition();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private PersistenceUnitPropertiesEditorPageDefinition() {
		super();
	}

	public ImageDescriptor getTitleImageDescriptor() {
		return null;
	}

	public String getTitleText() {
		return JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_PROPERTIES_COMPOSITE_PROPERTIES;
	}

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_PROPERTIES;
	}

	@Override
	protected void buildEditorPageContent(Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new PersistenceUnitPropertiesEditorPage(persistenceUnitModel, parentComposite, widgetFactory, resourceManager);
	}
}
