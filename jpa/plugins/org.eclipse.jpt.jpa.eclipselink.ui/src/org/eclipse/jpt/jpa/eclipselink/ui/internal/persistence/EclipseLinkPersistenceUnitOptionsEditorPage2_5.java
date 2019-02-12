/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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

public class EclipseLinkPersistenceUnitOptionsEditorPage2_5
	extends EclipseLinkPersistenceUnitOptionsEditorPage2_4
{
	public EclipseLinkPersistenceUnitOptionsEditorPage2_5(
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
