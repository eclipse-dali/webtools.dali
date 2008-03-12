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
import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.listeners.SWTListChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
 * @see GenerationComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GeneratedValueComposite extends AbstractFormPane<IdMapping>
{
	private PropertyChangeListener generatedValuePropertyChangeListener;
	private CCombo generatorNameCombo;
	private PropertyChangeListener generatorNamePropertyChangeListener;
	private ListChangeListener generatorsListChangeListener;

	/**
	 * Creates a new <code>GeneratedValueComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public GeneratedValueComposite(AbstractFormPane<? extends IdMapping> parentPane,
	 	                            Composite parent) {

		super(parentPane, parent);
	}

	private PropertyChangeListener buildGeneratedValuePropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(
			buildGeneratedValuePropertyChangeListener_()
		);
	}

	private PropertyChangeListener buildGeneratedValuePropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				disengageListeners((GeneratedValue) e.oldValue());
				engageListeners((GeneratedValue) e.newValue());

				if (!isPopulating()) {
					setPopulating(true);

					try {
						populateGeneratorNameCombo();
					}
					finally {
						setPopulating(false);
					}
				}
			}
		};
	}

	private ModifyListener buildGeneratorNameModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}

				String generatorName = ((CCombo) e.getSource()).getText();
				GeneratedValue generatedValue = subject().getGeneratedValue();

				if (StringTools.stringIsEmpty(generatorName)) {

					if ((generatedValue == null) ||
					    StringTools.stringIsEmpty(generatedValue.getGenerator()))
					{
						return;
					}

					generatorName = null;
				}

				retrieveGeneratedValue().setSpecifiedGenerator(generatorName);
			}
		};
	}

	private PropertyChangeListener buildGeneratorNamePropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(
			buildGeneratorNamePropertyChangeListener_()
		);
	}

	private PropertyChangeListener buildGeneratorNamePropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				if (!isPopulating()) {
					setPopulating(true);

					try {
						populateGeneratorName();
					}
					finally {
						setPopulating(false);
					}
				}
			}
		};
	}

	private ListChangeListener buildGeneratorsListChangeListener() {
		return new SWTListChangeListenerWrapper(
			buildGeneratorsListChangeListener_());
	}

	private ListChangeListener buildGeneratorsListChangeListener_() {
		return new ListChangeAdapter() {
			@Override
			// should only have to listen to this event - others aren't created
			public void listChanged(ListChangeEvent event) {
				if (! isPopulating()) {
					setPopulating(true);

					try {
						populateGeneratorChoices();
					}
					finally {
						setPopulating(false);
					}
				}

			}
		};
	}

	private PropertyValueModel<GeneratedValue> buildGeneratorValueHolder() {
		return new PropertyAspectAdapter<IdMapping, GeneratedValue>(getSubjectHolder(), IdMapping.GENERATED_VALUE_PROPERTY) {
			@Override
			protected GeneratedValue buildValue_() {
				return subject().getGeneratedValue();
			}
		};
	}

	private EnumFormComboViewer<GeneratedValue, GenerationType> buildStrategyComboViewer(Composite parent) {

		return new EnumFormComboViewer<GeneratedValue, GenerationType>(this, buildGeneratorValueHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(GeneratedValue.DEFAULT_STRATEGY_PROPERTY);
				propertyNames.add(GeneratedValue.SPECIFIED_STRATEGY_PROPERTY);
			}

			@Override
			protected GenerationType[] choices() {
				return GenerationType.values();
			}

			@Override
			protected GenerationType defaultValue() {
				return subject().getDefaultStrategy();
			}

			@Override
			protected String displayString(GenerationType value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					GeneratedValueComposite.this,
					value
				);
			}

			@Override
			protected GenerationType getValue() {
				return subject().getSpecifiedStrategy();
			}

			@Override
			protected void setValue(GenerationType value) {
				retrieveGeneratedValue().setSpecifiedStrategy(value);
			}
		};
	}

	private void disengageListeners(GeneratedValue generatedValue) {

		if (generatedValue != null) {

			generatedValue.removePropertyChangeListener(
				GeneratedValue.DEFAULT_GENERATOR_PROPERTY,
				generatorNamePropertyChangeListener
			);

			generatedValue.removePropertyChangeListener(
				GeneratedValue.SPECIFIED_GENERATOR_PROPERTY,
				generatorNamePropertyChangeListener
			);

			generatedValue.persistenceUnit().removeListChangeListener(
				PersistenceUnit.GENERATORS_LIST,
				generatorsListChangeListener
			);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners(IdMapping subject) {
		super.disengageListeners(subject);

		if (subject != null) {
			subject.removePropertyChangeListener(
				IdMapping.GENERATED_VALUE_PROPERTY,
				generatedValuePropertyChangeListener
			);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateGeneratorNameCombo();
	}

	private void engageListeners(GeneratedValue generatedValue) {

		if (generatedValue != null) {

			generatedValue.addPropertyChangeListener(
				GeneratedValue.DEFAULT_GENERATOR_PROPERTY,
				generatorNamePropertyChangeListener
			);

			generatedValue.addPropertyChangeListener(
				GeneratedValue.SPECIFIED_GENERATOR_PROPERTY,
				generatorNamePropertyChangeListener
			);

			generatedValue.persistenceUnit().addListChangeListener(
				PersistenceUnit.GENERATORS_LIST,
				generatorsListChangeListener
			);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners(IdMapping subject) {
		super.engageListeners(subject);

		if (subject != null) {
			subject.addPropertyChangeListener(
				IdMapping.GENERATED_VALUE_PROPERTY,
				generatedValuePropertyChangeListener
			);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();

		generatedValuePropertyChangeListener = buildGeneratedValuePropertyChangeListener();
		generatorNamePropertyChangeListener  = buildGeneratorNamePropertyChangeListener();
		generatorsListChangeListener = buildGeneratorsListChangeListener();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Strategy widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.GeneratedValueComposite_strategy,
			buildStrategyComboViewer(container),
			JpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY
		);

		// Generator Name widgets
		generatorNameCombo = buildLabeledEditableCCombo(
			container,
			JptUiMappingsMessages.GeneratedValueComposite_generatorName,
			buildGeneratorNameModifyListener(),
			JpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY
		);

		generatorNameCombo.add(JptUiMappingsMessages.TableComposite_defaultEmpty);
	}

	private void populateGeneratorChoices() {
		if (subject() == null) {
			this.generatorNameCombo.setItems(new String[0]);
		}
		else {
			this.generatorNameCombo.setItems(this.sortedUniqueGeneratorNames());
		}
	}

	private void populateGeneratorName() {
		if (subject() == null) {
			this.generatorNameCombo.setText("");
		}
		else {
			GeneratedValue generatedValue = subject().getGeneratedValue();

			if (generatedValue == null) {
				this.generatorNameCombo.setText("");
			}
			else {
				String generatorName = generatedValue.getGenerator();

				if (StringTools.stringIsEmpty(generatorName)) {
					this.generatorNameCombo.setText("");
				}
				else if (!this.generatorNameCombo.getText().equals(generatorName)) {
					this.generatorNameCombo.setText(generatorName);
				}
			}
		}
	}

	private void populateGeneratorNameCombo() {
		populateGeneratorName();
		populateGeneratorChoices();
	}

	private GeneratedValue retrieveGeneratedValue() {
		GeneratedValue generatedValue = subject().getGeneratedValue();

		if (generatedValue == null) {
			setPopulating(true);

			try {
				generatedValue = subject().addGeneratedValue();
			}
			finally {
				setPopulating(false);
			}
		}

		return generatedValue;
	}

	private String[] sortedUniqueGeneratorNames() {
		return CollectionTools.array(
			CollectionTools.sortedSet(
				new TransformationIterator<Generator, String>(subject().persistenceUnit().allGenerators()) {
					@Override
					protected String transform(Generator next) {
						return next.getName();
					}
				}),
			new String[0]);
	}
}