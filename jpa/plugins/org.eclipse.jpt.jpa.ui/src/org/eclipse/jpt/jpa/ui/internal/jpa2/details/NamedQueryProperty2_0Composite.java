/*******************************************************************************
* Copyright (c) 2009, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.QueryHintsComposite;
import org.eclipse.jpt.jpa.ui.internal.jpql.JpaJpqlContentProposalProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * Here's the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Query:     | I                                                          | |
 * |            |                                                            | |
 * |            |                                                            | |
 * |            -------------------------------------------------------------- |
 * |            -------------------------------------------------------------- |
 * | Lock Mode: |                                                          |v| |
 * |            -------------------------------------------------------------- |
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
 *
 * @see NamedQuery2_0
 * @see NamedQueriesComposite - The parent container
 * @see QueryHintsComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class NamedQueryProperty2_0Composite extends Pane<NamedQuery2_0> {

	/**
	 * Creates a new <code>NamedQueryProperty2_0Composite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public NamedQueryProperty2_0Composite(Pane<?> parentPane,
	                                      PropertyValueModel<NamedQuery2_0> subjectHolder,
	                                      Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	protected WritablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<NamedQuery2_0, String>(getSubjectHolder(), Query.NAME_PROPERTY) {
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

	protected WritablePropertyValueModel<String> buildQueryHolder() {
		return new PropertyAspectAdapter<NamedQuery2_0, String>(getSubjectHolder(), Query.QUERY_PROPERTY) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		this.addLabeledText(
			container,
			JptUiDetailsMessages.NamedQueryComposite_nameTextLabel,
			this.buildNameTextHolder());

		JpaJpqlContentProposalProvider provider = new JpaJpqlContentProposalProvider(
			container,
			getSubjectHolder(),
			buildQueryHolder()
		);

		// Query text area
		Composite queryWidgets = this.addLabeledComposite(
			container,
			JptUiDetailsMessages.NamedQueryPropertyComposite_query,
			provider.getStyledText()
		);

		// Install the content assist icon at the top left of the StyledText.
		// Note: For some reason, this needs to be done after the StyledText
		//       is added to the labeled composite
		provider.installControlDecoration();

		adjustMultiLineTextLayout(
			queryWidgets,
			4,
			provider.getStyledText(),
			provider.getStyledText().getLineHeight()
		);

		// Lock Mode type
		new LockModeComposite(this, container);

		// Query Hints pane
		container = this.addTitledGroup(
			this.addSubPane(container, 5),
			JptUiDetailsMessages.NamedQueryPropertyComposite_queryHintsGroupBox
		);

		new QueryHintsComposite(this, container);
	}
}