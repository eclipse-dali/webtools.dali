/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

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
public class OrmJavaClassChooser extends AbstractFormPane<OrmTypeMapping> {

	/**
	 * Creates a new <code>XmlJavaClassChooser</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmJavaClassChooser(AbstractFormPane<?> parentPane,
	                           PropertyValueModel<? extends OrmTypeMapping> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>XmlJavaClassChooser</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmJavaClassChooser(PropertyValueModel<? extends OrmTypeMapping> subjectHolder,
	                           Composite parent,
	                           TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private ClassChooserPane<OrmTypeMapping> initializeClassChooser(Composite container) {

		return new ClassChooserPane<OrmTypeMapping>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<OrmTypeMapping, String>(getSubjectHolder(), OrmTypeMapping.CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return subject.getClass_();
					}

					@Override
					protected void setValue_(String value) {
						subject.setClass(value);
					}
				};
			}

			@Override
			protected String className() {
				return subject().getClass_();
			}

			@Override
			protected String labelText() {
				return JptUiOrmMessages.OrmJavaClassChooser_javaClass;
			}

			@Override
			protected IPackageFragmentRoot packageFragmentRoot() {
				IProject project = subject().jpaProject().project();
				IJavaProject root = JavaCore.create(project);

				try {
					return root.getAllPackageFragmentRoots()[0];
				}
				catch (JavaModelException e) {
					JptUiPlugin.log(e);
				}

				return null;
			}

			@Override
			protected void promptType() {
				IType type = chooseType();

				if (type != null) {
					String className = type.getFullyQualifiedName();
					subject().setClass(className);
				}
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		initializeClassChooser(container);
	}
}