/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.BatchWriting;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Connection;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Composite;

/**
 * BatchWritingComposite
 */
public class BatchWritingComposite<T extends Connection>
		extends Pane<T>
{
	/**
	 * Creates a new <code>BatchWritingComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public BatchWritingComposite(
					Pane<T> parentComposite, 
					Composite parent) {

		super( parentComposite, parent);
	}

	private EnumFormComboViewer<Connection, BatchWriting> addBatchWritingCombo(Composite container) {
		return new EnumFormComboViewer<Connection, BatchWriting>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Connection.BATCH_WRITING_PROPERTY);
			}

			@Override
			protected BatchWriting[] getChoices() {
				return BatchWriting.values();
			}

			@Override
			protected BatchWriting getDefaultValue() {
				return getSubject().getDefaultBatchWriting();
			}

			@Override
			protected String displayString(BatchWriting value) {
				return buildDisplayString(EclipseLinkUiMessages.class, BatchWritingComposite.this, value);
			}

			@Override
			protected BatchWriting getValue() {
				return getSubject().getBatchWriting();
			}

			@Override
			protected void setValue(BatchWriting value) {
				getSubject().setBatchWriting(value);
			}
		};
	}
	
	@Override
	protected void initializeLayout( Composite container) {

		this.addLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_batchWritingLabel,
			this.addBatchWritingCombo( container),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
	}
}
