/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.IdClassReference;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class IdClassChooser
	extends ClassChooserComboPane<IdClassReference>
{
	public IdClassChooser(Pane<?> parentPane, PropertyValueModel<IdClassReference> subjectHolder, Composite parent, Hyperlink hyperlink) {
		super(parentPane, subjectHolder, parent, hyperlink);
	}

	@Override
	protected String getClassName() {
		return getSubject().getIdClassName();
	}

	@Override
	protected void setClassName(String className) {
		getSubject().setSpecifiedIdClassName(className);
	}

	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	@Override
	protected char getEnclosingTypeSeparator() {
		return getSubject().getIdClassEnclosingTypeSeparator();
	}

	@Override
	protected String getFullyQualifiedClassName() {
		return getSubject().getFullyQualifiedIdClassName();
	}

	@Override
	protected ModifiablePropertyValueModel<String> buildTextModel() {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				this.getSubjectHolder(),
				IdClassReference.ID_CLASS_NAME_PROPERTY,
				m -> (m.getSpecifiedIdClassName() == null) ? this.defaultText(m) : m.getSpecifiedIdClassName(),
				(m, value) -> m.setSpecifiedIdClassName(defaultText(m).equals(value) ? null : value)
			);
	}

	protected String defaultText(IdClassReference idClassReference) {
		String defaultName = idClassReference.getDefaultIdClassName();
		return (defaultName == null) ?
				JptCommonUiMessages.NONE_SELECTED :
				NLS.bind(JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM, defaultName);
	}

	@Override
	protected ListValueModel<String> buildClassListModel() {
		return new PropertyListValueModelAdapter<>(
			new PropertyAspectAdapterXXXX<IdClassReference, String>(
					getSubjectHolder(), IdClassReference.DEFAULT_ID_CLASS_NAME_PROPERTY) {
				@Override
				protected String buildValue_() {
					return defaultText(this.subject);
				}
			});
	}
}
