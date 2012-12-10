/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/

package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import java.util.Hashtable;

import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;

public abstract class HasReferanceRelation {
	protected final static String SEPARATOR = ";hasReference;";							//$NON-NLS-1$

	protected JavaPersistentType embeddingEntity;
	protected JavaPersistentType embeddable;
	
	public final static Hashtable<HasReferenceType, String> relTypeToIdPart = new Hashtable<HasReferenceType, String>(); 
	
	private JavaPersistentAttribute embeddedAnnotatedAttribute;
	
	public static enum HasReferenceType {
		SINGLE, COLLECTION
	}
	
	static {
		relTypeToIdPart.put(HasReferenceType.SINGLE, "1-1;"); //$NON-NLS-1$
		relTypeToIdPart.put(HasReferenceType.COLLECTION, "1-N;"); //$NON-NLS-1$
	}
	
	public HasReferanceRelation(JavaPersistentType embeddingEntity, 
					   JavaPersistentType embeddable) {
		this.embeddingEntity = embeddingEntity;
		this.embeddable = embeddable;
	}
	
	public HasReferanceRelation(IJPAEditorFeatureProvider fp, Connection conn) {
		Anchor start = conn.getStart();
		Anchor end = conn.getEnd();
		Object startObj = fp.getBusinessObjectForPictogramElement((ContainerShape)start.eContainer());
		Object endObj = fp.getBusinessObjectForPictogramElement((ContainerShape)end.eContainer());
		if ((endObj == null) || (startObj == null))
			throw new NullPointerException("Some of the connection ends is null");	//$NON-NLS-1$
		if (!(endObj instanceof JavaPersistentType) || !(startObj instanceof JavaPersistentType))
			throw new IllegalArgumentException();
		this.embeddingEntity = (JavaPersistentType)startObj;
		this.embeddable = (JavaPersistentType)endObj;
	}
	
	
	public JavaPersistentType getEmbeddable() {
		return embeddable; 
	}
	
	public JavaPersistentType getEmbeddingEntity() {
		return embeddingEntity; 
	}

	public String getId() {
		return generateId(embeddingEntity, embeddable, embeddedAnnotatedAttribute.getName(), getReferenceType());
	}

	public static String generateId(JavaPersistentType startJpt, JavaPersistentType endJpt, String embeddedAttributeName, HasReferenceType relType) {		
		return JPAEditorConstants.HAS_REFERENCE_RELATION_ID_PREFIX +
				startJpt.getName() + SEPARATOR + relTypeToIdPart.get(relType) + endJpt.getName()+ "-" + embeddedAttributeName; //$NON-NLS-1$
	}

	@Override
	public boolean equals(Object otherRel) {
		if (!HasReferanceRelation.class.isInstance(otherRel))
			return false;
		return getId().equals(((HasReferanceRelation)otherRel).getId());
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	public abstract HasReferenceType getReferenceType();

	public JavaPersistentAttribute getEmbeddedAnnotatedAttribute() {
		return embeddedAnnotatedAttribute;
	}

	public void setEmbeddedAnnotatedAttribute(JavaPersistentAttribute embeddedAnnotatedAttribute) {
		this.embeddedAnnotatedAttribute = embeddedAnnotatedAttribute;
	}
}