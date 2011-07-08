/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpql;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.persistence.jpa.jpql.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.spi.IMappingType;
import org.eclipse.persistence.jpa.jpql.spi.IType;
import org.eclipse.persistence.jpa.jpql.spi.ITypeDeclaration;

/**
 * The concrete implementation of {@link IMapping} that is wrapping the design-time representation
 * of a mapping.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
final class JpaMapping implements IMapping {

	/**
	 * The design-time {@link AttributeMapping} wrapped by this class.
	 */
	private final AttributeMapping mapping;

	/**
	 * The type of the actual mapping.
	 */
	private IMappingType mappingType;

	/**
	 * The parent of this mapping.
	 */
	private final JpaManagedType parent;

	/**
	 * The {@link IType} of the property represented by the mapping.
	 */
	private IType type;

	/**
	 * The {@link ITypeDeclaration} of the property represented by the mapping.
	 */
	private ITypeDeclaration typeDeclaration;

	/**
	 * Creates a new <code>JpaMapping</code>.
	 *
	 * @param parent The parent of this mapping
	 * @param mapping The design-time {@link AttributeMapping} wrapped by this class
	 */
	JpaMapping(JpaManagedType parent, AttributeMapping mapping) {
		super();
		this.parent  = parent;
		this.mapping = mapping;
	}

	private ITypeDeclaration[] buildGenericTypeDeclarations() {
		JavaPersistentAttribute javaPersistentAttribute = mapping.getPersistentAttribute().getJavaPersistentAttribute();
		JavaResourcePersistentAttribute resource = javaPersistentAttribute.getResourcePersistentAttribute();
		List<ITypeDeclaration> declarations = CollectionTools.list(buildGenericTypeDeclarations(resource));
		return declarations.toArray(new ITypeDeclaration[declarations.size()]);
	}

	private Iterator<ITypeDeclaration> buildGenericTypeDeclarations(JavaResourcePersistentAttribute resource) {
		return new TransformationIterator<String, ITypeDeclaration>(resource.typeTypeArgumentNames()) {
			@Override
			protected ITypeDeclaration transform(String next) {
				return getTypeRepository().getType(next).getTypeDeclaration();
			}
		};
	}

	private ITypeDeclaration buildTypeDeclaration() {

		PersistentAttribute property = mapping.getPersistentAttribute();
		boolean array = property.getTypeName().endsWith("[]");
		int dimensionality = 0;

		if (array) {
			dimensionality = getType().getTypeDeclaration().getDimensionality();
		}

		return new JpaTypeDeclaration(
			getType(),
			buildGenericTypeDeclarations(),
			dimensionality
		);
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(IMapping mapping) {
		return getName().compareTo(mapping.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public IMappingType getMappingType() {
		if (mappingType == null) {
			getTypeDeclaration();
			mappingType = mappingType();
		}
		return mappingType;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return mapping.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public IManagedType getParent() {
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	public IType getType() {
		if (type == null) {
			PersistentAttribute property = mapping.getPersistentAttribute();
			type = getTypeRepository().getType(property.getTypeName());
		}
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	public ITypeDeclaration getTypeDeclaration() {
		if (typeDeclaration == null) {
			typeDeclaration = buildTypeDeclaration();
		}
		return typeDeclaration;
	}

	private JpaTypeRepository getTypeRepository() {
		return parent.getProvider().getTypeRepository();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		JavaResourcePersistentAttribute attribute = mapping.getPersistentAttribute().getJavaPersistentAttribute().getResourcePersistentAttribute();
		return attribute.getAnnotation(annotationType.getName()) != null;
	}

	private IMappingType mappingType() {

		String type = mapping.getKey();

		// Basic
		if (type == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.BASIC;
		}

		// Embedded
		if (type == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.EMBEDDED;
		}

		// Embedded Id
		if (type == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.EMBEDDED_ID;
		}

		// Id
		if (type == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.ID;
		}

		// M:M
		if (type == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.MANY_TO_MANY;
		}

		// 1:M
		if (type == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.ONE_TO_MANY;
		}

		// M:1
		if (type == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.MANY_TO_ONE;
		}

		// 1:1
		if (type == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.ONE_TO_ONE;
		}

		// Version
		if (type == MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.VERSION;
		}

		// Element Collection
		if (type == MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY) {
			return IMappingType.ELEMENT_COLLECTION;
		}

		// Basic Collection
//		if (type == EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY) {
//			return IMappingType.BASIC_COLLECTION;
//		}
//
//		// Basic Map
//		if (type == EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY) {
//			return IMappingType.BASIC_MAP;
//		}
//
//		// Transformation
//		if (type == EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY) {
//			return IMappingType.TRANSFORMATION;
//		}
//
//		// Variable 1:1
//		if (type == EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
//			return IMappingType.VARIABLE_ONE_TO_ONE;
//		}

		return IMappingType.TRANSIENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.appendSimpleToString(sb, this);
		sb.append(", name=");
		sb.append(getName());
		sb.append(", mappingType=");
		sb.append(getMappingType());
		return sb.toString();
	}
}