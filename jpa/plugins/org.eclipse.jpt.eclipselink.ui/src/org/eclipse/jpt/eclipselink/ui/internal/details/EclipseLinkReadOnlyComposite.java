/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This composite simply shows the Read Only check box.
 *
 * @see EclipseLinkReadOnly
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkReadOnlyComposite extends Pane<EclipseLinkReadOnly>
{
	/**
	 * Creates a new <code>ReadOnlyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkReadOnlyComposite(Pane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkReadOnly> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Unique tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkReadOnlyComposite_readOnlyLabel,
			buildSpecifiedReadOnlyHolder(),
			buildReadOnlyStringHolder(),
			null
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildSpecifiedReadOnlyHolder() {
		return new PropertyAspectAdapter<EclipseLinkReadOnly, Boolean>(
			getSubjectHolder(),
			EclipseLinkReadOnly.SPECIFIED_READ_ONLY_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedReadOnly();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedReadOnly(value);
			}
		};
	}

	private PropertyValueModel<String> buildReadOnlyStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultReadOnlyHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiDetailsMessages.EclipseLinkReadOnlyComposite_readOnlyWithDefault, defaultStringValue);
				}
				return EclipseLinkUiDetailsMessages.EclipseLinkReadOnlyComposite_readOnlyLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultReadOnlyHolder() {
		return new PropertyAspectAdapter<EclipseLinkReadOnly, Boolean>(
			getSubjectHolder(),
			EclipseLinkReadOnly.SPECIFIED_READ_ONLY_PROPERTY,
			EclipseLinkReadOnly.DEFAULT_READ_ONLY_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedReadOnly() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultReadOnly());
			}
		};
	}
}
