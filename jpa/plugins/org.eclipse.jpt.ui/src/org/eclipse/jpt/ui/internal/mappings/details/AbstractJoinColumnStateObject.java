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
 * The abstract definition of a state object used to edit or create a new
 * join column.
 *
 * @see IAbstractJoinColumn
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class AbstractJoinColumnStateObject extends AbstractNode
{
	/**
	 * Either the join column is being edited or <code>null</code> the state
	 * object is being created.
	 */
	private IAbstractJoinColumn joinColumn;

	/**
	 * The join column's name or <code>null</code> if not defined.
	 */
	private String name;

	/**
	 * The owner of the join column to create or where it is located.
	 */
	private Object owner;

	/**
	 * The referenced column name or <code>null</code> if not defined.
	 */
	private String referencedColumnName;

	/**
	 * Keeps track of the <code>Validator</code> since this is the root object.
	 */
	private Validator validator;

	/**
	 * Identifies a change in the name property.
	 */
	public static final String NAME_PROPERTY = "name";

	/**
	 * Identifies a change in the referenced column name property.
	 */
	public static final String REFERENCED_COLUMN_NAME_PROPERTY = "referencedColumnName";

	/**
	 * Creates a new <code>AbstractJoinColumnStateObject</code>.
	 *
	 * @param owner The owner of the join column to create or where it is located
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public AbstractJoinColumnStateObject(Object owner,
	                                     IAbstractJoinColumn joinColumn) {
		super(null);
		initialize(owner, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected final void checkParent(Node parentNode) {
		// This is the root of the Join Column state object
	}

	/**
	 * Returns the default name if the join column is being edited otherwise
	 * <code>null</code> is returned.
	 *
	 * @return Either the default name defined by the join column or <code>null</code>
	 */
	public String defaultName() {
		if (this.joinColumn == null) {
			return null;
		}

		return this.joinColumn.getDefaultName();
	}

	/**
	 * Returns the default referenced column name if the join column is being
	 * edited otherwise <code>null</code> is returned.
	 *
	 * @return Either the default referenced column name defined by the join
	 * column or <code>null</code>
	 */
	public String defaultReferencedColumnName() {
		if (this.joinColumn == null) {
			return null;
		}

		return this.joinColumn.getDefaultReferencedColumnName();
	}

	/*
	 * (non-Javadoc)
	 */
	public final String displayString() {
		return "";
	}

	/**
	 * Returns the edited join column or <code>null</code> if this state object
	 * is used to create a new one.
	 *
	 * @return The edited join column or <code>null</code>
	 */
	public IAbstractJoinColumn getJoinColumn() {
		return this.joinColumn;
	}

	/**
	 * Returns the name of the join column.
	 *
	 * @return Either join column's name or <code>null</code> to use the default
	 * name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the database table if one can be found.
	 *
	 * @return The database table
	 */
	public abstract Table getNameTable();

	/**
	 * Returns the owner where the join column is located or where a new one can
	 * be added.
	 *
	 * @return The parent of the join column
	 */
	public Object getOwner() {
		return owner;
	}

	/**
	 * Returns the referenced column name of the join column.
	 *
	 * @return Either join column's referenced column name or <code>null</code>
	 * to use the default name
	 */
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}

	public abstract Table getReferencedNameTable();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		validator = NULL_VALIDATOR;
	}

	/**
	 * Initializes this state object.
	 *
	 * @param owner The owner of the join column to create or where it is located
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	protected void initialize(Object owner, IAbstractJoinColumn joinColumn) {

		this.owner      = owner;
		this.joinColumn = joinColumn;

		if (joinColumn != null) {
			this.name                 = joinColumn.getSpecifiedName();
			this.referencedColumnName = joinColumn.getSpecifiedReferencedColumnName();
		}
	}

	/**
	 * Sets the name of the join column.
	 *
	 * @param name The new join column's name or <code>null</code> to use the
	 * default name
	 */
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		firePropertyChanged(NAME_PROPERTY, oldName, name);
	}

	/**
	 * Sets the referenced column name of the join column.
	 *
	 * @param referencedColumnName The new join column's referenced column name
	 * or <code>null</code> to use the default referenced column name
	 */
	public void setReferencedColumnName(String referencedColumnName) {
		String oldReferencedColumnName = this.referencedColumnName;
		this.referencedColumnName = referencedColumnName;
		firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, oldReferencedColumnName, referencedColumnName);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public final void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * Updates the given join column with the values contained in this state
	 * object.
	 *
	 * @param joinColumn The join column to update
	 */
	public void updateJoinColumn(IAbstractJoinColumn joinColumn) {

		// Name
		if (valuesAreDifferent(name, joinColumn.getSpecifiedName())) {
			joinColumn.setSpecifiedName(name);
		}

		// Referenced Column Name
		if (valuesAreDifferent(referencedColumnName, joinColumn.getSpecifiedReferencedColumnName())) {
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public final Validator validator() {
		return this.validator;
	}
}