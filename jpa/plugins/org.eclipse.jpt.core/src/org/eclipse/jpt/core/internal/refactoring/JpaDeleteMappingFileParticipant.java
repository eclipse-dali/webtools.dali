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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaProjectManager;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteArguments;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * Participant in the delete refactoring of {@link IFile}s with content type {@link JptCorePlugin#MAPPING_FILE_CONTENT_TYPE}.
 * If the deleted mapping file is listed in a persistence.xml file of any JpaProject
 * then a Change object is created that will delete that reference from the file.
 */
public class JpaDeleteMappingFileParticipant
	extends DeleteParticipant 
	implements ISharableParticipant
{

	/**
	 * Store the {@link IFile}s to be deleted with content type {@link JptCorePlugin#MAPPING_FILE_CONTENT_TYPE}
	 * and their corresponding {@link DeleteArguments}
	 */
	protected final Map<IFile, DeleteArguments> mappingFiles;

	/**
	 * Store the persistence.xml DeleteEdits in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<DeleteEdit>> persistenceXmlMappingFileDeleteEdits;

	public JpaDeleteMappingFileParticipant() {
		super();
		this.mappingFiles = new HashMap<IFile, DeleteArguments>();
		this.persistenceXmlMappingFileDeleteEdits = new HashMap<IFile, Iterable<DeleteEdit>>();
	}
	
	@Override
	public String getName() {
		return JpaCoreRefactoringMessages.JPA_DELETE_MAPPING_FILE_REFACTORING_PARTICIPANT_NAME;
	}

	@Override
	protected boolean initialize(Object element) {
		this.addElement(element, getArguments());
		return true;
	}


	//****************ISharableParticipant implementation *****************
	/**
	 * This is used when multiple mapping files are deleted.
	 * RefactoringParticipant#initialize(Object) is called for the first deleted IFile.
	 * RefactoringParticipant#getArguments() only applies to the first deleted IFile
	 */
	public void addElement(Object element, RefactoringArguments arguments) {
		this.mappingFiles.put((IFile) element, (DeleteArguments) arguments);
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
		JpaProjectManager jpaProjectManager = JptCorePlugin.getJpaProjectManager();
		if (jpaProjectManager.getJpaProjectsSize() == 0) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, jpaProjectManager.getJpaProjectsSize()*10 + 1);
		sm.subTask(JpaCoreRefactoringMessages.JPA_DELETE_MAPPING_FILE_REFACTORING_SUB_TASK_NAME);
		ResourceChangeChecker checker = (ResourceChangeChecker) context.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = checker.getDeltaFactory();

		for (JpaProject jpaProject : JptCorePlugin.getJpaProjectManager().getJpaProjects()) {
			this.createDeleteEdits(jpaProject);
			sm.worked(10);
		}
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		for (IFile persistenceXmlFile : this.persistenceXmlMappingFileDeleteEdits.keySet()) {
			deltaFactory.change(persistenceXmlFile);
		}
		sm.worked(1);
	
		return null;
	}

	protected void createDeleteEdits(JpaProject jpaProject) throws OperationCanceledException {
		PersistenceUnit persistenceUnit = getPersistenceUnit(jpaProject);
		if (persistenceUnit == null) {
			return;
		}
		Iterable<DeleteEdit> classRefDeleteEdits = this.createSpecifiedMappingFileRefDeleteEdits(persistenceUnit);
		if (!CollectionTools.isEmpty(classRefDeleteEdits)) {
			this.persistenceXmlMappingFileDeleteEdits.put(jpaProject.getPersistenceXmlResource().getFile(), classRefDeleteEdits);
		}
	}

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (this.persistenceXmlMappingFileDeleteEdits.isEmpty()) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, this.persistenceXmlMappingFileDeleteEdits.size());
		sm.subTask(JpaCoreRefactoringMessages.JPA_DELETE_MAPPING_FILE_REFACTORING_SUB_TASK_NAME);
		CompositeChange compositeChange = new CompositeChange(JpaCoreRefactoringMessages.JPA_DELETE_MAPPING_FILE_REFACTORING_CHANGE_NAME);
		for (IFile persistenceXmlFile : this.persistenceXmlMappingFileDeleteEdits.keySet()) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			this.addPersistenceXmlDeleteMappingFileChange(persistenceXmlFile, compositeChange);			
		}
		//must check for children in case all changes were made in other participants TextChanges, 
		//want to return null so our node does not appear in the preview tree
		return compositeChange.getChildren().length == 0 ? null : compositeChange;
	}

	private Iterable<DeleteEdit> createSpecifiedMappingFileRefDeleteEdits(final PersistenceUnit persistenceUnit) {
		return new CompositeIterable<DeleteEdit>(
			new TransformationIterable<IFile, Iterable<DeleteEdit>>(this.mappingFiles.keySet()) {
				@Override
				protected Iterable<DeleteEdit> transform(IFile file) {
					return persistenceUnit.createDeleteMappingFileEdits(file);
				}
			}
		);
	}
	
	protected void addPersistenceXmlDeleteMappingFileChange(IFile persistenceXmlFile, CompositeChange compositeChange) {
		TextChange textChange = getTextChange(persistenceXmlFile);
		if (textChange == null) {
			textChange = new TextFileChange(JpaCoreRefactoringMessages.JPA_DELETE_MAPPING_FILE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME, persistenceXmlFile);
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
			compositeChange.add(textChange);
		}
		Iterable<DeleteEdit> mappingFileDeleteEdits = this.persistenceXmlMappingFileDeleteEdits.get(persistenceXmlFile);
		this.addEdits(textChange, mappingFileDeleteEdits);
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
		if (persistence.persistenceUnitsSize() != 1) {
			return null; // the context model currently only supports 1 persistence unit
		}
		return persistence.persistenceUnits().next();
	}

	private void addEdits(TextChange textChange, Iterable<? extends TextEdit> textEdits) {
		for (TextEdit textEdit : textEdits) {
			try {
				textChange.addEdit(textEdit);
			}
			catch (MalformedTreeException e) {
				//log exception and don't add this persistence.xml type deletion to the conflicting change object
				JptCorePlugin.log(e);
			}
		}
	}
}