/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.GeneratorHolder;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | v Table Generator                                                         |
 * |                                                                           |
 * |   x Table Generator                                                       |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | TableGeneratorComposite                                             | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * |                                                                           |
 * | v Sequence Generator                                                      |
 * |                                                                           |
 * |   x Sequence Generator                                                    |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | SequenceGeneratorComposite                                          | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see GeneratorHolder
 * @see TableGeneratorComposite
 * @see SequenceGeneratorComposite
 * @see AbstractEntityComposite - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class GeneratorsComposite extends AbstractPane<GeneratorHolder>
{
	private WritablePropertyValueModel<Boolean> sequenceGeneratorExpansionStateHolder;
	private WritablePropertyValueModel<Boolean> tableGeneratorExpansionStateHolder;

	/**
	 * Creates a new <code>GeneratorsComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public GeneratorsComposite(AbstractPane<? extends GeneratorHolder> parentPane,
	                           Composite parent) {

		super(parentPane, parent, false);
	}

	private WritablePropertyValueModel<Boolean> buildSequenceGeneratorBooleanHolder() {
		return new PropertyAspectAdapter<GeneratorHolder, Boolean>(getSubjectHolder(), GeneratorHolder.SEQUENCE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getSequenceGenerator() != null;
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value && (subject.getSequenceGenerator() == null)) {
					subject.addSequenceGenerator();
				}
				else if (!value && (subject.getSequenceGenerator() != null)) {
					subject.removeSequenceGenerator();
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildTableGeneratorBooleanHolder() {
		return new PropertyAspectAdapter<GeneratorHolder, Boolean>(getSubjectHolder(), GeneratorHolder.TABLE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getTableGenerator() != null;
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value && (subject.getTableGenerator() == null)) {
					subject.addTableGenerator();
				}
				else if (!value && (subject.getTableGenerator() != null)) {
					subject.removeTableGenerator();
				}
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();

		sequenceGeneratorExpansionStateHolder.setValue(subject() != null && subject().getSequenceGenerator() != null);
		tableGeneratorExpansionStateHolder   .setValue(subject() != null && subject().getTableGenerator() != null);
	}

 	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();

		sequenceGeneratorExpansionStateHolder = new SimplePropertyValueModel<Boolean>(false);
		tableGeneratorExpansionStateHolder    = new SimplePropertyValueModel<Boolean>(false);
	}

 	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		initializeTableGeneratorPane(container);
		initializeSequenceGeneratorPane(container);
	}

	private void initializeSequenceGeneratorPane(Composite container) {

		// Sequence Generator sub-section
		container = buildCollapsableSubSection(
			buildSubPane(container, 10),
			JptUiMappingsMessages.IdMappingComposite_sequenceGenerator,
			sequenceGeneratorExpansionStateHolder
		);

		// Sequence Generator check box
		Button sequenceGeneratorCheckBox = buildCheckBox(
			buildSubPane(container, 5),
			JptUiMappingsMessages.IdMappingComposite_sequenceGenerator,
			buildSequenceGeneratorBooleanHolder(),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR
		);

		// Sequence Generator pane
		new SequenceGeneratorComposite(
			this,
			buildSubPane(container, 0, sequenceGeneratorCheckBox.getBorderWidth() + 16)
		);
	}

 	private void initializeTableGeneratorPane(Composite container) {

		// Table Generator sub-section
		container = buildCollapsableSubSection(
			container,
			JptUiMappingsMessages.IdMappingComposite_tableGenerator,
			tableGeneratorExpansionStateHolder
		);

		Button tableGeneratorCheckBox = buildCheckBox(
			buildSubPane(container, 5),
			JptUiMappingsMessages.IdMappingComposite_tableGenerator,
			buildTableGeneratorBooleanHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR
		);

		new TableGeneratorComposite(
			this,
			buildSubPane(container, 0, tableGeneratorCheckBox.getBorderWidth() + 16)
		);
	}
}
