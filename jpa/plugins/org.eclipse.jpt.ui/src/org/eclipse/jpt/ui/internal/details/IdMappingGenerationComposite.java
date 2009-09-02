/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.GeneratorComposite.GeneratorBuilder;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This panel is partially a copy of the GenerationComposite panel. The difference
 * is that this panel includes the Generated Value composite.  When a table
 * or sequence generator is added, we set the "name" to be the same as the 
 * generated value "generator" if it already exists
 * 
 * Here is the layout of this pane:
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
 * @version 2.2
 * @since 1.0
 */
public class IdMappingGenerationComposite extends FormPane<IdMapping>
{

	//These are built to stand alone because we do not want the panels to collapse just
	//because the generator is removed either in the source or using the check box in the UI.  
	//We don't want these to be built on the model generator properties.
	private WritablePropertyValueModel<Boolean> sequenceGeneratorExpansionStateHolder;
	private WritablePropertyValueModel<Boolean> tableGeneratorExpansionStateHolder;


	/**
	 * Creates a new <code>GenerationComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public IdMappingGenerationComposite(FormPane<? extends IdMapping> parentPane,
	                           Composite parent)
	{
		super(parentPane, parent, false);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.sequenceGeneratorExpansionStateHolder = new SimplePropertyValueModel<Boolean>(Boolean.FALSE);
		this.tableGeneratorExpansionStateHolder    = new SimplePropertyValueModel<Boolean>(Boolean.FALSE);
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.sequenceGeneratorExpansionStateHolder.setValue(Boolean.valueOf(getSubject() != null && getSubject().getGeneratorContainer().getSequenceGenerator() != null));
		this.tableGeneratorExpansionStateHolder   .setValue(Boolean.valueOf(getSubject() != null && getSubject().getGeneratorContainer().getTableGenerator() != null));
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Primary Key Generation section
		container = addCollapsableSection(
			container,
			JptUiDetailsMessages.IdMappingComposite_primaryKeyGenerationSection,
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		// Primary Key Generation check box
		Button primaryKeyGenerationCheckBox = addCheckBox(
			container,
			JptUiDetailsMessages.IdMappingComposite_primaryKeyGenerationCheckBox,
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

		PropertyValueModel<GeneratorContainer> generatorHolder = buildGeneratorContainer();
		// Table Generator pane
		initializeTableGeneratorPane(addSubPane(container, 10), generatorHolder);

		// Sequence Generator pane
		initializeSequenceGeneratorPane(addSubPane(container, 10), generatorHolder);
	}

	private WritablePropertyValueModel<Boolean> buildPrimaryKeyGenerationHolder() {
		return new PropertyAspectAdapter<IdMapping, Boolean>(getSubjectHolder(), IdMapping.GENERATED_VALUE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getGeneratedValue() != null);
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value.booleanValue() && (this.subject.getGeneratedValue() == null)) {
					this.subject.addGeneratedValue();
				}
				else if (!value.booleanValue() && (this.subject.getGeneratedValue() != null)) {
					this.subject.removeGeneratedValue();
				}
			}
		};
	}
	
	private PropertyValueModel<GeneratorContainer> buildGeneratorContainer() {
		return new PropertyAspectAdapter<IdMapping, GeneratorContainer>(getSubjectHolder()) {
			@Override
			protected GeneratorContainer buildValue_() {
				return this.subject.getGeneratorContainer();
			}
		};
	}

	private void initializeSequenceGeneratorPane(Composite container, PropertyValueModel<GeneratorContainer> generatorHolder) {

		// Sequence Generator sub-section
		container = addCollapsableSubSection(
			container,
			JptUiDetailsMessages.IdMappingComposite_sequenceGeneratorSection,
			this.sequenceGeneratorExpansionStateHolder
		);

		// Sequence Generator check box
		Button sequenceGeneratorCheckBox = addCheckBox(
			container,
			JptUiDetailsMessages.IdMappingComposite_sequenceGeneratorCheckBox,
			buildSequenceGeneratorBooleanHolder(generatorHolder),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR
		);

		// Sequence Generator pane
		this.buildSequenceGeneratorComposite(
			container, 
			buildSequenceGeneratorHolder(generatorHolder),
			buildSequenceGeneratorBuilder(generatorHolder),
			0, 
			sequenceGeneratorCheckBox.getBorderWidth() + 16);
	}

	private WritablePropertyValueModel<Boolean> buildSequenceGeneratorBooleanHolder(PropertyValueModel<GeneratorContainer> generatorHolder) {
		return new PropertyAspectAdapter<GeneratorContainer, Boolean>(generatorHolder, GeneratorContainer.SEQUENCE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getSequenceGenerator() != null);
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value.booleanValue() && (this.subject.getSequenceGenerator() == null)) {

					SequenceGenerator sequenceGenerator = this.subject.addSequenceGenerator();
					GeneratedValue generatedValue = getSubject().getGeneratedValue();

					if ((generatedValue != null) &&
					    (generatedValue.getGenerator() != null))
					{
						sequenceGenerator.setName(generatedValue.getGenerator());
					}
				}
				else if (!value.booleanValue() && (this.subject.getSequenceGenerator() != null)) {
					this.subject.removeSequenceGenerator();
				}
			}
		};
	}
	
	protected SequenceGeneratorComposite buildSequenceGeneratorComposite(
			Composite container, 
			PropertyValueModel<SequenceGenerator> sequenceGeneratorHolder,
			GeneratorBuilder<SequenceGenerator> generatorBuilder,
			int topMargin,
			int leftMargin) {

		return new SequenceGeneratorComposite(
			this,
			sequenceGeneratorHolder,
			this.addSubPane(container, topMargin, leftMargin),
			generatorBuilder
		);
	}

	private PropertyValueModel<SequenceGenerator> buildSequenceGeneratorHolder(PropertyValueModel<GeneratorContainer> generatorHolder) {
		return new PropertyAspectAdapter<GeneratorContainer, SequenceGenerator>(generatorHolder, GeneratorContainer.SEQUENCE_GENERATOR_PROPERTY) {
			@Override
			protected SequenceGenerator buildValue_() {
				return this.subject.getSequenceGenerator();
			}
		};
	}
	private GeneratorBuilder<SequenceGenerator> buildSequenceGeneratorBuilder(final PropertyValueModel<GeneratorContainer> generatorHolder) {
		return new GeneratorBuilder<SequenceGenerator>() {
			public SequenceGenerator addGenerator() {
				return generatorHolder.getValue().addSequenceGenerator();
			}
		};
	}
	
	private void initializeTableGeneratorPane(Composite container, PropertyValueModel<GeneratorContainer> generatorHolder) {

		// Table Generator sub-section
		container = addCollapsableSubSection(
			container,
			JptUiDetailsMessages.IdMappingComposite_tableGeneratorSection,
			this.tableGeneratorExpansionStateHolder
		);

		Button tableGeneratorCheckBox = addCheckBox(
			container,
			JptUiDetailsMessages.IdMappingComposite_tableGeneratorCheckBox,
			buildTableGeneratorBooleanHolder(generatorHolder),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR
		);

		// Sequence Generator pane
		this.buildTableGeneratorComposite(
			container, 
			buildTableGeneratorHolder(generatorHolder),
			buildTableGeneratorBuilder(generatorHolder),
			0, 
			tableGeneratorCheckBox.getBorderWidth() + 16);
	}	
	
	protected TableGeneratorComposite buildTableGeneratorComposite(
			Composite container, 
			PropertyValueModel<TableGenerator> tableGeneratorHolder,
			GeneratorBuilder<TableGenerator> generatorBuilder,
			int topMargin,
			int leftMargin) {

		return new TableGeneratorComposite(
			this,
			tableGeneratorHolder,
			this.addSubPane(container, topMargin, leftMargin),
			generatorBuilder
		);
	}

	private PropertyValueModel<TableGenerator> buildTableGeneratorHolder(PropertyValueModel<GeneratorContainer> generatorHolder) {
		return new PropertyAspectAdapter<GeneratorContainer, TableGenerator>(generatorHolder, GeneratorContainer.TABLE_GENERATOR_PROPERTY) {
			@Override
			protected TableGenerator buildValue_() {
				return this.subject.getTableGenerator();
			}
		};
	}

	private GeneratorBuilder<TableGenerator> buildTableGeneratorBuilder(final PropertyValueModel<GeneratorContainer> generatorHolder) {
		return new GeneratorBuilder<TableGenerator>() {
			public TableGenerator addGenerator() {
				return generatorHolder.getValue().addTableGenerator();
			}
		};
	}

 	private WritablePropertyValueModel<Boolean> buildTableGeneratorBooleanHolder(PropertyValueModel<GeneratorContainer> generatorHolder) {
		return new PropertyAspectAdapter<GeneratorContainer, Boolean>(generatorHolder, GeneratorContainer.TABLE_GENERATOR_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getTableGenerator() != null);
			}

			@Override
			protected void setValue_(Boolean value) {

				if (value.booleanValue() && (this.subject.getTableGenerator() == null)) {

					TableGenerator tableGenerator = this.subject.addTableGenerator();
					GeneratedValue generatedValue = getSubject().getGeneratedValue();

					if ((generatedValue != null) &&
					    (generatedValue.getGenerator() != null))
					{
						tableGenerator.setName(generatedValue.getGenerator());
					}
				}
				else if (!value.booleanValue() && (this.subject.getTableGenerator() != null)) {
					this.subject.removeTableGenerator();
				}
			}
		};
	}

}