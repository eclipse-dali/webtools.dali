/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.mappings.details.AbstractInheritanceComposite;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for java inheritance.
 *
 * @see OrmEntity
 * @see OrmPrimaryKeyJoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmInheritanceComposite extends AbstractInheritanceComposite<OrmEntity> {

	/**
	 * Creates a new <code>OrmInheritanceComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public OrmInheritanceComposite(AbstractPane<OrmEntity> parentPane,
	                            Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>OrmInheritanceComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>OrmEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmInheritanceComposite(PropertyValueModel<OrmEntity> subjectHolder,
	                            Composite parent,
	                            WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void buildPrimaryKeyJoinColumnsComposite(Composite container) {
		new OrmPrimaryKeyJoinColumnsComposite(this, container);
	}
}