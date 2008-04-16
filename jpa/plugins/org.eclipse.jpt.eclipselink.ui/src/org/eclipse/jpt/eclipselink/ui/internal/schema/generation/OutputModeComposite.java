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

import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.OutputMode;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * OutputModeComposite
 */
public class OutputModeComposite extends AbstractFormPane<SchemaGeneration>
{
	/**
	 * Creates a new <code>OutputModeComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public OutputModeComposite(
				AbstractFormPane<? extends SchemaGeneration> parentComposite, 
				Composite parent) {
		
		super(parentComposite, parent);
	}

	private EnumFormComboViewer<SchemaGeneration, OutputMode> buildOutputModeCombo(Composite container) {
		return new EnumFormComboViewer<SchemaGeneration, OutputMode>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration.OUTPUT_MODE_PROPERTY);
			}

			@Override
			protected OutputMode[] choices() {
				return OutputMode.values();
			}

			@Override
			protected OutputMode defaultValue() {
				return this.subject().getDefaultOutputMode();
			}

			@Override
			protected String displayString(OutputMode value) {
				return this.buildDisplayString(EclipseLinkUiMessages.class, OutputModeComposite.this, value);
			}

			@Override
			protected OutputMode getValue() {
				return this.subject().getOutputMode();
			}

			@Override
			protected void setValue(OutputMode value) {
				this.subject().setOutputMode(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.buildLabeledComposite(
				container,
				EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_outputModeLabel,
				this.buildOutputModeCombo(container),
				null // TODO IJpaHelpContextIds.
		);
	}
}
