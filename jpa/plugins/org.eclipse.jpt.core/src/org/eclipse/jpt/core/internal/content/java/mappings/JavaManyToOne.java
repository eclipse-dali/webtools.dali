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
import org.eclipse.jpt.core.internal.mappings.IManyToOne;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Many To One</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaManyToOne()
 * @model kind="class"
 * @generated
 */
public class JavaManyToOne extends JavaSingleRelationshipMapping
	implements IManyToOne
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.MANY_TO_ONE);

	private static final DeclarationAnnotationElementAdapter TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__TARGET_ENTITY);

	private static final DeclarationAnnotationElementAdapter CASCADE_ADAPTER = buildEnumAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__CASCADE);

	private static final DeclarationAnnotationElementAdapter FETCH_ADAPTER = buildEnumAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__FETCH);

	private static final DeclarationAnnotationElementAdapter OPTIONAL_ADAPTER = buildOptionalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.MANY_TO_ONE__OPTIONAL);

	protected JavaManyToOne() {
		throw new UnsupportedOperationException("Use JavaManyToOne(Attribute) instead");
	}

	protected JavaManyToOne(Attribute attribute) {
		super(attribute);
	}

	// ********** initialization **********
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

	protected DeclarationAnnotationElementAdapter optionalAdapter() {
		return OPTIONAL_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_MANY_TO_ONE;
	}

	public String getKey() {
		return IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
}