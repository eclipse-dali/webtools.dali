/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
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
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.SequenceGeneratorComposite_name);
		this.addText(container, this.buildGeneratorNameHolder(), JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME);

		// Sequence Generator widgets
		this.addLabel(container, JptJpaUiDetailsMessages.SequenceGeneratorComposite_sequence);
		this.buildSequenceNameCombo(container);

		this.addLabel(container, JptJpaUiDetailsMessages.GeneratorComposite_allocationSize);
		this.addAllocationSizeCombo(container);

		this.addLabel(container, JptJpaUiDetailsMessages.GeneratorComposite_initialValue);
		this.addInitialValueCombo(container);
	}

	protected SequenceCombo<SequenceGenerator> buildSequenceNameCombo(Composite parent) {
		return new LocalSequenceCombo(this, getSubjectHolder(), parent);
	}

	protected class LocalSequenceCombo
		extends SequenceCombo<SequenceGenerator>
	{
		protected LocalSequenceCombo(Pane<?> parentPane, PropertyValueModel<? extends SequenceGenerator> subjectHolder, Composite parent) {
			super(parentPane, subjectHolder, parent);
		}

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

		@Override
		protected String getHelpId() {
			return JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE;
		}
	}
}
