/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.refactoring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;


public abstract class AbstractJpaDeleteTypeParticipant
	extends DeleteParticipant
{

	/**
	 * Store all the types that are going to be deleted including nestedTypes.
	 */
	protected final Set<IType> allTypes;

	/**
	 * Store the persistence.xml DeleteEdits in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<DeleteEdit>> persistenceXmlDeleteEdits;

	/**
	 * Store the mapping file DeleteEdits in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<DeleteEdit>> mappingFilePersistentTypeDeleteEdits;

	protected AbstractJpaDeleteTypeParticipant() {
		super();
		this.allTypes = new HashSet<IType>();
		this.persistenceXmlDeleteEdits = new HashMap<IFile, Iterable<DeleteEdit>>();
		this.mappingFilePersistentTypeDeleteEdits = new HashMap<IFile, Iterable<DeleteEdit>>();
	}
	
	/**
	 * Nested types are not added to the deleteParticipant when the parent is deleted.
	 * We must handle them in case they are mapped.
	 */
	protected void addType(IType type) {
		this.allTypes.add(type);
		this.addNestedTypes(type);
	}

	private void addNestedTypes(IType type) {
		IType[] nestedTypes;
		try {
			nestedTypes = type.getTypes();
		}
		catch (JavaModelException ex) {
			JptJpaCorePlugin.log(ex);
			return;
		}

		for (IType nestedType : nestedTypes) {
			this.addType(nestedType);
		}
	}

	//**************** RefactoringParticipant implementation *****************

	/**
	 * Inform the refactoring processor of any files that are going to change. In the process of determining
	 * this go ahead and build up the appropriate DeleteEdits to be used in the createChange()
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor monitor, CheckConditionsContext context) throws OperationCanceledException {
		//since the progress bar will hang if a large JPA project is being loaded, 
		//we can at least set the subtask and report no progress. Only happens first time getJpaProjectManager() is called.
		monitor.subTask(JpaCoreRefactoringMessages.JPA_REFACORING_PARTICIPANT_LOADING_JPA_PROJECTS_SUB_TASK_NAME);
		JpaProjectManager jpaProjectManager = JptJpaCorePlugin.getJpaProjectManager();
		if (jpaProjectManager.getJpaProjectsSize() == 0) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, jpaProjectManager.getJpaProjectsSize()*10 + 2);
		sm.subTask(this.getCheckConditionsSubTaskName());
		ResourceChangeChecker checker = (ResourceChangeChecker) context.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = checker.getDeltaFactory();
		
		for (JpaProject jpaProject : jpaProjectManager.getJpaProjects()) {
			this.createDeleteEdits(sm.newChild(10), jpaProject);
		}
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		for (IFile file : this.persistenceXmlDeleteEdits.keySet()) {
			deltaFactory.change(file);
		}
		sm.worked(1);
		for (IFile file : this.mappingFilePersistentTypeDeleteEdits.keySet()) {
			deltaFactory.change(file);
		}
		sm.worked(1);
		
		return null;
	}

	/**
	 * This will be appended to the main refactoring task named : 'Checking preconditions...'
	 */
	protected String getCheckConditionsSubTaskName() {
		return JpaCoreRefactoringMessages.JPA_DELETE_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (this.persistenceXmlDeleteEdits.isEmpty() && this.mappingFilePersistentTypeDeleteEdits.isEmpty()) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, this.persistenceXmlDeleteEdits.size() + this.mappingFilePersistentTypeDeleteEdits.size());
		sm.subTask(this.getCreateChangeSubTaskName());
		CompositeChange compositeChange = new CompositeChange(JpaCoreRefactoringMessages.JPA_DELETE_TYPE_REFACTORING_CHANGE_NAME);
		for (IFile persistenceXmlFile : this.persistenceXmlDeleteEdits.keySet()) {
			this.addPersistenceXmlDeleteTypeChange(persistenceXmlFile, compositeChange);
			sm.worked(1);
		}
		for (IFile mappingFile : this.mappingFilePersistentTypeDeleteEdits.keySet()) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			this.addMappingFileDeleteTypeChange(mappingFile, compositeChange);
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
		return JpaCoreRefactoringMessages.JPA_DELETE_TYPE_REFACTORING_SUB_TASK_NAME;
	}

	protected void createDeleteEdits(IProgressMonitor monitor, JpaProject jpaProject) throws OperationCanceledException {
		PersistenceUnit persistenceUnit = getPersistenceUnit(jpaProject);
		if (persistenceUnit == null) {
			return;
		}
		SubMonitor sm = SubMonitor.convert(monitor, 1 + persistenceUnit.getMappingFileRefsSize());
		Iterable<DeleteEdit> persistenceXmlDeleteEdits = this.createPersistenceXmlDeleteEdits(persistenceUnit);
		if (!CollectionTools.isEmpty(persistenceXmlDeleteEdits)) {
			this.persistenceXmlDeleteEdits.put(jpaProject.getPersistenceXmlResource().getFile(), persistenceXmlDeleteEdits);
		}
		sm.worked(1);
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFileRefs()) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			Iterable<DeleteEdit> mappingFileDeleteEdits = this.createMappingFileDeleteTypeEdits(mappingFileRef);
			if (!CollectionTools.isEmpty(mappingFileDeleteEdits)) {
				this.mappingFilePersistentTypeDeleteEdits.put((IFile) mappingFileRef.getMappingFile().getResource(), mappingFileDeleteEdits);
			}
			sm.worked(1);
		}
	}

	protected Iterable<DeleteEdit> createPersistenceXmlDeleteEdits(final PersistenceUnit persistenceUnit) {
		return new CompositeIterable<DeleteEdit>(
			new TransformationIterable<IType, Iterable<DeleteEdit>>(this.getTypesOnClasspath(persistenceUnit.getJpaProject())) {
				@Override
				protected Iterable<DeleteEdit> transform(IType type) {
					return persistenceUnit.createDeleteTypeEdits(type);
				}
			}
		);
	}

	protected Iterable<IType> getTypesOnClasspath(final JpaProject jpaProject) {
		final IJavaProject javaProject = jpaProject.getJavaProject();
		return new FilteringIterable<IType>(this.allTypes) {
			@Override
			protected boolean accept(IType type) {
				return javaProject.isOnClasspath(type);
			}
		};
	}

	protected void addPersistenceXmlDeleteTypeChange(IFile persistenceXmlFile, CompositeChange compositeChange) {
		Iterable<DeleteEdit> deleteTypeEdits = this.persistenceXmlDeleteEdits.get(persistenceXmlFile);

		TextChange textChange = getTextChange(persistenceXmlFile);
		if (textChange == null) {
			textChange = new TextFileChange(JpaCoreRefactoringMessages.JPA_DELETE_TYPE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME, persistenceXmlFile);
			//TODO probably need to figure out TextEditGroups since these can be used to show check boxes under the file in the preview dialog
			//also used to add edits to existing changes??
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
//			textChange.addTextEditGroup(new TextEditGroup("edit persistence unit", multiTextEdit));???
			compositeChange.add(textChange);
		}
		this.addEdits(textChange, deleteTypeEdits);
	}

	private Iterable<DeleteEdit> createMappingFileDeleteTypeEdits(final MappingFileRef mappingFileRef) {
		return new CompositeIterable<DeleteEdit>(
			new TransformationIterable<IType, Iterable<DeleteEdit>>(this.getTypesOnClasspath(mappingFileRef.getJpaProject())) {
				@Override
				protected Iterable<DeleteEdit> transform(IType type) {
					return mappingFileRef.createDeleteTypeEdits(type);
				}
			}
		);
	}
	
	protected void addMappingFileDeleteTypeChange(IFile mappingFile, CompositeChange compositeChange) {
		Iterable<DeleteEdit> deleteTypeEdits = this.mappingFilePersistentTypeDeleteEdits.get(mappingFile);

		TextChange textChange = getTextChange(mappingFile);
		if (textChange == null) {
			textChange = new TextFileChange(JpaCoreRefactoringMessages.JPA_DELETE_TYPE_REFACTORING_CHANGE_MAPPING_FILE_NAME, mappingFile);
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
			compositeChange.add(textChange);
		}
		this.addEdits(textChange, deleteTypeEdits);
	}

	private PersistenceUnit getPersistenceUnit(JpaProject jpaProject) {
		PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
		if (persistenceXml == null) {
			return null;
		}
		Persistence persistence = persistenceXml.getPersistence();
		if (persistence == null) {
			return null;
		}
		if (persistence.getPersistenceUnitsSize() != 1) {
			return null;  // the context model currently only supports 1 persistence unit
		}
		return persistence.getPersistenceUnits().iterator().next();		
	}

	private void addEdits(TextChange textChange, Iterable<? extends TextEdit> textEdits) {
		for (TextEdit textEdit : textEdits) {
			try {
				textChange.addEdit(textEdit);
			}
			catch (MalformedTreeException ex) {
				//log exception and don't add this persistence.xml type deletion to the conflicting change object
				JptJpaCorePlugin.log(ex);
			}
		}
	}
}