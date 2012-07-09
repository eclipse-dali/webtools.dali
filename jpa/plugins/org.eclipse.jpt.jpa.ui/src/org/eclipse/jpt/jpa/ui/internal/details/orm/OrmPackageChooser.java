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
import org.eclipse.jpt.common.ui.internal.widgets.PackageChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
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
public class OrmPackageChooser extends PackageChooserPane<EntityMappings>
{
	/**
	 * Creates a new <code>XmlPackageChooser</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public OrmPackageChooser(Pane<? extends EntityMappings> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected ModifiablePropertyValueModel<String> buildTextHolder() {
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
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	@Override
	protected String getPackageName() {
		return getSubject().getPackage();
	}

	@Override
	protected void setPackageName(String packageName) {
		getSubject().setPackage(packageName);
	}
}