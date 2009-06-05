/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
	private WritablePropertyValueModel<Boolean> buildOptionalHolder() {
		return new PropertyAspectAdapter<Nullable, Boolean>(getSubjectHolder(), Nullable.SPECIFIED_OPTIONAL_PROPERTY) {
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
					String defaultStringValue = value.booleanValue() ? JptUiMappingsMessages.Boolean_True : JptUiMappingsMessages.Boolean_False;
					return NLS.bind(JptUiMappingsMessages.BasicGeneralSection_optionalLabelDefault, defaultStringValue);
				}
				return JptUiMappingsMessages.BasicGeneralSection_optionalLabel;
			}
		};
	}
	
	
	private PropertyValueModel<Boolean> buildDefaultOptionalHolder() {
		return new PropertyAspectAdapter<Nullable, Boolean>(
			getSubjectHolder(),
			Nullable.SPECIFIED_OPTIONAL_PROPERTY,
			Nullable.DEFAULT_OPTIONAL_PROPERTY)
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