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
import java.util.HashSet;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
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
		return JptJpaCoreRefactoringMessages.JPA_DELETE_PACKAGE_OR_FOLDER_REFACTORING_PARTICIPANT_NAME;
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
			for (IJavaElement child : JDTTools.getChildren(root)) {
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
			JptJpaCorePlugin.instance().logError(ex);
		}
	}
	
	/**
	 * Add mapping file delete edits here, super.createPersistenceXmlDeleteEdits() is
	 * called to add the type delete edits.
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<DeleteEdit> createPersistenceXmlDeleteEdits(final PersistenceUnit persistenceUnit) {
		Transformer<IFile, Iterable<DeleteEdit>> transformer = new TransformerAdapter<IFile, Iterable<DeleteEdit>>() {
			@Override
			public Iterable<DeleteEdit> transform(IFile file) {
				return persistenceUnit.createDeleteMappingFileEdits(file);
			}
		};
		return IterableTools.concatenate(
				super.createPersistenceXmlDeleteEdits(persistenceUnit),
				IterableTools.children(this.getMappingFilesOnClasspath(persistenceUnit.getJpaProject()), transformer)
			);
	}

	@SuppressWarnings("unchecked")
	protected Iterable<IFile> getMappingFilesOnClasspath(final JpaProject jpaProject) {
		final IJavaProject javaProject = jpaProject.getJavaProject();
		final JpaPlatform jpaPlatform = jpaProject.getJpaPlatform();
		return IterableTools.filter(
				IterableTools.concatenate(
					getPossibleMappingFilesInFolders(),
					getPossibleMappingFilesInPackageFragments()
				),
				new MappingFileIsOnClasspath(javaProject, jpaPlatform));
	}

	public static class MappingFileIsOnClasspath
		extends FilterAdapter<IFile>
	{
		private final IJavaProject javaProject;
		private final JpaPlatform jpaPlatform;
		public MappingFileIsOnClasspath(IJavaProject javaProject, JpaPlatform jpaPlatform) {
			super();
			this.javaProject = javaProject;
			this.jpaPlatform = jpaPlatform;
		}
		@Override
		public boolean accept(IFile file) {
			if (this.javaProject.isOnClasspath(file)) {
				IContentType contentType = this.jpaPlatform.getContentType(file);
				return (contentType != null) && contentType.isKindOf(ResourceMappingFile.Root.CONTENT_TYPE);
			}
			return false;
		}
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
		return IterableTools.children(this.packageFragments, PACKAGE_FRAGMENT_NON_JAVA_FILES_TRANSFORMER);
	}

	protected static final Transformer<IPackageFragment, Iterable<IFile>> PACKAGE_FRAGMENT_NON_JAVA_FILES_TRANSFORMER = new PackageFragmentNonJavaFilesTransformer();
	protected static class PackageFragmentNonJavaFilesTransformer
		extends TransformerAdapter<IPackageFragment, Iterable<IFile>>
	{
		@Override
		public Iterable<IFile> transform(IPackageFragment packageFragment) {
			return getNonJavaFiles(packageFragment);
		}
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
			JptJpaCorePlugin.instance().logError(e);
			return ObjectTools.EMPTY_OBJECT_ARRAY;
		}
	}
	
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
