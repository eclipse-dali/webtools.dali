/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

/**
 *  CacheStatementsPropertiesComposite
 */
public class CacheStatementsPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	public CacheStatementsPropertiesComposite(
						Pane<T> parentComposite, 
						Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		WritablePropertyValueModel<Boolean> cacheStatementsHolder = buildCacheStatementsHolder();

		container = this.addSubPane(container, 3, 5, 0, 0, 0);
		
		this.addTriStateCheckBox(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_cacheStatementsLabel,
			cacheStatementsHolder,
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		IntegerCombo<?> combo = addCacheStatementsSizeCombo(container);

		this.installControlEnabler(cacheStatementsHolder, combo);
	}

	private WritablePropertyValueModel<Boolean> buildCacheStatementsHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(getSubjectHolder(), Connection.CACHE_STATEMENTS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getCacheStatements();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setCacheStatements(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChanged(Boolean.TRUE, newValue);
				}
			}
		};
	}
	
	private IntegerCombo<Connection> addCacheStatementsSizeCombo(Composite container) {
		return new IntegerCombo<Connection>(this, container) {
			
			@Override
			protected CCombo addIntegerCombo(Composite container) {
				return this.addEditableCCombo(
						container,
						buildDefaultListHolder(),
						buildSelectedItemStringHolder(),
						StringConverter.Default.<String>instance());
			}
		
			@Override
			protected String getLabelText() {
				throw new UnsupportedOperationException();
			}
		
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultCacheStatementsSize();
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.CACHE_STATEMENTS_SIZE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getCacheStatementsSize();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setCacheStatementsSize(value);
					}
				};
			}
		};
	}

	private void installControlEnabler(WritablePropertyValueModel<Boolean> cacheStatementsHolder, IntegerCombo<?> combo) {

		new PaneEnabler(cacheStatementsHolder, combo);
	}
}