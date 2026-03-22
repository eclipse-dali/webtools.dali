/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *     Lakshminarayana Nekkanti - JPA 3.x jakarta namespace support
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;

import org.eclipse.jpt.common.core.internal.utility.jdt.JakartaAwareDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.TableAnnotation;

/**
 * <code>javax.persistence.Table</code>
 */
public final class SourceTableAnnotation
	extends SourceBaseTableAnnotation
	implements TableAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = JakartaAwareDeclarationAnnotationAdapter.forJavax(ANNOTATION_NAME);


	public SourceTableAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public SourceTableAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** SourceBaseTableAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildSchemaDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.TABLE__SCHEMA);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildCatalogDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.TABLE__CATALOG);
	}

	@Override
	protected String getUniqueConstraintsElementName() {
		return JPA.TABLE__UNIQUE_CONSTRAINTS;
	}

}
