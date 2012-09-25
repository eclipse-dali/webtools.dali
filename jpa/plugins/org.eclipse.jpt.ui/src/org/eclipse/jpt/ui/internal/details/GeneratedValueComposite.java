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

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
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
			buildGeneratorNameListHolder(),
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

	/**
	 * Use the CompositeListValueModel even though it only contains 1 list value model
	 * This prevents the combo items from being reset when the list of generators
	 * hasn't really changed.  This keeps the cursor from going back to the beginning
	 * of the list every time the generator name is edited in the combo.
	 * AbstractComboModelAdapter.listChanged() does not handle this case well, 
	 * the CompositeListValueModel does handle listChanged events well.
	 */
	protected ListValueModel<String> buildGeneratorNameListHolder() {
		java.util.List<ListValueModel<String>> list = new ArrayList<ListValueModel<String>>();
		list.add(new ListAspectAdapter<PersistenceUnit, String>(
			buildPersistenceUnitHolder(),
			PersistenceUnit.GENERATORS_LIST)
		{
			@Override
			protected ListIterator<String> listIterator_() {
				return CollectionTools.listIterator(ArrayTools.sort(this.subject.uniqueGeneratorNames()));
			}
		});
		return new CompositeListValueModel<ListValueModel<String>, String>(list);
	}

	protected PropertyValueModel<PersistenceUnit> buildPersistenceUnitHolder() {
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
