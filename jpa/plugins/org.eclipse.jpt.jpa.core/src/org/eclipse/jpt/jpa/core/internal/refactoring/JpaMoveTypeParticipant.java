/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.refactoring;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.refactoring.JptJpaCoreRefactoringMessages;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.text.edits.ReplaceEdit;

public class JpaMoveTypeParticipant
	extends AbstractJpaMoveJavaElementParticipant {

	public JpaMoveTypeParticipant() {
		super();
	}

	@Override
	public String getName() {
		return JptJpaCoreRefactoringMessages.JPA_MOVE_TYPE_REFACTORING_PARTICIPANT_NAME;
	}

	@Override
	public void addElement(Object element, RefactoringArguments arguments) {
		super.addElement(element, arguments);
		this.addNestedTypes((IType) element, arguments);
	}

	protected void addNestedType(IType renamedType, RefactoringArguments arguments) {
		super.addElement(renamedType, arguments);
		this.addNestedTypes(renamedType, arguments);
	}

	private void addNestedTypes(IType renamedType, RefactoringArguments arguments) {
		for (IType nestedType : TypeTools.getTypes(renamedType)) {
			this.addNestedType(nestedType, arguments);
		}
	}


	//**************** AbstractJpaRenameJavaElementParticipant implementation *****************
	
	@Override
	protected Iterable<ReplaceEdit> createPersistenceXmlReplaceEdits(PersistenceUnit persistenceUnit, IJavaElement javaElement, Object destination) {
		IType type = (IType) javaElement;
		if (((IJavaElement) destination).getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
			return persistenceUnit.createMoveTypeEdits(type, (IPackageFragment) destination);
		}
		return persistenceUnit.createRenameTypeEdits(type, getNewNameForNestedType(type, (IType) destination));
	}

	@Override
	protected Iterable<ReplaceEdit> createMappingFileReplaceEdits(MappingFileRef mappingFileRef, IJavaElement javaElement, Object destination) {
		IType type = (IType) javaElement;
		if (((IJavaElement) destination).getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
			return mappingFileRef.createMoveTypeEdits(type, (IPackageFragment) destination);
		}
		return mappingFileRef.createRenameTypeEdits(type, getNewNameForNestedType(type, (IType) destination));
	}

	protected String getNewNameForNestedType(IType nestedType, IType destination) {
		return nestedType.getTypeQualifiedName('$').replaceFirst(nestedType.getDeclaringType().getElementName(), destination.getElementName());
	}

	protected IPackageFragment getNewPackage() {
		return (IPackageFragment) getArguments().getDestination();
	}

	@Override
	protected String getCheckConditionsSubTaskName() {
		return JptJpaCoreRefactoringMessages.JPA_MOVE_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	protected String getCreateChangeSubTaskName() {
		return JptJpaCoreRefactoringMessages.JPA_MOVE_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	protected String getCompositeChangeName() {
		return JptJpaCoreRefactoringMessages.JPA_MOVE_TYPE_REFACTORING_CHANGE_NAME;
	}

	@Override
	protected String getPersistenceXmlChangeName() {
		return JptJpaCoreRefactoringMessages.JPA_MOVE_TYPE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	}

	@Override
	protected String getMappingFileChangeName() {
		return JptJpaCoreRefactoringMessages.JPA_MOVE_TYPE_REFACTORING_CHANGE_MAPPING_FILE_NAME;
	}
}