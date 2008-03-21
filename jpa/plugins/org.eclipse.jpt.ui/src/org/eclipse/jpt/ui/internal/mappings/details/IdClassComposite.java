/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.context.IdClass;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * @see IdClass
 * @see ClassChooserPane
 * @see AbstractEntityComposite - A parent container
 * @see MappedSuperclassComposite - A parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class IdClassComposite extends AbstractPane<IdClass>
{
	/**
	 * Creates a new <code>IdClassComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public IdClassComposite(AbstractPane<? extends IdClass> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}

	private ClassChooserPane<IdClass> initializeClassChooser(Composite container) {

		return new ClassChooserPane<IdClass>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<IdClass, String>(getSubjectHolder(), IdClass.ID_CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return subject.getIdClass();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						subject.setIdClass(value);
					}
				};
			}

			@Override
			protected String className() {
				return subject().getIdClass();
			}

			@Override
			protected String labelText() {
				return JptUiMappingsMessages.IdClassComposite_label;
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
					subject().setIdClass(className);
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
