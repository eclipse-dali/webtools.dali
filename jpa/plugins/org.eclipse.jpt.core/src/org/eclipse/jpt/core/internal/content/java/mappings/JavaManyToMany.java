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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IManyToMany;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Many To Many</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaManyToMany()
 * @model kind="class"
 * @generated
 */
public class JavaManyToMany extends JavaMultiRelationshipMapping
	implements IManyToMany
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.MANY_TO_MANY);

	private static final DeclarationAnnotationElementAdapter TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__TARGET_ENTITY);

	private static final DeclarationAnnotationElementAdapter CASCADE_ADAPTER = buildEnumAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__CASCADE);

	private static final DeclarationAnnotationElementAdapter FETCH_ADAPTER = buildEnumAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__FETCH);

	private static final DeclarationAnnotationElementAdapter MAPPED_BY_ADAPTER = buildAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_MANY__MAPPED_BY);

	protected JavaManyToMany() {
		throw new UnsupportedOperationException("Use JavaManyToMany(Attribute) instead");
	}

	protected JavaManyToMany(Attribute attribute) {
		super(attribute);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_MANY_TO_MANY;
	}

	public String getKey() {
		return IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	// ********** JavaRelationshipMappingModelAdapter implementation **********
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter targetEntityAdapter() {
		return TARGET_ENTITY_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter cascadeAdapter() {
		return CASCADE_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter fetchAdapter() {
		return FETCH_ADAPTER;
	}

	// ********** JavaMultiRelationshipMappingModelAdapter implementation **********
	protected DeclarationAnnotationElementAdapter mappedByAdapter() {
		return MAPPED_BY_ADAPTER;
	}
}