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
import org.eclipse.jpt.core.internal.jdtutility.BooleanStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;


public class ManyToOneImpl extends AbstractRelationshipMappingAnnotation implements ManyToOne
{	
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.MANY_TO_ONE);
	
	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter();	

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildCascadeAdapter();

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildFetchAdapter();
	
	private static final DeclarationAnnotationElementAdapter<String> OPTIONAL_ADAPTER = buildOptionalAdapter();
	
	private final AnnotationElementAdapter<String> optionalAdapter;

	private Boolean optional;

	public ManyToOneImpl(JavaPersistentAttributeResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.optionalAdapter = this.buildAnnotationElementAdapter(OPTIONAL_ADAPTER);
	}
	
	//**************** AbstractRelationshipMappingAnnotation implementation **************
	
	@Override
	protected DeclarationAnnotationElementAdapter<String> targetEntityAdapter() {
		return TARGET_ENTITY_ADAPTER;
	}
	
	@Override
	protected DeclarationAnnotationElementAdapter<String[]> cascadeAdapter() {
		return CASCADE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> fetchAdapter() {
		return FETCH_ADAPTER;
	}
	
	//**************** Annotation implementation **************
	
	public String getAnnotationName() {
		return JPA.MANY_TO_ONE;
	}

	
	public Boolean getOptional() {
		return this.optional;
	}
	
	public void setOptional(Boolean optional) {
		this.optional = optional;
		this.optionalAdapter.setValue(BooleanUtility.toJavaAnnotationValue(optional));
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setOptional(BooleanUtility.fromJavaAnnotationValue(this.optionalAdapter.getValue(astRoot)));
	}

	// ********** static methods **********
	
	private static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter() {
		return buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__TARGET_ENTITY);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildCascadeAdapter() {
		return buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__CASCADE);
	}

	private static DeclarationAnnotationElementAdapter<String> buildFetchAdapter() {
		return buildFetchAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__FETCH);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter() {
		return buildOptionalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__OPTIONAL);
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, BooleanStringExpressionConverter.instance());
	}

}
