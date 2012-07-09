/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.editors.JpaPageComposite;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

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
 * |                                                                           |
 * |                                                                           |
 * | - JAR Files ------------------------------------------------------------- |
 * |                                                                           |
 * |   Description                                                             |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | PersistenceUnitJarFilesComposite                                    | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see PersistenceUnitMappedClassesComposite
 * @see PersistenceUnitMappingFilesComposite
 * @see PersistenceUnitJarFilesComposite
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class PersistenceUnitGeneralTab extends Pane<PersistenceUnit>
                                             implements JpaPageComposite
{
	/**
	 * Creates a new <code>PersistenceUnitGeneralComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistenceUnitGeneralTab(PropertyValueModel<? extends PersistenceUnit> subjectHolder,
	                                       Composite container,
	                                       WidgetFactory widgetFactory) {

		super(subjectHolder, container, widgetFactory);
	}

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_GENERAL;
	}

	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}

	public String getPageText() {
		return JptUiPersistenceMessages.PersistenceUnitGeneralComposite_general;
	}

	@Override
	protected Composite addComposite(Composite parent) {
		GridLayout layout = new GridLayout(2, true);//2 columns
		layout.marginHeight = 0;
		layout.marginWidth  = 0;

		return this.addPane(parent, layout);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Section generalSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		generalSection.setText(JptUiPersistenceMessages.PersistenceUnitGeneralComposite_general);
		Control generalComposite = this.buildGeneralComposite(generalSection);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		generalSection.setLayoutData(gridData);
		generalSection.setClient(generalComposite);

		Section mappingFilesSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		mappingFilesSection.setText(JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jpaMappingDescriptors);
		mappingFilesSection.setDescription(JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jpaMappingDescriptors_description);
		Control mappingFilesComposite = this.buildMappingFilesComposite(mappingFilesSection);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		mappingFilesSection.setLayoutData(gridData);
		mappingFilesSection.setClient(mappingFilesComposite);

		Section classesSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		classesSection.setText(JptUiPersistenceMessages.PersistenceUnitGeneralComposite_mappedClasses);
		classesSection.setDescription(JptUiPersistenceMessages.PersistenceUnitClassesComposite_description);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalAlignment = SWT.TOP;
		classesSection.setLayoutData(gridData);
		Control classesComposite = this.buildClassesComposite(classesSection);
		classesSection.setClient(classesComposite);
		
		Section jarFilesSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		jarFilesSection.setText(JptUiPersistenceMessages.PersistenceUnitGeneralComposite_jarFiles);
		Control jarFilesComposite = this.buildJarFilesComposite(jarFilesSection);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalAlignment = SWT.TOP;
		jarFilesSection.setLayoutData(gridData);
		jarFilesSection.setClient(jarFilesComposite);
	}

	protected Control buildGeneralComposite(Composite parent) {
		return new PersistenceUnitGeneralComposite(this, parent).getControl();
	}

	protected Control buildClassesComposite(Composite parent) {
		return new PersistenceUnitClassesComposite(this, parent).getControl();
	}

	protected Control buildMappingFilesComposite(Composite parent) {
		return new GenericPersistenceUnitMappingFilesComposite(this, parent).getControl();
	}

	protected Control buildJarFilesComposite(Composite parent) {
		return new GenericPersistenceUnitJarFilesComposite(this, parent).getControl();
	}
}
