/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.swt.widgets.Composite;

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
 * @see OrmTypeMapping
 * @see OrmPersistentTypeDetailsPage - The parent container
 * @see ClassChooserPane
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmJavaClassChooser extends Pane<OrmTypeMapping> {

	/**
	 * Creates a new <code>XmlJavaClassChooser</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmJavaClassChooser(Pane<?> parentPane,
	                           PropertyValueModel<? extends OrmTypeMapping> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	public OrmJavaClassChooser(Pane<?> parentPane,
        PropertyValueModel<? extends OrmTypeMapping> subjectHolder,
        Composite parent,
        boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}


	private ClassChooserPane<OrmTypeMapping> addClassChooser(Composite container) {

		return new ClassChooserPane<OrmTypeMapping>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<OrmTypeMapping, String>(getSubjectHolder(), OrmTypeMapping.CLASS_PROPERTY) {
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
			protected String getLabelText() {
				return JptUiDetailsOrmMessages.OrmJavaClassChooser_javaClass;
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
				return getSubject().getPersistentType().getName();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		addClassChooser(container);
	}
}