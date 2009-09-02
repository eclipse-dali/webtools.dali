/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import java.util.Collection;
import java.util.List;
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
 * @version 2.1
 * @since 2.1
 */
@SuppressWarnings("nls")
final class EclipseLinkConversionValueStateObject extends AbstractNode
{
	/**
	 * The initial dataValue or <code>null</code>
	 */
	private String dataValue;
	
	/**
	 * The collection of data values that can't be used or an empty collection
	 */
	private Collection<String> dataValues;

	/**
	 * The initial objectValue or <code>null</code>
	 */
	private String objectValue;

	/**
	 * The <code>Validator</code> used to validate this state object.
	 */
	private Validator validator;

	/**
	 * Notifies a change in the data value property.
	 */
	static final String DATA_VALUE_PROPERTY = "dataValue";
	
	/**
	 * Notifies a change in the object value property.
	 */
	static final String OBJECT_VALUE_PROPERTY = "objectValue";

	/**
	 * Creates a new <code>NewNameStateObject</code>.
	 *
	 * @param name The initial input or <code>null</code> if no initial value can
	 * be specified
	 * @param names The collection of names that can't be used or an empty
	 * collection if none are available
	 */
	EclipseLinkConversionValueStateObject(String dataValue, String objectValue, Collection<String> dataValues) {
		super(null);

		this.dataValue  = dataValue;
		this.objectValue = objectValue;
		this.dataValues = dataValues;
	}

	private void addDataValueProblemsTo(List<Problem> currentProblems) {
		if (StringTools.stringIsEmpty(this.dataValue)) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConversionValueStateObject_dataValueMustBeSpecified));
		}
		else if (this.dataValues.contains(this.dataValue)) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConversionValueStateObject_dataValueAlreadyExists));
		}
	}

	private void addObjectValueProblemsTo(List<Problem> currentProblems) {
		if (StringTools.stringIsEmpty(this.objectValue)) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConversionValueStateObject_objectValueMustBeSpecified));
		}
	}

	@Override
	protected void addProblemsTo(List<Problem> currentProblems) {
		super.addProblemsTo(currentProblems);
		addDataValueProblemsTo(currentProblems);
		addObjectValueProblemsTo(currentProblems);
	}

	@Override
	protected void checkParent(Node parentNode) {
		//not parent
	}

	public String displayString() {
		return null;
	}

	String getDataValue() {
		return this.dataValue;
	}

	String getObjectValue() {
		return this.objectValue;
	}

	public void setDataValue(String dataValue) {
		String oldDataValue = this.dataValue;
		this.dataValue = dataValue;
		firePropertyChanged(DATA_VALUE_PROPERTY, oldDataValue, dataValue);
	}

	public void setObjectValue(String objectValue) {
		String oldObjectValue = this.objectValue;
		this.objectValue = objectValue;
		firePropertyChanged(OBJECT_VALUE_PROPERTY, oldObjectValue, objectValue);
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
