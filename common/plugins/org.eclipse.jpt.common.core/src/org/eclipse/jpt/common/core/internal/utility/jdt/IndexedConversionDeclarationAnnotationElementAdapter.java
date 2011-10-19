/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;


public class IndexedConversionDeclarationAnnotationElementAdapter<T>
		extends ConversionDeclarationAnnotationElementAdapter<T[]>
		implements IndexedDeclarationAnnotationElementAdapter<T> {
	
	public IndexedConversionDeclarationAnnotationElementAdapter(
			DeclarationAnnotationAdapter annotationAdapter, IndexedExpressionConverter<T> converter) {
		super(annotationAdapter, converter);
	}

	public IndexedConversionDeclarationAnnotationElementAdapter(
			DeclarationAnnotationAdapter annotationAdapter, String elementName, IndexedExpressionConverter<T> converter) {
		super(annotationAdapter, elementName, converter);
	}
	
	
	@Override
	protected IndexedExpressionConverter<T> getConverter() {
		return (IndexedExpressionConverter<T>) super.getConverter();
	}
	
	
	public Expression getSubvalueExpression(int index, ModifiedDeclaration declaration) {
		return getConverter().getSubexpression(index, super.getExpression(declaration));
	}
}
