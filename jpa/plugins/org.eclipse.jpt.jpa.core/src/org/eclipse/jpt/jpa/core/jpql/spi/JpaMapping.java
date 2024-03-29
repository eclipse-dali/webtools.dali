/*******************************************************************************
 * Copyright (c) 2011, 2023 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpql.spi;

import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.BASIC;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.ELEMENT_COLLECTION;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.EMBEDDED;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.EMBEDDED_ID;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.ID;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.MANY_TO_MANY;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.MANY_TO_ONE;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.ONE_TO_MANY;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.ONE_TO_ONE;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.TRANSIENT;
import static org.eclipse.persistence.jpa.jpql.tools.spi.IMappingType.VERSION;
import java.lang.annotation.Annotation;
import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.tools.spi.IType;
import org.eclipse.persistence.jpa.jpql.tools.spi.ITypeDeclaration;
import org.eclipse.persistence.jpa.jpql.tools.spi.ITypeRepository;

/**
 * The concrete implementation of {@link IMapping} that is wrapping the design-time representation
 * of a mapping.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public abstract class JpaMapping implements IMapping {

	/**
	 * The design-time {@link AttributeMapping} wrapped by this class.
	 */
	private final AttributeMapping mapping;

	/**
	 * The type of the actual mapping.
	 */
	private int mappingType;

	/**
	 * The parent of this mapping.
	 */
	private final IManagedType parent;

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
	protected JpaMapping(IManagedType parent, AttributeMapping mapping) {
		super();
		this.parent      = parent;
		this.mapping     = mapping;
		this.mappingType = -1;
	}

	protected ITypeDeclaration[] buildGenericTypeDeclarations() {
		JavaSpecifiedPersistentAttribute javaPersistentAttribute = mapping.getPersistentAttribute().getJavaPersistentAttribute();
		JavaResourceAttribute resource = javaPersistentAttribute == null ? null : javaPersistentAttribute.getResourceAttribute();
		List<ITypeDeclaration> declarations = ListTools.arrayList(buildGenericTypeDeclarations(resource));
		return declarations.toArray(new ITypeDeclaration[declarations.size()]);
	}

	protected Iterable<ITypeDeclaration> buildGenericTypeDeclarations(JavaResourceAttribute resource) {
		return (resource != null) ?
				IterableTools.transform(resource.getTypeBinding().getTypeArgumentNames(), new TypeDeclarationTransformer()) :
				IterableTools.<ITypeDeclaration>emptyIterable();
	}

	protected class TypeDeclarationTransformer
		extends TransformerAdapter<String, ITypeDeclaration>
	{
		@Override
		public ITypeDeclaration transform(String typeName) {
			return getTypeRepository().getType(typeName).getTypeDeclaration();
		}
	}

	protected IType buildType(boolean resolveRelationshipType) {

		SpecifiedPersistentAttribute property = mapping.getPersistentAttribute();
		String typeName = property.getTypeName();

		// The attribute could be virtual, incorrectly specified in the orm.xml
		if (typeName == null) {
			return getTypeRepository().getTypeHelper().unknownType();
		}

		// For relationship mapping, make sure to check the target entity first
		if (resolveRelationshipType && isRelationship()) {

			if (mappingType == ELEMENT_COLLECTION) {
				String targetClass = ((ElementCollectionMapping2_0) mapping).getTargetClass();
				if (StringTools.isNotBlank(targetClass)) {
					return getTypeRepository().getType(targetClass);
				}
			}
			else {
				typeName = ((RelationshipMapping) mapping).getFullyQualifiedTargetEntity();

				if (StringTools.isNotBlank(typeName)) {
					IEntity entity = getParent().getProvider().getEntity(typeName);
					if (entity != null) {
						return entity.getType();
					}
				}
			}
		}

		return getTypeRepository().getType(typeName);
	}

	protected ITypeDeclaration buildTypeDeclaration() {

		SpecifiedPersistentAttribute property = mapping.getPersistentAttribute();
		String typeName = property.getTypeName();

		// The attribute could be virtual, incorrectly specified in the orm.xml
		if (typeName == null) {
			return getTypeRepository().getTypeHelper().unknownTypeDeclaration();
		}

		boolean array = typeName.endsWith("[]");
		int dimensionality = 0;

		if (array) {
			dimensionality = getType().getTypeDeclaration().getDimensionality();
		}

		return new JpaTypeDeclaration(
			buildType(false),
			buildGenericTypeDeclarations(),
			dimensionality
		);
	}

	/**
	 * Calculates the type of the persistent attribute represented by this external form.
	 *
	 * @return The mapping type, which is one of the constants defined in {@link org.eclipse.
	 * persistence.jpa.jpql.spi.IMappingType IMappingType} when the provider is generic JPA
	 */
	protected int calculateMappingType() {

		String type = mapping.getKey();

		// Basic
		if (type == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
			return BASIC;
		}

		// Embedded
		if (type == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			return EMBEDDED;
		}

		// Embedded Id
		if (type == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
			return EMBEDDED_ID;
		}

		// Id
		if (type == MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
			return ID;
		}

		// M:M
		if (type == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			return MANY_TO_MANY;
		}

		// 1:M
		if (type == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
			return ONE_TO_MANY;
		}

		// M:1
		if (type == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return MANY_TO_ONE;
		}

		// 1:1
		if (type == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return ONE_TO_ONE;
		}

		// Version
		if (type == MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
			return VERSION;
		}

		// Element Collection
		if (type == MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY) {
			return ELEMENT_COLLECTION;
		}

		return TRANSIENT;
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(IMapping mapping) {
		return getName().compareTo(mapping.getName());
	}

	/**
	 * Returns the design-time mapping object.
	 *
	 * @return The actual {@link AttributeMapping} wrapped by this {@link JpaMapping}
	 */
	protected AttributeMapping getMapping() {
		return mapping;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMappingType() {
		if (mappingType == -1) {
			mappingType = calculateMappingType();
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
			type = buildType(true);
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

	/**
	 * Returns the type repository for the application.
	 *
	 * @return The repository of {@link IType ITypes}
	 */
	protected ITypeRepository getTypeRepository() {
		return parent.getProvider().getTypeRepository();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		JavaResourceAttribute attribute = mapping.getPersistentAttribute().getJavaPersistentAttribute().getResourceAttribute();
		return attribute.getAnnotation(annotationType.getName()) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCollection() {
		switch (getMappingType()) {
			case ELEMENT_COLLECTION:
			case MANY_TO_MANY:
			case ONE_TO_MANY: return true;
			default:          return false;
		}
	}

	public boolean isEmbeddable() {
		switch (getMappingType()) {
			case EMBEDDED:
			case EMBEDDED_ID:
				return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isProperty() {
		switch (getMappingType()) {
			case BASIC:
			case ID:
			case VERSION: return true;
			default:      return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isRelationship() {
		switch (getMappingType()) {
			case ELEMENT_COLLECTION:
			case EMBEDDED_ID:
			case MANY_TO_MANY:
			case MANY_TO_ONE:
			case ONE_TO_MANY:
			case ONE_TO_ONE: return true;
			default:         return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isTransient() {
		return getMappingType() == TRANSIENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, this);
		sb.append(" name=");
		sb.append(getName());
		sb.append(", mappingType=");
		sb.append(mapping.getKey());
		return sb.toString();
	}
}
