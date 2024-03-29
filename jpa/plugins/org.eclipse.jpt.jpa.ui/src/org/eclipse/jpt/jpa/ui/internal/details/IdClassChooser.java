/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
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
	protected ModifiablePropertyValueModel<String> buildTextHolder() {
		return new PropertyAspectAdapter<IdClassReference, String>(
				getSubjectHolder(), 
				IdClassReference.SPECIFIED_ID_CLASS_NAME_PROPERTY,
				IdClassReference.DEFAULT_ID_CLASS_NAME_PROPERTY) {

			@Override
			protected String buildValue_() {
				String value = this.subject.getSpecifiedIdClassName();
				return (value == null) ? defaultText(this.subject) : value;
			}

			@Override
			protected void setValue_(String value) {
				if (value == null 
						|| value.length() == 0 
						|| value.equals(defaultText(this.subject))) {
					value = null;
				}
				this.subject.setSpecifiedIdClassName(value);
			}
		};
	}

	protected String defaultText(IdClassReference idClassReference) {
		String defaultClassName = idClassReference.getDefaultIdClassName();
		return (defaultClassName == null) ?
				JptCommonUiMessages.NONE_SELECTED
				: NLS.bind(JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM, defaultClassName);
	}

	@Override
	protected ListValueModel<String> buildClassListHolder() {
		return new PropertyListValueModelAdapter<String>(
			new PropertyAspectAdapter<IdClassReference, String>(
					getSubjectHolder(), IdClassReference.DEFAULT_ID_CLASS_NAME_PROPERTY) {
				@Override
				protected String buildValue_() {
					return defaultText(this.subject);
				}
			});
	}
}