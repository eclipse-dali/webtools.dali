/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.SequenceCombo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                     ----------------------------------------------------- |
 * | Name:               | I                                                 | |
 * |                     ----------------------------------------------------- |
 * |                     ----------------------------------------------------- |
 * | Sequence Generator: | SequenceCombo                                     | |
 * |                     ----------------------------------------------------- |
 * |                     -------------                                         |
 * | Allocation Size:    | I       |I|                                         |
 * |                     -------------                                         |
 * |                     -------------                                         |
 * | Initial Value:      |         |I|                                         |
 * |                     -------------                                         |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IdMapping
 * @see SequenceGenerator
 * @see IdMappingGenerationComposite - The parent container
 * @see SequenceCombo
 *
 * @version 2.2
 * @since 1.0
 */
public class SequenceGeneratorComposite extends GeneratorComposite<SequenceGenerator>
{
	
	public SequenceGeneratorComposite(Pane<?> parentPane,
        							PropertyValueModel<SequenceGenerator> subjectHolder,
        							Composite parent,
        							GeneratorBuilder<SequenceGenerator> builder) {

		super(parentPane, subjectHolder, parent, builder);
	}

	@Override
	protected String getPropertyName() {
		return GeneratorContainer.SEQUENCE_GENERATOR_PROPERTY;
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		addLabeledText(
			container,
			JptUiDetailsMessages.SequenceGeneratorComposite_name,
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.SequenceGeneratorComposite_sequence,
			buildSequenceNameCombo(container),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE
		);

		addAllocationSizeCombo(container);
		addInitialValueCombo(container);
	}

	protected SequenceCombo<SequenceGenerator> buildSequenceNameCombo(Composite parent) {

		return new SequenceCombo<SequenceGenerator>(this, getSubjectHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SequenceGenerator.DEFAULT_SEQUENCE_NAME_PROPERTY);
				propertyNames.add(SequenceGenerator.SPECIFIED_SEQUENCE_NAME_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultSequenceName();
			}

			@Override
			protected void setValue(String value) {
				retrieveGenerator().setSpecifiedSequenceName(value);
			}

			@Override
			protected String getValue() {
				return (getSubject() == null) ? null : getSubject().getSpecifiedSequenceName();
			}

			@Override
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return SequenceGeneratorComposite.this.getJpaProject();
			}

			@Override
			protected Schema getDbSchema_() {
				return this.getSubject().getDbSchema();
			}

		};
	}

}