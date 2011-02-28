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
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorImageCreator.RelEndDir;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchSite;


public class JPAEditorUtilImpl implements IJPAEditorUtil {

	public int calcConnectionLength(FreeFormConnection c) {
		return JPAEditorUtil.calcConnectionLength(c);
	}

	public List<Point> createBendPointList(FreeFormConnection c,  int cnt, int connectionsNum,  boolean selfRelation) {
		return JPAEditorUtil.createBendPointList(c,  cnt, connectionsNum, selfRelation);
	}
	
	public List<Point> createBendPointList(FreeFormConnection c,  boolean selfRelation) {
		return JPAEditorUtil.createBendPointList(c,  selfRelation);
	}
	
	public RelEndDir getConnectionStartDir(FreeFormConnection c) {
		return JPAEditorUtil.getConnectionStartDir(c);
		
	}
	
	public RelEndDir getConnectionEndDir(FreeFormConnection c) {
		return JPAEditorUtil.getConnectionEndDir(c);
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
	
	public String generateUniqueEntityName(JpaProject jpaProject, 
			  String pack, 
			  IJPAEditorFeatureProvider fp) {
		return JPAEditorUtil.generateUniqueEntityName(jpaProject, pack, fp);
	}
	
	public String generateUniqueMappedSuperclassName(JpaProject jpaProject, String pack, IJPAEditorFeatureProvider fp) {
		return JPAEditorUtil.generateUniqueMappedSuperclassName(jpaProject, pack, fp);
	}

	
	public IFile createEntityInProject(IProject project, String entityName, IPreferenceStore jpaPreferenceStore,
			boolean isMappedSuperclassChild, String mappedSuperclassName, String mappedSuperclassPackage, boolean hasPrimaryKey) throws Exception {
		return JPAEditorUtil.createEntityInProject(project, entityName, jpaPreferenceStore, isMappedSuperclassChild, 
				mappedSuperclassName, mappedSuperclassPackage, hasPrimaryKey);
 	}

	
	public ICompilationUnit getCompilationUnit(JavaPersistentType jpt) {
		return JPAEditorUtil.getCompilationUnit(jpt);
	}
	
	public boolean isCardinalityDecorator(ConnectionDecorator dc) {
		return JPAEditorUtil.isCardinalityDecorator(dc);
	}

	public void discardWorkingCopyOnce(ICompilationUnit cu) {
		JPAEditorUtil.discardWorkingCopyOnce(cu);
	}
	
	public IFile createEntityFromMappedSuperclassInProject(IProject project,
			String mappedSuperclassName, IPreferenceStore jpaPreferenceStore) throws Exception {
		return JPAEditorUtil.createEntityFromMappedSuperclassInProject(project, mappedSuperclassName, jpaPreferenceStore);
	}

}
