/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.schema.generation;

import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.swt.widgets.Composite;

/**
 * EclipseLinkSchemaGenerationComposite
 */
public class EclipseLinkSchemaGenerationComposite
	extends AbstractFormPane<SchemaGeneration>
{
	public EclipseLinkSchemaGenerationComposite(
					AbstractFormPane<SchemaGeneration> subjectHolder,
					Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = buildSection(
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