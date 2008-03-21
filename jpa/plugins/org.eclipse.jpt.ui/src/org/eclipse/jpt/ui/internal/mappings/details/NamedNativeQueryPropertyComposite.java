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
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |               --------------------------------------------- ------------- |
 * | Result Class: | I                                         | | Browse... | |
 * |               --------------------------------------------- ------------- |
 * |               ---------------------------------------------               |
 * | Query:        | I                                         |               |
 * |               |                                           |               |
 * |               |                                           |               |
 * |               |                                           |               |
 * |               ---------------------------------------------               |
 * |                                                                           |
 * | - Query Hints ----------------------------------------------------------- |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | QueryHintsComposite                                               | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see NamedNativeQuery
 * @see NamedNativeQueriesComposite - The parent container
 * @see ClassChooserPane
 *
 * @version 2.0
 * @since 2.0
 */
public class NamedNativeQueryPropertyComposite extends AbstractPane<NamedNativeQuery>
{
	private ClassChooserPane<NamedNativeQuery> resultClassChooserPane;

	/**
	 * Creates a new <code>NamedNativeQueryPropertyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public NamedNativeQueryPropertyComposite(AbstractPane<?> parentPane,
	                                         PropertyValueModel<? extends NamedNativeQuery> subjectHolder,
	                                         Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	private ClassChooserPane<NamedNativeQuery> buildClassChooser(Composite container) {

		return new ClassChooserPane<NamedNativeQuery>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<NamedNativeQuery, String>(getSubjectHolder(), NamedNativeQuery.RESULT_CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return subject.getResultClass();
					}

					@Override
					protected void setValue_(String value) {
						subject.setResultClass(value);
					}
				};
			}

			@Override
			protected String className() {
				return subject().getResultClass();
			}

			@Override
			protected String labelText() {
				return JptUiMappingsMessages.NamedNativeQueryPropertyComposite_resultClass;
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
					subject().setResultClass(className);
				}
			}
		};
	}

	private WritablePropertyValueModel<String> buildQueryHolder() {
		return new PropertyAspectAdapter<NamedNativeQuery, String>(getSubjectHolder(), NamedNativeQuery.QUERY_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getQuery();
			}

			@Override
			protected void setValue_(String value) {
				subject.setQuery(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		resultClassChooserPane.enableWidgets(enabled);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Result class chooser
		resultClassChooserPane = buildClassChooser(container);

		// Query text area
		buildLabeledMultiLineText(
			container,
			JptUiMappingsMessages.NamedNativeQueryPropertyComposite_query,
			buildQueryHolder(),
			4
		);

		// Query Hints pane
		container = buildTitledPane(
			buildSubPane(container, 5),
			JptUiMappingsMessages.NamedNativeQueryPropertyComposite_queryHintsGroupBox
		);

		new QueryHintsComposite(this, container);
	}
}