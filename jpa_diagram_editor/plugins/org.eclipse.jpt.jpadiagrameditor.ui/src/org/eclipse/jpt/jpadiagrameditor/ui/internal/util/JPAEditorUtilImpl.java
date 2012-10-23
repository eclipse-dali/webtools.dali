/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchSite;


public class JPAEditorUtilImpl implements IJPAEditorUtil {

	public List<Point> createBendPointList(FreeFormConnection c,  boolean selfRelation) {
		return JPAEditorUtil.createBendPointList(c,  selfRelation);
	}
	
	public JavaPersistentType getJPType(ICompilationUnit cu) {
		return JPAEditorUtil.getJPType(cu);
	}
	
	public void organizeImports(ICompilationUnit cu, IWorkbenchSite ws) {
		JPAEditorUtil.organizeImports(cu, ws);
	}
	
	public void formatCode(ICompilationUnit cu, IWorkbenchSite ws) {
		JPAEditorUtil.formatCode(cu, ws);
	}
	
	public IFile createEntityInProject(IProject project, String entityName, IPreferenceStore jpaPreferenceStore,
									   boolean isMappedSuperclassChild, String mappedSuperclassName, 
									   String mappedSuperclassPackage, String idName, 
									   boolean hasPrimaryKey) throws Exception {
		return JPAEditorUtil.createEntityInProject(project, entityName, jpaPreferenceStore, isMappedSuperclassChild, 
				mappedSuperclassName, mappedSuperclassPackage, idName, hasPrimaryKey);
 	}

	public IFile createEntityInProject(IProject project, 
			   String entityName, 
			   JavaPersistentType mappedSuperclass) throws Exception {
		return JPAEditorUtil.createEntityInProject(project, entityName, mappedSuperclass);		
	}
	
	public ICompilationUnit getCompilationUnit(JavaPersistentType jpt) {
		return JPAEditorUtil.getCompilationUnit(jpt);
	}

	public void discardWorkingCopyOnce(ICompilationUnit cu) {
		JPAEditorUtil.discardWorkingCopyOnce(cu);
	}

	public IFile createMappedSuperclassInProject(IProject project,
			String mappedSuperclassName) throws Exception {
		return JPAEditorUtil.createMappedSuperclassInProject(project, mappedSuperclassName);
	}

	public String generateUniqueTypeName(JpaProject jpaProject, String pack,
			String objectTypeName, IJPAEditorFeatureProvider fp) {
		return JPAEditorUtil.generateUniquePersistentObjectName(jpaProject, pack, objectTypeName, fp);
	}

	public IFile createEmbeddableInProject(IProject project,
			String embeddableName) throws Exception {
		return JPAEditorUtil.createEmbeddableInProject(project, embeddableName);
	}

}
