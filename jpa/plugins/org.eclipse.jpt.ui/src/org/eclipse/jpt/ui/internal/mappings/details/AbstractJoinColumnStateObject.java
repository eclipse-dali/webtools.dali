/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.node.AbstractNode;
import org.eclipse.jpt.utility.internal.node.Node;

/**
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class AbstractJoinColumnStateObject extends AbstractNode
{
	private boolean defaultNameSelected;
	private boolean defaultReferencedColumnNameSelected;
	private IAbstractJoinColumn joinColumn;
	private String selectedName;
	private String selectedReferencedColumnName;

	static final String DEFAULT_NAME_SELECTED_PROPERTY = "defaultNameSelected";
	static final String DEFAULT_REFERENCE_COLUMN_NAME_SELECTED_PROPERTY = "defaultReferencedColumnNameSelected";
	static final String SELECTED_NAME_PROPERTY = "selectedName";
	static final String SELECTED_REFERENCED_COLUMN_NAME_PROPERTY = "selectedReferencedColumnName";

	/**
	 * Creates a new <code>AbstractJoinColumnStateObject</code>.
	 */
	public AbstractJoinColumnStateObject() {
		super(null);
	}

	/**
	 * Creates a new <code>AbstractJoinColumnStateObject</code>.
	 *
	 * @param joinColumn
	 */
	public AbstractJoinColumnStateObject(IAbstractJoinColumn joinColumn) {
		super(null);
		this.joinColumn = joinColumn;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void checkParent(Node parentNode) {
	}

	/*
	 * (non-Javadoc)
	 */
	public String displayString() {
		return "";
	}

	public String getDefaultName() {
		if (joinColumn == null) {
			return null;
		}

		return joinColumn.getDefaultName();
	}

	public String getDefaultReferencedColumnName() {
		if (joinColumn == null) {
			return null;
		}

		return joinColumn.getDefaultReferencedColumnName();
	}

	public IAbstractJoinColumn getJoinColumn() {
		return joinColumn;
	}

	public abstract Table getNameTable();

	public abstract Table getReferencedNameTable();

	public String getSelectedName() {
		return selectedName;
	}

	public String getSelectedReferencedColumnName() {
		return selectedReferencedColumnName;
	}

	public String getSpecifiedName() {
		if (joinColumn == null) {
			return null;
		}

		return joinColumn.getSpecifiedName();
	}

	public String getSpecifiedReferencedColumnName() {
		if (joinColumn == null) {
			return null;
		}

		return joinColumn.getSpecifiedReferencedColumnName();
	}

	public boolean isDefaultNameSelected() {
		return defaultNameSelected;
	}

	public boolean isDefaultReferencedColumnNameSelected() {
		return defaultReferencedColumnNameSelected;
	}

	public void setDefaultNameSelected(boolean defaultNameSelected) {
		boolean oldDefaultNameSelected = this.defaultNameSelected;
		this.defaultNameSelected = defaultNameSelected;
		firePropertyChanged(DEFAULT_NAME_SELECTED_PROPERTY, oldDefaultNameSelected, defaultNameSelected);
	}

	public void setDefaultReferencedColumnNameSelected(boolean defaultReferencedColumnNameSelected) {
		boolean oldDefaultReferencedColumnNameSelected = this.defaultReferencedColumnNameSelected;
		this.defaultReferencedColumnNameSelected = defaultReferencedColumnNameSelected;
		firePropertyChanged(DEFAULT_REFERENCE_COLUMN_NAME_SELECTED_PROPERTY, oldDefaultReferencedColumnNameSelected, defaultReferencedColumnNameSelected);
	}

	public void setSelectedName(String selectedName) {
		String oldSelectedName = this.selectedName;
		this.selectedName = selectedName;
		firePropertyChanged(SELECTED_NAME_PROPERTY, oldSelectedName, selectedName);
	}

	public void setSelectedReferencedColumnName(String selectedReferencedColumnName) {
		String oldSelectedReferencedColumnName = this.selectedReferencedColumnName;
		this.selectedReferencedColumnName = selectedReferencedColumnName;
		firePropertyChanged(SELECTED_REFERENCED_COLUMN_NAME_PROPERTY, oldSelectedReferencedColumnName, selectedReferencedColumnName);
	}
}
