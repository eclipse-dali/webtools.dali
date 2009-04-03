/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.List;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.node.AbstractNode;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.jpt.utility.internal.node.Problem;

/**
 * This is the state object used by the <code>EclipseLinkConverterDialog</code>, which stores
 * the current name and validates it when it is modified.
 *
 * @see AddGeneratorDialog
 *
 * @version 2.1
 * @since 2.1
 */
final class AddGeneratorStateObject extends AbstractNode
{
	/**
	 * The initial name or <code>null</code>
	 */
	private String name;

	/**
	 * The initial generatorType or <code>null</code>
	 */
	private String generatorType;

	/**
	 * The <code>Validator</code> used to validate this state object.
	 */
	private Validator validator;

	/**
	 * Notifies a change in the data value property.
	 */
	static final String NAME_PROPERTY = "nameProperty"; //$NON-NLS-1$
	
	/**
	 * Notifies a change in the generator type property.
	 */
	static final String GENERATOR_TYPE_PROPERTY = "generatorTypeProperty"; //$NON-NLS-1$

	/**
	 * Creates a new <code>NewNameStateObject</code>.
	 *
	 * @param name The initial input or <code>null</code> if no initial value can
	 * be specified
	 * @param names The collection of names that can't be used or an empty
	 * collection if none are available
	 */
	AddGeneratorStateObject() {
		super(null);
	}

	private void addNameProblemsTo(List<Problem> currentProblems) {
		if (StringTools.stringIsEmpty(this.name)) {
			currentProblems.add(buildProblem(JptUiOrmMessages.GeneratorStateObject_nameMustBeSpecified));
		}
	}

	private void addGeneratorTypeProblemsTo(List<Problem> currentProblems) {
		if (StringTools.stringIsEmpty(this.generatorType)) {
			currentProblems.add(buildProblem(JptUiOrmMessages.GeneratorStateObject_typeMustBeSpecified));
		}
	}

	@Override
	protected void addProblemsTo(List<Problem> currentProblems) {
		super.addProblemsTo(currentProblems);
		addNameProblemsTo(currentProblems);
		addGeneratorTypeProblemsTo(currentProblems);
	}

	@Override
	protected void checkParent(Node parentNode) {
		//no parent
	}

	public String displayString() {
		return null;
	}

	String getName() {
		return this.name;
	}

	String getGeneratorType() {
		return this.generatorType;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public void setGeneratorType(String newGeneratorType) {
		String old = this.generatorType;
		this.generatorType = newGeneratorType;
		firePropertyChanged(GENERATOR_TYPE_PROPERTY, old, newGeneratorType);
	}

	@Override
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public Validator getValidator() {
		return this.validator;
	}
}
