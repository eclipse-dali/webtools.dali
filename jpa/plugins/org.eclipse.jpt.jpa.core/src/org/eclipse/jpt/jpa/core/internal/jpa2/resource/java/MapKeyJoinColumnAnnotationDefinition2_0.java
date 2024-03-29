/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary.BinaryMapKeyJoinColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source.SourceMapKeyJoinColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;

/**
 * <code>javax.persistence.MapKeyJoinColumn</code>
 */
public final class MapKeyJoinColumnAnnotationDefinition2_0
	implements NestableAnnotationDefinition
{
	// singleton
	private static final NestableAnnotationDefinition INSTANCE = new MapKeyJoinColumnAnnotationDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static NestableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private MapKeyJoinColumnAnnotationDefinition2_0() {
		super();
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		return SourceMapKeyJoinColumnAnnotation2_0.buildSourceMapKeyJoinColumnAnnotation(parent, annotatedElement, index);
	}

	public NestableAnnotation buildAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation, int index) {
		return new BinaryMapKeyJoinColumnAnnotation2_0(parent, jdtAnnotation);
	}

	public String getNestableAnnotationName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN;
	}

	public String getContainerAnnotationName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMNS;
	}

	public String getElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMNS__VALUE;
	}
}
