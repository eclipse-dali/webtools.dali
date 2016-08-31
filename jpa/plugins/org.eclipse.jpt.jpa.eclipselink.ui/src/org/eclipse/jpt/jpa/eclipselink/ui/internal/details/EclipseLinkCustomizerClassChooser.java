/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 *
 * @see EclipseLinkCustomizer
 *
 * @version 2.3
 * @since 2.1
 */
public class EclipseLinkCustomizerClassChooser
	extends ClassChooserComboPane<EclipseLinkCustomizer>
{
	public EclipseLinkCustomizerClassChooser(Pane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkCustomizer> subjectHolder,
		Composite parent,
		Hyperlink hyperlink) {

			super(parentPane, subjectHolder, parent, hyperlink);
	}


	@Override
	protected String getClassName() {
		return getSubject().getSpecifiedCustomizerClass();
	}

	@Override
	protected void setClassName(String className) {
		getSubject().setSpecifiedCustomizerClass(className);
	}

	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	@Override
	protected String getFullyQualifiedClassName() {
		return getSubject().getFullyQualifiedCustomizerClass();
	}

	@Override
	protected String getSuperInterfaceName() {
		return EclipseLinkCustomizer.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME;
	}

	@Override
	protected char getEnclosingTypeSeparator() {
		return getSubject().getCustomizerClassEnclosingTypeSeparator();
	}

	@Override
	protected ModifiablePropertyValueModel<String> buildTextModel() {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				this.getSubjectHolder(),
				EclipseLinkCustomizer.SPECIFIED_CUSTOMIZER_CLASS_PROPERTY,
				m -> {
					String className = m.getSpecifiedCustomizerClass();
					return (className == null) ? defaultText(m) : className;
				},
				(m, value) -> m.setSpecifiedCustomizerClass(((value == null) || (value.length() == 0) || value.equals(defaultText(m))) ? null : value)
			);
	}

	@Override
	protected ListValueModel<String> buildClassListModel() {
		return new PropertyListValueModelAdapter<>(this.buildClassModel());
	}

	protected PropertyValueModel<String> buildClassModel() {
		return PropertyValueModelTools.subjectModelAspectAdapter(
				this.getSubjectHolder(),
				EclipseLinkCustomizer.DEFAULT_CUSTOMIZER_CLASS_PROPERTY,
				m -> defaultText(m)
			);
	}

	protected String defaultText(EclipseLinkCustomizer customizer) {
		String defaultClassName = customizer.getDefaultCustomizerClass();
		return (defaultClassName == null) ?
				JptCommonUiMessages.NONE_SELECTED
				: NLS.bind(JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM, defaultClassName);
	}
}
