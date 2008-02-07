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

import java.util.List;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.node.AbstractNode;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.jpt.utility.internal.node.Problem;

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
	private String name;
	private String referencedColumnName;
	private Validator validator;

	static final String NAME_PROPERTY = "name";
	static final String REFERENCED_COLUMN_NAME_PROPERTY = "referencedColumnName";

	/**
	 * Creates a new <code>AbstractJoinColumnStateObject</code>.
	 */
	public AbstractJoinColumnStateObject() {
		this(null);
	}

	/**
	 * Creates a new <code>AbstractJoinColumnStateObject</code>.
	 *
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public AbstractJoinColumnStateObject(IAbstractJoinColumn joinColumn) {
		super(null);
		initialize(joinColumn);
	}

	private void addNameProblemsTo(List<Problem> currentProblems) {
		if (StringTools.stringIsEmpty(name)) {
			currentProblems.add(buildProblem(JptUiMappingsMessages.AbstractJoinColumnStateObject_NameNotSpecified));
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addProblemsTo(List<Problem> currentProblems) {
		super.addProblemsTo(currentProblems);
		addNameProblemsTo(currentProblems);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected final void checkParent(Node parentNode) {
		// This is the root of the Join Column state object
	}

	public String defaultName() {
		if (this.joinColumn == null) {
			return null;
		}

		return this.joinColumn.getDefaultName();
	}

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

	public IAbstractJoinColumn getJoinColumn() {
		return this.joinColumn;
	}

	public String getName() {
		return this.name;
	}

	public abstract Table getNameTable();

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
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	protected void initialize(IAbstractJoinColumn joinColumn) {
		this.joinColumn = joinColumn;

		if (joinColumn != null) {
			this.name                                = joinColumn.getName();
			this.defaultNameSelected                 = joinColumn.getSpecifiedName() == null;
			this.referencedColumnName                = joinColumn.getReferencedColumnName();
			this.defaultReferencedColumnNameSelected = joinColumn.getSpecifiedReferencedColumnName() == null;
		}
	}

	public boolean isDefaultNameSelected() {
		return this.defaultNameSelected;
	}

	public boolean isDefaultReferencedColumnNameSelected() {
		return this.defaultReferencedColumnNameSelected;
	}

	public void setDefaultNameSelected(boolean defaultNameSelected) {
		this.defaultNameSelected = defaultNameSelected;
	}

	public void setDefaultReferencedColumnNameSelected(boolean defaultReferencedColumnNameSelected) {
		this.defaultReferencedColumnNameSelected = defaultReferencedColumnNameSelected;
	}

	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		firePropertyChanged(NAME_PROPERTY, oldName, name);
	}

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
		if (defaultNameSelected) {

			if (joinColumn.getSpecifiedName() != null) {
				joinColumn.setSpecifiedName(null);
			}
		}
		else if (joinColumn.getSpecifiedName() == null ||
		        !joinColumn.getSpecifiedName().equals(name)){

			joinColumn.setSpecifiedName(name);
		}

		// Referenced Column Name
		if (defaultReferencedColumnNameSelected) {

			if (joinColumn.getSpecifiedReferencedColumnName() != null) {
				joinColumn.setSpecifiedReferencedColumnName(null);
			}
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null ||
		        !joinColumn.getSpecifiedReferencedColumnName().equals(referencedColumnName)){

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