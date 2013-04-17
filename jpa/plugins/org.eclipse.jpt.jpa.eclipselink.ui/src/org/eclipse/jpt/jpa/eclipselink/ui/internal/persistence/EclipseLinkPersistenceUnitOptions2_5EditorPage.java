/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkPersistenceUnitOptions2_5EditorPage
 */
public class EclipseLinkPersistenceUnitOptions2_5EditorPage
	extends EclipseLinkPersistenceUnitOptions2_4EditorPage
{
	public EclipseLinkPersistenceUnitOptions2_5EditorPage(
			PropertyValueModel<PersistenceUnit> persistenceUnitModel,
	        Composite parentComposite,
	        WidgetFactory widgetFactory,
	        ResourceManager resourceManager) {
		super(persistenceUnitModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildOptionsSection(container);
		
		this.buildLoggingSection(container);

		this.buildMiscellaneousSection(container);
	}

}