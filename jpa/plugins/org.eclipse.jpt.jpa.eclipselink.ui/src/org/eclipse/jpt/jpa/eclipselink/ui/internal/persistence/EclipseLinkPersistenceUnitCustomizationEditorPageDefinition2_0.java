/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization.EclipseLinkPersistenceUnitCustomizationEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition2_0;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitCustomizationEditorPageDefinition2_0
	extends PersistenceUnitEditorPageDefinition2_0
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
			new EclipseLinkPersistenceUnitCustomizationEditorPageDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private EclipseLinkPersistenceUnitCustomizationEditorPageDefinition2_0() {
		super();
	}

	public ImageDescriptor getTitleImageDescriptor() {
		return EclipseLinkPersistenceUnitCustomizationEditorPageDefinition.instance().getTitleImageDescriptor();
	}

	public String getTitleText() {
		return EclipseLinkPersistenceUnitCustomizationEditorPageDefinition.instance().getTitleText();
	}

	public String getHelpID() {
		return EclipseLinkPersistenceUnitCustomizationEditorPageDefinition.instance().getHelpID();
	}

	@Override
	protected void buildEditorPageContent(Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new EclipseLinkPersistenceUnitCustomizationEditorPage2_0(EclipseLinkPersistenceUnitCustomizationEditorPageDefinition.buildCustomizationModel(persistenceUnitModel), parentComposite, widgetFactory, resourceManager);
	}
}
