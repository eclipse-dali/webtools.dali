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
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.widgets.Composite;

/**
 * OutputModeComposite
 */
public class OutputModeComposite extends FormPane<SchemaGeneration>
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
				FormPane<? extends SchemaGeneration> parentComposite, 
				Composite parent) {
		
		super(parentComposite, parent);
	}

	private EnumFormComboViewer<SchemaGeneration, OutputMode> addBuildOutputModeCombo(Composite container) {
		return new EnumFormComboViewer<SchemaGeneration, OutputMode>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration.OUTPUT_MODE_PROPERTY);
			}

			@Override
			protected OutputMode[] getChoices() {
				return OutputMode.values();
			}

			@Override
			protected OutputMode getDefaultValue() {
				return this.getSubject().getDefaultOutputMode();
			}

			@Override
			protected String displayString(OutputMode value) {
				return this.buildDisplayString(EclipseLinkUiMessages.class, OutputModeComposite.this, value);
			}

			@Override
			protected OutputMode getValue() {
				return this.getSubject().getOutputMode();
			}

			@Override
			protected void setValue(OutputMode value) {
				this.getSubject().setOutputMode(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addLabeledComposite(
				container,
				EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_outputModeLabel,
				this.addBuildOutputModeCombo(container),
				null // TODO IJpaHelpContextIds.
		);
	}
}
