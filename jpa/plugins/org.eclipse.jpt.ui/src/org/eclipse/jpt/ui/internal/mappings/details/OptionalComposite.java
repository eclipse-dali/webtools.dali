/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This composite simply shows a tri-state check box for the Optional option.
 *
 * @see BasicMapping
 * @see BasicMappingComposite - A container of this pane
 * @see ManyToOneMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 *
 * @version 1.0
 * @since 2.0
 */
public class OptionalComposite extends FormPane<Nullable>
{
	/**
	 * Creates a new <code>OptionalComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public OptionalComposite(FormPane<? extends Nullable> parentPane,
	                         Composite parent)
	{
		super(parentPane, parent);
	}

	private WritablePropertyValueModel<Boolean> buildOptionalHolder() {
		return new PropertyAspectAdapter<Nullable, Boolean>(getSubjectHolder(), Nullable.SPECIFIED_OPTIONAL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedOptional();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedOptional(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildOptionalStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildOptionalHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((getSubject() != null) && (value == null)) {

					Boolean defaultValue = getSubject().getDefaultOptional();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.BasicGeneralSection_optionalLabelDefault,
							defaultStringValue
						);
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
	protected void initializeLayout(Composite container) {

		addTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.BasicGeneralSection_optionalLabel,
			buildOptionalHolder(),
			buildOptionalStringHolder(),
			JpaHelpContextIds.MAPPING_OPTIONAL
		);
	}
}