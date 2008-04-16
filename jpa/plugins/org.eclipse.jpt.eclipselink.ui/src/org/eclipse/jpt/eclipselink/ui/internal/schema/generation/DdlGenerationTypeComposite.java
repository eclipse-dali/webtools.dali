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

import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.DdlGenerationType;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * DdlGenerationTypeComposite
 */
public class DdlGenerationTypeComposite
	extends AbstractFormPane<SchemaGeneration>
{
	/**
	 * Creates a new <code>DdlGenerationTypeComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public DdlGenerationTypeComposite(
					AbstractFormPane<? extends SchemaGeneration> parentComposite, 
					Composite parent) {
		
		super(parentComposite, parent);
	}

	private EnumFormComboViewer<SchemaGeneration, DdlGenerationType> buildDdlGenerationTypeCombo(Composite container) {
		return new EnumFormComboViewer<SchemaGeneration, DdlGenerationType>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY);
			}

			@Override
			protected DdlGenerationType[] choices() {
				return DdlGenerationType.values();
			}

			@Override
			protected DdlGenerationType defaultValue() {
				return this.subject().getDefaultDdlGenerationType();
			}

			@Override
			protected String displayString(DdlGenerationType value) {
				return this.buildDisplayString(EclipseLinkUiMessages.class, DdlGenerationTypeComposite.this, value);
			}

			@Override
			protected DdlGenerationType getValue() {
				return this.subject().getDdlGenerationType();
			}

			@Override
			protected void setValue(DdlGenerationType value) {
				this.subject().setDdlGenerationType(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_ddlGenerationTypeLabel,
			buildDdlGenerationTypeCombo( container),
			null		// TODO IJpaHelpContextIds.
		);
	}
}
