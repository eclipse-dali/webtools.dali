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
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.StringTools;
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
	private CCombo generatorNameCombo;

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
		propertyNames.add(IGeneratedValue.DEFAULT_GENERATOR_PROPERTY);
		propertyNames.add(IGeneratedValue.SPECIFIED_GENERATOR_PROPERTY);
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

				retrieveGeneratedValue().setSpecifiedGenerator(generatorName);
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

	private EnumFormComboViewer<IGeneratedValue, GenerationType> buildStrategyComboViewer(Composite parent) {

		return new EnumFormComboViewer<IGeneratedValue, GenerationType>(this, buildGeneratorValueHolder(), parent) {

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
				retrieveGeneratedValue().setSpecifiedStrategy(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateGeneratorNameCombo();
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
			IJpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY
		);

		// Generator Name widgets
		generatorNameCombo = buildLabeledEditableCCombo(
			container,
			JptUiMappingsMessages.GeneratedValueComposite_generatorName,
			buildGeneratorNameModifyListener(),
			IJpaHelpContextIds.MAPPING_GENERATED_VALUE_STRATEGY
		);

		generatorNameCombo.add(JptUiMappingsMessages.TableComposite_defaultEmpty);
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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == IGeneratedValue.DEFAULT_GENERATOR_PROPERTY ||
		    propertyName == IGeneratedValue.SPECIFIED_GENERATOR_PROPERTY) {

			populateGeneratorNameCombo();
		}
	}

	private IGeneratedValue retrieveGeneratedValue() {
		IGeneratedValue generatedValue = subject().getGeneratedValue();

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
}