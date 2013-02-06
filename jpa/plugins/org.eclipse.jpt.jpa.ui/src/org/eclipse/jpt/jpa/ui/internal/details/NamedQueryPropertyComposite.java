/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.ui.internal.jpql.JpaJpqlContentProposalProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Here's the layout of this pane:
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
 * @version 3.3
 * @since 2.3
 */
public class NamedQueryPropertyComposite<T extends NamedQuery> extends Pane<T> {

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

	protected ModifiablePropertyValueModel<String> buildNameTextHolder() {
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

	protected ModifiablePropertyValueModel<String> buildQueryHolder() {
		return new PropertyAspectAdapter<NamedQuery, String>(getSubjectHolder(), NamedQuery.QUERY_PROPERTY) {
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

		Label queryLabel = this.addLabel(container, JptUiDetailsMessages.NamedQueryPropertyComposite_query);
		GridData gridData = new GridData();
		gridData.verticalAlignment = SWT.TOP;
		queryLabel.setLayoutData(gridData);

		JpaJpqlContentProposalProvider provider = new JpaJpqlContentProposalProvider(
			container,
			getSubjectHolder(),
			buildQueryHolder()
		);

		// Install the content assist icon at the top left of the StyledText.
		// Note: For some reason, this needs to be done after the StyledText
		//       is added to the labeled composite
		provider.installControlDecoration();

		adjustMultiLineTextLayout(
			JpaPreferences.getJpqlQueryTextAreaNumberOfLines(),
			provider.getStyledText(),
			provider.getStyledText().getLineHeight()
		);

		QueryHintsComposite hintsComposite = new QueryHintsComposite(this, container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		hintsComposite.getControl().setLayoutData(gridData);
	}
}