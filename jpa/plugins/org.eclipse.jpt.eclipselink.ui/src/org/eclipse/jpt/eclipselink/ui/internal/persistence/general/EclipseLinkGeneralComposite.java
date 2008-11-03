/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.general;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkGeneralComposite
 */
public class EclipseLinkGeneralComposite
	extends FormPane<GeneralProperties>
{
	// ********** constructors **********
	public EclipseLinkGeneralComposite(
					FormPane<GeneralProperties> subjectHolder,
					Composite container) {
		super(subjectHolder, container, false);
	}

	// ********** initialization **********
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeGeneralPane(container);
		this.initializeMappedClassesPane(container);
		this.initializeJPAMappingDescriptorsPane(container);
		this.initializeExcludeEclipselinkOrmPane(container);
	}

	private void initializeExcludeEclipselinkOrmPane(Composite container) {

		this.updateGridData(container);
		this.updateGridData(container.getParent());

		new ExcludeEclipselinkOrmComposite(this, container);
	}

	private void initializeJPAMappingDescriptorsPane(Composite container) {

		container = addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_xmlMappingFilesSectionTitle,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_xmlMappingFilesSectionDescription
		);

		this.updateGridData(container);
		this.updateGridData(container.getParent());

		new XmlMappingFilesComposite(this, container);
	}

	private void initializeMappedClassesPane(Composite container) {

		container = addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_mappedClassesSectionTitle,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_mappedClassesSectionDescription
		);

		this.updateGridData(container);
		this.updateGridData(container.getParent());

		new ManagedClassesComposite(this, container);
	}
	
	private void initializeGeneralPane(Composite container) {

		container = addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_generalSectionTitle
		);

		new GeneralComposite(this, container);
	}

	// ********** internal methods **********

	private void updateGridData(Composite container) {

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.FILL;
		container.setLayoutData(gridData);
	}
	
	@Override
	protected Composite addContainer(Composite parent) {

		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight    = 0;
		layout.marginWidth     = 0;
		layout.marginTop       = 0;
		layout.marginLeft      = 0;
		layout.marginBottom    = 0;
		layout.marginRight     = 0;
		layout.verticalSpacing = 15;

		Composite container = addPane(parent, layout);
		this.updateGridData(container);

		return container;
	}
}