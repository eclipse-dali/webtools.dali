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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;


public class ManyToManyImpl extends AbstractAnnotationResource<Attribute> implements ManyToMany
{
	private final AnnotationElementAdapter<String> fetchAdapter;

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();

	private FetchType fetch;
	
	public ManyToManyImpl(JavaPersistentAttributeResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.fetchAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, FETCH_ADAPTER);
	}
	
	public String getAnnotationName() {
		return JPA.MANY_TO_MANY;
	}

	public FetchType getFetch() {
		return this.fetch;
	}
	
	public void setFetch(FetchType fetch) {
		this.fetch = fetch;
		this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(fetch));
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		this.setFetch(FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot)));
	}
	
	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__FETCH, false);
	}

}
