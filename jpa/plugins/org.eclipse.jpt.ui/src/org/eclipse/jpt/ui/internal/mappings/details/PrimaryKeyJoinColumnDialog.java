/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * TODO
 *
 * @see PrimaryKeyJoinColumnStateObject
 * @see PrimaryKeyJoinColumnDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnDialog extends AbstractJoinColumnDialog<PrimaryKeyJoinColumnStateObject> {

	private IEntity entity;

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param entity
	 */
	public PrimaryKeyJoinColumnDialog(Shell parent, IEntity entity) {

		super(parent);
		this.entity = entity;
	}

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnDialog</code>.
	 *
	 * @param parent The parent shell
	 * @param joinColumn
	 */
	public PrimaryKeyJoinColumnDialog(Shell parent,
	                                  IPrimaryKeyJoinColumn joinColumn) {

		super(parent, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected PrimaryKeyJoinColumnStateObject buildStateObject() {

		if (entity != null) {
			return new PrimaryKeyJoinColumnStateObject(entity);
		}

		return new PrimaryKeyJoinColumnStateObject(getJoinColumn());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IPrimaryKeyJoinColumn getJoinColumn() {
		return (IPrimaryKeyJoinColumn) super.getJoinColumn();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeMainPane(Composite container) {
		new PrimaryKeyJoinColumnDialogPane(getSubjectHolder(), container);
	}
}