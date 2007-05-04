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

import java.util.Iterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.mappings.IMappedSuperclass;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Mapped Superclass</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaMappedSuperclass()
 * @model kind="class"
 * @generated
 */
public class JavaMappedSuperclass extends JavaTypeMapping
	implements IMappedSuperclass
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.MAPPED_SUPERCLASS);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaMappedSuperclass() {
		super();
	}

	protected JavaMappedSuperclass(Type type) {
		super(type);
	}

	@Override
	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_MAPPED_SUPERCLASS;
	}

	public String getKey() {
		return IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	@Override
	public Iterator<String> overridableAttributeNames() {
		return this.namesOf(this.overridableAttributes());
	}

	private Iterator<IPersistentAttribute> overridableAttributes() {
		return new FilteringIterator<IPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> overridableAssociationNames() {
		return this.namesOf(this.overridableAssociations());
	}

	private Iterator<IPersistentAttribute> overridableAssociations() {
		return new FilteringIterator<IPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAssociation();
			}
		};
	}

	private Iterator<String> namesOf(Iterator<IPersistentAttribute> attributes) {
		return new TransformationIterator<IPersistentAttribute, String>(attributes) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}
}
