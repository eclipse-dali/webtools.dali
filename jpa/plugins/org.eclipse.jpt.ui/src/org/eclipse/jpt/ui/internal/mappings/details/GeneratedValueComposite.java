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
import org.eclipse.jpt.core.internal.context.base.GenerationType;
import org.eclipse.jpt.core.internal.context.base.IGeneratedValue;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumComboViewer;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
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
 * @see IIdMapping
 * @see IGeneratedValue
 * @see GenerationComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GeneratedValueComposite extends AbstractFormPane<IIdMapping>
{
	private PropertyChangeListener generatedValueChangeListener;
	private PropertyChangeListener generatorNameChangeListener;
	private CCombo generatorNameCombo;
	private PropertyChangeListener subjectChangeListener;

	/**
	 * Creates a new <code>GeneratedValueComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public GeneratedValueComposite(AbstractFormPane<? extends IIdMapping> parentPane,
	 	                            Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(IGeneratedValue.SPECIFIED_GENERATOR_PROPERTY);
	}

	private PropertyChangeListener buildGeneratedValueChangeListener() {
		return new SWTPropertyChangeListenerWrapper(buildGeneratedValueChangeListener_());
	}
	
	private PropertyChangeListener buildGeneratedValueChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {

				IGeneratedValue oldValue = (IGeneratedValue) e.oldValue();
				IGeneratedValue newValue = (IGeneratedValue) e.newValue();

				uninstallGeneratedValueListeners(oldValue);
				repopulate();
				installGeneratedValueListeners(newValue);
			}
		};
	}

	private PropertyChangeListener buildGeneratorNameChangeListener() {
		return new SWTPropertyChangeListenerWrapper(buildGeneratorNameChangeListener_());
	}
	
	private PropertyChangeListener buildGeneratorNameChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				if (isPopulating()) {
					return;
				}

				populateGeneratorName();
			}
		};
	}

	private CCombo buildGeneratorNameCombo(Composite parent) {

		CCombo combo = buildCombo(parent);
		combo.add(JptUiMappingsMessages.TableComposite_defaultEmpty);
		combo.addModifyListener(buildGeneratorNameModifyListener());
		return combo;
	}

	private ModifyListener buildGeneratorNameModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}

				String generatorName = ((CCombo) e.getSource()).getText();
				IGeneratedValue generatedValue = subject().getGeneratedValue();

				if (StringTools.stringIsEmpty(generatorName)) {

					if ((generatedValue == null) ||
					    StringTools.stringIsEmpty(generatedValue.getGenerator()))
					{
						return;
					}

					generatorName = null;
				}

				if (generatedValue == null) {
					generatedValue = subject().addGeneratedValue();
				}

				generatedValue.setSpecifiedGenerator(generatorName);
			}
		};
	}

	private PropertyValueModel<IGeneratedValue> buildGeneratorValueHolder() {
		return new PropertyAspectAdapter<IIdMapping, IGeneratedValue>(getSubjectHolder(), IIdMapping.GENERATED_VALUE_PROPERTY) {
			@Override
			protected IGeneratedValue buildValue_() {
				return subject().getGeneratedValue();
			}
		};
	}

	private EnumComboViewer<IGeneratedValue, GenerationType> buildStrategyComboViewer(Composite parent) {
		return new EnumComboViewer<IGeneratedValue, GenerationType>(this, buildGeneratorValueHolder(), parent) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IGeneratedValue.DEFAULT_STRATEGY_PROPERTY);
				propertyNames.add(IGeneratedValue.SPECIFIED_STRATEGY_PROPERTY);
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
				IGeneratedValue generatedValue = subject();

				if (generatedValue == null) {
					generatedValue = GeneratedValueComposite.this.subject().addGeneratedValue();
				}

				subject().setSpecifiedStrategy(value);
			}
		};
	}

	private PropertyChangeListener buildSubjectChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				uninstallListeners((IIdMapping) e.oldValue());
				installListeners((IIdMapping) e.newValue());
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners() {
		super.disengageListeners();
		getSubjectHolder().removePropertyChangeListener(PropertyValueModel.VALUE, subjectChangeListener);
		uninstallListeners(subject());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateGeneratorNameCombo();
	}

//	private IGeneratorRepository getGeneratorRepository() {
//		return NullGeneratorRepository.instance(); //this.id.getJpaProject().getPlatform().generatorRepository(this.id.typeMapping().getPersistentType());
//	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners() {
		super.engageListeners();
		getSubjectHolder().addPropertyChangeListener(PropertyValueModel.VALUE, subjectChangeListener);
		installListeners(subject());
	}

	@Override
	protected void initialize() {
		super.initialize();

		subjectChangeListener        = buildSubjectChangeListener();
		generatedValueChangeListener = buildGeneratedValueChangeListener();
		generatorNameChangeListener  = buildGeneratorNameChangeListener();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Strategy widgets
		EnumComboViewer<IGeneratedValue, GenerationType> strategyComboViewer =
			buildStrategyComboViewer(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.GeneratedValueComposite_strategy,
			strategyComboViewer.getControl(),
			IJpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY
		);

		// Generator Name widgets
		generatorNameCombo = buildGeneratorNameCombo(container);

		// Note: The combo's parent is a container fixing the issue with the
		// border not being painted
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.GeneratedValueComposite_generatorName,
			generatorNameCombo.getParent(),
			IJpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY
		);

		// TODO
		// buildGeneratorNameSelectionButton( this);
	}

	private void installGeneratedValueListeners(IGeneratedValue generatedValue) {
		if (generatedValue != null) {
			generatedValue.addPropertyChangeListener(IGeneratedValue.DEFAULT_GENERATOR_PROPERTY,   generatorNameChangeListener);
			generatedValue.addPropertyChangeListener(IGeneratedValue.SPECIFIED_GENERATOR_PROPERTY, generatorNameChangeListener);
		}
	}

	private void installListeners(IIdMapping idMapping) {
		if (idMapping != null) {
			idMapping.addPropertyChangeListener(IIdMapping.GENERATED_VALUE_PROPERTY, generatedValueChangeListener);
			installGeneratedValueListeners(idMapping.getGeneratedValue());
		}
	}

	private void populateGeneratorName() {
		if (subject() == null) {
			this.generatorNameCombo.setText("");
		}
		else {
			IGeneratedValue generatedValue = subject().getGeneratedValue();

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
		this.generatorNameCombo.removeAll();
		//TODO
//		for (Iterator<String> i = getGeneratorRepository().generatorNames(); i.hasNext(); ){
//			this.generatorNameCombo.add(i.next());
//		}

		populateGeneratorName();
	}

	private void uninstallGeneratedValueListeners(IGeneratedValue generatedValue) {
		if (generatedValue != null) {
			generatedValue.removePropertyChangeListener(IGeneratedValue.DEFAULT_GENERATOR_PROPERTY,   generatorNameChangeListener);
			generatedValue.removePropertyChangeListener(IGeneratedValue.SPECIFIED_GENERATOR_PROPERTY, generatorNameChangeListener);
		}
	}

	private void uninstallListeners(IIdMapping idMapping) {
		if (idMapping != null) {
			idMapping.removePropertyChangeListener(IIdMapping.GENERATED_VALUE_PROPERTY, generatedValueChangeListener);
			uninstallGeneratedValueListeners(idMapping.getGeneratedValue());
		}
	}
}