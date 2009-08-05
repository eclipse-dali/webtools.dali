/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.SequenceCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
 * @see GenerationComposite - The parent container
 * @see SequenceCombo
 *
 * @version 2.0
 * @since 1.0
 */
public class SequenceGeneratorComposite extends GeneratorComposite<SequenceGenerator>
{

	public SequenceGeneratorComposite(
		Pane<? extends GeneratorContainer> parentPane,
		Composite parent) {

		super(parentPane, parent);
	}
	
	public SequenceGeneratorComposite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends GeneratorContainer> subjectHolder,
		Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected SequenceGenerator buildGenerator(GeneratorContainer subject) {
		return subject.addSequenceGenerator();
	}

	private PropertyValueModel<SequenceGenerator> buildSequenceGeneratorHolder() {
		return new PropertyAspectAdapter<GeneratorContainer, SequenceGenerator>(getSubjectHolder(), GeneratorContainer.SEQUENCE_GENERATOR_PROPERTY) {
			@Override
			protected SequenceGenerator buildValue_() {
				return this.subject.getSequenceGenerator();
			}
		};
	}

	protected SequenceCombo<SequenceGenerator> buildSequenceNameCombo(Composite parent) {

		return new SequenceCombo<SequenceGenerator>(this, buildSequenceGeneratorHolder(), parent) {

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
				retrieveGenerator(SequenceGeneratorComposite.this.getSubject()).setSpecifiedSequenceName(value);
			}

			@Override
			protected String getValue() {
				SequenceGenerator generator = SequenceGeneratorComposite.this.getGenerator();
				return (generator == null) ? null : generator.getSpecifiedSequenceName();
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

	@Override
	protected SequenceGenerator getGenerator(GeneratorContainer subject) {
		return subject.getSequenceGenerator();
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		addLabeledText(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_name,
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		addLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_sequence,
			buildSequenceNameCombo(container),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE
		);

		addAllocationSizeCombo(container);
		addInitialValueCombo(container);
	}

	@Override
	protected String getPropertyName() {
		return GeneratorContainer.SEQUENCE_GENERATOR_PROPERTY;
	}
}