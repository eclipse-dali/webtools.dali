/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.node.AbstractNode;
import org.eclipse.jpt.common.utility.internal.node.Node;
import org.eclipse.jpt.common.utility.internal.node.Problem;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;

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
	private Class<? extends EclipseLinkConverter> converterType;

	/**
	 * The <code>Validator</code> used to validate this state object.
	 */
	private Validator validator;
	
	/**
	 * The associated persistence unit
	 */
	private EclipseLinkPersistenceUnit pUnit;

	/**
	 * Notifies a change in the data value property.
	 */
	static final String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Notifies a change in the object value property.
	 */
	static final String CONVERTER_TYPE_PROPERTY = "converterType"; //$NON-NLS-1$


	EclipseLinkConverterStateObject(EclipseLinkPersistenceUnit pUnit) {
		super(null);
		this.pUnit = pUnit;
	}

	private void addNameProblemsTo(List<Problem> currentProblems) {
		if (StringTools.stringIsEmpty(this.name)) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConverterStateObject_nameMustBeSpecified, IMessageProvider.ERROR));
		} 
		else if (names().contains(this.name)) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConverterStateObject_nameExists, IMessageProvider.WARNING));
		} else if (ArrayTools.contains(EclipseLinkConvert.RESERVED_CONVERTER_NAMES, this.name)) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConverterStateObject_nameIsReserved, IMessageProvider.ERROR));
		}
	}

	private void addConverterTypeProblemsTo(List<Problem> currentProblems) {
		if (this.converterType == null) {
			currentProblems.add(buildProblem(EclipseLinkUiDetailsMessages.EclipseLinkConverterStateObject_typeMustBeSpecified, IMessageProvider.ERROR));
		}
	}

	@Override
	protected void addProblemsTo(List<Problem> currentProblems) {
		super.addProblemsTo(currentProblems);
		addNameProblemsTo(currentProblems);
		addConverterTypeProblemsTo(currentProblems);
	}
	
	private List<String> names() {
		List<String> names = new ArrayList<String>();
		for (Iterator<EclipseLinkConverter> converters = this.pUnit.allConverters(); converters.hasNext();){
			names.add(converters.next().getName());
		}
		return names ;
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

	Class<? extends EclipseLinkConverter> getConverterType() {
		return this.converterType;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public void setConverterType(Class<? extends EclipseLinkConverter> newConverterType) {
		Class<? extends EclipseLinkConverter> oldConverterType = this.converterType;
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
