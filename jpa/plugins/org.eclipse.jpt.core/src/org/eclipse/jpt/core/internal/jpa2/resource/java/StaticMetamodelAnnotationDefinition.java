/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.internal.jpa2.resource.java.binary.BinaryStaticMetamodelAnnotation;
import org.eclipse.jpt.core.internal.jpa2.resource.java.source.SourceStaticMetamodelAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.StaticMetamodelAnnotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.metamodel.StaticMetamodel
 * <p>
 * This annotation definition is not really required; it's just here for a bit
 * of consistency....
 */
public final class StaticMetamodelAnnotationDefinition
	implements AnnotationDefinition
{
	// singleton
	private static final StaticMetamodelAnnotationDefinition INSTANCE = new StaticMetamodelAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static StaticMetamodelAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private StaticMetamodelAnnotationDefinition() {
		super();
	}

	public StaticMetamodelAnnotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
		return new SourceStaticMetamodelAnnotation((JavaResourcePersistentType) parent, (Type) member);
	}

	public StaticMetamodelAnnotation buildNullAnnotation(JavaResourcePersistentMember parent) {
		return null;
	}

	public StaticMetamodelAnnotation buildAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		return new BinaryStaticMetamodelAnnotation((JavaResourcePersistentType) parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return StaticMetamodelAnnotation.ANNOTATION_NAME;
	}

}
