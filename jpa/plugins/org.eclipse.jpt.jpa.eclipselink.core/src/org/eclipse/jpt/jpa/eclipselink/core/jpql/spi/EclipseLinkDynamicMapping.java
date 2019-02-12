/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
package org.eclipse.jpt.jpa.eclipselink.core.jpql.spi;

import java.lang.annotation.Annotation;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaTypeDeclaration;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaTypeRepository;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.tools.spi.IType;
import org.eclipse.persistence.jpa.jpql.tools.spi.ITypeDeclaration;

/**
 * The abstract implementation of a {@link IMapping} that supports a dynamic mapping.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.2
 * @since 3.2
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public class EclipseLinkDynamicMapping implements IMapping {

	/**
	 * The default implementation of {@link IMapping}.
	 */
	private EclipseLinkMapping delegate;

	/**
	 * The parent of this mapping.
	 */
	private EclipseLinkDynamicManagedType parent;

	/**
	 * The {@link IType} of the property represented by the mapping.
	 */
	private IType type;

	/**
	 * The {@link ITypeDeclaration} of the property represented by the mapping.
	 */
	private ITypeDeclaration typeDeclaration;

	/**
	 * Creates a new <code>EclipseLinkDynamicMapping</code>.
	 *
	 * @param parent The parent of this mapping
	 * @param delegate The default implementation of {@link IMapping}
	 */
	public EclipseLinkDynamicMapping(EclipseLinkDynamicManagedType parent,
	                                 EclipseLinkMapping delegate) {

		super();
		this.parent   = parent;
		this.delegate = delegate;
	}

	/**
	 * Creates the list of {@link ITypeDeclaration type declarations} that represents the generics of
	 * the type.
	 *
	 * @return The list of {@link ITypeDeclaration type declarations} or an empty list if the type is
	 * not parameterized
	 */
	protected ITypeDeclaration[] buildGenericTypeDeclarations() {

		if (delegate.isRelationship() ||
		    delegate.isCollection()) {

			RelationshipMapping mapping = (RelationshipMapping) delegate.getMapping();
			Entity targetEntity = mapping.getResolvedTargetEntity();

			if (targetEntity != null) {
				IEntity entity = parent.getProvider().getEntityNamed(targetEntity.getName());
				return new ITypeDeclaration[] { entity.getType().getTypeDeclaration() };
			}
		}

		return new ITypeDeclaration[0];
	}

	/**
	 * Creates the right {@link IType} and make sure it checks for dynamic type.
	 *
	 * @return The {@link IType} of this dynamic mapping
	 */
	protected IType buildType() {

		if (delegate.isCollection() ||
		    delegate.isRelationship()) {

			IManagedType managedType = parent.getProvider().getManagedType(getTypeName());

			if (managedType != null) {
				return managedType.getType();
			}
		}

		return getTypeRepository().getType(getTypeName());
	}

	/**
	 * Creates the right {@link ITypeDeclaration} and make sure it checks for dynamic type.
	 *
	 * @return The {@link ITypeDeclaration} of this dynamic mapping
	 */
	protected ITypeDeclaration buildTypeDeclaration() {

		String typeName = getTypeName();
		int dimensionality = 0;

		if (StringTools.isNotBlank(typeName)) {
			int index = typeName.indexOf("[]");
			dimensionality = (typeName.length() - index) >> 1;
		}

		return new JpaTypeDeclaration(getType(), buildGenericTypeDeclarations(), dimensionality);
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(IMapping mapping) {
		return delegate.compareTo(mapping);
	}

	/**
	 * Returns the mapping's attribute (typically its parent node in the containment hierarchy).
	 *
	 * @return The {@link SpecifiedPersistentAttribute}
	 */
	public SpecifiedPersistentAttribute getAttribute() {
		return delegate.getMapping().getPersistentAttribute();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMappingType() {
		return delegate.getMappingType();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return delegate.getName();
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
			type = buildType();
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
	 * Returns the type name of the persistent attribute.
	 *
	 * @return The fully qualified type name
	 */
	public String getTypeName() {
		return getAttribute().getTypeName();
	}

	/**
	 * Returns the type repository for the application.
	 *
	 * @return The repository of {@link IType ITypes}
	 */
	protected JpaTypeRepository getTypeRepository() {
		return parent.getProvider().getTypeRepository();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return delegate.hasAnnotation(annotationType);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCollection() {
		return delegate.isCollection();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isProperty() {
		return delegate.isProperty();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isRelationship() {
		return delegate.isRelationship();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isTransient() {
		return delegate.isTransient();
	}
}