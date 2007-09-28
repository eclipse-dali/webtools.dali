/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;


public class BasicImpl extends AbstractAnnotationResource<Attribute> implements Basic
{
//	private final AnnotationElementAdapter<String> nameAdapter;
//
//	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
//
//
//	private String name;

	
	public BasicImpl(Attribute attribute, IJpaPlatform jpaPlatform) {
		super(attribute, jpaPlatform, DECLARATION_ANNOTATION_ADAPTER);
//		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getMember(), NAME_ADAPTER);
	}
	
	public String getAnnotationName() {
		return JPA.BASIC;
	}
			
//	public String getName() {
//		return this.name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	public void updateFromJava(CompilationUnit astRoot) {
//		setName(this.nameAdapter.getValue(astRoot));
	}
	
//	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
//		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ENTITY__NAME, false); // false = do not remove annotation when empty
//	}

}
