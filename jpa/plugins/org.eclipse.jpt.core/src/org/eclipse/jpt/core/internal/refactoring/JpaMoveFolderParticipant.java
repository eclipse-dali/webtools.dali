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
import org.eclipse.core.resources.IContainer;
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
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaProjectManager;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
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
 * Participant in the move refactoring of {@link IFolders}s.
 * Any mapping file references in the persistence.xml will be updated to reflect the new location.
 */
public class JpaMoveFolderParticipant
	extends MoveParticipant
	implements ISharableParticipant
{

	/**
	 * Store the {@link IFolder}s to be moved and their corresponding MoveArguments
	 */
	protected final Map<IFolder, MoveArguments> originalFolders;

	/**
	 * Store the persistence.xml ReplaceEdit in the checkConditions() call 
	 * to avoid duplicated effort in createChange().
	 */
	protected final Map<IFile, Iterable<ReplaceEdit>> persistenceXmlReplaceEdits;

	public JpaMoveFolderParticipant() {
		super();
		this.originalFolders = new HashMap<IFolder, MoveArguments>();
		this.persistenceXmlReplaceEdits = new HashMap<IFile, Iterable<ReplaceEdit>>();
	}
	
	@Override
	public String getName() {
		return JpaCoreRefactoringMessages.JPA_MOVE_FOLDER_REFACTORING_PARTICIPANT_NAME;
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
	 * This is used when multiple folders are moved.
	 * RefactoringParticipant#initialize(Object) is called for the first moved IFolder.
	 * RefactoringParticipant#getArguments() only applies to the first moved IFolder.
	 */
	public void addElement(Object element, RefactoringArguments arguments) {
		this.originalFolders.put((IFolder) element, (MoveArguments) arguments);
	}

	protected MoveArguments getArguments(IFolder element) {
		return this.originalFolders.get(element);
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
		sm.subTask(JpaCoreRefactoringMessages.JPA_MOVE_FOLDER_REFACTORING_SUB_TASK_NAME);
		ResourceChangeChecker checker = (ResourceChangeChecker) context.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = checker.getDeltaFactory();

		for (JpaProject jpaProject : JptCorePlugin.getJpaProjectManager().getJpaProjects()) {
			this.createReplaceEdits(jpaProject);
			sm.worked(10);
		}
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		for (IFile persistenceXmlFile : this.persistenceXmlReplaceEdits.keySet()) {
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
			this.persistenceXmlReplaceEdits.put(jpaProject.getPersistenceXmlResource().getFile(), replaceEdits);
		}
	}

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (this.persistenceXmlReplaceEdits.isEmpty()) {
			return null;
		}
		SubMonitor sm = SubMonitor.convert(monitor, this.persistenceXmlReplaceEdits.size());
		sm.subTask(JpaCoreRefactoringMessages.JPA_MOVE_FOLDER_REFACTORING_SUB_TASK_NAME);
		CompositeChange compositeChange = new CompositeChange(JpaCoreRefactoringMessages.JPA_MOVE_FOLDER_REFACTORING_CHANGE_NAME);
		for (IFile persistenceXmlFile : this.persistenceXmlReplaceEdits.keySet()) {
			if (sm.isCanceled()) {
				throw new OperationCanceledException();
			}
			this.addPersistenceXmlRenameChange(persistenceXmlFile, compositeChange);			
		}
		//must check for children in case all changes were made in other participants TextChanges, 
		//want to return null so our node does not appear in the preview tree
		return compositeChange.getChildren().length == 0 ? null : compositeChange;
	}

	protected Iterable<ReplaceEdit> createPersistenceUnitReplaceEditsCheckClasspath(final PersistenceUnit persistenceUnit) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<IFolder, Iterable<ReplaceEdit>>(this.getOriginalFoldersOnClasspath(persistenceUnit.getJpaProject())) {
				@Override
				protected Iterable<ReplaceEdit> transform(IFolder folder) {
					return createPersistenceUnitReplaceEdits(persistenceUnit, folder, (IContainer) getArguments(folder).getDestination());
				}
			}
		);
	}

	protected Iterable<IFolder> getOriginalFoldersOnClasspath(final JpaProject jpaProject) {
		final IJavaProject javaProject = jpaProject.getJavaProject();
		return new FilteringIterable<IFolder>(this.originalFolders.keySet()) {
			@Override
			protected boolean accept(IFolder folder) {
				return javaProject.isOnClasspath(folder);
			}
		};
	}

	protected Iterable<ReplaceEdit> createPersistenceUnitReplaceEdits(PersistenceUnit persistenceUnit, IFolder folder, IContainer destination) {
		IProject project = destination.getProject();
		IPath fullPath = destination.getFullPath().append(folder.getName());
		IPath runtimePath = JptCorePlugin.getResourceLocator(project).getRuntimePath(project, fullPath);
		return persistenceUnit.createMoveFolderEdits(folder, runtimePath);
	}
	
	protected void addPersistenceXmlRenameChange(IFile persistenceXmlFile, CompositeChange compositeChange) {
		TextChange textChange = getTextChange(persistenceXmlFile);
		if (textChange == null) {
			textChange = new TextFileChange(JpaCoreRefactoringMessages.JPA_MOVE_FOLDER_REFACTORING_CHANGE_PERSISTENCE_XML_NAME, persistenceXmlFile);
			MultiTextEdit multiTextEdit = new MultiTextEdit();
			textChange.setEdit(multiTextEdit);
			compositeChange.add(textChange);
		}
		Iterable<ReplaceEdit> mappingFileReplaceEdits = this.persistenceXmlReplaceEdits.get(persistenceXmlFile);
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