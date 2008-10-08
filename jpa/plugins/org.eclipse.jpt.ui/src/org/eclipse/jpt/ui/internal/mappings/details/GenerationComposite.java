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

import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GeneratorHolder;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | v Primary Key Generation                                                  |
 * |                                                                           |
 * |   x Primary Key Generation                                                |
 * |     --------------------------------------------------------------------- |
 * |     |                                                                   | |
 * |     | GeneratedValueComposite                                           | |
 * |     |                                                                   | |
 * |     --------------------------------------------------------------------- |
 * |                                                                           |
 * |   v Table Generator                                                       |
 * |                                                                           |
 * |     x Table Generator                                                     |
 * |     --------------------------------------------------------------------- |
 * |     |                                                                   | |
 * |     | TableGeneratorComposite                                           | |
 * |     |                                                                   | |
 * |     --------------------------------------------------------------------- |
 * |                                                                           |
 * |   v Sequence Generator                                                    |
 * |                                                                           |
 * |     x Sequence Generator                                                  |
 * |     --------------------------------------------------------------------- |
 * |     |                                                                   | |
 * |     | SequenceGeneratorComposite                                        | |
 * |     |                                                                   | |
 * |     --------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IdMapping
 * @see GeneratedValueComposite
 * @see TableGeneratorComposite
 * @see SequenceGeneratorComposite
 * @see IdMappingComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class GenerationComposite extends FormPane<IdMapping>
{
	private WritablePropertyValueModel<Boolean> sequenceGeneratorExpansionStateHolder;
	private WritablePropertyValueModel<Boolean> tableGeneratorExpansionStateHolder;

	/**
	 * Creates a new <code>GenerationComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public GenerationComposite(FormPane<? extends IdMapping> parentPane,
	                           Composite parent)
	{
		super(parentPane, parent, false);
	}

	private WritablePropertyValueModel<Boolean> buildPrimaryKeyGenerationHolder() {
		return new PropertyAspectAdapter<IdMapping, Boolean>(getSubjectHolder(), IdMapping.GENERATED_VALUE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getGeneratedValue() != null;
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value && (subject.getGeneratedValue() == null)) {
					subject.addGeneratedValue();
				}
				else if (!value && (subject.getGeneratedValue() != null)) {
					subject.removeGeneratedValue();
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildSequenceGeneratorBooleanHolder() {
		return new PropertyAspectAdapter<IdMapping, Boolean>(getSubjectHolder(), GeneratorHolder.SEQUENCE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getSequenceGenerator() != null;
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value && (subject.getSequenceGenerator() == null)) {

					SequenceGenerator sequenceGenerator = subject.addSequenceGenerator();
					GeneratedValue generatedValue = subject.getGeneratedValue();

					if ((generatedValue != null) &&
					    (generatedValue.getGenerator() != null))
					{
						sequenceGenerator.setName(generatedValue.getGenerator());
					}
				}
				else if (!value && (subject.getSequenceGenerator() != null)) {
					subject.removeSequenceGenerator();
				}
			}
		};
	}

 	private WritablePropertyValueModel<Boolean> buildTableGeneratorBooleanHolder() {
		return new PropertyAspectAdapter<IdMapping, Boolean>(getSubjectHolder(), GeneratorHolder.TABLE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getTableGenerator() != null;
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value && (subject.getTableGenerator() == null)) {

					TableGenerator tableGenerator = subject.addTableGenerator();
					GeneratedValue generatedValue = subject.getGeneratedValue();

					if ((generatedValue != null) &&
					    (generatedValue.getGenerator() != null))
					{
						tableGenerator.setName(generatedValue.getGenerator());
					}
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
	protected void doPopulate()
	{
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

		// Primary Key Generation section
		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.IdMappingComposite_primaryKeyGenerationSection,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		// Primary Key Generation check box
		Button primaryKeyGenerationCheckBox = addCheckBox(
			container,
			JptUiMappingsMessages.IdMappingComposite_primaryKeyGenerationCheckBox,
			buildPrimaryKeyGenerationHolder(),
			JpaHelpContextIds.MAPPING_PRIMARY_KEY_GENERATION
		);

		// Generated Value widgets
		GeneratedValueComposite generatedValueComposite = new GeneratedValueComposite(
			this,
			container
		);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent          = primaryKeyGenerationCheckBox.getBorderWidth() + 16;

		generatedValueComposite.getControl().setLayoutData(gridData);

		// Table Generator pane
		initializeTableGeneratorPane(addSubPane(container, 10));

		// Sequence Generator pane
		initializeSequenceGeneratorPane(addSubPane(container, 10));
	}

	private void initializeSequenceGeneratorPane(Composite container) {

		// Sequence Generator sub-section
		container = addCollapsableSubSection(
			container,
			JptUiMappingsMessages.IdMappingComposite_sequenceGeneratorSection,
			sequenceGeneratorExpansionStateHolder
		);

		// Sequence Generator check box
		Button sequenceGeneratorCheckBox = addCheckBox(
			container,
			JptUiMappingsMessages.IdMappingComposite_sequenceGeneratorCheckBox,
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
			JptUiMappingsMessages.IdMappingComposite_tableGeneratorSection,
			tableGeneratorExpansionStateHolder
		);

		Button tableGeneratorCheckBox = addCheckBox(
			container,
			JptUiMappingsMessages.IdMappingComposite_tableGeneratorCheckBox,
			buildTableGeneratorBooleanHolder(),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR
		);

		new TableGeneratorComposite(
			this,
			addSubPane(container, 0, tableGeneratorCheckBox.getBorderWidth() + 16)
		);
	}
}