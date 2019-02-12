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

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.persistence.JptJpaUiPersistenceMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class PersistenceUnitGeneralEditorPage
	extends Pane<PersistenceUnit>
{
	public PersistenceUnitGeneralEditorPage(
			PropertyValueModel<PersistenceUnit> subjectHolder,
            Composite parent,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(subjectHolder, parent, widgetFactory, resourceManager);
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
		generalSection.setText(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_GENERAL_COMPOSITE_GENERAL);
		Control generalComposite = this.buildGeneralComposite(generalSection);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		generalSection.setLayoutData(gridData);
		generalSection.setClient(generalComposite);

		Section classesSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		classesSection.setText(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_GENERAL_COMPOSITE_MAPPED_CLASSES);
		classesSection.setDescription(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_CLASSES_COMPOSITE_DESCRIPTION);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		classesSection.setLayoutData(gridData);
		Control classesComposite = this.buildClassesComposite(classesSection);
		classesSection.setClient(classesComposite);

		Section mappingFilesSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		mappingFilesSection.setText(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_GENERAL_COMPOSITE_JPA_MAPPING_DESCRIPTORS);
		mappingFilesSection.setDescription(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_GENERAL_COMPOSITE_JPA_MAPPING_DESCRIPTORS_DESCRIPTION);
		Control mappingFilesComposite = this.buildMappingFilesComposite(mappingFilesSection);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.verticalIndent = 5;
		mappingFilesSection.setLayoutData(gridData);
		mappingFilesSection.setClient(mappingFilesComposite);

		Section jarFilesSection = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		jarFilesSection.setText(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_GENERAL_COMPOSITE_JAR_FILES);
		Control jarFilesComposite = this.buildJarFilesComposite(jarFilesSection);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.verticalIndent = 5;
		jarFilesSection.setLayoutData(gridData);
		jarFilesSection.setClient(jarFilesComposite);
	}

	protected Control buildGeneralComposite(Composite parentComposite) {
		return new PersistenceUnitGeneralComposite(this, parentComposite).getControl();
	}

	protected Control buildClassesComposite(Composite parentComposite) {
		return new PersistenceUnitClassesComposite(this, parentComposite).getControl();
	}

	protected Control buildMappingFilesComposite(Composite parentComposite) {
		return new GenericPersistenceUnitMappingFilesComposite(this, parentComposite).getControl();
	}

	protected Control buildJarFilesComposite(Composite parentComposite) {
		return new GenericPersistenceUnitJarFilesComposite(this, parentComposite).getControl();
	}
}
