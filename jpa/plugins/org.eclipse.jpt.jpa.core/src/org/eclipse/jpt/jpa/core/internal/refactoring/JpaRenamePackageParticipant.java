/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.refactoring;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.text.edits.ReplaceEdit;

public class JpaRenamePackageParticipant
	extends AbstractJpaRenameJavaElementParticipant {


	public JpaRenamePackageParticipant() {
		super();
	}

	@Override
	public String getName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_PACKAGE_REFACTORING_PARTICIPANT_NAME;
	}

	protected IPackageFragment getOriginalPackage() {
		return (IPackageFragment) super.getOriginalJavaElement();
	}


	//**************** AbstractJpaRenameJavaElementParticipant implementation *****************

	@Override
	protected Iterable<ReplaceEdit> createPersistenceXmlReplaceEdits(PersistenceUnit persistenceUnit) {	
		return persistenceUnit.createRenamePackageEdits(this.getOriginalPackage(), this.getNewName());
	}

	@Override
	protected Iterable<ReplaceEdit> createMappingFileReplaceEdits(MappingFileRef mappingFileRef) {
		return mappingFileRef.createRenamePackageEdits(this.getOriginalPackage(), this.getNewName());
	}

	protected String getNewName() {
		return getArguments().getNewName();
	}

	@Override
	protected String getCheckConditionsSubTaskName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_PACKAGE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	protected String getCreateChangeSubTaskName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_PACKAGE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	protected String getCompositeChangeName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_PACKAGE_REFACTORING_CHANGE_NAME;
	}

	@Override
	protected String getPersistenceXmlChangeName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_PACKAGE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	}

	@Override
	protected String getMappingFileChangeName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_PACKAGE_REFACTORING_CHANGE_MAPPING_FILE_NAME;
	}
}