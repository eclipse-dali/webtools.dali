/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This composite simply shows a tri-state check box for the Mutable option.
 *
 * @see EclipseLinkMutable
 * @see EclipseLinkBasicMappingComposite - A container of this pane
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkMutableTriStateCheckBox extends Pane<EclipseLinkMutable>
{
	private TriStateCheckBox checkBox;

	/**
	 * Creates a new <code>MutableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkMutableTriStateCheckBox(Pane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkMutable> subjectHolder,
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
		this.checkBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MUTABLE_COMPOSITE_MUTABLE_LABEL,
			buildMutableHolder(),
			buildMutableStringHolder(),
			null
		);
	}

	private ModifiablePropertyValueModel<Boolean> buildMutableHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MUTABLE_COMPOSITE_MUTABLE_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MUTABLE_COMPOSITE_MUTABLE_LABEL;
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