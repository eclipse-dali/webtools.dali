/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.node.AbstractNode;
import org.eclipse.jpt.common.utility.node.Node;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;

/**
 * This is the state object used by the <code>AddQueryDialog</code>, which stores
 * the current name and validates it when it is modified.
 *
 * @see AddQueryDialog
 *
 * @version 2.1
 * @since 2.1
 */
public final class AddQueryStateObject extends AbstractNode
{
	/**
	 * The initial name or <code>null</code>
	 */
	private String name;

	/**
	 * The initial queryType or <code>null</code>
	 */
	private String queryType;

	/**
	 * The <code>Validator</code> used to validate this state object.
	 */
	private Validator validator;

	/**
	 * The associated persistence unit
	 */
	private PersistenceUnit pUnit;
	
	/**
	 * Notifies a change in the data value property.
	 */
	static final String NAME_PROPERTY = "nameProperty"; //$NON-NLS-1$
	
	/**
	 * Notifies a change in the query type property.
	 */
	static final String QUERY_TYPE_PROPERTY = "queryTypeProperty"; //$NON-NLS-1$

	/**
	 * Creates a new <code>NewNameStateObject</code>.
	 *
	 * @param name The initial input or <code>null</code> if no initial value can
	 * be specified
	 * @param names The collection of names that can't be used or an empty
	 * collection if none are available
	 */
	public AddQueryStateObject(PersistenceUnit pUnit) {
		super(null);
		this.pUnit = pUnit;
	}

	private void addNameProblemsTo(List<Problem> currentProblems) {
		if (StringTools.isBlank(this.name)) {
			currentProblems.add(buildProblem(JptJpaUiDetailsMessages.QUERYSTATEOBJECT_NAME_MUST_BE_SPECIFIED, IMessageProvider.ERROR));
		}
		else if (names().contains(this.name)){
			currentProblems.add(buildProblem(JptJpaUiDetailsMessages.ADD_QUERY_DIALOG_NAME_EXISTS, IMessageProvider.WARNING));
		}
	}

	private void addQueryTypeProblemsTo(List<Problem> currentProblems) {
		if (StringTools.isBlank(this.queryType)) {
			currentProblems.add(buildProblem(JptJpaUiDetailsMessages.QUERYSTATEOBJECT_TYPE_MUST_BE_SPECIFIED, IMessageProvider.ERROR));
		}
	}

	@Override
	protected void addProblemsTo(List<Problem> currentProblems) {
		super.addProblemsTo(currentProblems);
		addNameProblemsTo(currentProblems);
		addQueryTypeProblemsTo(currentProblems);
	}

	private List<String> names(){
			List<String> names = new ArrayList<String>();
			for (Query query : this.pUnit.getQueries()){
				String name = query.getName();
				names.add(name);
			}
			return names;
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

	String getQueryType() {
		return this.queryType;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public void setQueryType(String newQueryType) {
		String old = this.queryType;
		this.queryType = newQueryType;
		firePropertyChanged(QUERY_TYPE_PROPERTY, old, newQueryType);
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
