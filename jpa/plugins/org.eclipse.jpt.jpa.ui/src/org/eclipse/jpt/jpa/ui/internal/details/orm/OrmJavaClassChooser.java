/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | ClassChooserPane                                                          |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * TODO possibly help the user and if they have chosen a package at the
 * entity-mappings level only insert the class name in the xml file if they
 * choose a class from the package.
 * Not sure if this should be driven by the UI or by ui api in the model
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmJavaClassChooser extends ClassChooserPane<OrmManagedType> {

	/**
	 * Creates a new <code>XmlJavaClassChooser</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmJavaClassChooser(Pane<?> parentPane,
	                           PropertyValueModel<? extends OrmManagedType> subjectHolder,
	                           Composite parent,
	                           Hyperlink hyperlink) {

		super(parentPane, subjectHolder, parent, hyperlink);
	}

	@Override
	protected ModifiablePropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapterXXXX<OrmManagedType, String>(getSubjectHolder(), OrmManagedType.CLASS_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getClass_();
			}

			@Override
			protected void setValue_(String value) {
				this.subject.setClass(value);
			}
		};
	}

	@Override
	protected String getClassName() {
		return getSubject().getClass_();
	}

	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	@Override
	protected void setClassName(String className) {
		getSubject().setClass(className);
	}

	@Override
	protected String getFullyQualifiedClassName() {
		return getSubject().getName();
	}
}