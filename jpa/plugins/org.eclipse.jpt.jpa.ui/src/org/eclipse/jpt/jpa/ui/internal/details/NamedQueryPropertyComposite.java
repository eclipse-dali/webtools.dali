/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ------------------------------------------------------------------ |
 * | Query: | I                                                              | |
 * |        |                                                                | |
 * |        |                                                                | |
 * |        ------------------------------------------------------------------ |
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
 * @see NamedQuery
 * @see NamedQueriesComposite - The parent container
 * @see QueryHintsComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class NamedQueryPropertyComposite<T extends NamedQuery> extends Pane<T>
{
	/**
	 * Creates a new <code>NamedQueryPropertyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public NamedQueryPropertyComposite(Pane<?> parentPane,
	                                   PropertyValueModel<T> subjectHolder,
	                                   Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	protected WritablePropertyValueModel<String> buildQueryHolder() {
		return new PropertyAspectAdapter<NamedQuery, String>(getSubjectHolder(), Query.QUERY_PROPERTY) {
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
	protected void initializeLayout(Composite container) {
		
		addLabeledText(
			container, 
			JptUiDetailsMessages.NamedQueryComposite_nameTextLabel, 
			buildNameTextHolder());

		// Query text area
		addLabeledMultiLineText(
			container,
			JptUiDetailsMessages.NamedQueryPropertyComposite_query,
			buildQueryHolder(),
			4,
			null
		);

		// Query Hints pane
		container = addTitledGroup(
			addSubPane(container, 5),
			JptUiDetailsMessages.NamedQueryPropertyComposite_queryHintsGroupBox
		);

		new QueryHintsComposite(this, container);
	}
		
	protected WritablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<NamedQuery, String>(
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