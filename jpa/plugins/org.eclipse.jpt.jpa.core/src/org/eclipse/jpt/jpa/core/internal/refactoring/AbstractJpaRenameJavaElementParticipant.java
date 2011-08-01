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
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
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
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

public abstract class AbstractJpaRenameJavaElementParticipant
	extends RenameParticipant {

	protected IJavaElement originalJavaElement;

	/**
	 * Store the persistence.xml ReplaceEdits in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<ReplaceEdit>> persistenceXmlReplaceEdits;

	/**
	 * Store the mapping file ReplaceEdits in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<ReplaceEdit>> mappingFileReplaceEdits;

	protected AbstractJpaRenameJavaElementParticipant() {
		super();
		this.persistenceXmlReplaceEdits = new HashMap<IFile, Iterable<ReplaceEdit>>();
		this.mappingFileReplaceEdits = new HashMap<IFile, Iterable<ReplaceEdit>>();
	}

	@Override
	protected boolean initialize(Object element) {
		if (!getArguments().getUpdateReferences()) {
			//we do not want to do any refactoring if the user chooses not to update references
			return false;
		}
		this.originalJavaElement = (IJavaElement) element;
		return true;
	}

	protected IJavaElement getOriginalJavaElement() {
		return this.originalJavaElement;
	}


	//**************** RefactoringParticipant implementation *****************

	/**
	 * Inform the refactoring processor of any files that are going to change. In the process of determining
	 * this go ahead and build up the appropriate ReplaceEdits to be used in the createChange()
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor monitor, CheckConditionsContext context) throws OperationCanceledException {
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
			this.createReplaceEdits(sm.newChild(10), jpaProject);
		}
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		for (IFile file : this.persistenceXmlReplaceEdits.keySet()) {
			deltaFactory.change(file);
		}
		sm.worked(1);
		for (IFile file : this.mappingFileReplaceEdits.keySet()) {
			deltaFactory.change(file);
		}
		sm.worked(1);
		
		return null;
	}

	protected abstract String getCompositeChangeName();

	/**
	 * This will be appended to the main refactoring task named : 'Checking preconditions...'
	 */
	protected abstract String getCheckConditionsSubTaskName();

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (this.persistenceXmlReplaceEdits.isEmpty() && this.mappingFileReplaceEdits.isEmpty()) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, this.persistenceXmlReplaceEdits.size() + this.mappingFileReplaceEdits.size());
		sm.subTask(this.getCreateChangeSubTaskName());
		CompositeChange compositeChange = new CompositeChange(this.getCompositeChangeName());
		for (IFile persistenceXmlFile : this.persistenceXmlReplaceEdits.keySet()) {
			this.addPersistenceXmlRenameEdits(persistenceXmlFile, compositeChange);
			sm.worked(1);
		}
		for (IFile mappingFile : this.mappingFileReplaceEdits.keySet()) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			this.addMappingFileRenameEdits(mappingFile, compositeChange);
			sm.worked(1);
		}
		//must check for children in case all changes were made in other participant's TextChanges, 
		//want to return null so our node does not appear in the preview tree
		return compositeChange.getChildren().length == 0 ? null : compositeChange;
	}

	/**
	 * This will be appended to the main refactoring task named : 'Creating workspace modifications...'
	 */
	protected abstract String getCreateChangeSubTaskName();

	protected void createReplaceEdits(IProgressMonitor monitor, JpaProject jpaProject) throws OperationCanceledException {
		PersistenceUnit persistenceUnit = getPersistenceUnit(jpaProject);
		if (persistenceUnit == null) {
			return;
		}
		SubMonitor sm = SubMonitor.convert(monitor, 1 + persistenceUnit.getMappingFileRefsSize());
		Iterable<ReplaceEdit> classRefDeleteEdits = this.createPersistenceXmlReplaceEditsCheckClasspath(persistenceUnit);
		sm.worked(1);
		if (!CollectionTools.isEmpty(classRefDeleteEdits)) {
			this.persistenceXmlReplaceEdits.put(jpaProject.getPersistenceXmlResource().getFile(), classRefDeleteEdits);
		}
		for (MappingFileRef mappingFileRef : persistenceUnit.getMappingFileRefs()) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			Iterable<ReplaceEdit> mappingFileReplaceEdits = this.createMappingFileReplaceEditsCheckClasspath(mappingFileRef);
			if (!CollectionTools.isEmpty(mappingFileReplaceEdits)) {
				IFile file = (IFile) mappingFileRef.getMappingFile().getResource();
				this.mappingFileReplaceEdits.put(file, mappingFileReplaceEdits);
			}
			sm.worked(1);
		}
	}
	
	protected Iterable<ReplaceEdit> createPersistenceXmlReplaceEditsCheckClasspath(PersistenceUnit persistenceUnit) {		
		//check isOnClassPath since there could be types with the same name in different projects
		if (persistenceUnit.getJpaProject().getJavaProject().isOnClasspath(this.originalJavaElement)) {
			return createPersistenceXmlReplaceEdits(persistenceUnit);
		}
		return EmptyIterable.instance();
	}

	protected abstract Iterable<ReplaceEdit> createPersistenceXmlReplaceEdits(PersistenceUnit persistenceUnit);

	protected void addPersistenceXmlRenameEdits(IFile persistenceXmlFile, CompositeChange compositeChange) {
		Iterable<ReplaceEdit> replacePackageEdits = this.persistenceXmlReplaceEdits.get(persistenceXmlFile);

		TextChange textChange = getTextChange(persistenceXmlFile);
		if (textChange == null) {
			textChange = new TextFileChange(this.getPersistenceXmlChangeName(), persistenceXmlFile);
			//TODO probably need to figure out TextEditGroups since these can be used to show check boxes under the file in the preview dialog
			//also used to add edits to existing changes??
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
//			textChange.addTextEditGroup(new TextEditGroup("edit persistence unit", multiTextEdit));???
			compositeChange.add(textChange);
		}
		this.addEdits(textChange, replacePackageEdits);
	}
	
	protected abstract String getPersistenceXmlChangeName();

	protected Iterable<ReplaceEdit> createMappingFileReplaceEditsCheckClasspath(MappingFileRef mappingFileRef) {		
		//check isOnClassPath since there could be types with the same name in different projects
		if (mappingFileRef.getJpaProject().getJavaProject().isOnClasspath(this.originalJavaElement)) {
			return this.createMappingFileReplaceEdits(mappingFileRef);
		}
		return EmptyIterable.instance();
	}
	
	protected abstract Iterable<ReplaceEdit> createMappingFileReplaceEdits(MappingFileRef mappingFileRef);

	
	protected void addMappingFileRenameEdits(IFile mappingFile, CompositeChange compositeChange) {
		Iterable<ReplaceEdit> replacedTypeEdits = this.mappingFileReplaceEdits.get(mappingFile);

		TextChange textChange = getTextChange(mappingFile);
		if (textChange == null) {
			textChange = new TextFileChange(this.getMappingFileChangeName(), mappingFile);
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
			compositeChange.add(textChange);
		}
		this.addEdits(textChange, replacedTypeEdits);
	}

	protected abstract String getMappingFileChangeName();

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