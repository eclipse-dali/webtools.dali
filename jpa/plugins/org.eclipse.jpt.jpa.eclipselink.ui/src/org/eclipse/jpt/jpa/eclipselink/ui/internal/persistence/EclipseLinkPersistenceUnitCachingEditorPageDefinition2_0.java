/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.EclipseLinkPersistenceUnitCachingEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition2_0;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitCachingEditorPageDefinition2_0
	extends PersistenceUnitEditorPageDefinition2_0
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
			new EclipseLinkPersistenceUnitCachingEditorPageDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private EclipseLinkPersistenceUnitCachingEditorPageDefinition2_0() {
		super();
	}

	public ImageDescriptor getTitleImageDescriptor() {
		return this.getCachingEditorPageDefinition().getTitleImageDescriptor();
	}

	public String getTitleText() {
		return this.getCachingEditorPageDefinition().getTitleText();
	}

	public String getHelpID() {
		return this.getCachingEditorPageDefinition().getHelpID();
	}

	private JpaEditorPageDefinition getCachingEditorPageDefinition() {
		return EclipseLinkPersistenceUnitCachingEditorPageDefinition.instance();
	}

	@Override
	protected void buildEditorPageContent(Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new EclipseLinkPersistenceUnitCachingEditorPage2_0(EclipseLinkPersistenceUnitCachingEditorPageDefinition.buildCachingModel(persistenceUnitModel), parentComposite, widgetFactory, resourceManager);
	}
}
