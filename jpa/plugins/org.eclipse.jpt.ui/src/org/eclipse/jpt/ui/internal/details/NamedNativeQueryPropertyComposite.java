/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
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
public class NamedNativeQueryPropertyComposite extends Pane<NamedNativeQuery>
{
	private ClassChooserPane<NamedNativeQuery> resultClassChooserPane;

	/**
	 * Creates a new <code>NamedNativeQueryPropertyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public NamedNativeQueryPropertyComposite(Pane<?> parentPane,
	                                         PropertyValueModel<? extends NamedNativeQuery> subjectHolder,
	                                         Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	private ClassChooserPane<NamedNativeQuery> addClassChooser(Composite container) {

		return new ClassChooserPane<NamedNativeQuery>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<NamedNativeQuery, String>(getSubjectHolder(), NamedNativeQuery.RESULT_CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getResultClass();
					}

					@Override
					protected void setValue_(String value) {
						this.subject.setResultClass(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getResultClass();
			}

			@Override
			protected String getLabelText() {
				return JptUiDetailsMessages.NamedNativeQueryPropertyComposite_resultClass;
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setResultClass(className);
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getResultClassEnclosingTypeSeparator();
			}
		};
	}

	private WritablePropertyValueModel<String> buildQueryHolder() {
		return new PropertyAspectAdapter<NamedNativeQuery, String>(getSubjectHolder(), Query.QUERY_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getQuery();
			}

			@Override
			protected void setValue_(String value) {
				this.subject.setQuery(value);
			}
		};
	}

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		this.resultClassChooserPane.enableWidgets(enabled);
	}

	@Override
	protected void initializeLayout(Composite container) {
		
		addLabeledText(
			container, 
			JptUiDetailsMessages.NamedQueryComposite_nameTextLabel, 
			buildNameTextHolder());

		// Result class chooser
		this.resultClassChooserPane = addClassChooser(container);

		// Query text area
		addLabeledMultiLineText(
			container,
			JptUiDetailsMessages.NamedNativeQueryPropertyComposite_query,
			buildQueryHolder(),
			4,
			null
		);

		// Query Hints pane
		container = addTitledGroup(
			addSubPane(container, 5),
			JptUiDetailsMessages.NamedNativeQueryPropertyComposite_queryHintsGroupBox
		);

		new QueryHintsComposite(this, container);
	}
	
	protected WritablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<NamedNativeQuery, String>(
				getSubjectHolder(), Query.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}
		
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setName(value);
			}
		};
	}
}