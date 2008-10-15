/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This composite simply shows the Read Only check box.
 *
 * @see ReadOnly
 *
 * @version 2.1
 * @since 2.1
 */
public class ReadOnlyComposite extends FormPane<ReadOnly>
{
	/**
	 * Creates a new <code>ReadOnlyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ReadOnlyComposite(FormPane<?> parentPane, 
		PropertyValueModel<? extends ReadOnly> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Unique tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMappingsMessages.ReadOnlyComposite_readOnlyLabel,
			buildReadOnlyHolder(),
			buildReadOnlyStringHolder(),
			null
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildReadOnlyHolder() {
		return new PropertyAspectAdapter<ReadOnly, Boolean>(
			getSubjectHolder(),
			ReadOnly.DEFAULT_READ_ONLY_PROPERTY,
			ReadOnly.SPECIFIED_READ_ONLY_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedReadOnly();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedReadOnly(value);
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

	private PropertyValueModel<String> buildReadOnlyStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildReadOnlyHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((getSubject() != null) && (value == null)) {

					Boolean defaultValue = getSubject().getDefaultReadOnly();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							EclipseLinkUiMappingsMessages.ReadOnlyComposite_readOnlyWithDefault,
							defaultStringValue
						);
					}
				}

				return EclipseLinkUiMappingsMessages.ReadOnlyComposite_readOnlyLabel;
			}
		};
	}

}
