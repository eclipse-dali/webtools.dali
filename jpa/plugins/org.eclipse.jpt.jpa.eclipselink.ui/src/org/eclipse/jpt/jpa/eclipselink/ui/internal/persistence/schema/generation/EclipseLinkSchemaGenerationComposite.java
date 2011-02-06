/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.schema.generation;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkSchemaGenerationComposite
 */
public class EclipseLinkSchemaGenerationComposite
	extends Pane<SchemaGeneration>
{
	public EclipseLinkSchemaGenerationComposite(
					Pane<SchemaGeneration> subjectHolder,
					Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_sectionTitle,
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_sectionDescription
		);

		// DDL Generation Type:
		new DdlGenerationTypeComposite(this, container);
		// Output Mode:
		new OutputModeComposite(this, container);
		// DDL Generation Location
		new DdlGenerationLocationComposite(this, container);
		// Create DDL File Name:
		new CreateDdlFileNameComposite(this, container);
		// Drop DDL File Name:
		new DropDdlFileNameComposite(this, container);
	}
}