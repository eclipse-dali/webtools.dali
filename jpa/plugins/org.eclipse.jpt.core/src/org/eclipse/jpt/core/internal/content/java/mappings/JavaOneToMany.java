/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IOneToMany;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java One To Many</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOneToMany()
 * @model kind="class"
 * @generated
 */
public class JavaOneToMany extends JavaMultiRelationshipMapping
	implements IOneToMany
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ONE_TO_MANY);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__TARGET_ENTITY);

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__CASCADE);

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildEnumAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__FETCH);

	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_MANY__MAPPED_BY);

	protected JavaOneToMany() {
		throw new UnsupportedOperationException("Use JavaOneToMany(Attribute) instead");
	}

	protected JavaOneToMany(Attribute attribute) {
		super(attribute);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_ONE_TO_MANY;
	}

	public String getKey() {
		return IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	// ********** JavaRelationshipMappingModelAdapter implementation **********
	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

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

	// ********** JavaMultiRelationshipMappingModelAdapter implementation **********
	@Override
	protected DeclarationAnnotationElementAdapter<String> mappedByAdapter() {
		return MAPPED_BY_ADAPTER;
	}

	// ********** INonOwningMapping implementation **********
	public boolean mappedByIsValid(IAttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}
}