/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.emfutility;

import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TreeIterator;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.componentcore.resources.IVirtualResource;

public class ComponentUtilities
{
	/**
	 * Return the deployment path for the given source file.  If there is no
	 * corresponding deployment file, null will be returned.
	 */
	public static IPath computeDeployPath(IFile sourceFile) {
		// Unfortunately, the only current way to do this is to exhaustively 
		// search all deployment files and attempt to match to this file.
		// Bug 202943 has been logged to track this issue.
		for (IVirtualFile virtualFile : CollectionTools.iterable(allVirtualFiles(sourceFile.getProject()))) {
			for (IFile underlyingFile : virtualFile.getUnderlyingFiles()) {
				if (sourceFile.equals(underlyingFile)) {
					return virtualFile.getRuntimePath();
				}
			}
		}
		return null;
	}
	
	private static Iterator<IVirtualFile> allVirtualFiles(IProject project) {
		return new FilteringIterator<IVirtualResource, IVirtualFile>(allVirtualResources(project)) {
			@Override
			protected boolean accept(IVirtualResource o) {
				return o.getType() == IVirtualResource.FILE;
			}
		};
	}
	
	private static Iterator<IVirtualResource> allVirtualResources(IProject project) {
		IVirtualComponent virtualComponent = ComponentCore.createComponent(project);
		
		if (virtualComponent == null) {
			return EmptyIterator.instance();
		}
		
		return new TreeIterator<IVirtualResource>(virtualComponent.getRootFolder()) {
			@Override
			protected Iterator<? extends IVirtualResource> children(IVirtualResource next) {
				if (next.getType() == IVirtualResource.FOLDER) {
					try {
						return CollectionTools.iterator(((IVirtualFolder) next).members());
					}
					catch (CoreException ce) { /* fall through, return default case */ }
				}
				return EmptyIterator.instance();
			}
		};
	}
}
