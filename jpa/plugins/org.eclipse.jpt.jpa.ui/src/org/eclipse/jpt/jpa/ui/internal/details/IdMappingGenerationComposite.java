/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.GeneratedValue;
import org.eclipse.jpt.jpa.core.context.GenerationType;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.TableGenerator;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.GeneratorComposite.GeneratorBuilder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

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
public class IdMappingGenerationComposite extends Pane<IdMapping>
{

	//These are built to stand alone because we do not want the panels to collapse just
	//because the generator is removed either in the source or using the check box in the UI.  
	//We don't want these to be built on the model generator properties.
	private ModifiablePropertyValueModel<Boolean> sequenceGeneratorExpansionStateHolder;
	private ModifiablePropertyValueModel<Boolean> tableGeneratorExpansionStateHolder;


	/**
	 * Creates a new <code>GenerationComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public IdMappingGenerationComposite(Pane<? extends IdMapping> parentPane,
	                           Composite parent)
	{
		super(parentPane, parent);
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
	protected Composite addComposite(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ID_MAPPING_COMPOSITE_PRIMARY_KEY_GENERATION_SECTION);

		Composite subPane = this.addSubPane(section, 2, 0, 0, 0, 0);
		section.setClient(subPane);
		return subPane;
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Primary Key Generation check box
		Button primaryKeyGenerationCheckBox = addCheckBox(
			container,
			JptJpaUiDetailsMessages.ID_MAPPING_COMPOSITE_PRIMARY_KEY_GENERATION_CHECK_BOX,
			buildPrimaryKeyGenerationHolder(),
			JpaHelpContextIds.MAPPING_PRIMARY_KEY_GENERATION
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		primaryKeyGenerationCheckBox.setLayoutData(gridData);

		// Strategy widgets
		Label strategyLabel = addLabel(container, JptJpaUiDetailsMessages.GENERATED_VALUE_COMPOSITE_STRATEGY);
		gridData = new GridData();
		gridData.horizontalIndent = primaryKeyGenerationCheckBox.getBorderWidth() + 16;
		strategyLabel.setLayoutData(gridData);
		this.addStrategyComboViewer(container);

		Label nameLabel = this.addLabel(container, JptJpaUiDetailsMessages.GENERATED_VALUE_COMPOSITE_GENERATOR_NAME);
		gridData = new GridData();
		gridData.horizontalIndent = primaryKeyGenerationCheckBox.getBorderWidth() + 16;
		nameLabel.setLayoutData(gridData);
		this.addEditableCombo(
			container,
			buildSortedGeneraterNamesModel(),
			buildGeneratorNameHolder(),
			TransformerTools.<String>objectToStringTransformer(),
			JpaHelpContextIds.MAPPING_GENERATED_VALUE_GENERATOR_NAME
		);

		PropertyValueModel<GeneratorContainer> generatorHolder = buildGeneratorContainer();
		// Table Generator pane
		Composite tableGeneratorComposite = initializeTableGeneratorCollapsibleSection(container, generatorHolder);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tableGeneratorComposite.setLayoutData(gridData);

		// Sequence Generator pane
		Composite sequenceGeneratorComposite = initializeSequenceGeneratorCollapsibleSection(container, generatorHolder);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		sequenceGeneratorComposite.setLayoutData(gridData);
	}

	private ModifiablePropertyValueModel<Boolean> buildPrimaryKeyGenerationHolder() {
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

	protected Section initializeSequenceGeneratorCollapsibleSection(Composite container, PropertyValueModel<GeneratorContainer> generatorHolder) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TWISTIE);
		section.setText(JptJpaUiDetailsMessages.ID_MAPPING_COMPOSITE_SEQUENCE_GENERATOR_SECTION);
		SWTBindingTools.bindExpandedState(this.sequenceGeneratorExpansionStateHolder, section);

		section.setClient(this.initializeSequenceGeneratorPane(section, generatorHolder));

		return section;
	}

	private Composite initializeSequenceGeneratorPane(Composite container, PropertyValueModel<GeneratorContainer> generatorHolder) {
		Composite client = this.getWidgetFactory().createComposite(container);
		client.setLayout(new GridLayout(1, false));
		client.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Sequence Generator check box
		Button sequenceGeneratorCheckBox = addCheckBox(
				client,
			JptJpaUiDetailsMessages.ID_MAPPING_COMPOSITE_SEQUENCE_GENERATOR_CHECK_BOX,
			buildSequenceGeneratorBooleanHolder(generatorHolder),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR
		);

		// Sequence Generator pane
		SequenceGeneratorComposite sequenceGeneratorComposite = this.buildSequenceGeneratorComposite(
				client, 
			buildSequenceGeneratorHolder(generatorHolder),
			buildSequenceGeneratorBuilder(generatorHolder));
			
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = sequenceGeneratorCheckBox.getBorderWidth() + 16;
		sequenceGeneratorComposite.getControl().setLayoutData(gridData);

		return client;
	}

	private ModifiablePropertyValueModel<Boolean> buildSequenceGeneratorBooleanHolder(PropertyValueModel<GeneratorContainer> generatorHolder) {
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
			GeneratorBuilder<SequenceGenerator> generatorBuilder) {

		return new SequenceGeneratorComposite(
			this,
			sequenceGeneratorHolder,
			container,
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

	protected Section initializeTableGeneratorCollapsibleSection(Composite container, PropertyValueModel<GeneratorContainer> generatorHolder) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TWISTIE);
		section.setText(JptJpaUiDetailsMessages.ID_MAPPING_COMPOSITE_TABLE_GENERATOR_SECTION);
		SWTBindingTools.bindExpandedState(this.tableGeneratorExpansionStateHolder, section);

		section.setClient(this.initializeTableGeneratorPane(section, generatorHolder));

		return section;
	}

	private Composite initializeTableGeneratorPane(Composite container, PropertyValueModel<GeneratorContainer> generatorHolder) {
		Composite client = this.getWidgetFactory().createComposite(container);
		client.setLayout(new GridLayout(1, false));
		client.setLayoutData(new GridData(GridData.FILL_BOTH));

		Button tableGeneratorCheckBox = addCheckBox(
				client,
			JptJpaUiDetailsMessages.ID_MAPPING_COMPOSITE_TABLE_GENERATOR_CHECK_BOX,
			buildTableGeneratorBooleanHolder(generatorHolder),
			JpaHelpContextIds.MAPPING_TABLE_GENERATOR
		);

		// Table Generator pane
		TableGeneratorComposite tableGeneratorComposite = this.buildTableGeneratorComposite(
				client, 
			buildTableGeneratorHolder(generatorHolder),
			buildTableGeneratorBuilder(generatorHolder));

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = tableGeneratorCheckBox.getBorderWidth() + 16;
		tableGeneratorComposite.getControl().setLayoutData(gridData);
		
		return client;
	}	
	
	protected TableGeneratorComposite buildTableGeneratorComposite(
			Composite container, 
			PropertyValueModel<TableGenerator> tableGeneratorHolder,
			GeneratorBuilder<TableGenerator> generatorBuilder) {

		return new TableGeneratorComposite(
			this,
			tableGeneratorHolder,
			container,
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

 	private ModifiablePropertyValueModel<Boolean> buildTableGeneratorBooleanHolder(PropertyValueModel<GeneratorContainer> generatorHolder) {
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
 
	private EnumFormComboViewer<GeneratedValue, GenerationType> addStrategyComboViewer(Composite parent) {
		return new EnumFormComboViewer<GeneratedValue, GenerationType>(this, buildGeneratedValueHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(GeneratedValue.DEFAULT_STRATEGY_PROPERTY);
				propertyNames.add(GeneratedValue.SPECIFIED_STRATEGY_PROPERTY);
			}

			@Override
			protected GenerationType[] getChoices() {
				return GenerationType.values();
			}

			@Override
			protected GenerationType getDefaultValue() {
				return getSubject().getDefaultStrategy();
			}

			@Override
			protected String displayString(GenerationType value) {
				switch (value) {
					case AUTO :
						return JptJpaUiDetailsMessages.GENERATED_VALUE_COMPOSITE_AUTO;
					case IDENTITY :
						return JptJpaUiDetailsMessages.GENERATED_VALUE_COMPOSITE_IDENTITY;
					case SEQUENCE :
						return JptJpaUiDetailsMessages.GENERATED_VALUE_COMPOSITE_SEQUENCE;
					case TABLE :
						return JptJpaUiDetailsMessages.GENERATED_VALUE_COMPOSITE_TABLE;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected GenerationType getValue() {
				return getSubject().getSpecifiedStrategy();
			}

			@Override
			protected void setValue(GenerationType value) {
				getGeneratedValueForUpdate().setSpecifiedStrategy(value);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY;
			}
		};
	}

	private PropertyValueModel<GeneratedValue> buildGeneratedValueHolder() {
		return new PropertyAspectAdapter<IdMapping, GeneratedValue>(getSubjectHolder(), IdMapping.GENERATED_VALUE_PROPERTY) {
			@Override
			protected GeneratedValue buildValue_() {
				return getSubject().getGeneratedValue();
			}
		};
	}
	
	protected final ModifiablePropertyValueModel<String> buildGeneratorNameHolder() {
		return new PropertyAspectAdapter<GeneratedValue, String>(buildGeneratedValueHolder(), GeneratedValue.SPECIFIED_GENERATOR_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getSpecifiedGenerator();
			}

			@Override
			public void setValue(String value) {
				if (this.subject != null) {
					setValue_(value);
					return;
				}
				if (value.length() == 0) {
					return;
				}
				getGeneratedValueForUpdate().setSpecifiedGenerator(value);
			}

			@Override
			protected void setValue_(String value) {
				if (value !=null && value.length() == 0) {
					value = null;
				}
				this.subject.setSpecifiedGenerator(value);
			}
		};
	}

	protected ListValueModel<String> buildSortedGeneraterNamesModel() {
		return new SortedListValueModelAdapter<String>(this.buildUniqueGeneratorNamesModel());
	}

	protected CollectionValueModel<String> buildUniqueGeneratorNamesModel() {
		return new SetCollectionValueModel<String>(this.buildGeneratorNamesModel());
	}

	protected CollectionValueModel<String> buildGeneratorNamesModel() {
		return new FilteringCollectionValueModel<String>(this.buildGeneratorNamesModel_(), StringTools.IS_NOT_BLANK);
	}

	protected ListValueModel<String> buildGeneratorNamesModel_() {
		return new TransformationListValueModel<Generator, String>(this.buildGeneratorsModel()) {
			@Override
			protected String transformItem_(Generator generator) {
				return generator.getName();
			}
		};
	}

	protected ListValueModel<Generator> buildGeneratorsModel() {
		return new ItemPropertyListValueModelAdapter<Generator>(this.buildGeneratorsModel_(), JpaNamedContextModel.NAME_PROPERTY);
	}

	protected CollectionValueModel<Generator> buildGeneratorsModel_() {
		return new CollectionAspectAdapter<PersistenceUnit, Generator>(this.buildPersistenceUnitModel(), PersistenceUnit.GENERATORS_COLLECTION) {
			@Override
			protected Iterable<Generator> getIterable() {
				return this.subject.getGenerators();
			}
			@Override
			protected int size_() {
				return this.subject.getGeneratorsSize();
			}
		};
	}

	protected PropertyValueModel<PersistenceUnit> buildPersistenceUnitModel() {
		return new PropertyAspectAdapter<IdMapping, PersistenceUnit>(getSubjectHolder()) {
			@Override
			protected PersistenceUnit buildValue_() {
				return this.subject.getPersistenceUnit();
			}
		};
	}

	/* CU private */ GeneratedValue getGeneratedValueForUpdate() {
		GeneratedValue generatedValue = getSubject().getGeneratedValue();

		if (generatedValue == null) {
			generatedValue = getSubject().addGeneratedValue();
		}
		return generatedValue;
	}
}