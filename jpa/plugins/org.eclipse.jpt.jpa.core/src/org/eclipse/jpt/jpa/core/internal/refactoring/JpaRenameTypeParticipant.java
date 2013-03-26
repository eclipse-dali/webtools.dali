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

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.refactoring.JptJpaCoreRefactoringMessages;
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
		return JptJpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_PARTICIPANT_NAME;
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
		for (IType nestedType : TypeTools.getTypes(renamedType)) {
			this.addNestedType(nestedType);
		}
	}


	//**************** AbstractJpaRenameJavaElementParticipant implementation *****************
	
	@SuppressWarnings("unchecked")
	@Override
	protected Iterable<ReplaceEdit> createPersistenceXmlReplaceEdits(PersistenceUnit persistenceUnit) {
		return IterableTools.concatenate(
			this.createPersistenceXmlReplaceOriginalTypeEdits(persistenceUnit),
			this.createPersistenceXmlReplaceNestedTypeEdits(persistenceUnit));
	}

	private Iterable<ReplaceEdit> createPersistenceXmlReplaceOriginalTypeEdits(PersistenceUnit persistenceUnit) {
		return persistenceUnit.createRenameTypeEdits(this.getOriginalType(), this.getNewName());
	}

	private Iterable<ReplaceEdit> createPersistenceXmlReplaceNestedTypeEdits(PersistenceUnit persistenceUnit) {
		return IterableTools.children(this.nestedTypes, new PersistenceUnitNestedTypeRenameTypeEditsTransformer(persistenceUnit));
	}

	public class PersistenceUnitNestedTypeRenameTypeEditsTransformer
		extends TransformerAdapter<IType, Iterable<ReplaceEdit>>
	{
		protected final PersistenceUnit persistenceUnit;
		public PersistenceUnitNestedTypeRenameTypeEditsTransformer(PersistenceUnit persistenceUnit) {
			super();
			this.persistenceUnit = persistenceUnit;
		}
		@Override
		public Iterable<ReplaceEdit> transform(IType nestedType) {
			String newName = JpaRenameTypeParticipant.this.getNewNameForNestedType(nestedType);
			return this.persistenceUnit.createRenameTypeEdits(nestedType, newName);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Iterable<ReplaceEdit> createMappingFileReplaceEdits(MappingFileRef mappingFileRef) {
		return IterableTools.concatenate(
			this.createMappingFileReplaceOriginalTypeEdits(mappingFileRef),
			this.createMappingFileReplaceNestedTypeEdits(mappingFileRef));
	}

	private Iterable<ReplaceEdit> createMappingFileReplaceOriginalTypeEdits(MappingFileRef mappingFileRef) {
		return mappingFileRef.createRenameTypeEdits(this.getOriginalType(), this.getNewName());
	}

	private Iterable<ReplaceEdit> createMappingFileReplaceNestedTypeEdits(final MappingFileRef mappingFileRef) {
		return IterableTools.children(this.nestedTypes, new MappingFileNestedTypeRenameTypeEditsTransformer(mappingFileRef));
	}

	public class MappingFileNestedTypeRenameTypeEditsTransformer
		extends TransformerAdapter<IType, Iterable<ReplaceEdit>>
	{
		protected final MappingFileRef mappingFileRef;
		public MappingFileNestedTypeRenameTypeEditsTransformer(MappingFileRef mappingFileRef) {
			super();
			this.mappingFileRef = mappingFileRef;
		}
		@Override
		public Iterable<ReplaceEdit> transform(IType nestedType) {
			String newName = JpaRenameTypeParticipant.this.getNewNameForNestedType(nestedType);
			return this.mappingFileRef.createRenameTypeEdits(nestedType, newName);
		}
	}

	protected String getNewName() {
		String newName = getArguments().getNewName();
		try {
			if (this.getOriginalType().isMember()) {
				newName = this.getOriginalType().getTypeQualifiedName().substring(0, this.getOriginalType().getTypeQualifiedName().lastIndexOf('$')) + '$' + newName;
			}
		}
		catch (JavaModelException e) {
			JptJpaCorePlugin.instance().logError(e);
		}
		return newName;
	}

	protected String getNewNameForNestedType(IType nestedType) {
		return nestedType.getTypeQualifiedName('$').replaceFirst(this.getOriginalType().getElementName(), getArguments().getNewName());
	}

	@Override
	protected String getCheckConditionsSubTaskName() {
		return JptJpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	protected String getCreateChangeSubTaskName() {
		return JptJpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	protected String getCompositeChangeName() {
		return JptJpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_NAME;
	}

	@Override
	protected String getPersistenceXmlChangeName() {
		return JptJpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	}

	@Override
	protected String getMappingFileChangeName() {
		return JptJpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_MAPPING_FILE_NAME;
	}
}
