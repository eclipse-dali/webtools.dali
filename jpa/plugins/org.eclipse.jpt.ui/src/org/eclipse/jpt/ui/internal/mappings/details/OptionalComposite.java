/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.swt.TriStateBooleanButtonModelAdapter;
import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledTableItem;
import org.eclipse.jpt.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This composite simply shows a tri-state check box for the Optional option.
 *
 * @see IBasicMapping
 * @see BasicMappingComposite - A container of this pane
 *
 * @version 1.0
 * @since 2.0
 */
public class OptionalComposite extends BaseJpaController<IBasicMapping>
{
	private TriStateCheckBox optionalCheckBox;

	/**
	 * Creates a new <code>OptionalComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public OptionalComposite(BaseJpaComposite<IBasicMapping> parentComposite,
	                         Composite parent)
	{
		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Boolean> buildOptionalHolder() {
		return new PropertyAspectAdapter<IBasicMapping, Boolean>(getSubjectHolder(), IBasicMapping.SPECIFIED_OPTIONAL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject().getSpecifiedOptional();
			}
			@Override
			protected void setValue_(Boolean value) {
				subject().setSpecifiedOptional(value);
			}
		};
	}

	private PropertyValueModel<String> buildOptionalStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(this.buildOptionalHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {
					Boolean defaultValue = subject().getDefaultOptional();

					if (defaultValue != null) {
						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True : JptUiMappingsMessages.Boolean_False;
						return NLS.bind(JptUiMappingsMessages.BasicGeneralSection_optionalLabelDefault, defaultStringValue);
					}
				}

				return JptUiMappingsMessages.BasicGeneralSection_optionalLabel;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void buildWidgets(Composite parent) {

		optionalCheckBox = new TriStateCheckBox(parent);
		optionalCheckBox.setText(JptUiMappingsMessages.BasicGeneralSection_optionalLabel);

		installLabeledControlUpdater(optionalCheckBox);
		helpSystem().setHelp(optionalCheckBox.getControl(), IJpaHelpContextIds.MAPPING_OPTIONAL);

		TriStateBooleanButtonModelAdapter.adapt(
			buildOptionalHolder(),
			optionalCheckBox
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Control getControl() {
		return optionalCheckBox.getControl();
	}

	private void installLabeledControlUpdater(TriStateCheckBox optionalCheckBox) {
		new LabeledControlUpdater(
			new LabeledTableItem(optionalCheckBox.getCheckBox()),
			buildOptionalStringHolder()
		);
	}
}