/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import java.util.Collection;
import java.util.List;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.node.AbstractNode;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.jpt.utility.internal.node.Problem;

/**
 * This is the state object used by the <code>NewNameDialog</code>, which stores
 * the current name and validates it when it is modified.
 *
 * @see NewNameDialog
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
final class NewNameStateObject extends AbstractNode
{
	/**
	 * The initial input or <code>null</code> if no initial value can be
	 * specified.
	 */
	private String name;

	/**
	 * The collection of names that can't be used or an empty collection if none
	 * are available.
	 */
	private Collection<String> names;

	/**
	 * The <code>Validator</code> used to validate this state object.
	 */
	private Validator validator;

	/**
	 * Notifies a change in the name property.
	 */
	static final String NAME_PROPERTY = "name";

	/**
	 * Creates a new <code>NewNameStateObject</code>.
	 *
	 * @param name The initial input or <code>null</code> if no initial value can
	 * be specified
	 * @param names The collection of names that can't be used or an empty
	 * collection if none are available
	 */
	NewNameStateObject(String name, Collection<String> names) {
		super(null);

		this.name  = name;
		this.names = names;
	}

	/**
	 * Validates the name property.
	 *
	 * @param currentProblems The list to which <code>Problem</code>s can be
	 * added
	 */
	private void addNameProblems(List<Problem> currentProblems) {

		if (StringTools.stringIsEmpty(name)) {
			currentProblems.add(buildProblem(JptUiMappingsMessages.NewNameStateObject_nameMustBeSpecified));
		}
		else if (names.contains(name.trim())) {
			currentProblems.add(buildProblem(JptUiMappingsMessages.NewNameStateObject_nameAlreadyExists));
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addProblemsTo(List<Problem> currentProblems)
	{
		super.addProblemsTo(currentProblems);
		addNameProblems(currentProblems);
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
		return null;
	}

	/**
	 * Returns the current name stored in this state object.
	 *
	 * @return The current name or <code>null</code>
	 */
	String getName() {
		return name;
	}

	/**
	 * Sets the current name stored in this state object or <code>null</code> to
	 * clear it.
	 *
	 * @param name The new name or <code>null</code>
	 */
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		firePropertyChanged(NAME_PROPERTY, oldName, name);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Validator getValidator() {
		return validator;
	}
}
