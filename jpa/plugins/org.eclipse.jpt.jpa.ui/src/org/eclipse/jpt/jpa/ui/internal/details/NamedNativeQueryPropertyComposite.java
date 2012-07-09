/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Hyperlink;

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

	private ClassChooserPane<NamedNativeQuery> addResultClassChooser(Composite container, Hyperlink hyperlink) {

		return new ClassChooserPane<NamedNativeQuery>(this, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<NamedNativeQuery, String>(getSubjectHolder(), NamedNativeQuery.RESULT_CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getResultClass();
					}

					@Override
					protected void setValue_(String value) {
						if (value.length() == 0) {
							value = null;
						}				
						this.subject.setResultClass(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getResultClass();
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

			@Override
			protected String getFullyQualifiedClassName() {
				return getSubject().getFullyQualifiedResultClass();
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildQueryHolder() {
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
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptUiDetailsMessages.NamedQueryComposite_nameTextLabel);
		this.addText(container, buildNameTextHolder());

		// Result class chooser
		Hyperlink resultClassHyperlink = this.addHyperlink(container, JptUiDetailsMessages.NamedNativeQueryPropertyComposite_resultClass);
		this.resultClassChooserPane = this.addResultClassChooser(container, resultClassHyperlink);

		// Query text area
		Label queryLabel = this.addLabel(container, JptUiDetailsMessages.NamedNativeQueryPropertyComposite_query);
		GridData gridData = new GridData();
		gridData.verticalAlignment = SWT.TOP;
		queryLabel.setLayoutData(gridData);
		this.addMultiLineText(container, buildQueryHolder(), 4, null);

		QueryHintsComposite hintsComposite = new QueryHintsComposite(this, container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		hintsComposite.getControl().setLayoutData(gridData);
	}
	
	protected ModifiablePropertyValueModel<String> buildNameTextHolder() {
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