/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
			buildGeneratorNamesModel(),
			buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY
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
				retrieveGeneratedValue().setSpecifiedStrategy(value);
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
				retrieveGeneratedValue().setSpecifiedGenerator(value);
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setSpecifiedGenerator(value);
			}
		};
	}

	protected ListValueModel<String> buildGeneratorNamesModel() {
		return new TransformationListValueModel<Generator, String>(this.buildSortedGeneratorsModel()) {
			@Override
			protected String transformItem_(Generator item) {
				return item.getName();
			}
		};
	}

	protected ListValueModel<Generator> buildSortedGeneratorsModel() {
		return new SortedListValueModelAdapter<Generator>(this.buildGeneratorsModel(), GENERATOR_COMPARATOR);
	}

	protected static final Comparator<Generator> GENERATOR_COMPARATOR = new Comparator<Generator>() {
		public int compare(Generator generator1, Generator generator2) {
			return generator1.getName().compareTo(generator2.getName());
		}
	};

	protected CollectionValueModel<Generator> buildGeneratorsModel() {
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
				return getSubject().getPersistenceUnit();
			}
		};
	}

	private GeneratedValue retrieveGeneratedValue() {
		GeneratedValue generatedValue = getSubject().getGeneratedValue();

		if (generatedValue == null) {
			generatedValue = getSubject().addGeneratedValue();
		}
		return generatedValue;
	}
}
