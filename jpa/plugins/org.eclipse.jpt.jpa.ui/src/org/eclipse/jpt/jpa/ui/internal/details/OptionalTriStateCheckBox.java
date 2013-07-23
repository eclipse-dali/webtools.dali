/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.OptionalMapping;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This composite simply shows a tri-state check box for the Optional option.
 *
 * @version 1.0
 * @since 2.0
 */
public class OptionalTriStateCheckBox extends Pane<OptionalMapping>
{
	private TriStateCheckBox checkBox;

	/**
	 * Creates a new <code>OptionalComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public OptionalTriStateCheckBox(Pane<? extends OptionalMapping> parentPane,
	                         Composite parent)
	{
		super(parentPane, parent);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.checkBox.getCheckBox();
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.checkBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages.BASIC_GENERAL_SECTION_OPTIONAL_LABEL,
			buildSpecifiedOptionalHolder(),
			buildOptionalStringHolder(),
			JpaHelpContextIds.MAPPING_OPTIONAL
		);
	}

	private ModifiablePropertyValueModel<Boolean> buildSpecifiedOptionalHolder() {
		return new PropertyAspectAdapter<OptionalMapping, Boolean>(getSubjectHolder(), OptionalMapping.SPECIFIED_OPTIONAL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedOptional();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedOptional(value);
			}
		};
	}

	private PropertyValueModel<String> buildOptionalStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultOptionalHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.BASIC_GENERAL_SECTION_OPTIONAL_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.BASIC_GENERAL_SECTION_OPTIONAL_LABEL;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultOptionalHolder() {
		return new PropertyAspectAdapter<OptionalMapping, Boolean>(
			getSubjectHolder(),
			OptionalMapping.SPECIFIED_OPTIONAL_PROPERTY,
			OptionalMapping.DEFAULT_OPTIONAL_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedOptional() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultOptional());
			}
		};
	}
}