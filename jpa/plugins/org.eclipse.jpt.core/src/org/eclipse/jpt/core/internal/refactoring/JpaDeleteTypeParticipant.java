/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.refactoring;

import org.eclipse.jdt.core.IType;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;

public class JpaDeleteTypeParticipant
	extends AbstractJpaDeleteTypeParticipant
	implements ISharableParticipant
{

	public JpaDeleteTypeParticipant() {
		super();
	}

	@Override
	protected boolean initialize(Object element) {
		this.addElement(element, getArguments());
		return true;
	}

	@Override
	public String getName() {
		return JpaCoreRefactoringMessages.JPA_DELETE_TYPE_REFACTORING_PARTICIPANT_NAME;
	}


	//****************ISharableParticipant implementation *****************

	/**
	 * This is used when multiple ITypes are deleted.
	 * RefactoringParticipant#initialize(Object) is called for the first deleted IType.
	 * RefactoringParticipant#getArguments() only applies to the first deleted IType
	 */
	public void addElement(Object element, RefactoringArguments arguments) {
		this.addType((IType) element);
	}
}