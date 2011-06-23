/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.GeneratedValue;
import org.eclipse.jpt.jpa.core.context.GenerationType;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                 --------------------------------------------------------- |
 * | Strategy:       | I                                                   |v| |
 * |                 --------------------------------------------------------- |
 * |                 --------------------------------------------------------- |
 * | Generator Name: | I                                                   |v| |
 * |                 --------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IdMapping
 * @see GeneratedValue
 * @see IdMappingGenerationComposite - The parent container
 *
 * @version 2.3
 * @since 1.0
 */
public class GeneratedValueComposite extends Pane<IdMapping>
{

	/**
	 * Creates a new <code>GeneratedValueComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public GeneratedValueComposite(Pane<? extends IdMapping> parentPane,
	 	                            Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Strategy widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.GeneratedValueComposite_strategy,
			addStrategyComboViewer(container),
			JpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY
		);

		addLabeledEditableCombo(
			container,
			JptUiDetailsMessages.GeneratedValueComposite_generatorName,
			buildSortedGeneraterNamesModel(),
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_GENERATED_VALUE_GENERATOR_NAME
		);
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
				return buildDisplayString(
					JptUiDetailsMessages.class,
					GeneratedValueComposite.this,
					value
				);
			}

			@Override
			protected GenerationType getValue() {
				return getSubject().getSpecifiedStrategy();
			}

			@Override
			protected void setValue(GenerationType value) {
				getGeneratedValueForUpdate().setSpecifiedStrategy(value);
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
	
	protected final WritablePropertyValueModel<String> buildGeneratorNameHolder() {
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
		return new FilteringCollectionValueModel<String>(this.buildGeneratorNamesModel_(), NON_EMPTY_STRING_FILTER);
	}

	protected static final Filter<String> NON_EMPTY_STRING_FILTER =
		new Filter<String>() {
			public boolean accept(String string) {
				return StringTools.stringIsNotEmpty(string);
			}
		};

	protected ListValueModel<String> buildGeneratorNamesModel_() {
		return new TransformationListValueModel<Generator, String>(this.buildGeneratorsModel()) {
			@Override
			protected String transformItem_(Generator generator) {
				return generator.getName();
			}
		};
	}

	protected ListValueModel<Generator> buildGeneratorsModel() {
		return new ItemPropertyListValueModelAdapter<Generator>(this.buildGeneratorsModel_(), JpaNamedContextNode.NAME_PROPERTY);
	}

	protected CollectionValueModel<Generator> buildGeneratorsModel_() {
		return new CollectionAspectAdapter<PersistenceUnit, Generator>(this.buildPersistenceUnitModel(), PersistenceUnit.GENERATORS_COLLECTION) {
			@Override
			protected Iterator<Generator> iterator_() {
				return this.subject.generators();
			}
			@Override
			protected int size_() {
				return this.subject.generatorsSize();
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
