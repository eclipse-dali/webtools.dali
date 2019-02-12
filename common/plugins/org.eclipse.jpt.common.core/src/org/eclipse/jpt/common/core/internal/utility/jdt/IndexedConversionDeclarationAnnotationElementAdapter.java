/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedExpressionConverter;

/**
 * Wrap a declaration annotation element adapter that deals with AST
 * array expressions, converting them to/from various other objects.
 * 
 * @param <T> the type of the object in the array to be passed to and
 * returned by the adapter
 */
public class IndexedConversionDeclarationAnnotationElementAdapter<T>
	extends ConversionDeclarationAnnotationElementAdapter<T[]>
	implements IndexedDeclarationAnnotationElementAdapter<T>
{
	public IndexedConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, IndexedExpressionConverter<T> converter) {
		super(annotationAdapter, converter);
	}

	public IndexedConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, IndexedExpressionConverter<T> converter) {
		super(annotationAdapter, elementName, converter);
	}

	protected IndexedExpressionConverter<T> getConverter() {
		return (IndexedExpressionConverter<T>) this.converter;
	}

	public Expression selectExpression(Annotation astAnnotation, int index) {
		return this.getConverter().selectExpression(this.getExpression(astAnnotation), index);
	}
}
