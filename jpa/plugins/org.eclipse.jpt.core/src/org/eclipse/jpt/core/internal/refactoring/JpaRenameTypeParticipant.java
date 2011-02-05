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

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.text.edits.ReplaceEdit;

//TODO RenameTypeArguments.updateSimilarDeclarations() - http://www.eclipse.org/jdt/ui/r3_2/RenameType.html
public class JpaRenameTypeParticipant
	extends AbstractJpaRenameJavaElementParticipant {

	protected final Collection<IType> nestedTypes;

	public JpaRenameTypeParticipant() {
		super();
		this.nestedTypes = new ArrayList<IType>();
	}

	@Override
	public String getName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_PARTICIPANT_NAME;
	}

	protected IType getOriginalType() {
		return (IType) super.getOriginalJavaElement();
	}

	@Override
	protected boolean initialize(Object element) {
		boolean initialize = super.initialize(element);
		if (initialize) {
			this.addNestedTypes(this.getOriginalType());
			return true;
		}
		return false;
	}

	protected void addNestedType(IType renamedType) {
		this.nestedTypes.add(renamedType);
		this.addNestedTypes(renamedType);
	}

	private void addNestedTypes(IType renamedType) {
		IType[] nestedTypes;
		try {
			nestedTypes = renamedType.getTypes();
		}
		catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return;
		}

		for (IType nestedType : nestedTypes) {
			this.addNestedType(nestedType);
		}
	}


	//**************** AbstractJpaRenameJavaElementParticipant implementation *****************
	
	@SuppressWarnings("unchecked")
	@Override
	protected Iterable<ReplaceEdit> createPersistenceXmlReplaceEdits(PersistenceUnit persistenceUnit) {
		return new CompositeIterable<ReplaceEdit>(
			this.createPersistenceXmlReplaceOriginalTypeEdits(persistenceUnit),
			this.createPersistenceXmlReplaceNestedTypeEdits(persistenceUnit));
	}

	private Iterable<ReplaceEdit> createPersistenceXmlReplaceOriginalTypeEdits(PersistenceUnit persistenceUnit) {
		return persistenceUnit.createRenameTypeEdits(this.getOriginalType(), this.getNewName());
	}

	private Iterable<ReplaceEdit> createPersistenceXmlReplaceNestedTypeEdits(final PersistenceUnit persistenceUnit) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<IType, Iterable<ReplaceEdit>>(this.nestedTypes) {
				@Override
				protected Iterable<ReplaceEdit> transform(IType nestedType) {
					String newName = getNewNameForNestedType(nestedType);
					return persistenceUnit.createRenameTypeEdits(nestedType, newName);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Iterable<ReplaceEdit> createMappingFileReplaceEdits(MappingFileRef mappingFileRef) {
		return new CompositeIterable<ReplaceEdit>(
			this.createMappingFileReplaceOriginalTypeEdits(mappingFileRef),
			this.createMappingFileReplaceNestedTypeEdits(mappingFileRef));
	}

	private Iterable<ReplaceEdit> createMappingFileReplaceOriginalTypeEdits(MappingFileRef mappingFileRef) {
		return mappingFileRef.createRenameTypeEdits(this.getOriginalType(), this.getNewName());
	}

	private Iterable<ReplaceEdit> createMappingFileReplaceNestedTypeEdits(final MappingFileRef mappingFileRef) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<IType, Iterable<ReplaceEdit>>(this.nestedTypes) {
				@Override
				protected Iterable<ReplaceEdit> transform(IType nestedType) {
					String newName = getNewNameForNestedType(nestedType);
					return mappingFileRef.createRenameTypeEdits(nestedType, newName);
				}
			}
		);
	}

	protected String getNewName() {
		String newName = getArguments().getNewName();
		try {
			if (this.getOriginalType().isMember()) {
				newName = this.getOriginalType().getTypeQualifiedName().substring(0, this.getOriginalType().getTypeQualifiedName().lastIndexOf('$')) + '$' + newName;
			}
		}
		catch (JavaModelException e) {
			JptCorePlugin.log(e);
		}
		return newName;
	}

	protected String getNewNameForNestedType(IType nestedType) {
		return nestedType.getTypeQualifiedName('$').replaceFirst(this.getOriginalType().getElementName(), getArguments().getNewName());
	}

	@Override
	protected String getCheckConditionsSubTaskName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	protected String getCreateChangeSubTaskName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	protected String getCompositeChangeName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_NAME;
	}

	@Override
	protected String getPersistenceXmlChangeName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	}

	@Override
	protected String getMappingFileChangeName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_MAPPING_FILE_NAME;
	}
}