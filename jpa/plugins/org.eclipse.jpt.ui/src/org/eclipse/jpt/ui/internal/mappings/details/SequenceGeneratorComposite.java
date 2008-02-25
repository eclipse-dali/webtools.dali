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

import java.util.Collection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
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
	/**
	 * Creates a new <code>SequenceGeneratorComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public SequenceGeneratorComposite(AbstractFormPane<? extends IdMapping> parentPane,
	                                  Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected SequenceGenerator buildGenerator() {
		return subject().addSequenceGenerator();
	}

	private PropertyValueModel<SequenceGenerator> buildSequenceGeneratorHolder() {
		return new PropertyAspectAdapter<IdMapping, SequenceGenerator>(getSubjectHolder(), propertyName()) {
			@Override
			protected SequenceGenerator buildValue_() {
				return subject.getSequenceGenerator();
			}
		};
	}

	private SequenceCombo<SequenceGenerator> buildSequenceNameCombo(Composite parent) {

		return new SequenceCombo<SequenceGenerator>(this, buildSequenceGeneratorHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SequenceGenerator.DEFAULT_SEQUENCE_NAME_PROPERTY);
				propertyNames.add(SequenceGenerator.SPECIFIED_SEQUENCE_NAME_PROPERTY);
			}

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
			protected JpaProject jpaProject() {
				return SequenceGeneratorComposite.this.jpaProject();
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
				SequenceGenerator generator = generator();

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
	protected SequenceGenerator generator(IdMapping subject) {
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
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_sequence,
			buildSequenceNameCombo(container),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String propertyName() {
		return IdMapping.SEQUENCE_GENERATOR_PROPERTY;
	}
}