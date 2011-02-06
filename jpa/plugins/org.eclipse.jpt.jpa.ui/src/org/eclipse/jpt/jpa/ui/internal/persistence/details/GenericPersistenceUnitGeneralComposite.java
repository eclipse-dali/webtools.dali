/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.details.JpaPageComposite;
import org.eclipse.jpt.jpa.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | - General --------------------------------------------------------------- |
 * |                         ------------------------------------------------- |
 * |   Name:                 | I                                             | |
 * |                         ------------------------------------------------- |
 * |                         ------------------------------------------------- |
 * |   Persistence Provider: |                                             |v| |
 * |                         ------------------------------------------------- |
 * |                                                                           |
 * |                                                                           |
 * | - Mapped Classes -------------------------------------------------------- |
 * |                                                                           |
 * |   Description                                                             |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | PersistenceUnitMappedClassesComposite                               | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * |                                                                           |
 * |                                                                           |
 * | - XML Mapping Files ----------------------------------------------------- |
 * |                                                                           |
 * |   Description                                                             |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | PersistenceUnitMappingFilesComposite                                | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see PersistenceUnitJarFilesComposite
 * @see PersistenceUnitMappedClassesComposite
 * @see PersistenceUnitMappingFilesComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class GenericPersistenceUnitGeneralComposite extends PersistenceUnitGeneralComposite
                                             implements JpaPageComposite
{
	/**
	 * Creates a new <code>PersistenceUnitGeneralComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public GenericPersistenceUnitGeneralComposite(PropertyValueModel<PersistenceUnit> subjectHolder,
	                                       Composite container,
	                                       WidgetFactory widgetFactory) {

		super(subjectHolder, container, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		initializeGeneralPane(container);
		initializeMappedClassesPane(container);
		initializeJPAMappingDescriptorsPane(container);
		initializeJarFilesPane(container);
	}
	

	protected void initializeJPAMappingDescriptorsPane(Composite container) {

		container = addCollapsibleSection(
			container,
			JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jpaMappingDescriptors,
			JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jpaMappingDescriptors_description
		);

		updateGridData(container);
		updateGridData(container.getParent());

		new GenericPersistenceUnitMappingFilesComposite(this, container);
	}
	
	protected void initializeJarFilesPane(Composite container) {

		container = addCollapsibleSection(
			container,
			JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jarFiles
		);
		
		updateGridData(container);
		updateGridData(container.getParent());
		
		new GenericPersistenceUnitJarFilesComposite(this, container);
	}
}
