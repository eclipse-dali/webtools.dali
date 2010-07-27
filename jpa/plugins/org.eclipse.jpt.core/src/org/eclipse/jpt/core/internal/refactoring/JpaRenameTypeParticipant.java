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
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaProjectManager;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

//TODO RenameTypeArguments.updateSimilarDeclarations() - http://www.eclipse.org/jdt/ui/r3_2/RenameType.html
public class JpaRenameTypeParticipant
	extends RenameParticipant {

	protected IType originalType;
	
	protected final Collection<IType> nestedTypes;

	/**
	 * Store the persistence.xml ReplaceEdits in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<ReplaceEdit>> persistenceXmlClassRefReplaceEdits;

	/**
	 * Store the mapping file ReplaceEdits in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<ReplaceEdit>> mappingFilePersistentTypeReplaceEdits;

	public JpaRenameTypeParticipant() {
		super();
		this.nestedTypes = new ArrayList<IType>();
		this.persistenceXmlClassRefReplaceEdits = new HashMap<IFile, Iterable<ReplaceEdit>>();
		this.mappingFilePersistentTypeReplaceEdits = new HashMap<IFile, Iterable<ReplaceEdit>>();
	}

	@Override
	public String getName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_PARTICIPANT_NAME;
	}

	@Override
	protected boolean initialize(Object element) {
		if (!getArguments().getUpdateReferences()) {
			//we do not want to do any refactoring if the user chooses not to update references
			return false;
		}
		this.originalType = (IType) element;
		this.addNestedTypes(this.originalType);
		return true;
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


	//**************** RefactoringParticipant implementation *****************

	/**
	 * Inform the refactoring processor of any files that are going to change. In the process of determining
	 * this go ahead and build up the appropriate ReplaceEdits to be used in the createChange()
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor monitor, CheckConditionsContext context) throws OperationCanceledException {
		monitor.subTask(JpaCoreRefactoringMessages.JPA_REFACORING_PARTICIPANT_LOADING_JPA_PROJECTS_SUB_TASK_NAME);
		JpaProjectManager jpaProjectManager = JptCorePlugin.getJpaProjectManager();
		if (jpaProjectManager.getJpaProjectsSize() == 0) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, jpaProjectManager.getJpaProjectsSize()*10 + 2);
		sm.subTask(this.getCheckConditionsSubTaskName());
		ResourceChangeChecker checker = (ResourceChangeChecker) context.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = checker.getDeltaFactory();

		for (JpaProject jpaProject : jpaProjectManager.getJpaProjects()) {
			this.createReplaceEdits(sm.newChild(10), jpaProject);
		}
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		for (IFile file : this.persistenceXmlClassRefReplaceEdits.keySet()) {
			deltaFactory.change(file);
		}
		sm.worked(1);
		for (IFile file : this.mappingFilePersistentTypeReplaceEdits.keySet()) {
			deltaFactory.change(file);
		}
		sm.worked(1);
		
		return null;
	}

	/**
	 * This will be appended to the main refactoring task named : 'Checking preconditions...'
	 */
	protected String getCheckConditionsSubTaskName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (this.persistenceXmlClassRefReplaceEdits.isEmpty() && this.mappingFilePersistentTypeReplaceEdits.isEmpty()) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, this.persistenceXmlClassRefReplaceEdits.size() + this.mappingFilePersistentTypeReplaceEdits.size());
		sm.subTask(this.getCreateChangeSubTaskName());
		CompositeChange compositeChange = new CompositeChange(JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_NAME);
		for (IFile persistenceXmlFile : this.persistenceXmlClassRefReplaceEdits.keySet()) {
			this.addPersistenceXmlRenameTypeChange(persistenceXmlFile, compositeChange);
			sm.worked(1);
		}
		for (IFile mappingFile : this.mappingFilePersistentTypeReplaceEdits.keySet()) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			this.addMappingFileRenameTypeChange(mappingFile, compositeChange);
			sm.worked(1);
		}
		//must check for children in case all changes were made in other participant's TextChanges, 
		//want to return null so our node does not appear in the preview tree
		return compositeChange.getChildren().length == 0 ? null : compositeChange;
	}

	/**
	 * This will be appended to the main refactoring task named : 'Creating workspace modifications...'
	 */
	protected String getCreateChangeSubTaskName() {
		return JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	protected void createReplaceEdits(IProgressMonitor monitor, JpaProject jpaProject) throws OperationCanceledException {
		PersistenceUnit persistenceUnit = getPersistenceUnit(jpaProject);
		if (persistenceUnit == null) {
			return;
		}
		SubMonitor sm = SubMonitor.convert(monitor, 1 + persistenceUnit.mappingFileRefsSize());
		Iterable<ReplaceEdit> classRefDeleteEdits = this.createPersistenceXmlReplaceTypeEdits(persistenceUnit);
		sm.worked(1);
		if (!CollectionTools.isEmpty(classRefDeleteEdits)) {
			this.persistenceXmlClassRefReplaceEdits.put(jpaProject.getPersistenceXmlResource().getFile(), classRefDeleteEdits);
		}
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(persistenceUnit.mappingFileRefs())) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			Iterable<ReplaceEdit> mappingFileReplaceEdits = this.createMappingFileReplaceTypeEdits(mappingFileRef);
			if (!CollectionTools.isEmpty(mappingFileReplaceEdits)) {
				IFile file = (IFile) mappingFileRef.getMappingFile().getResource();
				this.mappingFilePersistentTypeReplaceEdits.put(file, mappingFileReplaceEdits);
			}
			sm.worked(1);
		}
	}

	@SuppressWarnings("unchecked")
	private Iterable<ReplaceEdit> createPersistenceXmlReplaceTypeEdits(PersistenceUnit persistenceUnit) {		
		//check isOnClassPath since there could be types with the same name in different projects
		if (persistenceUnit.getJpaProject().getJavaProject().isOnClasspath(this.originalType)) {
			return new CompositeIterable<ReplaceEdit>(
				this.createPersistenceXmlReplaceOriginalTypeEdits(persistenceUnit),
				this.createPersistenceXmlReplaceNestedTypeEdits(persistenceUnit));
		}
		return EmptyIterable.instance();
	}

	private Iterable<ReplaceEdit> createPersistenceXmlReplaceOriginalTypeEdits(PersistenceUnit persistenceUnit) {
		return persistenceUnit.createReplaceTypeEdits(this.originalType, this.getNewName());
	}

	private Iterable<ReplaceEdit> createPersistenceXmlReplaceNestedTypeEdits(final PersistenceUnit persistenceUnit) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<IType, Iterable<ReplaceEdit>>(this.nestedTypes) {
				@Override
				protected Iterable<ReplaceEdit> transform(IType nestedType) {
					String newName = getNewNameForNestedType(nestedType);
					return persistenceUnit.createReplaceTypeEdits(nestedType, newName);
				}
			}
		);
	}

	protected String getNewName() {
		String newName = getArguments().getNewName();
		try {
			if (this.originalType.isMember()) {
				newName = this.originalType.getTypeQualifiedName().substring(0, this.originalType.getTypeQualifiedName().lastIndexOf('$')) + '$' + newName;
			}
		}
		catch (JavaModelException e) {
			JptCorePlugin.log(e);
		}
		return newName;
	}

	protected String getNewNameForNestedType(IType nestedType) {
		return nestedType.getTypeQualifiedName('$').replaceFirst(this.originalType.getElementName(), getArguments().getNewName());
	}

	protected void addPersistenceXmlRenameTypeChange(IFile persistenceXmlFile, CompositeChange compositeChange) {
		Iterable<ReplaceEdit> replaceTypeEdits = this.persistenceXmlClassRefReplaceEdits.get(persistenceXmlFile);

		TextChange textChange = getTextChange(persistenceXmlFile);
		if (textChange == null) {
			textChange = new TextFileChange(JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME, persistenceXmlFile);
			//TODO probably need to figure out TextEditGroups since these can be used to show check boxes under the file in the preview dialog
			//also used to add edits to existing changes??
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
//			textChange.addTextEditGroup(new TextEditGroup("edit persistence unit", multiTextEdit));???
			compositeChange.add(textChange);
		}
		this.addEdits(textChange, replaceTypeEdits);
	}

	@SuppressWarnings("unchecked")
	private Iterable<ReplaceEdit> createMappingFileReplaceTypeEdits(MappingFileRef mappingFileRef) {		
		//check isOnClassPath since there could be types with the same name in different projects
		if (mappingFileRef.getJpaProject().getJavaProject().isOnClasspath(this.originalType)) {
			return new CompositeIterable<ReplaceEdit>(
				this.createMappingFileReplaceOriginalTypeEdits(mappingFileRef),
				this.createMappingFileReplaceNestedTypeEdits(mappingFileRef));
		}
		return EmptyIterable.instance();
	}

	private Iterable<ReplaceEdit> createMappingFileReplaceOriginalTypeEdits(MappingFileRef mappingFileRef) {
		return mappingFileRef.createReplaceTypeEdits(this.originalType, this.getNewName());
	}

	private Iterable<ReplaceEdit> createMappingFileReplaceNestedTypeEdits(final MappingFileRef mappingFileRef) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<IType, Iterable<ReplaceEdit>>(this.nestedTypes) {
				@Override
				protected Iterable<ReplaceEdit> transform(IType nestedType) {
					String newName = getNewNameForNestedType(nestedType);
					return mappingFileRef.createReplaceTypeEdits(nestedType, newName);
				}
			}
		);
	}

	protected void addMappingFileRenameTypeChange(IFile mappingFile, CompositeChange compositeChange) {
		Iterable<ReplaceEdit> replacedTypeEdits = this.mappingFilePersistentTypeReplaceEdits.get(mappingFile);

		TextChange textChange = getTextChange(mappingFile);
		if (textChange == null) {
			textChange = new TextFileChange(JpaCoreRefactoringMessages.JPA_RENAME_TYPE_REFACTORING_CHANGE_MAPPING_FILE_NAME, mappingFile);
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
			compositeChange.add(textChange);
		}
		this.addEdits(textChange, replacedTypeEdits);
	}

	private PersistenceUnit getPersistenceUnit(JpaProject jpaProject) {
		PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
		Persistence persistence = persistenceXml.getPersistence();
		if (persistence == null) {
			return null;
		}
		if (persistence.persistenceUnitsSize() != 1) {
			return null;  // the context model currently only supports 1 persistence unit
		}
		return persistence.persistenceUnits().next();		
	}

	private void addEdits(TextChange textChange, Iterable<? extends TextEdit> textEdits) {
		for (TextEdit textEdit : textEdits) {
			try {
				textChange.addEdit(textEdit);
			}
			catch (MalformedTreeException ex) {
				//log exception and don't add this persistence.xml type deletion to the conflicting change object
				JptCorePlugin.log(ex);
			}
		}
	}
}