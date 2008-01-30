/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.SequenceCombo;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

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
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IIdMapping
 * @see ISequenceGenerator
 * @see GenerationComposite - The parent container
 * @see SequenceCombo
 *
 * @version 2.0
 * @since 1.0
 */
public class SequenceGeneratorComposite extends GeneratorComposite<ISequenceGenerator>
{
	/**
	 * Creates a new <code>SequenceGeneratorComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public SequenceGeneratorComposite(AbstractFormPane<? extends IIdMapping> parentPane,
	                                  Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ISequenceGenerator buildGenerator() {
		return subject().addSequenceGenerator();
	}

	private SequenceCombo<IJpaNode> buildSequenceNameCombo(Composite parent) {

		return new SequenceCombo<IJpaNode>(this, parent) {

			@Override
			protected String defaultValue() {
				return JptUiMappingsMessages.SequenceGeneratorComposite_default;
			}

			@Override
			protected Schema schema() {
				return null;
				// TODO
//				return database().schemaNamed(subject().jpaProject().getSchemaName());
			}

			@Override
			protected void setValue(String value) {
				ISequenceGenerator generator = getGenerator();

				if (generator == null) {
					generator = buildGenerator();
				}

				generator.setSpecifiedSequenceName(value);
			}

			@Override
			protected IJpaNode subject() {
				IJpaNode subject = super.subject();

				if (subject == null) {
					subject = SequenceGeneratorComposite.this.subject();
				}

				return subject;
			}

			@Override
			protected String value() {
				ISequenceGenerator generator = getGenerator();

				if (generator != null) {
					return generator.getSpecifiedSequenceName();
				}

				return null;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ISequenceGenerator getGenerator() {
		return (subject() != null) ? subject().getSequenceGenerator() : null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		Text nameText = buildNameText(shell());
		setNameText(nameText);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_name,
			nameText,
			IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		SequenceCombo<IJpaNode> sequenceNameCombo =
			buildSequenceNameCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_sequence,
			sequenceNameCombo.getCombo().getParent(),
			IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String propertyName() {
		return IIdMapping.SEQUENCE_GENERATOR_PROPERTY;
	}
}