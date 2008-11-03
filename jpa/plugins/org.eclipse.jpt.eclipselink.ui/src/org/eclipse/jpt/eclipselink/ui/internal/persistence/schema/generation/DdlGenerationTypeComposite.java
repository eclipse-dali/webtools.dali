/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.schema.generation;

import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.DdlGenerationType;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.widgets.Composite;

/**
 * DdlGenerationTypeComposite
 */
public class DdlGenerationTypeComposite
	extends FormPane<SchemaGeneration>
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
					FormPane<? extends SchemaGeneration> parentComposite, 
					Composite parent) {
		
		super(parentComposite, parent);
	}

	private EnumFormComboViewer<SchemaGeneration, DdlGenerationType> addDdlGenerationTypeCombo(Composite container) {
		return new EnumFormComboViewer<SchemaGeneration, DdlGenerationType>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY);
			}

			@Override
			protected DdlGenerationType[] getChoices() {
				return DdlGenerationType.values();
			}

			@Override
			protected DdlGenerationType getDefaultValue() {
				return this.getSubject().getDefaultDdlGenerationType();
			}

			@Override
			protected String displayString(DdlGenerationType value) {
				return this.buildDisplayString(EclipseLinkUiMessages.class, DdlGenerationTypeComposite.this, value);
			}

			@Override
			protected DdlGenerationType getValue() {
				return this.getSubject().getDdlGenerationType();
			}

			@Override
			protected void setValue(DdlGenerationType value) {
				this.getSubject().setDdlGenerationType(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_ddlGenerationTypeLabel,
			addDdlGenerationTypeCombo( container),
			null		// TODO IJpaHelpContextIds.
		);
	}
}
