/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.EclipseLinkPersistenceUnitOptionsEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkPersistenceUnitOptions2_5EditorPageDefinition
 */
public class EclipseLinkPersistenceUnitOptions2_5EditorPageDefinition
extends EclipseLinkPersistenceUnitOptions2_4EditorPageDefinition
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
		new EclipseLinkPersistenceUnitOptions2_5EditorPageDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private EclipseLinkPersistenceUnitOptions2_5EditorPageDefinition() {
		super();
	}
	
	public ImageDescriptor getTitleImageDescriptor() {
		return EclipseLinkPersistenceUnitOptionsEditorPageDefinition.instance().getTitleImageDescriptor();
	}
	
	public String getTitleText() {
		return EclipseLinkPersistenceUnitOptionsEditorPageDefinition.instance().getTitleText();
	}
	
	public String getHelpID() {
		return EclipseLinkPersistenceUnitOptionsEditorPageDefinition.instance().getHelpID();
	}

	@Override
	protected void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new EclipseLinkPersistenceUnitOptions2_5EditorPage(persistenceUnitModel, parent, widgetFactory, resourceManager);
	}
}