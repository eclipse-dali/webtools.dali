/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMutable;
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
 * This composite simply shows a tri-state check box for the Mutable option.
 *
 * @see EclipseLinkMutable
 * @see EclipseLinkBasicMappingComposite - A container of this pane
 *
 * @version 2.1
 * @since 2.1
 */
public class MutableComposite extends FormPane<EclipseLinkMutable>
{
	/**
	 * Creates a new <code>MutableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public MutableComposite(FormPane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkMutable> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMappingsMessages.MutableComposite_mutableLabel,
			buildMutableHolder(),
			buildMutableStringHolder(),
			null
		);
	}

	private WritablePropertyValueModel<Boolean> buildMutableHolder() {
		return new PropertyAspectAdapter<EclipseLinkMutable, Boolean>(getSubjectHolder(), EclipseLinkMutable.SPECIFIED_MUTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedMutable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedMutable(value);
			}

		};
	}

	private PropertyValueModel<String> buildMutableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultMutableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiMappingsMessages.Boolean_True : JptUiMappingsMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMappingsMessages.MutableComposite_mutableLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMappingsMessages.MutableComposite_mutableLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultMutableHolder() {
		return new PropertyAspectAdapter<EclipseLinkMutable, Boolean>(
			getSubjectHolder(),
			EclipseLinkMutable.SPECIFIED_MUTABLE_PROPERTY,
			EclipseLinkMutable.DEFAULT_MUTABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedMutable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultMutable());
			}
		};
	}
}