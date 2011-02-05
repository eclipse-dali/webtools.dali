/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.schema.generation;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * PersistenceXmlSchemaGenerationTab
 */
public class PersistenceXmlSchemaGenerationTab
	extends Pane<SchemaGeneration>
	implements JpaPageComposite
{
	// ********** constructors/initialization **********
	public PersistenceXmlSchemaGenerationTab(
				PropertyValueModel<SchemaGeneration> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		new EclipseLinkSchemaGenerationComposite(this, container);
	}

	// ********** JpaPageComposite implementation **********

	public String getHelpID() {
		return EclipseLinkHelpContextIds.PERSISTENCE_SCHEMA_GENERATION;
	}

	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}

	public String getPageText() {
		return EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_title;
	}
}