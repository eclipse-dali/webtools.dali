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
import org.eclipse.jpt.ui.internal.widgets.Pane;
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
public class GeneratorsComposite extends Pane<GeneratorHolder>
{
	private WritablePropertyValueModel<Boolean> sequenceGeneratorExpansionStateHolder;
	private WritablePropertyValueModel<Boolean> tableGeneratorExpansionStateHolder;

	/**
	 * Creates a new <code>GeneratorsComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public GeneratorsComposite(Pane<? extends GeneratorHolder> parentPane,
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

		sequenceGeneratorExpansionStateHolder.setValue(getSubject() != null && getSubject().getSequenceGenerator() != null);
		tableGeneratorExpansionStateHolder   .setValue(getSubject() != null && getSubject().getTableGenerator() != null);
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
		container = addCollapsableSubSection(
			addSubPane(container, 10),
			JptUiMappingsMessages.GeneratorsComposite_sequenceGeneratorSection,
			sequenceGeneratorExpansionStateHolder
		);

		// Sequence Generator check box
		Button sequenceGeneratorCheckBox = addCheckBox(
			addSubPane(container, 5),
			JptUiMappingsMessages.GeneratorsComposite_sequenceGeneratorCheckBox,
			buildSequenceGeneratorBooleanHolder(),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR
		);

		// Sequence Generator pane
		new SequenceGeneratorComposite(
			this,
			addSubPane(container, 0, sequenceGeneratorCheckBox.getBorderWidth() + 16)
		);
	}

 	private void initializeTableGeneratorPane(Composite container) {

		// Table Generator sub-section
		container = addCollapsableSubSection(
			container,
			JptUiMappingsMessages.GeneratorsComposite_tableGeneratorSection,
			tableGeneratorExpansionStateHolder
		);

		Button tableGeneratorCheckBox = addCheckBox(
			addSubPane(container, 5),
			JptUiMappingsMessages.GeneratorsComposite_tableGeneratorCheckBox,
			buildTableGeneratorBooleanHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR
		);

		new TableGeneratorComposite(
			this,
			addSubPane(container, 0, tableGeneratorCheckBox.getBorderWidth() + 16)
		);
	}
}
