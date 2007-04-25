/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.mappings.IEmbeddable;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Embeddable</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEmbeddable()
 * @model kind="class"
 * @generated
 */
public class JavaEmbeddable extends JavaTypeMapping implements IEmbeddable
{
	public static final DeclarationAnnotationAdapter ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.EMBEDDABLE);

	protected JavaEmbeddable() {
		throw new UnsupportedOperationException("Use JavaEmbeddable(Type) instead");
	}

	protected JavaEmbeddable(Type type) {
		super(type);
	}

	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return ANNOTATION_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_EMBEDDABLE;
	}

	public String getKey() {
		return IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTablesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAssociationNames() {
		return EmptyIterator.instance();
	}

	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return attributeMappingKey == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY || attributeMappingKey == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	public List<String> candidateValuesFor(int pos, CompilationUnit astRoot) {
		return Collections.emptyList();
	}
}