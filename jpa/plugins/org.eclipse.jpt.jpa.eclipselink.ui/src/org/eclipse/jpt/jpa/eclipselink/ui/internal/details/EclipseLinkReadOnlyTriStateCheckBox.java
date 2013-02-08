/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This composite simply shows the Read Only check box.
 *
 * @see EclipseLinkReadOnly
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkReadOnlyTriStateCheckBox extends Pane<EclipseLinkReadOnly>
{
	private TriStateCheckBox checkBox;

	/**
	 * Creates a new <code>ReadOnlyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkReadOnlyTriStateCheckBox(Pane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkReadOnly> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
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
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_READ_ONLY_COMPOSITE_READ_ONLY_LABEL,
			buildSpecifiedReadOnlyHolder(),
			buildReadOnlyStringHolder(),
			null
		);
	}

	private ModifiablePropertyValueModel<Boolean> buildSpecifiedReadOnlyHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_READ_ONLY_COMPOSITE_READ_ONLY_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_READ_ONLY_COMPOSITE_READ_ONLY_LABEL;
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
