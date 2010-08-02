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
import java.util.HashSet;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.text.edits.DeleteEdit;

public class JpaDeletePackageOrFolderParticipant
	extends AbstractJpaDeleteTypeParticipant
	implements ISharableParticipant
{
	
	/** 
	 * The folders that are going to be deleted.
	 * Only folders which are not also IPackageFragments or IPackageFragmentRoots are added to this collection.
	 */
	protected final Collection<IFolder> folders;
	
	/** 
	 * The IPackageFragments that are going to be deleted.
	 */
	protected final Collection<IPackageFragment> packageFragments;

	public JpaDeletePackageOrFolderParticipant() {
		super();
		this.folders = new ArrayList<IFolder>();
		this.packageFragments = new ArrayList<IPackageFragment>();
	}

	//**************** RefactoringParticipant implementation *****************
	
	@Override
	public String getName() {
		return JpaCoreRefactoringMessages.JPA_DELETE_PACKAGE_OR_FOLDER_REFACTORING_PARTICIPANT_NAME;
	}

	@Override
	protected boolean initialize(Object element) {
		if (getProcessor() instanceof RenameProcessor) {
			//Renaming a package that then ends up with no subpackages or types will call the delete folder participant.
			//We do not want to delete references in the persistence.xml and mapping files in this case, will be handled
			//with the rename participant
			return false;
		}
		this.addElement(element, getArguments());
		return true;
	}

	//**************** ISharableParticipant implementation *****************
	/**
	 * This is used when multiple IPackageFraments/IFolders are deleted.
	 * RefactoringParticipant#initialize(Object) is called for the first deleted IPackageFragment/IFolder.
	 * RefactoringParticipant#getArguments() only applies to the first deleted IPackageFragment/IFolder
	 */
	public void addElement(Object element, RefactoringArguments arguments) {
		if (element instanceof IFolder) {
			IJavaElement javaElement = JavaCore.create((IFolder) element);
			if (javaElement != null) {
				if (javaElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT) {
					this.addPackageFragmentRoot((IPackageFragmentRoot) javaElement);
				}
				else {
					this.addPackageFragment((IPackageFragment) javaElement);
				}
			}
			else {
				this.addFolder((IFolder) element);
			}
		}
		else {
			this.addPackageFragment((IPackageFragment) element);
		}
	}

	protected void addFolder(IFolder folder) {
		this.folders.add(folder);
	}

	protected void addPackageFragmentRoot(IPackageFragmentRoot root) {
		if (JDTTools.packageFragmentRootIsSourceFolder(root)) {
			IJavaElement[] children = JDTTools.getJDTChildren(root);
			for (IJavaElement child : children) {
				this.addPackageFragment((IPackageFragment) child);
			}
		}
	}

	protected void addPackageFragment(IPackageFragment packageFragment) {
		this.packageFragments.add(packageFragment);
		Collection<IType> allDeletedTypes = new HashSet<IType>();
		addAffectedTypes(allDeletedTypes, packageFragment);
		for (IType deletedType : allDeletedTypes) {
			this.addType(deletedType);
		}		
	}

	//nestedTypes are handled in AbstractJpaDeleteTypeParticipant
	static void addAffectedTypes(Collection<IType> types, IPackageFragment fragment) {
		try {
			if (fragment.containsJavaResources()) {
				ICompilationUnit[] cunits = fragment.getCompilationUnits();
				IType type = null;
				for (int i = 0; i < cunits.length; i++) {
					type = cunits[i].findPrimaryType();
					if (type == null) {
						continue;
					}
					types.add(type);
				}
			}
		}
		catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
		}
	}
	
	/**
	 * Add mapping file delete edits here, super.createPersistenceXmlDeleteEdits() is
	 * called to add the type delete edits.
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<DeleteEdit> createPersistenceXmlDeleteEdits(final PersistenceUnit persistenceUnit) {
		return new CompositeIterable<DeleteEdit>(
				super.createPersistenceXmlDeleteEdits(persistenceUnit),
				new CompositeIterable<DeleteEdit>(
					new TransformationIterable<IFile, Iterable<DeleteEdit>>(this.getMappingFilesOnClasspath(persistenceUnit.getJpaProject())) {
						@Override
						protected Iterable<DeleteEdit> transform(IFile file) {
							return persistenceUnit.createDeleteMappingFileEdits(file);
						}
					}
				)
			);
	}

	@SuppressWarnings("unchecked")
	protected Iterable<IFile> getMappingFilesOnClasspath(final JpaProject jpaProject) {
		final IJavaProject javaProject = jpaProject.getJavaProject();
		return new FilteringIterable<IFile>(new CompositeIterable<IFile>(
				getPossibleMappingFilesInFolders(),
				getPossibleMappingFilesInPackageFragments())) 
			{
				@Override
				protected boolean accept(IFile file) {
					return javaProject.isOnClasspath(file) 
							&& PlatformTools.getContentType(file).isKindOf(JptCorePlugin.MAPPING_FILE_CONTENT_TYPE);
				}
			};
	}
	
	protected Iterable<IFile> getPossibleMappingFilesInFolders() {
		Collection<IFile> files = new ArrayList<IFile>();
		FolderResourceProxyVisitor visitor = new FolderResourceProxyVisitor(files);
		for (IFolder folder : this.folders) {
			visitor.visitFolder(folder);
		}
		return files;
	}

	protected Iterable<IFile> getPossibleMappingFilesInPackageFragments() {
		return new CompositeIterable<IFile>(
			new TransformationIterable<IPackageFragment, Iterable<IFile>>(this.packageFragments) {
				@Override
				protected Iterable<IFile> transform(IPackageFragment packageFragment) {
					return getNonJavaFiles(packageFragment);
				}
			}
		);
	}

	protected static Iterable<IFile> getNonJavaFiles(IPackageFragment packageFragment) {
		Collection<IFile> files = new ArrayList<IFile>();
		Object[] resources = getNonJavaResources(packageFragment);
		for (Object resource : resources) {
			if (resource instanceof IFile) {
				files.add((IFile) resource);
			}
		}
		return files;
	}

	protected static Object[] getNonJavaResources(IPackageFragment packageFragment) {
		try {
			return packageFragment.getNonJavaResources();
		}
		catch (JavaModelException e) {
			JptCorePlugin.log(e);
			return EMPTY_OBJECT_ARRAY;
		}
	}
	
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	protected class FolderResourceProxyVisitor implements IResourceProxyVisitor {
		private Collection<IFile> files;
		protected FolderResourceProxyVisitor(Collection<IFile> files) {
			super();
			this.files = files;
		}
		protected void visitFolder(IFolder folder) {
			try {
				folder.accept(this, IResource.NONE);
			} catch (CoreException ex) {
				// shouldn't happen - we don't throw any CoreExceptions
				throw new RuntimeException(ex);
			}
		}
		public boolean visit(IResourceProxy resource) {
			switch (resource.getType()) {
				case IResource.FOLDER :
					return true;  // visit children
				case IResource.FILE :
					this.files.add((IFile) resource.requestResource());
					return false;  // no children
				default :
					return false;  // no children
			}
		}
	}
}