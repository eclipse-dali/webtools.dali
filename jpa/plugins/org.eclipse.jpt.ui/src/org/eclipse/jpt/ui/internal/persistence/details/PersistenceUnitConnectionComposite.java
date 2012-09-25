/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence.details;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | - General --------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PersistenceUnitConnectionGeneralComposite                             | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - Database -------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PersistenceUnitConnectionDatabaseComposite                            | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see PersistenceUnitConnectionGeneralComposite
 * @see PersistenceUnitConnectionDatabaseComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class PersistenceUnitConnectionComposite extends Pane<PersistenceUnit>
                                                implements JpaPageComposite
{
	/**
	 * Creates a new <code>PersistenceUnitConnectionComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistenceUnitConnectionComposite(PropertyValueModel<PersistenceUnit> subjectHolder,
	                                          Composite container,
	                                          WidgetFactory widgetFactory) {

		super(subjectHolder, container, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
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
		updateGridData(container);

		return container;
	}

	/*
	 * (non-Javadoc)
	 */
	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
	}

	private void initializeDatabasePane(Composite container) {

		container = addSection(
			container,
			JptUiPersistenceMessages.PersistenceUnitConnectionComposite_database
		);

		new PersistenceUnitConnectionDatabaseComposite(this, container);
	}

	private void initializeGeneralPane(Composite container) {

		container = addSection(
			container,
			JptUiPersistenceMessages.PersistenceUnitConnectionComposite_general
		);

		new PersistenceUnitConnectionGeneralComposite(this, container);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		initializeGeneralPane(container);
		initializeDatabasePane(container);
	}

	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}

	public String getPageText() {
		return JptUiPersistenceMessages.PersistenceUnitConnectionComposite_connection;
	}

	private void updateGridData(Composite container) {

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.FILL;
		container.setLayoutData(gridData);
	}
}
