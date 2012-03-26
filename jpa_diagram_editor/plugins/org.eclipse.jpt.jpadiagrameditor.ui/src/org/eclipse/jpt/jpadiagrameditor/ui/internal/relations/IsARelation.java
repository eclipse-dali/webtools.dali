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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;

public class IsARelation {

	protected final static String SEPARATOR = ";isA;";							//$NON-NLS-1$
	public final static String IS_A_CONNECTION_PROP_KEY = "is_is_a_connection";	//$NON-NLS-1$
	
	
	protected JavaPersistentType subclass;
	protected JavaPersistentType superclass;
	
	public IsARelation(JavaPersistentType subclass, 
					   JavaPersistentType superclass) {
		this.subclass = subclass;
		this.superclass = superclass;
	}
	
	public IsARelation(IJPAEditorFeatureProvider fp, Connection conn) {
		Anchor start = conn.getStart();
		Anchor end = conn.getEnd();
		Object startObj = fp.getBusinessObjectForPictogramElement((ContainerShape)start.eContainer());
		Object endObj = fp.getBusinessObjectForPictogramElement((ContainerShape)end.eContainer());
		if ((startObj == null) || (endObj == null))
			throw new NullPointerException("Some of the connection ends is null");	//$NON-NLS-1$
		if (!(startObj instanceof JavaPersistentType) || !(endObj instanceof JavaPersistentType))
			throw new IllegalArgumentException();
		this.subclass = (JavaPersistentType)startObj;
		this.superclass = (JavaPersistentType)endObj;
	}
	
	
	public JavaPersistentType getSubclass() {
		return subclass; 
	}
	
	public JavaPersistentType getSuperclass() {
		return superclass; 
	}
	
	public static boolean isIsAConnection(Connection conn) {
		String val = JPAEditorUtil.getPeUtil().getPropertyValue(conn, IS_A_CONNECTION_PROP_KEY);
		return (Boolean.TRUE.toString().equals(val));
	}


	public String getId() {
		return generateId(subclass, superclass);
	}
	
	public static String generateId(IJPAEditorFeatureProvider fp, Connection conn) {
		Anchor start = conn.getStart();
		Anchor end = conn.getEnd();
		Object startObj = fp.getBusinessObjectForPictogramElement((ContainerShape)start.eContainer());
		Object endObj = fp.getBusinessObjectForPictogramElement((ContainerShape)end.eContainer());
		if ((startObj == null) || (endObj == null))
			return null;
		if (!(startObj instanceof JavaPersistentType) || !(endObj instanceof JavaPersistentType))
			return null;
		JavaPersistentType startJpt = (JavaPersistentType)startObj;
		JavaPersistentType endJpt = (JavaPersistentType)endObj;
		return generateId(startJpt, endJpt);
	}
	
	private static String generateId(JavaPersistentType startJpt, JavaPersistentType endJpt) {
		return JPAEditorConstants.IS_A_RELATION_ID_PREFIX + 
				startJpt.getName() + SEPARATOR + endJpt.getName();
	}
	
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this.hashCode() != obj.hashCode())
			return false;
		if (!(obj instanceof IsARelation))
			return false;
		IsARelation rel = (IsARelation)obj; 
		return this.getId().equals(rel.getId());
	}
	
	public int hashCode() {
		return getId().hashCode();
	}

}
