/*******************************************************************************
* Copyright (c) 2009, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.jpa2.context.LockModeType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.QueryHintsComposite;
import org.eclipse.jpt.jpa.ui.internal.jpql.JpaJpqlContentProposalProvider;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

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
 * @version 3.3
 * @since 2.0
 */
public class NamedQueryPropertyComposite2_0 extends Pane<NamedQuery2_0> {

	/**
	 * Creates a new <code>NamedQueryProperty2_0Composite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public NamedQueryPropertyComposite2_0(Pane<?> parentPane,
	                                      PropertyValueModel<NamedQuery2_0> subjectHolder,
	                                      Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	protected ModifiablePropertyValueModel<String> buildNameTextHolder() {
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

	protected ModifiablePropertyValueModel<String> buildQueryHolder() {
		return new PropertyAspectAdapter<NamedQuery2_0, String>(getSubjectHolder(), NamedQuery.QUERY_PROPERTY) {
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
		this.addLabel(container, JptJpaUiDetailsMessages.NamedQueryComposite_nameTextLabel);
		this.addText(container, buildNameTextHolder());

		// Query text area
		Label queryLabel = this.addLabel(container, JptJpaUiDetailsMessages.NamedQueryPropertyComposite_query);
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

		// Lock Mode type
		this.addLabel(container, JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_LOCK_MODE_LABEL);
		this.addLockModeTypeCombo(container);

		QueryHintsComposite queryHintsComposite = new QueryHintsComposite(this, container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		queryHintsComposite.getControl().setLayoutData(gridData);
	}

	private EnumFormComboViewer<NamedQuery2_0, LockModeType2_0> addLockModeTypeCombo(Composite container) {

		return new EnumFormComboViewer<NamedQuery2_0, LockModeType2_0>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(NamedQuery2_0.DEFAULT_LOCK_MODE_PROPERTY);
				propertyNames.add(NamedQuery2_0.SPECIFIED_LOCK_MODE_PROPERTY);
			}

			@Override
			protected LockModeType2_0[] getChoices() {
				return LockModeType2_0.values();
			}

			@Override
			protected LockModeType2_0 getDefaultValue() {
				return this.getSubject().getDefaultLockMode();
			}

			@Override
			protected String displayString(LockModeType2_0 value) {
				switch (value) {
					case NONE :
						return JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_NONE;
					case OPTIMISTIC :
						return JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_OPTIMISTIC;
					case OPTIMISTIC_FORCE_INCREMENT :
						return JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_OPTIMISTIC_FORCE_INCREMENT;
					case PESSIMISTIC_FORCE_INCREMENT :
						return JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_PESSIMISTIC_FORCE_INCREMENT;
					case PESSIMISTIC_READ :
						return JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_PESSIMISTIC_READ;
					case PESSIMISTIC_WRITE :
						return JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_PESSIMISTIC_WRITE;
					case READ :
						return JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_READ;
					case WRITE :
						return JptJpaUiDetailsMessages2_0.LOCK_MODE_COMPOSITE_WRITE;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected LockModeType2_0 getValue() {
				return this.getSubject().getSpecifiedLockMode();
			}

			@Override
			protected void setValue(LockModeType2_0 value) {
				this.getSubject().setSpecifiedLockMode(value);
			}
		};
	}
}
