/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import java.util.List;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.node.AbstractNode;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.jpt.utility.internal.node.Problem;

/**
 * This is the state object used by the <code>EclipseLinkConverterDialog</code>, which stores
 * the current name and validates it when it is modified.
 *
 * @see EclipseLinkConverterDialog
 *
 * @version 2.1
 * @since 2.1
 */
final class EclipseLinkConverterStateObject extends AbstractNode
{
	/**
	 * The initial name or <code>null</code>
	 */
	private String name;

	/**
	 * The initial converterType or <code>null</code>
	 */
	private String converterType;

	/**
	 * The <code>Validator</code> used to validate this state object.
	 */
	private Validator validator;

	/**
	 * Notifies a change in the data value property.
	 */
	static final String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Notifies a change in the object value property.
	 */
	static final String CONVERTER_TYPE_PROPERTY = "converterType"; //$NON-NLS-1$

	/**
	 * Creates a new <code>NewNameStateObject</code>.
	 *
	 * @param name The initial input or <code>null</code> if no initial value can
	 * be specified
	 * @param names The collection of names that can't be used or an empty
	 * collection if none are available
	 */
	EclipseLinkConverterStateObject() {
		super(null);

	}

	private void addNameProblemsTo(List<Problem> currentProblems) {
		if (StringTools.stringIsEmpty(this.name)) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConverterStateObject_nameMustBeSpecified));
		}
	}

	private void addConverterTypeProblemsTo(List<Problem> currentProblems) {
		if (StringTools.stringIsEmpty(this.converterType)) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConverterStateObject_typeMustBeSpecified));
		}
	}

	@Override
	protected void addProblemsTo(List<Problem> currentProblems) {
		super.addProblemsTo(currentProblems);
		addNameProblemsTo(currentProblems);
		addConverterTypeProblemsTo(currentProblems);
	}

	@Override
	protected void checkParent(@SuppressWarnings("unused") Node parentNode) {
		//no parent
	}

	public String displayString() {
		return null;
	}

	String getName() {
		return this.name;
	}

	String getConverterType() {
		return this.converterType;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public void setConverterType(String newConverterType) {
		String oldConverterType = this.converterType;
		this.converterType = newConverterType;
		firePropertyChanged(CONVERTER_TYPE_PROPERTY, oldConverterType, newConverterType);
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
