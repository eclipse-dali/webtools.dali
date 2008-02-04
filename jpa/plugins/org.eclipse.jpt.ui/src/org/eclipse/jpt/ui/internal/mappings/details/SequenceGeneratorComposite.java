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

import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.SequenceCombo;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
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

	private PropertyValueModel<ISequenceGenerator> buildSequenceGeneratorHolder() {
		return new PropertyAspectAdapter<IIdMapping, ISequenceGenerator>(getSubjectHolder(), propertyName()) {
			@Override
			protected ISequenceGenerator buildValue_() {
				return subject.getSequenceGenerator();
			}
		};
	}

	private SequenceCombo<ISequenceGenerator> buildSequenceNameCombo(Composite parent) {

		return new SequenceCombo<ISequenceGenerator>(this, buildSequenceGeneratorHolder(), parent) {

			@Override
			protected void buildSubject() {
				SequenceGeneratorComposite.this.buildGenerator();
			}

			@Override
			protected String defaultValue() {
				return JptUiMappingsMessages.SequenceGeneratorComposite_default;
			}

			@Override
			protected boolean isBuildSubjectAllowed() {
				return true;
			}

			@Override
			protected Schema schema() {
				// TODO
				return null;
			}

			@Override
			protected void setValue(String value) {
				retrieveGenerator().setSpecifiedSequenceName(value);
			}

			@Override
			protected String value() {
				ISequenceGenerator generator = generator();

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
	protected ISequenceGenerator generator(IIdMapping subject) {
		return subject.getSequenceGenerator();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		Text nameText = buildNameText(container);
		setNameText(nameText);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_name,
			nameText,
			IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		SequenceCombo<ISequenceGenerator> sequenceNameCombo =
			buildSequenceNameCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_sequence,
			sequenceNameCombo.getControl(),
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