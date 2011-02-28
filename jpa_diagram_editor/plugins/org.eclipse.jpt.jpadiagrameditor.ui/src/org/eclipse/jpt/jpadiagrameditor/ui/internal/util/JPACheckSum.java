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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

public class JPACheckSum {
	
	private static JPACheckSum instance = null;
	private static MessageDigest md5 = null;
	
	public static synchronized JPACheckSum INSTANCE() {
		if (instance == null) {
			try {
				md5 = MessageDigest.getInstance("MD5");	//$NON-NLS-1$
			} catch (NoSuchAlgorithmException e) {
				System.err.println("Couldn't create instance of MD5"); //$NON-NLS-1$
				e.printStackTrace();	
				return null;
			}
			instance = new JPACheckSum();
		}
		return instance; 
	}
		
	public String getSavedShapeMD5(Shape sh) {	
		String checkSumString = Graphiti.getPeService().getPropertyValue(sh, JPAEditorConstants.PROP_ENTITY_CHECKSUM);
		if (checkSumString == null)
			return "";	//$NON-NLS-1$
		return checkSumString;
	}
	
	public void assignEntityShapesMD5Strings(Diagram d, JpaProject jpaProject) {
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		List<Shape> picts = d.getChildren();
		if (picts.size() == 0)
			return;
		Iterator<Shape> it = picts.iterator();
		while (it.hasNext()) {
			Shape pict = it.next();
			String name = Graphiti.getPeService().getPropertyValue(pict, JPAEditorConstants.PROP_ENTITY_CLASS_NAME);
			JavaPersistentType jpt = JpaArtifactFactory.instance().getJPT(name, pu);
			String hash = "";	//$NON-NLS-1$
			if (jpt != null) {
				ICompilationUnit cu = JPAEditorUtil.getCompilationUnit(jpt);
				hash = generateCompilationUnitMD5String(cu);
			}
			Graphiti.getPeService().setPropertyValue(pict, JPAEditorConstants.PROP_ENTITY_CHECKSUM, hash);
		}
	}

		
	public String generateCompilationUnitMD5String(ICompilationUnit cu) {
		String src = null;
		try {
			src = cu.getSource();
		} catch (JavaModelException e) {
			System.err.println("Can't get the source of the compilation unit");	//$NON-NLS-1$
			e.printStackTrace();	
			return null;
		}
		byte[] res = md5.digest(src.getBytes());
		StringBuilder sb = new StringBuilder();
		for (byte b : res)
			sb.append(Byte.toString(b));
		return sb.toString();		
	}
	
	public boolean isEntityModelChanged(Shape sh, JpaProject jpaProject) {
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		String name = Graphiti.getPeService().getPropertyValue(sh, JPAEditorConstants.PROP_ENTITY_CLASS_NAME);
		JavaPersistentType jpt = JpaArtifactFactory.instance().getJPT(name, pu);
		String savedMD5 = getSavedShapeMD5(sh);	
		String actualMD5 = "";	//$NON-NLS-1$
		if (jpt != null) {
			ICompilationUnit cu = JPAEditorUtil.getCompilationUnit(jpt);
			actualMD5 = generateCompilationUnitMD5String(cu);
		}
		return !savedMD5.equals(actualMD5);
	}
	
	public boolean isModelDifferentFromDiagram(Diagram d, JpaProject jpaProject) {
		List<Shape> picts = d.getChildren();
		if (picts.size() == 0)
			return false;
		Iterator<Shape> it = picts.iterator();
		while (it.hasNext()) {
			Shape pict = it.next();
			if (isEntityModelChanged(pict, jpaProject))
				return true;
		}
		return false;
	}
}
