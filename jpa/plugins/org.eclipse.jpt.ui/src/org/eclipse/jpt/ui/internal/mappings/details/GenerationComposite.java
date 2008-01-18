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

import org.eclipse.jpt.core.internal.context.base.IGeneratedValue;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Section;

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
 * @see IIdMapping
 * @see GeneratedValueComposite
 * @see TableGeneratorComposite
 * @see SequenceGeneratorComposite
 * @see IdMappingComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class GenerationComposite extends BaseJpaComposite<IIdMapping> {

	private Button primaryKeyGenerationCheckBox;
	private Section primaryKeyGenerationSection;
	private Section sequenceGenerationSection;
	private Button sequenceGeneratorCheckBox;
	private Section tableGenerationSection;
	private Button tableGeneratorCheckBox;

	/**
	 * Creates a new <code>GenerationComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public GenerationComposite(BaseJpaController<? extends IIdMapping> parentController,
	                           Composite parent)
	{
		super(parentController, parent, false);
	}

 	private WritablePropertyValueModel<Boolean> buildPrimaryKeyGenerationHolder() {
		return new PropertyAspectAdapter<IIdMapping, Boolean>(getSubjectHolder(), IIdMapping.GENERATED_VALUE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject().getGeneratedValue() != null;
			}
			@Override
			protected void setValue_(Boolean value) {
				if (value) {
					subject().addGeneratedValue();
				}
				else {
					subject().removeGeneratedValue();
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildSequenceGeneratorHolder() {
		return new PropertyAspectAdapter<IIdMapping, Boolean>(getSubjectHolder(), IIdMapping.GENERATED_VALUE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject().getGeneratedValue() != null;
			}
			@Override
			protected void setValue_(Boolean value) {
				if (value) {
					ISequenceGenerator sequenceGenerator = subject().addSequenceGenerator();
					IGeneratedValue generatedValue = subject().getGeneratedValue();

					if ((generatedValue != null) && (generatedValue.getGenerator() != null)) {
						sequenceGenerator.setName(generatedValue.getGenerator());
					}
				}
				else {
					subject().removeSequenceGenerator();
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildTableGeneratorHolder() {
		return new PropertyAspectAdapter<IIdMapping, Boolean>(getSubjectHolder(), IIdMapping.GENERATED_VALUE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getGeneratedValue() != null;
			}
			@Override
			protected void setValue_(Boolean value) {
				if (value) {
					ITableGenerator tableGenerator = subject().addTableGenerator();
					IGeneratedValue generatedValue = subject().getGeneratedValue();

					if ((generatedValue != null) && (generatedValue.getGenerator() != null)) {
						tableGenerator.setName(generatedValue.getGenerator());
					}
				}
				else {
					subject().removeTableGenerator();
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

		this.primaryKeyGenerationSection.setExpanded(true);
		this.updateSequenceGeneratorExpandedState();
		this.updateTableGeneratorExpandedState();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Primary Key Generation section
		this.primaryKeyGenerationSection = buildSection(
			container,
			JptUiMappingsMessages.IdMappingComposite_primaryKeyGeneration
		);

		Composite generationClient = (Composite) this.primaryKeyGenerationSection.getClient();

		// Primary Key Generation check box
		this.primaryKeyGenerationCheckBox = this.buildCheckBox(
			generationClient,
			JptUiMappingsMessages.IdMappingComposite_primaryKeyGeneration,
			buildPrimaryKeyGenerationHolder()
		);

		this.helpSystem().setHelp(primaryKeyGenerationCheckBox, IJpaHelpContextIds.MAPPING_PRIMARY_KEY_GENERATION);

		// Generated Value widgets
		GeneratedValueComposite generatedValueComposite = new GeneratedValueComposite(
			this,
			generationClient
		);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent          = this.primaryKeyGenerationCheckBox.getBorderWidth() + 13;

		generatedValueComposite.getControl().setLayoutData(gridData);
		this.registerSubPane(generatedValueComposite);

		// Table Generator pane
		this.initializeTableGeneratorPane(generationClient);

		// Sequence Generator pane
		this.initializeSequenceGeneratorPane(generationClient);
	}

	private void initializeSequenceGeneratorPane(Composite container) {

		// Sequence Generator sub-section
		this.sequenceGenerationSection = buildSubSection(
			container,
			JptUiMappingsMessages.IdMappingComposite_sequenceGenerator
		);

		Composite sequenceGenClient = (Composite) this.sequenceGenerationSection.getClient();

		// Sequence Generator check box
		this.sequenceGeneratorCheckBox = this.buildCheckBox(
			sequenceGenClient,
			JptUiMappingsMessages.IdMappingComposite_sequenceGenerator,
			buildSequenceGeneratorHolder(),
			IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR
		);

		// Sequence Generator pane
		new SequenceGeneratorComposite(
			this,
			buildSubPane(sequenceGenClient, 0, sequenceGeneratorCheckBox.getBorderWidth() + 13)
		);
	}

	private void initializeTableGeneratorPane(Composite container) {

		// Table Generator sub-section
		this.tableGenerationSection = buildSubSection(
			container,
			JptUiMappingsMessages.IdMappingComposite_tableGenerator
		);

		Composite tableGenClient = (Composite) this.tableGenerationSection.getClient();

		this.tableGeneratorCheckBox = this.buildCheckBox(
			tableGenClient,
			JptUiMappingsMessages.IdMappingComposite_tableGenerator,
			buildTableGeneratorHolder(),
			IJpaHelpContextIds.MAPPING_TABLE_GENERATOR
		);

		new TableGeneratorComposite(
			this,
			buildSubPane(tableGenClient, 0, tableGeneratorCheckBox.getBorderWidth() + 13)
		);
	}

	private void updateSequenceGeneratorExpandedState() {
		this.sequenceGenerationSection.setExpanded(
			this.subject() != null && this.subject().getSequenceGenerator() != null
		);
	}

	private void updateTableGeneratorExpandedState() {
		this.tableGenerationSection.setExpanded(
			this.subject() != null && this.subject().getTableGenerator() != null
		);
	}
}
