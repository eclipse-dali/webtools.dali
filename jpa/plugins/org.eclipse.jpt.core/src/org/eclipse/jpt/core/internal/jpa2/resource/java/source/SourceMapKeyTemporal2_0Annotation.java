/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.resource.java.source.SourceBaseTemporalAnnotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyTemporal2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 * javax.persistence.MapKeyTemporal
 */
public final class SourceMapKeyTemporal2_0Annotation
	extends SourceBaseTemporalAnnotation
	implements MapKeyTemporal2_0Annotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(MapKeyTemporal2_0Annotation.ANNOTATION_NAME);


	public SourceMapKeyTemporal2_0Annotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return MapKeyTemporal2_0Annotation.ANNOTATION_NAME;
	}

	@Override
	protected String getValueElementName() {
		return JPA2_0.MAP_KEY_TEMPORAL__VALUE;
	}
}
