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
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.PackageChooserPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | PackageChooserPane                                                        |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EntityMappings
 * @see EntityMappingsDetailsPage - The parent container
 * @see PackageChooserPane
 *
 * @version 2.0
 * @since 2.0
 */
public class OrmPackageChooser extends FormPane<EntityMappings>
{
	/**
	 * Creates a new <code>XmlPackageChooser</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public OrmPackageChooser(FormPane<? extends EntityMappings> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		addPackageChooserPane(container);
	}

	private void addPackageChooserPane(Composite container) {

		new PackageChooserPane<EntityMappings>(this, container) {
			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EntityMappings, String>(getSubjectHolder(), EntityMappings.PACKAGE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return subject.getPackage();
					}

					@Override
					protected void setValue_(String value) {
						subject.setPackage(value == "" ? null : value); //$NON-NLS-1$
					}
				};
			}

			@Override
			protected String getLabelText() {
				return JptUiOrmMessages.EntityMappingsDetailsPage_package;
			}

			@Override
			protected IPackageFragmentRoot getPackageFragmentRoot() {
				IProject project = getSubject().getJpaProject().getProject();
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
			protected String getPackageName() {
				return getSubject().getPackage();
			}

			@Override
			protected void promptPackage() {
				IPackageFragment packageFragment = choosePackage();

				if (packageFragment != null) {
					String packageName = packageFragment.getElementName();
					getSubject().setPackage(packageName);
				}
			}
		};
	}
}