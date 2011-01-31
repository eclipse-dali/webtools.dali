/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.general;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.persistence.details.PersistenceUnitGeneralComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkPersistenceUnitGeneralComposite
 */
public class EclipseLinkPersistenceUnitGeneralComposite
	extends PersistenceUnitGeneralComposite
{
	// ********** constructors **********
	public EclipseLinkPersistenceUnitGeneralComposite(
		PropertyValueModel<PersistenceUnit> subjectHolder,
					Composite container,
					WidgetFactory widgetFactory) {
		super(subjectHolder, container, widgetFactory);
	}

	// ********** initialization **********
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeGeneralPane(container);
		this.initializeMappedClassesPane(container);
		this.initializeJPAMappingDescriptorsPane(container);
		this.initializeJarFilesPane(container);
	}
	
	protected void initializeJPAMappingDescriptorsPane(Composite container) {

		container = addCollapsibleSection(
			container,
			JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jpaMappingDescriptors,
			JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jpaMappingDescriptors_description
		);

		updateGridData(container);
		updateGridData(container.getParent());

		new EclipseLinkPersistenceUnitMappingFilesComposite(this, container);
	}
	
	protected void initializeJarFilesPane(Composite container) {

		container = addCollapsibleSection(
			container,
			JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jarFiles
		);
		
		updateGridData(container);
		updateGridData(container.getParent());
		
		new EclipseLinkPersistenceUnitJarFilesComposite(this, container);
	}
}