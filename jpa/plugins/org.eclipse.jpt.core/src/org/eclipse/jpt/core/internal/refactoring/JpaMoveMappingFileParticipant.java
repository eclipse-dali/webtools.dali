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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
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
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * Participant in the rename refactoring of {@link IFile}s with content type {@link JptCorePlugin#MAPPING_FILE_CONTENT_TYPE}.
 * If the renamed mapping file is listed in a persistence.xml file of any JpaProject
 * then a Change object is created that will rename that reference from the file.
 * If the renamed mapping file is an implied mapping file, then an actual reference to the mapping file will be specified.
 */
public class JpaMoveMappingFileParticipant
	extends MoveParticipant
	implements ISharableParticipant
{

	/**
	 * Store the {@link IFile}s to be renamed with content type {@link JptCorePlugin#MAPPING_FILE_CONTENT_TYPE}
	 * and their corresponding {@link MoveArguments}
	 */
	protected final Map<IFile, MoveArguments> originalMappingFiles;

	/**
	 * Store the persistence.xml ReplaceEdit in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<ReplaceEdit>> persistenceXmlMappingFileReplaceEdits;

	public JpaMoveMappingFileParticipant() {
		super();
		this.originalMappingFiles = new HashMap<IFile, MoveArguments>();
		this.persistenceXmlMappingFileReplaceEdits = new HashMap<IFile, Iterable<ReplaceEdit>>();
	}
	
	@Override
	public String getName() {
		return JpaCoreRefactoringMessages.JPA_MOVE_MAPPING_FILE_REFACTORING_PARTICIPANT_NAME;
	}

	@Override
	protected boolean initialize(Object element) {
		if (!getArguments().getUpdateReferences()) {
			return false;
		}
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
		this.originalMappingFiles.put((IFile) element, (MoveArguments) arguments);
	}

	protected MoveArguments getArguments(IFile element) {
		return this.originalMappingFiles.get(element);
	}


	//**************** RefactoringParticipant implementation *****************

	/**
	 * Inform the refactoring processor of any files that are going to change. In the process of determining
	 * this go ahead and build up the appropriate ReplaceEdits to be used in the createChange()
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
		sm.subTask(JpaCoreRefactoringMessages.JPA_MOVE_MAPPING_FILE_REFACTORING_SUB_TASK_NAME);
		ResourceChangeChecker checker = (ResourceChangeChecker) context.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = checker.getDeltaFactory();

		for (JpaProject jpaProject : JptCorePlugin.getJpaProjectManager().getJpaProjects()) {
			this.createReplaceEdits(jpaProject);
			sm.worked(10);
		}
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		for (IFile persistenceXmlFile : this.persistenceXmlMappingFileReplaceEdits.keySet()) {
			deltaFactory.change(persistenceXmlFile);
		}
		sm.worked(1);
	
		return null;
	}

	protected void createReplaceEdits(JpaProject jpaProject) throws OperationCanceledException {
		PersistenceUnit persistenceUnit = getPersistenceUnit(jpaProject);
		if (persistenceUnit == null) {
			return;
		}
		Iterable<ReplaceEdit> replaceEdits = this.createPersistenceUnitReplaceEditsCheckClasspath(persistenceUnit);
		if (!CollectionTools.isEmpty(replaceEdits)) {
			this.persistenceXmlMappingFileReplaceEdits.put(jpaProject.getPersistenceXmlResource().getFile(), replaceEdits);
		}
	}

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (this.persistenceXmlMappingFileReplaceEdits.isEmpty()) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, this.persistenceXmlMappingFileReplaceEdits.size());
		sm.subTask(JpaCoreRefactoringMessages.JPA_MOVE_MAPPING_FILE_REFACTORING_SUB_TASK_NAME);
		CompositeChange compositeChange = new CompositeChange(JpaCoreRefactoringMessages.JPA_MOVE_MAPPING_FILE_REFACTORING_CHANGE_NAME);
		for (IFile persistenceXmlFile : this.persistenceXmlMappingFileReplaceEdits.keySet()) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			this.addPersistenceXmlRenameMappingFileChange(persistenceXmlFile, compositeChange);			
		}
		//must check for children in case all changes were made in other participants TextChanges, 
		//want to return null so our node does not appear in the preview tree
		return compositeChange.getChildren().length == 0 ? null : compositeChange;
	}


	protected Iterable<ReplaceEdit> createPersistenceUnitReplaceEditsCheckClasspath(final PersistenceUnit persistenceUnit) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<IFile, Iterable<ReplaceEdit>>(this.getOriginalFoldersOnClasspath(persistenceUnit.getJpaProject())) {
				@Override
				protected Iterable<ReplaceEdit> transform(IFile mappingFile) {
					return createPersistenceUnitReplaceEdits(persistenceUnit, mappingFile, (IFolder) getArguments(mappingFile).getDestination());
				}
			}
		);
	}

	protected Iterable<IFile> getOriginalFoldersOnClasspath(final JpaProject jpaProject) {
		final IJavaProject javaProject = jpaProject.getJavaProject();
		return new FilteringIterable<IFile>(this.originalMappingFiles.keySet()) {
			@Override
			protected boolean accept(IFile file) {
				return javaProject.isOnClasspath(file);
			}
		};
	}

	private Iterable<ReplaceEdit> createPersistenceUnitReplaceEdits(PersistenceUnit persistenceUnit, IFile mappingFile, IFolder destination) {
		IProject project = destination.getProject();
		IPath fullPath = destination.getFullPath();
		IPath runtimePath = JptCommonCorePlugin.getResourceLocator(project).getRuntimePath(project, fullPath);
		return persistenceUnit.createMoveMappingFileEdits(mappingFile, runtimePath);
	}
	
	protected void addPersistenceXmlRenameMappingFileChange(IFile persistenceXmlFile, CompositeChange compositeChange) {
		TextChange textChange = getTextChange(persistenceXmlFile);
		if (textChange == null) {
			textChange = new TextFileChange(JpaCoreRefactoringMessages.JPA_MOVE_MAPPING_FILE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME, persistenceXmlFile);
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
			compositeChange.add(textChange);
		}
		Iterable<ReplaceEdit> mappingFileReplaceEdits = this.persistenceXmlMappingFileReplaceEdits.get(persistenceXmlFile);
		this.addEdits(textChange, mappingFileReplaceEdits);
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