/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import java.util.Iterator;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This database object combo handles showing the database's sequences for a
 * given <code>Schema</code>.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class SequenceCombo<T extends JpaNode> extends AbstractDatabaseObjectCombo<T>
{
	/**
	 * Creates a new <code>SequenceCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public SequenceCombo(AbstractPane<? extends T> parentPane, Composite parent)
	{
		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>SequenceCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public SequenceCombo(AbstractPane<?> parentPane,
	                     PropertyValueModel<? extends T> subjectHolder,
	                     Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>SequenceCombo</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public SequenceCombo(PropertyValueModel<? extends T> subjectHolder,
	                     Composite parent,
	                     WidgetFactory widgetFactory)
	{
		super(subjectHolder, parent, widgetFactory);
	}

	protected abstract Schema schema();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Iterator<String> values() {
		Schema schema = schema();

		if (schema != null) {
			return schema.sequenceNames();
		}

		return EmptyIterator.instance();
	}
}
