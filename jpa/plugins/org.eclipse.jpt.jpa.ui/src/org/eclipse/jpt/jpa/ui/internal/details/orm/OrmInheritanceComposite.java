/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractInheritanceComposite;
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
	public OrmInheritanceComposite(Pane<? extends OrmEntity> parentPane,
	                            Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void addPrimaryKeyJoinColumnsComposite(Composite container) {
		new OrmPrimaryKeyJoinColumnsComposite(this, container);
	}
}